package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import server.Room;
import util.Jokbo;
import util.MoneyFormat;
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
	private JPanel showMoneyPan = new JPanel();
	private JPanel betButtonPan = new JPanel();
	private String[] betAndBtnInitArr = { Protocol.Die + "_", Protocol.Call + "_", Protocol.Ddadang + "_",
			Protocol.Quater + "_", Protocol.Half + "_", Protocol.Allin + "_", "-", "-", "-", "-" };
	private int mySit; // 서버상 내 index
	private JLabel totalMoney = new JLabel();
	
	private JTextField chatText;
	private JPanel[] panlist = new JPanel[5];
	private JLabel[] card1 = new JLabel[5];
	private JLabel[] card2 = new JLabel[5];
	private JLabel[] nicText = new JLabel[5];
	private JLabel[] moneyText = new JLabel[5];
	private JLabel[] betText = new JLabel[5];
	private JLabel[] profile = new JLabel[5];
	private JLabel[] beticon = new JLabel[5];
	private JLabel noAndMoneyLal;
	private JLabel titleLal;
	private boolean[] liveList = { false, false, false, false, false };
	private JButton gameStartBtn = null;
	public static JPanel jokboPanel;
	private boolean gameStart = false;
	private boolean roomOut = false;
	private int roomMaster = 0;
	private int turnIndex;
	private int count = 10;
	private JProgressBar progressBar = new JProgressBar(0,10);
	private static Timer timer = new Timer();
	private TimerTask task;
	private JLabel masterSticker = new JLabel(new ImageIcon(RoomScreen.class.getResource("/img/master.png")));
	
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

		panlist[index].setBounds(0, 0, 0, 0);

		panlist[index] = new JPanel();

		int[] x = { 460, 0, 0, 915, 915 };
		int[] y = { 440, 215, 30, 30, 215 };

		panlist[index].setBounds(x[index], y[index], 350, 180);
		panlist[index].setLayout(null);
		panlist[index].setBackground(new Color(0, 0, 0, 122));
		add(panlist[index]);

		getContentPane().add(back, BorderLayout.CENTER);
		revalidate();
		repaint();

	} // exitPlayer();

	/**
	 * @param idx 방장이 나가거나 죽었을경우 방장 위임할 인덱스
	 */
	public void changeMaster(int idx) {
		
		remove(masterSticker);
		roomMaster = idx;
		idx = (idx - mySit + 5) % 5;
		if (!gameStart && roomMaster == mySit) {
			ClientPacketSender.instance.reloadPlayerList();
			RoomScreen.getInstance().startBtnSet();
		}
			

		if (idx == 0) {
			masterSticker.setBounds(440, 440, 15, 15);
		} else if (idx == 1) {
			masterSticker.setBounds(355, 215, 15, 15);
		} else if (idx == 2) {
			masterSticker.setBounds(355, 30, 15, 15);
		} else if (idx == 3) {
			masterSticker.setBounds(895, 30, 15, 15);
		} else if (idx == 4) {
			masterSticker.setBounds(895, 215, 15, 15);
		}
		masterSticker.setOpaque(false);

		add(masterSticker);
		add(betButtonPan);
		add(back);
		revalidate();
		repaint();
	} // 게임 진행 순서

	public void setButtonAndPrice(String[] arr) {

		ActionListener action = new ActionListener() {
			// 배팅하면 사람들 돈 새로고침 브로드 캐스트
			@Override
			public void actionPerformed(ActionEvent e) {
				task.cancel();
				if (e.getSource() == btn[0]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[0]);
				} else if (e.getSource() == btn[1]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[1]);
				} else if (e.getSource() == btn[2]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[2]);
				} else if (e.getSource() == btn[3]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[3]);
				} else if (e.getSource() == btn[4]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[4]);
				} else if (e.getSource() == btn[5]) {
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, arr[5]);
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
						new ImageIcon(RoomScreen.class.getResource("/img/button/" + arr[i] + ".png")));

				if (arr[i].indexOf("_") == -1) { // 버튼 활성화 된것만 리스너
					btn[i].addActionListener(action);
					btn[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					btn[i].setEnabled(false);
				}
			} catch (Exception e) {
				System.err.println(arr[i] + ".png");
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
					new ImageIcon(RoomScreen.class.getResource("/img/button/" + betAndBtnInitArr[i] + ".png")));
			btn[i].setEnabled(false);
		}
	} // buttonReset();

	public void reLoadMoney(int playerIdx, String money) {
		playerIdx = (playerIdx - mySit + 5) % 5;
		moneyText[playerIdx].setText(MoneyFormat.format(money));
	}

	public void showNeedMoney(String[] arr) {

		/**
		 * 라벨 8개 추가해서 테이블 형식으로 바꿈 콜 따당 쿼터 하프 1억 2억 3억5천 5억1천
		 */
		
		try {
			remove(showMoneyPan);
		}catch(NullPointerException e) {
			System.out.println("널포이트");
		}
		showMoneyPan = new JPanel(); 
		showMoneyPan.setBounds(440, 180, 400, 100);
		showMoneyPan.setBackground(new Color(0,0, 0, 122));
		showMoneyPan.setLayout(null);
		add(showMoneyPan);
		JPanel[] panMoney = new JPanel[8];
		JLabel[] lblMoney = new JLabel[8];
		String[] a = {arr[1],arr[2],arr[3],arr[4]};
		String data[][] ={{arr[6],arr[7],arr[8],arr[9]}};

		for (int i = 0; i < panMoney.length; i++) {
			panMoney[i] = new JPanel();
			lblMoney[i] = new JLabel();
			if (i == 0) {
				panMoney[i].setBounds(0, 0, 100, 50);
				lblMoney[i].setText(arr[1].contains("Call")?"콜":"체크");
			}else if (i == 1) {
				panMoney[i].setBounds(100, 0, 100, 50);
				lblMoney[i].setText(arr[2].contains("Ddadang")?"따당":"삥");
			}else if (i == 2) {
				panMoney[i].setBounds(200, 0, 100, 50);
				lblMoney[i].setText(arr[3].contains("Quater")?"쿼터":"쿼터");
			}else if (i == 3) {
				panMoney[i].setBounds(300, 0, 100, 50);
				lblMoney[i].setText(arr[4].contains("Half")?"하프":"하프");
			}else if (i == 4) {
				panMoney[i].setBounds(0, 50, 100, 50);
				lblMoney[i].setText(MoneyFormat.format(arr[6])+"");
			}else if (i == 5) {
				panMoney[i].setBounds(100, 50, 100, 50);
				lblMoney[i].setText(MoneyFormat.format(arr[7])+"");
			}else if (i == 6) {
				panMoney[i].setBounds(200, 50, 100, 50);
				lblMoney[i].setText(MoneyFormat.format(arr[8])+"");
			}else if (i == 7) {
				panMoney[i].setBounds(300, 50, 100, 50);
				lblMoney[i].setText(MoneyFormat.format(arr[9])+"");
			}
			panMoney[i].add(lblMoney[i]);
			lblMoney[i].setForeground(Color.orange);
			if(i<=3)
				lblMoney[i].setFont(new Font("Rosewood Std", Font.BOLD, 25));
			else
				lblMoney[i].setFont(new Font("Rosewood Std", Font.BOLD, 16));
			panMoney[i].setBackground(new Color(0,0,0));
			panMoney[i].setBorder(new LineBorder(Color.orange, 3));
			showMoneyPan.add(panMoney[i]);
			
			add(betButtonPan);
			add(back);
			revalidate();
			repaint();
		}
		
		
	      
		// 테이블에 출력할 데이터 배열
			
		DefaultTableModel model = new DefaultTableModel(data,a);
		JTable tbl = new JTable(model);
//		mat.add(tbl);
		mat.add(totalMoney);
	}
	
	/*
	 * 중앙 배팅 금액 패널
	 */
	public void mat() {
		mat.setBounds(465, 80, 350, 70);
		mat.setBackground(new Color(0, 0, 0, 122));
		totalMoney.setBounds(0, 0, 380, 50);
		totalMoney.setFont(new Font("Rosewood Std", Font.PLAIN, 50));
		totalMoney.setForeground(Color.yellow);
		totalMoney.setHorizontalAlignment(JLabel.CENTER);

		add(mat);
	} //mat();
	
	//상단에 룸정보 출력하는 메소드
	public void roomInfo() {
		JPanel infoPan = new JPanel();
		infoPan.setBounds(350, 0, 565, 30);;
		infoPan.setBackground(new Color(0, 0, 0, 122));
		infoPan.setLayout(null);
		
		noAndMoneyLal = new JLabel();
		noAndMoneyLal.setBounds(10, 0, 280, 30);
		noAndMoneyLal.setFont(new Font("Rosewood Std", Font.PLAIN, 20));
		noAndMoneyLal.setForeground(Color.YELLOW);
		noAndMoneyLal.setHorizontalAlignment(JLabel.CENTER);
		
		titleLal = new JLabel();
		titleLal.setBounds(280, 0, 285, 30);
		titleLal.setFont(new Font("Rosewood Std", Font.PLAIN, 20));
		titleLal.setForeground(Color.YELLOW);
		titleLal.setHorizontalAlignment(JLabel.CENTER);
				
			
		infoPan.add(noAndMoneyLal);	
		infoPan.add(titleLal);	
		add(infoPan);
	} //roomInfo();

	private boolean initialized = false;
	
	public void turn(int index) {
		turnIndex = (index - mySit + 5) % 5;
		for (int i = 0; i < 5; i++) {
			if (panlist[i] == null)
				continue;
			panlist[i].setBorder(null);
		}
		panlist[turnIndex].setBorder(new LineBorder(Color.orange, 1));
		progressBar.setBounds(2, 2, 347, 8);
		panlist[turnIndex].add(progressBar);

		count=10;	//시간제한 초기화
		
		task = new TimerTask() {
			@Override
			public void run() {
				if(count>0) {
					progressBar.setValue(count);	//감소시 바 게이지 감소
					count--;
				} else {
					if(index == mySit) {			//내 차례일때만 타임아웃 sender
						Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.TIME_OUT);
					}
				}
			}
		};
			timer.schedule(task,0,1000); // task를 0초뒤 1초 간격으로 실행
			
			try {
				beticon[turnIndex].setBounds(0, 0, 0, 0);
			} catch (NullPointerException e) { }
			revalidate();
			repaint();
	} // turn();

	// 게임시작 버튼
	public void startBtnSet() {

		if (gameStartBtn != null) {
			return;
		}

		ImageIcon gameStartBasic = new ImageIcon(RoomScreen.class.getResource("/img/button/GameStartBasic.png"));
		ImageIcon gameStartEnter = new ImageIcon(RoomScreen.class.getResource("/img/button/GameStartEnter.png"));
		gameStartBtn = new JButton(gameStartBasic);
		gameStartBtn.setBounds(510, 310, 240, 115);
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
					RoomScreen.getInstance().gameStart = true;
					Packing.sender(PlayerVO.myVO.getPwSocket(), new Packet(Protocol.GAME_START));
					if (gameStartBtn != null) {
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
		
		//플레이어 패널
		for (int i = 0; i < 5; i++) {
			panlist[i] = new JPanel();
			panlist[i].setBackground(new Color(0, 0, 0, 125));
			if (i == 0) {
				panlist[i].setBounds(460, 440, 350, 180);
			} else if (i == 1) {
				panlist[i].setBounds(0, 215, 350, 180);
			} else if (i == 2) {
				panlist[i].setBounds(0, 30, 350, 180);
			} else if (i == 3) {
				panlist[i].setBounds(915, 30, 350, 180);
			} else if (i == 4) {
				panlist[i].setBounds(915, 215, 350, 180);
			}
			add(panlist[i]);
		} // for

		setButtonAndPrice(betAndBtnInitArr);
		buttonReset();
		if (initialized)
			return;
		initialized = true;

		// 채팅 패널
		JPanel chatPan = new JPanel();
		chatPan.setBounds(0, 400, 420, 220);
		chatPan.setBackground(new Color(0, 0, 0, 120));
		chatPan.setLayout(null);
		add(chatPan);
		chatPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));

		// 채팅 필드
		chatText = new JTextField();
		chatText.setBounds(10, 186, 320, 25);
		chatPan.add(chatText);
		chatText.requestFocus();
		chatText.setFont(new Font("Rosewood Std", Font.PLAIN, 12));
		chatText.setBorder(new LineBorder(Color.orange, 1));
		
		// 15글자 넘어가면 입력불가
		chatText.addKeyListener(new KeyAdapter() {
	    	public void keyTyped(KeyEvent e) {
	    		if(e.getSource()==chatText) {
	    			if(chatText.getText().length()>=16)
	    				e.consume();
	    		}
	    	}
	    });
		
		
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
		ImageIcon chatSend = new ImageIcon(RoomScreen.class.getResource("/img/Send.png"));
		ImageIcon chatSendEnter = new ImageIcon(RoomScreen.class.getResource("/img/SendEnter.png"));

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
			} // actionPerformed();
		}); // addActionListener();

		// 나가기 버튼
		JButton exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("/img/smallExit.png")));
		exitBtn.setBounds(1235, 5, 20, 20);
		exitBtn.setBackground(new Color(0, 0, 0, 0));
		exitBtn.setFocusable(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(exitBtn);

		exitBtn.addMouseListener(new MouseAdapter() {
			int i = 0;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == exitBtn) {
					if (i % 2 == 0) {
						sendOff();
					} else {
						roomOut = false;
						JOptionPane.showMessageDialog(null, "나가기 예약이 취소 되었습니다.", "알림", JOptionPane.WARNING_MESSAGE);
					}
					i++;
				}
			}
		});

