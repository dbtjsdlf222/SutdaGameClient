package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import music.MusicPlayer;
import operator.ChattingOperator;
import util.Jokbo;
import util.Packing;
import vo.Packet;
import vo.PlayerVO;
import vo.Protocol;

@SuppressWarnings("serial")
public class RoomScreen extends JFrame {

	private final static Logger logger = LogManager.getLogger();

	public final int SCREEN_WIDTH = 1280;
	public final int SCREEN_HEIGHT = 720;

	private static RoomScreen instance;

	private Container content;
	private Background back = new Background();
	private JButton[] btn = new JButton[6];
	private JPanel mat = new JPanel();
	private JPanel betButtonPan = new JPanel();
	private String[] betBtnInitArr = { Protocol.Die + "_", Protocol.Ddadang + "_", Protocol.Call + "_",
			Protocol.Quater + "_", Protocol.Half + "_", Protocol.Allin + "_" };
	private int mySit;	//서버상 내 index
	private JLabel totalMoney = new JLabel();
	private JLabel masterSticker = new JLabel(new ImageIcon(RoomScreen.class.getResource("../../img/master.PNG")));
	private JPanel[] panlist = new JPanel[5];
	private JLabel[] card1 = new JLabel[5];
	private JLabel[] card2 = new JLabel[5];
	private JLabel[] nicText = new JLabel[5];
	private JLabel[] moneyText = new JLabel[5];
	private JLabel[] betText = new JLabel[5];
	private JLabel[] profile = new JLabel[5];
	private JLabel[] beticon = new JLabel[5];
	private boolean[] liveList = {false,false,false,false,false};
	private JButton gameStartBtn = null;
	public static JPanel jokboPanel;
	public static DecimalFormat fm = new DecimalFormat("###,###");
	private boolean gameStart= false;
	private int roomMaster = 0;
	public static RoomScreen getInstance() {
		if (instance == null)
			instance = new RoomScreen();
		return instance;

	} // getInstance();

	private RoomScreen() {
	} // RoomScreen();

	@Override
	public void dispose() {
		instance = null;
		ClientPacketSender.instance.exitRoom();
		super.dispose();
	} // dispose();

	/**
	 * @param index 나갈 사람의 index
	 */
	public void exitPlayer(int index) {

		index = (index - mySit + 5) % 5;
		liveList[index] = false;
		remove(panlist[index]);
		panlist[index] = new JPanel();

		int[] x = { 460,   0,  0, 915, 915 };
		int[] y = { 440, 215, 30,  30, 215 };

		panlist[index].setBounds(x[index], y[index], 350, 180);
		panlist[index].setLayout(null);
		panlist[index].setBackground(new Color(0, 0, 0,122));
		add(panlist[index]);

		getContentPane().add(back, BorderLayout.CENTER);
		revalidate();
		repaint();

	} // exitPlayer();

	/**
	 * @param idx 방장이 나가거나 죽었을경우 방장 위임할 인덱스
	 */
	public void changeMaster(int idx) {
		roomMaster = idx;
		idx = (idx - mySit + 5) % 5;

		if(!gameStart&& roomMaster==mySit)
			RoomScreen.getInstance().startBtnSet();
		
		if (idx == 0) {
			// frame.setBounds(450, 430, 370, 190);
			masterSticker.setBounds(430, 440, 15, 15);
		} else if (idx == 1) {
			// frame.setBounds(-5, 205, 370, 190);
			masterSticker.setBounds(370, 220, 15, 15);
		} else if (idx == 2) {
			// frame.setBounds(-5, 20, 370, 190);
			masterSticker.setBounds(370, 30, 15, 15);
		} else if (idx == 3) {
			// frame.setBounds(905, 20, 370, 190);
			masterSticker.setBounds(890, 30, 15, 15);
		} else if (idx == 4) {
			// frame.setBounds(905, 205, 370, 190);
			masterSticker.setBounds(890, 220, 15, 15);
		}
		masterSticker.setOpaque(false);

		add(masterSticker);
		revalidate();
		repaint();
	} // 게임 진행 순서

