package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Server.OrderType;
import vo.Packet;
import vo.PlayerVOsunil;

public class Lobby {
	private JTextField textField;
	private PlayerVOsunil pvo;
	private ArrayList<PlayerVOsunil> playerList = new ArrayList<>();
	
	public Lobby() {
		
	}
	
	public void roomList(PlayerVOsunil vo) {
		this.pvo = vo;
		
		try {
			//객체 포장원 (전송 최적화 포장을 해줌)
			ObjectMapper mapper = new ObjectMapper();
			
			//메세지
			String msg = "안녕?";
			
			//어떤 택배인지 옷인지 컴퓨터인지 가정제품인지 표기
			Packet pac = new Packet(OrderType.MESSAGE, msg);	
			String m;
			
			//
			m = mapper.writeValueAsString(pac);
			
			//네비게이션
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			//packet 싣고 > 네비 찍는놈
			pw.println(m);
			
			//출발
			pw.flush();
			
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		try {
			socket = new Socket("192.168.0.19",4888);
		}
		catch (UnknownHostException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		
		JFrame roList = new JFrame();
		roList.setSize(800, 950);
		roList.setLocation(600, 50);
		roList.setLayout(null);

		JTextField chatText = new JTextField();
		chatText.setBounds(0, 887, 450, 25);
		roList.add(chatText);
		chatText.requestFocus();
		
		JTextArea chatArea = new JTextArea();
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
					chatArea.append(chatText.getText() + "\n");
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

	public static void main(String[] args) {
		Lobby list = new Lobby();
		list.roomList();

	}

}
