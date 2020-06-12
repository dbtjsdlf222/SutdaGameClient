package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import operator.ChattingOperator;
import util.Packing;
import vo.PlayerVO;
import vo.Protocol;

public class Lobby {
	private JFrame lobbyJF = new JFrame();
	private Background imgP;
	private Container con;
	private JButton exitBtn;
	private JButton newBtn;
	private JButton gBtn;
	private ClientPacketSender cp;
	public JButton[] bt1 = new JButton[10];

	public Lobby(PlayerVO vo) { // 서버에 로그인된 사람의 정보를 전송
		cp = new ClientPacketSender(vo.getSocket(), vo);
		Packing.sender(vo.getPwSocket(), Protocol.ENTERLOBBY, vo);
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		// 로비 라벨
		JLabel lobbyLbl = new JLabel("L O B B Y");
		lobbyLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lobbyLbl.setForeground(new Color(255, 255, 255, 150));
		lobbyLbl.setBounds(340, 20, 200, 30);
		lobbyJF.add(lobbyLbl);

		// 로비 판넬 [방리스트]
		ClientPacketController.lobbyPan.setBackground(new Color(0, 0, 0, 120));
		ClientPacketController.lobbyPan.setBounds(5, 60, 818, 290);
		ClientPacketController.lobbyPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.lobbyPan.setLayout(new GridLayout(99, 1, 0, 0));
		lobbyJF.add(ClientPacketController.lobbyPan);

		// 방만들기 버튼
		newBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/newBtn.PNG")));
		newBtn.setBounds(681, 515, 150, 50);
		lobbyJF.add(newBtn);

		newBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == newBtn) {
					cp.makeRoom();
					new MainScreen();
					lobbyJF.dispose();
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
		JButton chatBtn = new JButton("../../img/Send.PNG");
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

		
		
		ClientPacketController cl = new ClientPacketController();
		
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = ClientPacketController.jT.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		//Header을 중앙에 배치
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)ClientPacketController.jT.getTableHeader()
				.getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		ClientPacketController.jT.getTableHeader().setDefaultRenderer(renderer);
		
		ClientPacketController.jT.getTableHeader().setBackground(Color.orange);
		ClientPacketController.jT.setShowVerticalLines(false);
		ClientPacketController.jT.setSelectionBackground(new Color(232,57,95));
		
		
		
		ClientPacketController.jT.setRowHeight(40);
		ClientPacketController.jT.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		ClientPacketController.jT.getTableHeader().setReorderingAllowed(false);
		ClientPacketController.jT.getTableHeader().setResizingAllowed(false);
		ClientPacketController.plScroll.getViewport().setBackground(new Color(0, 0, 0, 0));
		ClientPacketController.plScroll.setOpaque(false);
		ClientPacketController.plScroll.setBounds(10, 10, 370, 590);
		ClientPacketController.plScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.plScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		playerPan.add(ClientPacketController.plScroll);
		initialize(); // 초기화

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