	public void setButton(String[] buttonArray) {
		if(buttonArray!=null)
			showNeedMoney(buttonArray);
		
		ActionListener action = new ActionListener() {
			// 배팅하면 사람들 돈 새로고침 브로드 캐스트
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btn[0]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[0]);
				} else if (e.getSource() == btn[1]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[1]);
				} else if (e.getSource() == btn[2]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[2]);
				} else if (e.getSource() == btn[3]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[3]);
				} else if (e.getSource() == btn[4]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[4]);
				} else if (e.getSource() == btn[5]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[5]);
				}
				buttonReset();

			} // actionPerformed();
		};
		
		remove(betButtonPan);

		betButtonPan = new JPanel();
		betButtonPan.setBounds(0, 620, 1265, 60);
		betButtonPan.setLayout(new GridLayout(1, 6));
		betButtonPan.setOpaque(false);

		for (int i = 0; i < 6; i++) {
			try {
				btn[i] = new JButton(
						new ImageIcon(RoomScreen.class.getResource("../../img/button/" + buttonArray[i] + ".PNG")));

				if (buttonArray[i].indexOf("_") == -1) { // 버튼 활성화 된것만 리스너
					btn[i].addActionListener(action);
					btn[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					btn[i].setEnabled(false);
				}
			} catch (Exception e) {
				System.err.println(buttonArray[i] + ".PNG");
			} // try~catch
			btn[i].setOpaque(false);
			betButtonPan.add(btn[i]);
		}

		add(betButtonPan);
		add(back);
		revalidate();
		repaint();
	}

	public void buttonReset() {
		for (int i = 0; i < 6; i++) {
			btn[i].setIcon(
					new ImageIcon(RoomScreen.class.getResource("../../img/button/" + betBtnInitArr[i] + ".PNG")));
			btn[i].setEnabled(false);
		}
	} // buttonReset();

	public void reLoadMoney(int playerIdx, String money) {
		playerIdx = (playerIdx - mySit + 5) % 5;
		moneyText[playerIdx].setText(money);
	}

	public void showNeedMoney(String columnNames[]) {
		String[] a = {columnNames[1],columnNames[2],columnNames[3],columnNames[4]};
	      
		// 테이블에 출력할 데이터 배열
		String data[][] ={{"1450만원","5000만원","9600만원","2억4천만원"}};
	       
		DefaultTableModel model = new DefaultTableModel(data,a);
		JTable tbl = new JTable(model);
		mat.add(tbl);
		mat.add(totalMoney);
	}
	
	public void mat() {
		mat.setBounds(410, 50, 440, 300);
		mat.setBackground(new Color(0, 0, 0, 122));
		totalMoney.setBounds(10, 10, 420, 50);
		totalMoney.setFont(new Font("Rosewood Std", Font.PLAIN, 50));
		totalMoney.setForeground(Color.yellow);
		totalMoney.setHorizontalAlignment(JLabel.CENTER);
		
		add(mat); // 배팅 금액 패널
	}
	
	private boolean initialized = false;
	public void turn(int index) {
		index = (index - mySit + 5) % 5;
		for (int i = 0; i < 5; i++) {
			if(panlist[i]==null) continue;
			panlist[i].setBorder(null);	
		}
		panlist[index].setBorder(new LineBorder(Color.orange, 1));
		revalidate();
		repaint();
	} //turn();
	
	// 게임시작 버튼
	public void startBtnSet() {
		
		if(gameStartBtn!=null) { return; }
		
		ImageIcon gameStartBasic = new ImageIcon(RoomScreen.class.getResource("../../img/button/GameStartBasic.PNG"));
		ImageIcon gameStartEnter = new ImageIcon(RoomScreen.class.getResource("../../img/button/GameStartEnter.PNG"));
		gameStartBtn = new JButton(gameStartBasic);
		gameStartBtn.setBounds(510, 350, 240, 115);
		gameStartBtn.setBorderPainted(false);
		gameStartBtn.setContentAreaFilled(false);
		gameStartBtn.setFocusPainted(false);
		gameStartBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				gameStartBtn.setIcon(gameStartEnter);
				gameStartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gameStartBtn.setIcon(gameStartBasic);
				gameStartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int tempCount = 0;
				for (int i = 0; i < 5; i++) {
						if (profile[i] != null) {
							tempCount++;
						}
				} // for
				if (tempCount >= 2) {
					RoomScreen.getInstance().gameStart= true;
					Packing.sender(PlayerVO.myVO.getPwSocket(), new Packet(Protocol.GAMESTART));
					if(gameStartBtn!=null) {
						remove(gameStartBtn);
						gameStartBtn = null;
					}
				} else {
					JOptionPane.showMessageDialog(null, "플레이어가 2명 이상일 때만 시작 가능합니다.", "알림", JOptionPane.WARNING_MESSAGE);
				}
			} // mousePressed();
		});
		add(gameStartBtn);
		add(back);
		revalidate();
		repaint();
	}
	
	public void mainScreen() {
		
		for (int i = 0; i < 5; i++) {
			panlist[i] = new JPanel();
			if (i == 0) { panlist[i].setBounds(460, 440, 350, 180); } else 
			if (i == 1) { panlist[i].setBounds(  0, 215, 350, 180); } else 
			if (i == 2) { panlist[i].setBounds(  0,  30, 350, 180);	} else
			if (i == 3) { panlist[i].setBounds(915,  30, 350, 180); } else
			if (i == 4) { panlist[i].setBounds(915, 215, 350, 180); }
			add(panlist[i]);
		}

		setButton(betBtnInitArr);
		buttonReset();
		if (initialized)
			return;
		initialized = true;

		// 채팅 판넬
		JPanel chatPan = new JPanel();
		chatPan.setBounds(0, 400, 420, 220);
		chatPan.setBackground(new Color(0, 0, 0, 120));
		chatPan.setLayout(null);
		add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));

		// 채팅 필드
		JTextField chatText = new JTextField();
		chatText.setBounds(10, 186, 320, 25);
		chatPan.add(chatText);
		chatText.requestFocus();
		chatText.setFont(new Font("Rosewood Std", Font.PLAIN, 12));
		chatText.setBorder(new LineBorder(Color.orange, 1));
		ChattingOperator co = ChattingOperator.getInstance();

		ChattingOperator.chatArea.setEditable(false);
		ChattingOperator.chatArea.setLineWrap(true);
		ChattingOperator.chatArea.setBorder(new LineBorder(Color.orange, 1));
		ClientPacketController.scrollPane.setBorder(new TitledBorder(new LineBorder(Color.orange, 1)));
		ClientPacketController.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.scrollPane.setBounds(10, 10, 400, 165);
		ClientPacketController.scrollPane.setBorder(null);
		chatPan.add(ClientPacketController.scrollPane);

		// 채팅 보내기 버튼
		ImageIcon chatSend = new ImageIcon(RoomScreen.class.getResource("../../img/Send.PNG"));
		ImageIcon chatSendEnter = new ImageIcon(RoomScreen.class.getResource("../../img/SendEnter.PNG"));

		JButton chatBtn = new JButton(chatSend);
		chatBtn.setBounds(340, 186, 70, 25);
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
		add(chatBtn);

		chatPan.add(chatBtn);
		JRootPane rootPane = this.getRootPane();
		rootPane.setDefaultButton(chatBtn);

		chatBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					if (!(chatText.getText().equals(""))) {
						co.chatting(chatText.getText());
						chatText.requestFocus();

						chatText.setText("");
					}
				}
			} //actionPerformed();
		}); //addActionListener();

		// 나가기 버튼
		JButton exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/smallExit.PNG")));
		exitBtn.setBounds(1235, 5, 20, 20);
		exitBtn.setBackground(new Color(0, 0, 0, 0));
		exitBtn.setFocusable(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		add(exitBtn);

		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Lobby();
			}
		});
		
		new Thread(new MusicPlayer()).start(); // 배경음악

		setTitle("섯다 온라인");
		mat(); // 돈판 출력

		back.mainImage();
		content = getContentPane();
		content.add(back, BorderLayout.CENTER);
		back.setOpaque(false);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true); // 배경화면
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("../../img/titleIcon.jpg"));
		setIconImage(img);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 1274, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 691, Short.MAX_VALUE));
		getContentPane().setLayout(groupLayout);
		
