package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.RoomOperator;
import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class Lobby extends JFrame {

	public Lobby(PlayerVO vo) {
		vo.setLocation(Protocol.Lobby);

		// 서버에 로그인된 사람의 정보를 전송
		try {
			vo.getPwSocket().println(new ObjectMapper().writeValueAsString(new Packet(Protocol.JOINPLAYER, vo)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {

		setResizable(false);
		setSize(800, 950);
		setBackground(Color.black);
		setLayout(null);

		
		
		// 로비 접속자 목록
		JTextArea tArea = new JTextArea();
		JScrollPane plScroll = new JScrollPane(tArea);
		ArrayList<PlayerVO> playerList = vo.getLoctionList(Protocol.Lobby);

		plScroll.setBackground(Color.white);
		plScroll.setBounds(530, 10, 240, 580);
		plScroll.setBorder(new TitledBorder(new LineBorder(Color.red), "플 레 이 어 리 스 트"));
		add(plScroll);

		for (int i = 0; i < playerList.size(); i++) {
			tArea.setText("닉네임 : " + vo.getID() + "　승리 : " +  vo.getWin()+"" + "　패배 : " + vo.getLose()+"" + "　돈 : " + vo.getMoney()+"");
		}

	
		
		
		// 방 목록
		RoomOperator.getRoomOperator().getAllRoom();
		JPanel lobbypan = new JPanel();
		lobbypan.setBackground(Color.white);
		lobbypan.setLayout(null);
		lobbypan.setBounds(0, 10, 518, 580);
		lobbypan.setBorder(new TitledBorder(new LineBorder(Color.red), "로 비 리 스 트"));
		add(lobbypan);

	
		
		
		
		// 채팅방
		JPanel chatPan = new JPanel();
		chatPan.setBounds(0, 592, 518, 320);
		chatPan.setLayout(null);
		add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.red), "채 팅"));

		JTextField chatText = new JTextField();
		chatText.setBounds(6, 291, 435, 25);
		chatPan.add(chatText);
		chatText.requestFocus();

		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBounds(6, 15, 508, 275);
		chatPan.add(scrollPane);

		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(443, 291, 70, 25);
		chatPan.add(chatBtn);
		JRootPane rootPane = getRootPane();
		rootPane.setDefaultButton(chatBtn);
		chatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					co.chatting(chatText.getText(), vo);
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					chatText.requestFocus();
					chatText.setText("");
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}