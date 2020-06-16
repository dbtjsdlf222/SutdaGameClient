package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.table.TableColumnModel;

import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import operator.ChattingOperator;
import server.Room;
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
	Room value;

	public Lobby(PlayerVO vo) { // 서버에 로그인된 사람의 정보를 전송
		System.out.println("lobby 만들어졌따");
		ClientPacketSender.instance.enterLobby();
//		Packing.sender(vo.getPwSocket(), Protocol.ENTERLOBBY, vo);
		lobbyScreen(vo);
	}

	public void lobbyScreen(PlayerVO vo) {
		// 로비 라벨
		JLabel lobLbl = new JLabel("L O B B Y");
		lobLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lobLbl.setForeground(new Color(255, 255, 255, 150));
		lobLbl.setBounds(340, 20, 200, 30);
		lobbyJF.add(lobLbl);

		// 로비 판넬 [방리스트]
		ClientPacketController.rlobbyPan.setBackground(new Color(0, 0, 0, 120));
		ClientPacketController.rlobbyPan.setBounds(5, 60, 818, 290);
		ClientPacketController.rlobbyPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.rlobbyPan.setLayout(null);
		lobbyJF.add(ClientPacketController.rlobbyPan);

		// 중앙으로 배치
		DefaultTableCellRenderer lobRenderer = new DefaultTableCellRenderer();
		lobRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel lobTcmSchedule = ClientPacketController.roomJT.getColumnModel();
		for (int i = 0; i < lobTcmSchedule.getColumnCount(); i++) {
			lobTcmSchedule.getColumn(i).setCellRenderer(lobRenderer);
		}

		// Header을 중앙에 배치
		DefaultTableCellRenderer lobHRenderer = (DefaultTableCellRenderer) ClientPacketController.roomJT
				.getTableHeader().getDefaultRenderer();
		lobHRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ClientPacketController.roomJT.getTableHeader().setDefaultRenderer(lobHRenderer);

		ClientPacketController.roomJT.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					ClientPacketSender.instance.enterRoom(Integer
							.parseInt(ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][0]));

					System.out.println("들어가는숫자 : " + (Integer
							.parseInt(ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][0])));
					RoomScreen.instance.mainScreen();
					lobbyJF.dispose();
				}
			}
		});

		ClientPacketController.roomJT.getTableHeader().setBackground(Color.orange);
		ClientPacketController.roomJT.setShowVerticalLines(false);
		ClientPacketController.roomJT.setSelectionBackground(new Color(232, 57, 95));
		ClientPacketController.roomJT.setRowHeight(40);
		ClientPacketController.roomJT.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		ClientPacketController.roomJT.getTableHeader().setReorderingAllowed(false);
		ClientPacketController.roomJT.getTableHeader().setResizingAllowed(false);
		ClientPacketController.rlScroll.getViewport().setBackground(new Color(0, 0, 0));
		ClientPacketController.rlScroll.setOpaque(false);
		ClientPacketController.rlScroll.setBounds(10, 10, 798, 270);
		ClientPacketController.rlScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.rlScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.rlobbyPan.add(ClientPacketController.rlScroll);

		// 방만들기 버튼
		newBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/newBtn.PNG")));
		newBtn.setBounds(681, 515, 150, 50);
		lobbyJF.add(newBtn);

		newBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == newBtn) {
					ClientPacketSender.instance.makeRoom();
					RoomScreen.instance.mainScreen();
					lobbyJF.dispose();
				}
			} // action
		}); // listener

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
		chatText.setFont(new Font("휴먼옛체", Font.PLAIN, 13));
		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);
		ChattingOperator.chatArea.setLineWrap(true);
		ChattingOperator.chatArea.setFont(new Font("휴먼옛체", Font.PLAIN, 15));

		ClientPacketController.scrollPane.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.scrollPane.setBounds(10, 15, 638, 195);
		ClientPacketController.scrollPane.setBorder(null);
		chatPan.add(ClientPacketController.scrollPane);

		// 채팅 보내기 버튼
		JButton chatBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/Send.PNG")));
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

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = ClientPacketController.playerJT.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		// Header을 중앙에 배치
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ClientPacketController.playerJT.getTableHeader()
				.getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		ClientPacketController.playerJT.getTableHeader().setDefaultRenderer(renderer);

		ClientPacketController.playerJT.getTableHeader().setBackground(Color.orange);
		ClientPacketController.playerJT.setShowVerticalLines(false);
		ClientPacketController.playerJT.setSelectionBackground(new Color(232, 57, 95));

		ClientPacketController.playerJT.setRowHeight(40);
		ClientPacketController.playerJT.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		ClientPacketController.playerJT.getTableHeader().setReorderingAllowed(false);
		ClientPacketController.playerJT.getTableHeader().setResizingAllowed(false);
		ClientPacketController.plScroll.getViewport().setBackground(new Color(0, 0, 0));
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

		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == exitBtn) {
					System.exit(0);
				}
			}
		});

		// JFrame 정보
		imgP = new Background();
		imgP.lobbyImage();
		con = lobbyJF.getContentPane();
		con.add(imgP, BorderLayout.CENTER);
		lobbyJF.setSize(1280, 720);
//		lobbyJF.setUndecorated(true);
		lobbyJF.setVisible(true);
		lobbyJF.setResizable(false);
		lobbyJF.setLocationRelativeTo(null);
		lobbyJF.setLayout(null);
		lobbyJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initialize() {
	}
}