//		for(int i = 0; i < timers.length; i++) {
//			final int j = i;
//			timers[i] = new Timer(1000, e -> {
//				remove(beticon[j]);
//				revalidate();
//				repaint();
//			});
//			timers[i].setRepeats(false);
//		}
	} //mainScreen();

	// 시작 돈을 걷고 Text에 적용
	public void startPay(long sMoney) {
		int count = 0;
		gameStart = true;
		
		for (int i = 0; i < 5; i++) {
			if (moneyText[i] == null)
				continue;
			count++;
			moneyText[i].setText(Long.parseLong(moneyText[i].getText().replaceAll(",", "")) - sMoney + "");
			try {
				liveList[i] = true;
			} catch (NullPointerException e) { }
		} // for
		totalMoney.setText(count * sMoney + "");
	} // startPay();

	public void betAlert(int idx, String bet, String money,String total) {
		idx = (idx - mySit + 5) % 5;
		
		moneyText[idx].setText(money);
		
		totalMoney.setText(total);
			
		if(bet.equals(Protocol.Die)) {
			liveList[idx] = false;
			try {
				card1[idx].setIcon(null);
				card2[idx].setIcon(null);
			} catch (NullPointerException e) { }
		} //if
		betText[idx].setText(bet);
		betIcon(idx, bet);
	} // betAlert();
	