//		addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				sendOff();
//			}
//		});

		new Thread(new MusicPlayer()).start(); // 배경음악

		setTitle("섯다 온라인");
		mat(); // 돈판 출력
		roomInfo(); // 룸정보 출력
		showNeedMoney(betAndBtnInitArr);
		
		
		
		
		
		back.mainImage();
		content = getContentPane();
		content.add(back, BorderLayout.CENTER);
		back.setOpaque(false);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true); // 배경화면
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		setIconImage(img);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 1274, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 691, Short.MAX_VALUE));
		getContentPane().setLayout(groupLayout);

//		for(int i = 0; i < 2; i++) {
//			final int j = i;
//			timers[i] = new Timer(1000, e -> {
//				remove(beticon[j]);
//				revalidate();
//				repaint();
//			});
//			timers[i].setRepeats(false);
//		}
	} // mainScreen();

	/**
	 * gameStart 가 ture이면 게임 실행중 gameStart가 false이면 바로 나가고 true면 roomOut을 true로 변경
	 * roomOut가 true일 경우 게임종료시 나감
	 */
	public void sendOff() {
		if (!gameStart) {
			dispose();
			Lobby.getInstance().lobbyScreen();
			ChattingOperator.chatArea.setText("");
		} else {
			roomOut = true;
			JOptionPane.showMessageDialog(null, "나가기 예약이 되었습니다.", "알림", JOptionPane.WARNING_MESSAGE);
		}
	}

	// 시작 돈을 걷고 Text에 적용
	public void startPay(long sMoney) {
		int count = 0;
		gameStart = true;

		for (int i = 0; i < 5; i++) {
			if (moneyText[i] == null)
				continue;
			count++;
			moneyText[i].setText(MoneyFormat.format(MoneyFormat.reformat(moneyText[i].getText()) - sMoney) + "");
			try {
				liveList[i] = true;
			} catch (NullPointerException e) { }
		} // for
		totalMoney.setText(MoneyFormat.format(count * sMoney) + "");
	} // startPay();

	public void betAlert(int idx, String bet, String money, String total) {
		task.cancel();	//다른 사람이 배팅을 했을경우 스케줄러 중지
		idx = (idx - mySit + 5) % 5;

		moneyText[idx].setText(MoneyFormat.format(money));
		
		totalMoney.setText(MoneyFormat.format(total));

		if (bet.equals(Protocol.Die)) {
			liveList[idx] = false;
			try {
				card1[idx].setIcon(null);
				card2[idx].setIcon(null);
			} catch (NullPointerException e) {
			}
		} // if
//		betText[idx].setText(bet+"222");
		betIcon(idx, bet);
	} // betAlert();

