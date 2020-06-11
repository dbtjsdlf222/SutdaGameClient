package client.ui;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Background;
import client.service.ClientPacketController;
import operator.ChattingOperator;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

public class Lobby {
	private JFrame lobbyJF = new JFrame();
	private Background imgP;
	private Container con;
	private JButton exitBtn;
	private JButton newBtn;
	private JButton gBtn;
	public static JTable jT = new JTable();
	public static JScrollPane plScroll = new JScrollPane(jT);
	public static DefaultTableModel model;
	public JButton[] bt1 = new JButton[10];

	
	public Lobby(PlayerVO vo) {  // 서버에 로그인된 사람의 정보를 전송
		try {
			ObjectMapper map = new ObjectMapper();
			vo.getPwSocket().println(map.writeValueAsString(new Packet(Protocol.ENTERLOBBY, vo)));
		} catch (JsonProcessingException e) {
			System.out.println("Dwdwadawdafasfas");
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
		lobbyPan.setBounds(5, 60, 818, 290);
		lobbyPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		lobbyPan.setLayout(new GridLayout(5,0));
		lobbyJF.add(lobbyPan);
		
		
		
			
		
		// 방만들기 버튼
				newBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/newBtn.PNG")));
				newBtn.setBounds(681, 515, 150, 50);
				lobbyJF.add(newBtn);
				
				newBtn.addActionListener(new ActionListener() {

			
					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == newBtn) {
							int i = 0; 
						bt1[i] = new JButton(i+"번 방　　　　　4/5　　　　　　　입장");
						bt1[i].setSize(200, 200);
						lobbyPan.add(bt1[i]);
							i++;
							new MainScreen();
						//	lobbyJF.dispose();
						}

					}
				
				});



		
		
		
		// 채팅 라벨
		JLabel lbl = new JLabel("C H A T T I N G");
		lbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lbl.setForeground(new Color(255, 255, 255, 150));
		lbl.setBounds(300, 370, 280, 30);
		lobbyJF.add(lbl);
	
		// 채팅 판넬
		JPanel chatPan = new JPanel();
		chatPan.setBounds(5, 410, 658, 260);
		chatPan.setBackground(new Color(0, 0, 0, 120));
		chatPan.setLayout(null);
		lobbyJF.add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		
		// 채팅 필드
		JTextField chatText = new JTextField();
		chatText.setBounds(10, 225, 560, 25);
		chatPan.add(chatText);
		chatText.requestFocus();
		chatText.setFont(new Font("Rosewood Std", Font.PLAIN, 12));
		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);
		ChattingOperator.chatArea.setLineWrap(true);
		ClientPacketController.scrollPane.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.scrollPane.setBounds(10, 15, 638, 195);
		ClientPacketController.scrollPane.setBorder(null);
		chatPan.add(ClientPacketController.scrollPane);

	
	      // 채팅 보내기 버튼
		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(578, 225, 70, 25);
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

		// 플레이어 라벨
		JLabel playerLbl = new JLabel("P L A Y E R");
		playerLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		playerLbl.setForeground(new Color(255, 255, 255, 150));
		playerLbl.setBounds(970, 20, 200, 30);
		lobbyJF.add(playerLbl);
	
		// 플레이어 판넬
		JPanel playerPan = new JPanel();
		playerPan.setBounds(850, 60, 390, 610);
		playerPan.setBackground(new Color(0, 0, 0, 120));
		playerPan.setLayout(null);
		lobbyJF.add(playerPan);
		playerPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));

	
		
		// 로비에 플레이어 접속자 목록

		String b[] =  {"닉네임", "판수", "돈"};
		String n[][] = new String[99][99];
 		
		
		
		model = new DefaultTableModel(b, 0);
		
		
	
//		model.addRow();
//		jT = new JTable(model);
//		plScroll = new JScrollPane(jT);
		
		plScroll.getViewport().setBackground(new Color(0, 0, 0, 0));
		jT.getTableHeader().setReorderingAllowed(false); 
		jT.getTableHeader().setResizingAllowed(false);
		plScroll.setOpaque(false);
		plScroll.setBounds(10, 10, 370, 590);
		plScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		playerPan.add(plScroll);
		initialize();
		
				
		// 귓속말 버튼
		gBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/gBtn.PNG")));
		gBtn.setBounds(681, 410, 150, 50);
		lobbyJF.add(gBtn);

		

		// 나가기 버튼
		exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/exitBtn.PNG")));
		exitBtn.setBounds(681, 620, 150, 50);
		lobbyJF.add(exitBtn);

		
		// JFrame 정보
		imgP = new Background();
		imgP.lobbyImage();
		con = lobbyJF.getContentPane();
		con.add(imgP, BorderLayout.CENTER);
		lobbyJF.setSize(1280, 720);
		lobbyJF.setVisible(true);
		lobbyJF.setResizable(false);
		lobbyJF.setLocationRelativeTo(null);
		lobbyJF.setLayout(null);
		lobbyJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	private void initialize() {
		// TODO Auto-generated method stub
		
	}

}