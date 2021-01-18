package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import operator.ChattingOperator;
import util.MoneyFormat;
import vo.PlayerVO;

public class Lobby {
	private static Lobby instance;
	private JFrame lobbyJF;
	private Background imgP;
	private Container con;
	private JButton exitBtn;
	private JButton newBtn;
	private JPanel infoPan;
	private JTextField chatText;
	private int chatInputMaximum = 30;
	public static JLabel infoMoneyLbl = new JLabel(new ImageIcon(Lobby.class.getResource("/img/infoMoney.png")));
	
	private Lobby() {};
	
	public static Lobby getInstance() {
		if (instance == null)
			instance = new Lobby();
		return instance;
	} //getInstance();

	private static boolean initializeOnce = false;

	public void lobbyScreen() {
		lobbyJF = new JFrame();
		PopMenu();
		System.out.println(PlayerVO.myVO.getMoney());
		//돈이 10만원 밑이면 충전
		if(PlayerVO.myVO.getMoney()<10) {
			ClientPacketSender.instance.extraMoney();
		}
		 
		
		// 로비 라벨
		JLabel lobLbl = new JLabel("L O B B Y");
		lobLbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lobLbl.setForeground(new Color(255, 255, 255, 150));
		lobLbl.setBounds(340, 20, 200, 30);
		lobbyJF.add(lobLbl);

		// 로비 패널 [방리스트]
		ClientPacketController.rlobbyPan.setBackground(new Color(63, 28, 6, 120));
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

		if (!initializeOnce) {
			initializeOnce = true;
			ClientPacketController.roomJT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			ClientPacketController.roomJT.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) {
						if(!MakeRoom.getInstance().isMaking()) {
							if (ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][5]
									.equals("게임중")) {
								JOptionPane.showMessageDialog(null, "게임이 끝날 때까지 기다려 주세요. ", "알림",
										JOptionPane.ERROR_MESSAGE);
								return;
							}else if(PlayerVO.myVO.getMoney() < MoneyFormat.reformat(ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][3])) {
								JOptionPane.showMessageDialog(null, "판돈이 부족합니다.", "알림",
										JOptionPane.ERROR_MESSAGE);
								return;
							}else if(checkSit() != 1 ) {
								JOptionPane.showMessageDialog(null, "인원수 초과로 인해 입장하실 수 없습니다.", "알림",
								JOptionPane.ERROR_MESSAGE);
								return;
							}else if (ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][1]
									.equals("비공개")) {
								PasswordInput.getInstance().setNo(Integer.parseInt(
										ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][0]));
								PasswordInput.getInstance().getIn();
							}else {
								ClientPacketSender.instance.enterRoom(Integer.parseInt(
										ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][0]));
								((JFrame)SwingUtilities.getRoot((Component)e.getSource())).dispose();
							}
						}
					}//if(e.getClickcount());
				}//mouseClicked();
			});
		} //if(!initializeOnce);

		ClientPacketController.roomJT.getTableHeader().setBackground(Color.orange);
		ClientPacketController.roomJT.setShowVerticalLines(false);
		ClientPacketController.roomJT.setSelectionBackground(new Color(232, 57, 95));
		ClientPacketController.roomJT.setRowHeight(40);
		ClientPacketController.roomJT.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		ClientPacketController.roomJT.getTableHeader().setReorderingAllowed(false);
		ClientPacketController.roomJT.getTableHeader().setResizingAllowed(false);
		ClientPacketController.rlScroll.getViewport().setBackground(new Color(63, 28, 6));
		ClientPacketController.rlScroll.setOpaque(false);
		ClientPacketController.rlScroll.setBounds(10, 10, 798, 270);
		ClientPacketController.rlScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.rlScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.rlobbyPan.add(ClientPacketController.rlScroll);

		
		//인포라벨
		JLabel infolbl = new JLabel("I N F O");
		infolbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		infolbl.setForeground(new Color(255, 255, 255, 150));
		infolbl.setBounds(1000, 370, 280, 30);
		lobbyJF.add(infolbl);
		
		//인포 패널
		infoPan = new JPanel();
		infoPan.setBounds(850, 410, 390, 260);
		infoPan.setBackground(new Color(63, 28, 6));
		infoPan.setBorder(new LineBorder(Color.orange, 3));
		infoPan.setLayout(null);
		lobbyJF.add(infoPan);
		try {
			ClientPacketController.userCha = new JLabel(new ImageIcon(Lobby.class.getResource("/img/character/cha" + PlayerVO.myVO.getCha() + ".png")));
		} catch (Exception e2) {
			e2.printStackTrace();
		} 			
		ClientPacketController.userCha.setBorder(new LineBorder(new Color(235, 209, 192), 3));
		infoPan.add(ClientPacketController.userCha);
		ClientPacketController.userCha.setBounds(30, 50, 80, 100);
		ClientPacketController.userCha.setOpaque(false);
		
		//인포 닉네임 출력
		JLabel infoNicLbl = new JLabel();
		infoNicLbl.setText(PlayerVO.myVO.getNic());
		infoNicLbl.setBounds(130, 30, 200, 30);
		infoNicLbl.setBackground(new Color(63, 28, 6));
		infoNicLbl.setForeground(new Color(219, 182, 155));
		infoNicLbl.setHorizontalAlignment(JLabel.CENTER);
		infoNicLbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 22));
		infoNicLbl.setOpaque(true);
		infoPan.add(infoNicLbl);
	
		//인포 머니 출력
		infoMoneyLbl.setOpaque(true);
		infoMoneyLbl.setBackground(new Color(63, 28, 6));
		infoMoneyLbl.setForeground(new Color(163, 95, 56));
		infoMoneyLbl.setText("머니 : " +MoneyFormat.format(PlayerVO.myVO.getMoney()));
		infoMoneyLbl.setHorizontalAlignment(JLabel.LEFT);
		infoMoneyLbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		infoMoneyLbl.setBounds(120, 70, 250,30);
		infoPan.add(infoMoneyLbl);
		
		//인포 전적 출력
		JLabel infoRatingLbl = new JLabel(new ImageIcon(Lobby.class.getResource("/img/Rating.png")));
		infoRatingLbl.setOpaque(true);
		infoRatingLbl.setBackground(new Color(63, 28, 6));
		infoRatingLbl.setForeground(new Color(163, 95, 56));
		infoRatingLbl.setText("전적 : " + PlayerVO.myVO.getWin()+"승 "+ PlayerVO.myVO.getLose() + "패");
		infoRatingLbl.setHorizontalAlignment(JLabel.LEFT);
		infoRatingLbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		infoRatingLbl.setBounds(120, 110, 230,30);
		infoPan.add(infoRatingLbl);

		// 채팅 라벨
		JLabel lbl = new JLabel("C H A T T I N G");
		lbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		lbl.setForeground(new Color(255, 255, 255, 150));
		lbl.setBounds(300, 370, 280, 30);
		lobbyJF.add(lbl);

		// 채팅 패널
		JPanel chatPan = new JPanel();
		chatPan.setBounds(5, 410, 818, 260);
		chatPan.setBackground(new Color(0, 0, 0, 120));
		chatPan.setLayout(null);
		lobbyJF.add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));

		// 채팅 필드
		chatText = new JTextField();
		chatText.setBounds(10, 225, 720, 25);
		
	    // 채팅 입력 리스너
		chatText.addKeyListener(new KeyAdapter() {
	    	public void keyTyped(KeyEvent e) {
	    		if(e.getSource()==chatText) {
		    		if(chatText.getText().length() > chatInputMaximum) {
		    		  		e.consume();
	    		  	}//if~else
    			}
    	  	}//keyTyped();
	     });//addKeyListener();
		
		chatPan.add(chatText);
		chatText.requestFocus();
		chatText.setFont(new Font("휴먼옛체", Font.PLAIN, 13));
		ChattingOperator co = ChattingOperator.getInstance();
		ChattingOperator.chatArea.setEditable(false);
		ChattingOperator.chatArea.setLineWrap(true);
		ChattingOperator.chatArea.setFont(new Font("휴먼옛체", Font.PLAIN, 20));
		ChattingOperator.chatArea.setBackground(new Color(63, 28, 6));
		ChattingOperator.chatArea.setForeground(Color.white);
		ChattingOperator.chatArea.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.scrollPane.setBounds(10, 10, 798, 205);
		ClientPacketController.scrollPane.setBorder(null);
		chatPan.add(ClientPacketController.scrollPane);

		// 채팅 보내기 버튼
		ImageIcon chatSend = new ImageIcon(RoomScreen.class.getResource("/img/Send.png"));
		ImageIcon chatSendEnter = new ImageIcon(RoomScreen.class.getResource("/img/SendEnter.png"));

		JButton chatBtn = new JButton(chatSend);
		chatBtn.setBounds(738, 225, 70, 25);
		chatBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chatBtn.setBorderPainted(false);
		chatBtn.setContentAreaFilled(false);
		chatBtn.setFocusPainted(false);
		chatBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				chatBtn.setIcon(chatSendEnter);
				chatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chatBtn.setIcon(chatSend);
				chatBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		chatPan.add(chatBtn);
		JRootPane rootPane = lobbyJF.getRootPane();
		rootPane.setDefaultButton(chatBtn);

		chatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					if (!(chatText.getText().equals(""))) {
						try {
							co.chatting(chatText.getText().substring(0, chatInputMaximum));
						} catch (StringIndexOutOfBoundsException e2) {
							co.chatting(chatText.getText());							
						}
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

		// 플레이어 패널
		JPanel playerPan = new JPanel();
		playerPan.setBounds(850, 60, 390, 290);
		playerPan.setBackground(new Color(63, 28, 6, 120));
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
		ClientPacketController.playerJT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ClientPacketController.playerJT.setSelectionBackground(new Color(232, 57, 95));
		ClientPacketController.playerJT.setRowHeight(30);
		ClientPacketController.playerJT.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		ClientPacketController.playerJT.getTableHeader().setReorderingAllowed(false);
		ClientPacketController.playerJT.getTableHeader().setResizingAllowed(false);
		ClientPacketController.plScroll.getViewport().setBackground(new Color(63, 28, 6));
		ClientPacketController.plScroll.setOpaque(false);
		ClientPacketController.plScroll.setBounds(10, 10, 370, 270);
		ClientPacketController.plScroll.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.plScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		playerPan.add(ClientPacketController.plScroll);
		initialize(); // 초기화

		// 방만들기 버튼
		newBtn = new JButton(new ImageIcon(Lobby.class.getResource("/img/newBtn.png")));
		newBtn.setBounds(10, 200, 370, 50);
		newBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		infoPan.add(newBtn);
	
		newBtn.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == newBtn) {
					if(!MakeRoom.getInstance().isMaking())
						MakeRoom.getInstance().makeRoom();
					
				}
			} // action
		}); // listener
		
		// 나가기 버튼
		exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("/img/exit.png")));
		exitBtn.setBounds(1220, 10, 30, 30);
		exitBtn.setBackground(new Color(0, 0, 0, 0));
		exitBtn.setFocusable(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lobbyJF.add(exitBtn);

		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == exitBtn) {
					ClientPacketSender.instance.offline(PlayerVO.myVO);
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
		lobbyJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lobbyJF.setVisible(true);
		lobbyJF.setResizable(false);
		lobbyJF.setLocationRelativeTo(null);
		lobbyJF.setLayout(null);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		lobbyJF.setIconImage(img);
		lobbyJF.setTitle("섯다 온라인");
		
	}
	
	public void PopMenu() {
		
		//팝업메뉴 추가
		JPopupMenu popupMenu = new JPopupMenu();
		
		//팝업메뉴에 아이템 추가
		JMenuItem whisperItem = new JMenuItem("귓말");
		
		//아이템 기눙 구현
		whisperItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chatText.setText("/귓말 " + ClientPacketController.pn[ClientPacketController.playerJT.getSelectedRow()][0] + " ");
				chatText.requestFocus();
			}
		});
		
		
		//팝업메뉴에 아이템 추가
		popupMenu.add(whisperItem);

		//JTable에 팝업메뉴 추가
		ClientPacketController.playerJT.add(popupMenu);
		
		ClientPacketController.playerJT.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				//왼쪽클릭 || 내 자신이 아니면 팝업메뉴 실행 
				if(e.getButton() == MouseEvent.BUTTON1 && !ClientPacketController.pn[ClientPacketController.playerJT.getSelectedRow()][0].equals(PlayerVO.myVO.getNic())) {
					popupMenu.show(ClientPacketController.playerJT, e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//내자신이 아닌경우 실행하고 팝업메뉴가 꺼지면서 JTable 선택 행 초기화 
				if(!ClientPacketController.pn[ClientPacketController.playerJT.getSelectedRow()][0].equals(PlayerVO.myVO.getNic()))
					ClientPacketController.playerJT.clearSelection();
			}
		});
		
	}

	private void initialize() { }

	public JFrame getLobbyJF() { return lobbyJF; }
	
	
	public int checkSit() {
		String[] checkSit = ClientPacketController.rn[ClientPacketController.roomJT.getSelectedRow()][4].split("/");
		if(Integer.parseInt(checkSit[0])<Integer.parseInt(checkSit[1])) {
			return 1;
		}else
			return 0;
	}


}
