package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Server.RoomOperator;
import vo.Protocol;
import vo.PlayerVO;

public class Lobby {

	public Lobby(PlayerVO vo) {
		vo.setLocation(Protocol.Lobby);
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		//로비 접속자 목록
		vo.getLoctionList(Protocol.Lobby);
		
		//방 목록
		RoomOperator.getRoomOperator().getAllRoom();
		
		JFrame lobbyFrame = new JFrame();
		lobbyFrame.setResizable(false);
		lobbyFrame.setSize(800, 950);
		lobbyFrame.setLocation(600, 50);
		lobbyFrame.setLayout(null);

	 
		//채팅방
		JTextField chatText = new JTextField();
		chatText.setBounds(0, 887, 450, 25);
		lobbyFrame.add(chatText);
		chatText.requestFocus();
		
		ChattingOperator co = ChattingOperator.getInstance();
		
		ChattingOperator.chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ChattingOperator.chatArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 609, 518, 280);
		lobbyFrame.add(scrollPane);
		
		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(448, 887, 70, 24);
		lobbyFrame.add(chatBtn);
		chatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					co.chatting(chatText.getText(), vo);
					chatText.requestFocus();
					chatText.setText("");
				}
			}
		});
		
		JRootPane  rootPane  =  lobbyFrame.getRootPane();
        rootPane.setDefaultButton(chatBtn);

		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lobbyFrame.setVisible(true);
	}
}