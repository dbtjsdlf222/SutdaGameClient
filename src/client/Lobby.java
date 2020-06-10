package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import operator.ChattingOperator;
import operator.RoomOperator;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Lobby {
	public static JTextArea tArea = new JTextArea();
	JFrame lobbyJF = new JFrame();
	Background imgP;
	Container con;
	JButton newBtn;

	public Lobby(PlayerVO vo) {
		vo.setLocation(Protocol.LOBBY);

		// 서버에 로그인된 사람의 정보를 전송
		try {
			vo.getPwSocket().println(new ObjectMapper().writeValueAsString(new Packet(Protocol.CHANGELOCATION, vo)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		// 로비 라벨
		JLabel lobbyLbl = new JLabel("L O B B Y");
		lobbyLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lobbyLbl.setForeground(new Color(255, 255, 255, 150));
		lobbyLbl.setBounds(340, 20, 200, 30);
		lobbyJF.add(lobbyLbl);

		// 로비 판넬
		JPanel lobbyPan = new JPanel();
		lobbyPan.setBackground(new Color(0, 0, 0, 120));
		lobbyPan.setLayout(null);
		lobbyPan.setBounds(5, 60, 818, 290);
		lobbyPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		lobbyJF.add(lobbyPan);

		// 채팅 라벨
		JLabel lbl = new JLabel("C H A T T I N G");
		lbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lbl.setForeground(new Color(255, 255, 255, 150));
		lbl.setBounds(300, 370, 280, 30);
		lobbyJF.add(lbl);
		// 채팅 판넬
		JPanel chatPan = new JPanel();
		chatPan.setBounds(5, 410, 718, 260);
		chatPan.setBackground(new Color(0, 0, 0, 120));
		chatPan.setLayout(null);
		lobbyJF.add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		// 채팅 필드
		JTextField chatText = new JTextField();
		chatText.setBounds(10, 225, 620, 25);
		chatPan.add(chatText);
		chatText.requestFocus();
		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);
		chatPan.add(ReceiveServerPacket.scrollPane);
		ReceiveServerPacket.scrollPane.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		// 채팅 보내기 버튼
		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(638, 225, 70, 25);
		chatPan.add(chatBtn);
		JRootPane rootPane = lobbyJF.getRootPane();
		rootPane.setDefaultButton(chatBtn);
		chatBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					if (!(chatText.getText().equals(""))) {
						co.chatting(chatText.getText(), vo);
						chatText.requestFocus();
						chatText.setText("");
					}
				}
			}
		});

		// 로비 라벨
		JLabel playerLbl = new JLabel("P L A Y E R");
		playerLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		playerLbl.setForeground(new Color(255, 255, 255, 150));
		playerLbl.setBounds(970, 20, 200, 30);
		lobbyJF.add(playerLbl);

		JPanel playerPan = new JPanel();
		playerPan.setBounds(850, 60, 390, 290);
		playerPan.setBackground(new Color(0, 0, 0, 120));
		playerPan.setLayout(null);
		lobbyJF.add(playerPan);
		playerPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));

		// 로비 접속자 목록

		JScrollPane plScroll = new JScrollPane(tArea);
		tArea.setEditable(false);

		plScroll.setBounds(10, 10, 370, 270);
		plScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		playerPan.add(plScroll);


		// JFrame 정보
		imgP = new Background();
		imgP.lobbyImage();
		con = lobbyJF.getContentPane();
		con.add(imgP, BorderLayout.CENTER);
		lobbyJF.setSize(1280, 720);
		lobbyJF.setVisible(true);
		lobbyJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}