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
	private JTextField textField;
	private PlayerVO pvo;
	private ArrayList<PlayerVO> playerList = new ArrayList<>();

	public Lobby(PlayerVO vo) {
		this.pvo = vo;
		vo.setLocation(Protocol.Lobby);
		try {
			vo.setSocketWithBrPw(new Socket("127.0.0.1", 4888));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		//로비 접속자 목록
		PlayerVO.getLoctionList(Protocol.Lobby);
		
		//방 목록
		RoomOperator.getRoomOperator().getAllRoom();
		
		this.pvo = vo;
		
		JFrame roList = new JFrame();
		roList.setResizable(false);
		roList.setSize(800, 950);
		roList.setLocation(600, 50);
		roList.setLayout(null);

	
		//채팅방
		JTextField chatText = new JTextField();
		chatText.setBounds(0, 887, 450, 25);
		roList.add(chatText);
		chatText.requestFocus();
		
		ChattingOperator co = ChattingOperator.getInstance();
		
		JTextArea chatArea = co.chatArea;
		chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 609, 518, 280);
		roList.add(scrollPane);
		
		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(448, 887, 70, 24);
		roList.add(chatBtn);
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
		
		JRootPane  rootPane  =  roList.getRootPane();
        rootPane.setDefaultButton(chatBtn);

		roList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roList.setVisible(true);
	}
}