//	public Timer[] timers = new Timer[5];

	public void betIcon(int idx, String bet) {
		ImageIcon iCon;
		if (idx == 0)
			iCon = new ImageIcon(RoomScreen.class.getResource("/img/icon/" + bet + "Icon_.png"));
		else
			iCon = new ImageIcon(RoomScreen.class.getResource("/img/icon/" + bet + "Icon.png"));


		try {
			beticon[idx].setBounds(0, 0, 0, 0);
		} catch (NullPointerException e) {
		}

		beticon[idx] = new JLabel(iCon);

//		timers[idx].stop();
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

	} // betIcon();

	public ImageIcon cardFormat(float card) {
		return new ImageIcon(RoomScreen.class.getResource(
				"/img/card/" + String.format("%." + ((int) card == card ? "0" : "1") + "f", card) + ".png"));
	}

	public void openCard(Map<Integer, PlayerVO> cardMap) {

		for (int i = 0; i < 5; i++) {
			if (cardMap.get(i) == null || !cardMap.get(i).isLive())
				continue;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int idx = (i - mySit + 5) % 5;

			float c1 = cardMap.get(i).getCard1();
			float c2 = cardMap.get(i).getCard2();
			try {
				card1[idx].setIcon(cardFormat(c1));
				card2[idx].setIcon(cardFormat(c2));

			} catch (NullPointerException e) {
				e.printStackTrace();
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

		for (int i = 1; i < 5; i++) { // 다른 사람의 뒷패를 돌리므로 1부터
			if (liveList[i]) {

				if (card[0] != 0)
					card1[i].setIcon(new ImageIcon(RoomScreen.class.getResource("/img/card/Pae.png")));
				if (card[1] != 0)
					card2[i].setIcon(new ImageIcon(RoomScreen.class.getResource("/img/card/Pae.png")));

			} // if
		} // for
		try {
			remove(jokboPanel);
		} catch (NullPointerException e) {
		}
		Jokbo jokbo = Jokbo.getInstance();

		jokboPanel = new JPanel();
		jokboPanel.setBounds(850, 420, 415, 200);
		jokboPanel.setBackground(new Color(0, 0, 0));
		jokboPanel.setLayout(null);
		add(jokboPanel);

		jokbo.jokboPan(card[0], card[1]);

	} // receiveCard();

	/**
	 * @param msg             알림창에 넣을 내용
	 * @param winerIdx        승자 서버 인덱스 (생략시 승자 탈주 처리)
	 * @param winerTotalMoney 승자가 소유한 돈 (생략시 승자 탈주 처리)
	 */
	public void gameOver(String msg, int winerIdx, String winerTotalMoney) {
		repaint();
		gameStart = false;
		remove(jokboPanel);
		changeMaster(winerIdx);
		panlist[turnIndex].remove(progressBar);
		showNeedMoney(betAndBtnInitArr);
		JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.WARNING_MESSAGE);
		winerIdx = (winerIdx - mySit + 5) % 5;
		moneyText[winerIdx].setText(MoneyFormat.format(winerTotalMoney));
		RoomScreen.getInstance().gameStart = false;
		if (mySit == roomMaster)
			startBtnSet();
		totalMoney.setText("대기중");
		for (int j = 0; j < 5; j++) {
			try {
				card1[j].setIcon(null);
				card2[j].setIcon(null);
				panlist[j].setBorder(null);
				beticon[j].setBounds(0, 0, 0, 0);
			} catch (NullPointerException e) {
			}
			liveList[j] = false;
		}
		beticon = new JLabel[5];
		if (roomOut || PlayerVO.myVO.getMoney() == 0) {
			dispose();
			Lobby.getInstance().lobbyScreen();
		}
	} // gameOver();

	public void gameOver(String msg) {
		gameStart = false;
		JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.WARNING_MESSAGE);
		RoomScreen.getInstance().gameStart = false;
	} // gameOver();

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

			moneyText[i] = new JLabel(MoneyFormat.format(setVO.getMoney()) + "");
			moneyText[i].setForeground(new Color(255, 252, 128));
			moneyText[i].setHorizontalAlignment(JLabel.CENTER);
			moneyText[i].setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 9));

			// 1번과 2번 자리 앉은 사람은 이미지 반전
			if (i == 1 || i == 2) {
				profile[i] = new JLabel(new ImageIcon(
						RoomScreen.class.getResource("/img/character/cha" + setVO.getCha() + "_.png")));
			} else {
				profile[i] = new JLabel(new ImageIcon(
						RoomScreen.class.getResource("/img/character/cha" + setVO.getCha() + ".png")));
			}
			betText[i] = new JLabel();
			panlist[i].setLayout(null);
			panlist[i].setBackground(new Color(0, 0, 0, 122));

			int w = 0, t = 0;

			if (i == 0 || i == 1 || i == 2) {
				w = 10;
				t = 105;
			} else if (i == 3 || i == 4) {
				w = 250;
			}