//	public Timer[] timers = new Timer[5];
	
	public void betIcon(int idx, String bet) {
		ImageIcon iCon;
		if(idx == 0) {
			iCon = new ImageIcon(RoomScreen.class.getResource("../../img/icon/"+bet+"Icon_.png"));
		}else {
			iCon = new ImageIcon(RoomScreen.class.getResource("../../img/icon/"+bet+"Icon.png"));
		}
		System.err.println("BetIcon INDEX: [" + idx + "]");
		beticon[idx] = new JLabel(iCon);
		
//		timers[idx].stop();
		remove(beticon[idx]);
		switch (idx) {
		case 0:
			beticon[idx].setBounds(530, 380, 200, 60);
			add(beticon[idx]);
			break;
		case 1:
			beticon[idx].setBounds(350, 235, 60, 140);
			add(beticon[idx]);
			break;
		case 2:
			beticon[idx].setBounds(350, 50, 60, 140);
			add(beticon[idx]);
			break;
		case 3:
			beticon[idx].setBounds(855, 50, 60, 140);
			add(beticon[idx]);
			break;
		case 4:
			beticon[idx].setBounds(855, 235, 60, 140);
			add(beticon[idx]);
			break;
		}
		
		add(panlist[idx]);
		add(back);
		revalidate();
		repaint();
		
//		timers[idx].start();
		
	} //betIcon();

	public ImageIcon cardFormat(float card) {
		return  new ImageIcon(RoomScreen.class.getResource("../../img/card/"+
				String.format("%." +((int) card == card ? "0" : "1")+"f", card) +".png"));
	}
	
	public void openCard(Map<Integer,PlayerVO> cardMap) {
		
		for (int i = 0; i < 5; i++) {
			if(cardMap.get(i)==null || !cardMap.get(i).isLive()) continue;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) { e.printStackTrace(); }
			int idx = (i - mySit + 5) % 5;

			float c1 = cardMap.get(i).getCard1();
			float c2 = cardMap.get(i).getCard2();
			try {
				card1[idx].setIcon(cardFormat(c1));
				card2[idx].setIcon(cardFormat(c2));
//				card1[idx].setIcon(new ImageIcon(RoomScreen.class.getResource("../../img/card/"+
//				String.format("%." +((int) c1 == c1 ? "0" : "1")+"f", c1) +".png")));
//				
//				card2[idx].setIcon(new ImageIcon(RoomScreen.class.getResource("../../img/card/"+
//						String.format("%." +((int) c2 == c2 ? "0" : "1")+"f", c2) +".png")));
				
			} catch (NullPointerException e) {
				System.out.println("card1[idx] "+card1[idx]);
				System.out.println("card2[idx] "+card2[idx]);
				e.printStackTrace();
				System.err.println(cardFormat(c1));
				System.err.println(cardFormat(c2));
			}
		} 
	} // openCard();

	/**
	 * @param card card[] 배열안에 카드의 번호(!=0)가 들어있다면 적용
	 */

	public void receiveCard(float[] card) {
		
		if (card[0] != 0)
			card1[0].setIcon(cardFormat(card[0]));
			

		if (card[1] != 0)
			card2[0].setIcon(cardFormat(card[1]));

		for (int i = 1; i < 5; i++) {	//다른 사람의 뒷패를 돌리므로 1부터
			if (liveList[i]) {
				
				if(card[0] != 0)
					card1[i].setIcon(new ImageIcon(RoomScreen.class.getResource("../../img/card/Pae.png")));
				if(card[1] != 0)
					card2[i].setIcon(new ImageIcon(RoomScreen.class.getResource("../../img/card/Pae.png")));
				
			} //if
		} // for
		try {
		remove(jokboPanel);
		}catch(NullPointerException e) {
		}
		
		System.out.println("receiveCard");
		Jokbo jokbo= Jokbo.getInstance();
		
		jokboPanel = new JPanel();
		jokboPanel.setBounds(850, 420, 415, 200);
		jokboPanel.setBackground(new Color(0, 0, 0));
		jokboPanel.setLayout(null);
		add(jokboPanel);
		
		jokbo.jokboPan(card[0], card[1]);
	
	} // receiveCard();

	/**
	 * @param msg 알림창에 넣을 내용
	 * @param winerIdx 승자 서버 인덱스 (생략시 승자 탈주 처리)
	 * @param winerTotalMoney 승자가 소유한 돈 (생략시 승자 탈주 처리)
	 */
	public void gameOver(String msg, int winerIdx, String winerTotalMoney) {
		gameStart = false;
		roomMaster = winerIdx;
		JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.WARNING_MESSAGE);
		winerIdx = (winerIdx - mySit + 5) % 5;
		moneyText[winerIdx].setText(winerTotalMoney);
		RoomScreen.getInstance().gameStart= false;
		if(mySit == roomMaster)
			startBtnSet();
		beticon = new JLabel[5];
		totalMoney.setText("대기중");
		for (int j = 0; j < 5; j++) {
			try {
				card1[j].setIcon(null);
				card2[j].setIcon(null);
				panlist[j].setBorder(null);
				remove(beticon[j]);
			} catch (NullPointerException e) { }
			liveList[j] = false;
		}
	} //gameOver();

	
	public void gameOver(String msg) {
		gameStart = false;
		JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.WARNING_MESSAGE);
		RoomScreen.getInstance().gameStart= false;
	}  //gameOver();
	
	/**
	 * @param i     몇번째 자리
	 * @param setVO 자리에 앉을 사람의 VO
	 */
	public void setSit(int i, PlayerVO setVO) {
		try {
			liveList[i] = true;
			card1[i] = new JLabel();
			card2[i] = new JLabel();
			
			nicText[i] = new JLabel(setVO.getNic());
			nicText[i].setForeground(Color.white);
			nicText[i].setHorizontalAlignment(JLabel.CENTER);
			nicText[i].setFont(new Font("휴먼옛체", Font.PLAIN, 15));
			
//			moneyText[i] = new JLabel(fm.format(setVO.getMoney()));
			moneyText[i] = new JLabel(setVO.getMoney()+"");
			moneyText[i].setForeground(new Color(255, 252, 128));
			moneyText[i].setHorizontalAlignment(JLabel.CENTER);
			moneyText[i].setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 10));

			// 1번과 2번 자리 앉은 사람은 이미지 반전
			if (i == 1 || i == 2) {
				profile[i] = new JLabel(new ImageIcon(
						RoomScreen.class.getResource("../../img/character/cha" + setVO.getCha() + "_.PNG")));
			} else {
				profile[i] = new JLabel(new ImageIcon(
						RoomScreen.class.getResource("../../img/character/cha" + setVO.getCha() + ".PNG")));
			}
			betText[i] = new JLabel();
			panlist[i].setLayout(null);
			panlist[i].setBackground(new Color(0, 0, 0, 122));
			
			int w = 0, t = 0;
			
			if (i==0||i==1||i==2) { w= 10; t=105;}  else 
			if (i==3||i==4)  	  { w=250; }
			
