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

public class Lobby extends JFrame implements ActionListener {
		private JButton newBtn;
		private JButton exitBtn;
		private Background back = new Background();
		private Container content;
	
	
	public Lobby(PlayerVO vo) {
		vo.setLocation(Protocol.LOBBY);

		// 서버에 로그인된 사람의 정보를 전송
		try {
			vo.getPwSocket().println(new ObjectMapper().writeValueAsString(new Packet(Protocol.JOINPLAYER, vo)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		back.lobbyImage();
		content = getContentPane();    
		content.add(back, BorderLayout.CENTER);
		setResizable(false);
		setSize(1280, 720);
		setBackground(Color.black);
		setLayout(null);

				//방만들기 버튼
				newBtn = new JButton("방만들기");
				newBtn.setBounds(1103, 564, 150, 50);
				add(newBtn);
				
				newBtn.addActionListener(this);
				
				//나가기 버튼
				exitBtn = new JButton("나가기");
				exitBtn.setBounds(1103, 624, 150, 50);
				add(exitBtn);
				
				exitBtn.addActionListener(this);
					
				
				
		
		// 로비 접속자 목록
		JTextArea tArea = new JTextArea();
		JScrollPane plScroll = new JScrollPane(tArea);
		ArrayList<PlayerVO> playerList = vo.getLoctionList(Protocol.LOBBY);

		plScroll.setBackground(Color.white);
		plScroll.setBounds(730, 10, 525, 380);
		plScroll.setBorder(new TitledBorder(new LineBorder(Color.red), "플 레 이 어 리 스 트"));
		add(plScroll);

		for (int i = 0; i < playerList.size(); i++) {
			tArea.setText("닉네임 : " + vo.getNic() + "　판수 : " + (vo.getLose()+vo.getLose()) + "" + "　승리 : " + vo.getWin() + "" + "　돈 : "
					+ vo.getMoney() + "");
		}

		// 방 목록
		RoomOperator.getRoomOperator().getAllRoom();
		JPanel lobbypan = new JPanel();
		lobbypan.setBackground(Color.white);
		lobbypan.setLayout(null);
		lobbypan.setBounds(5, 10, 718, 440);
		lobbypan.setBorder(new TitledBorder(new LineBorder(Color.red), "로 비 리 스 트"));
		add(lobbypan);
		
		
		
		

		// 채팅방
		JPanel chatPan = new JPanel();
		chatPan.setBounds(5, 455, 718, 220);
		chatPan.setLayout(null);
		add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.red), "채 팅"));

		JTextField chatText = new JTextField();
		chatText.setBounds(6, 191, 635, 25);
		chatPan.add(chatText);
		chatText.requestFocus();

		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);

		chatPan.add(ReceiveServerPacket.scrollPane);

		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(643, 191, 70, 25);
		chatPan.add(chatBtn);
		JRootPane rootPane = getRootPane();
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

		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitBtn) {
			dispose();
		}else if (e.getSource() == newBtn) {
			
		}
		
		
	}
}