//			card1[i].setBounds(115, 10, 110, 160);
//			card2[i].setBounds(230, 10, 110, 160);

			card1[i].setBounds(10 + t, 10, 110, 160);
			card2[i].setBounds(125 + t, 10, 110, 160);

			profile[i].setBounds(w, 20, 90, 100);
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
			pMenu(i);
			revalidate();
			repaint();

		} catch (Exception e) {
			logger.info(Arrays.toString(panlist));
			logger.error(e.getMessage(), e);
		}
	} // setSit();

	/**
	 * 나중에 들어온 플레이어한테 방에 있는 플레이어 목록을
	 * 
	 * @param voList 방 플레이어 리스트
	 * @param index  서버상 자신의 index
	 */
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
			j = (mySit + i + 5) % 5;
			PlayerVO setVO = voList.get(j);
			if (setVO == null)
				continue;

			setSit(i, setVO);

		} // for
	} // enterPlayerList();

	public void enterPlayer(PlayerVO vo, int index) {
		if (PlayerVO.myVO.getNo() == vo.getNo())
			mySit = vo.getIndex();

		index = (index - mySit + 5) % 5;
		setSit(index, vo);
	} // enterPlayerList();

	
	public void pMenu(int sit) {
		
		System.out.println("플레이어 : " + PlayerVO.myVO.getNic());
		System.out.println("mySit : " + mySit);
		System.out.println("masterIdex : " + roomMaster);
		System.out.println("sit : " + sit);
		System.out.println();
		
		//팝업메뉴 추가
		JPopupMenu popupMenu = new JPopupMenu();
		
		//팝업메뉴에 아이템 추가
		JMenuItem whisperItem = new JMenuItem("귓말");
			
		//아이템 기눙 구현
		whisperItem.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				chatText.setText("/귓말 " + nicText[sit].getText() + " ");
				chatText.requestFocus();
			}
		});//addActionListener();
			
		//팝업메뉴에 아이템 추가
		popupMenu.add(whisperItem);
		
		//방장일 경우 추방 아이템 추가
		if(roomMaster == mySit) {
			JMenuItem kickItem = new JMenuItem("추방");		
			System.out.println("들어옴");
			
			//추방 아이템 기능 구현
			kickItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ClientPacketSender.instance.kick(nicText[sit].getText());
				}
			});//addActionListener();
						
			//팝업메뉴에 추방 아이템 추가
			popupMenu.add(kickItem);
	
		}
			
			
		
		//JTable에 팝업메뉴 추가
		add(popupMenu);
			
		
			profile[sit].addMouseListener(new MouseListener() {
				
				@Override
				public void mousePressed(MouseEvent e) {	
					if(e.getButton() == MouseEvent.BUTTON1) {
						//자신은 popupMenu를 띄우지 않음
						if(!(e.getSource() == profile[0]))
							popupMenu.show(profile[sit], e.getX(), e.getY());
					}
				}//mousePressed();
					
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
					
			});//addMouseListener();
	}//pMenu();
	
	


	public JLabel getTitleLal() { return titleLal; }

	public JLabel getNoAndMoneyLal() { return noAndMoneyLal; }

	public JProgressBar getProgressBar() { return progressBar; }
	
}