//			card1[i].setBounds(115, 10, 110, 160);
//			card2[i].setBounds(230, 10, 110, 160);
			
			card1[i].setBounds( 10+t, 10, 110, 160);
			card2[i].setBounds(125+t, 10, 110, 160);
			
			profile[i].setBounds(w, 10, 90, 100);
			betText[i].setBounds(w, 10, 90, 20);
			nicText[i].setBounds(w, 125, 90, 20);
			moneyText[i].setBounds(w, 150, 90, 20);
			
			
			profile[i].setOpaque(false);
			profile[i].setBackground(new Color(34, 116, 28));
			panlist[i].add(profile[i]);
			card1[i].setOpaque(false);
			card2[i].setOpaque(false);
			panlist[i].add(card1[i]);
			panlist[i].add(card2[i]); 
			betText[i].setOpaque(false);
			betText[i].setForeground(Color.black);
			panlist[i].add(betText[i]);
			nicText[i].setOpaque(false);
			panlist[i].add(nicText[i]);
			moneyText[i].setOpaque(false);
			panlist[i].add(moneyText[i]);

			revalidate();
			repaint();
			
		} catch (Exception e) {
			logger.info(Arrays.toString(panlist));
			logger.error(e.getMessage(), e);
		}
	} // setSit();

	public void enterPlayerList(Map<Integer, PlayerVO> voList, int index) {
		
//		for (Entry<Integer, PlayerVO> set : voList.entrySet()) {
//			if (PlayerVO.myVO.getNo() == set.getValue().getNo()) {
//				mySit = set.getValue().getIndex();
//				break;
//			}
//		} //for

		mySit = index;

		for (int i = 0; i < 5; i++) {
			int j;
			j = (mySit + i  + 5) % 5;
			PlayerVO setVO = voList.get(j);
			if (setVO == null)
				continue;
			
			setSit(i, setVO);

		} // for
	} // enterPlayerList();

	public void enterPlayer(PlayerVO vo, int index) {
		System.err.println("       " + index +" | "+ vo);
		if (PlayerVO.myVO.getNo() == vo.getNo())
			mySit = vo.getIndex();

		index = (index - mySit + 5) % 5;
		setSit(index, vo);
	} // enterPlayerList();

	public static void main(String[] args) {
		Map<Integer, PlayerVO> voList = new HashMap<Integer, PlayerVO>();

		voList.put(0, new PlayerVO("1hyo", 3, 600000));
		voList.put(1, new PlayerVO("2hyo", 3, 34600000));
		voList.put(2, new PlayerVO("3hyo", 3, 1345000));
		voList.put(3, new PlayerVO("4hyo", 3, 120000));
		voList.put(4, new PlayerVO("5hyo", 3, 4000));

		RoomScreen ms = new RoomScreen();
		ms.mainScreen();
		ms.enterPlayerList(voList, 2);
		ms.exitPlayer(1);
		ms.turn(0);
	}
}