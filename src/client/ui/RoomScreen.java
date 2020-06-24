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
import java.util.Map.Entry;

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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import music.MusicPlayer;
import operator.ChattingOperator;
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
	private JTextField tf;
	private JButton[] btn = new JButton[6];
	private JPanel mat = new JPanel();
	private JPanel betButtonPan = new JPanel();
	private String[] betBtnInitArr = { Protocol.Die + "_", Protocol.Ddadang + "_", Protocol.Call + "_",
			Protocol.Quater + "_", Protocol.Half + "_", Protocol.Allin + "_" };
	private int mySit;	//서버상 내 index
	private JLabel totalMoney = new JLabel();
	JLabel masterSticker = new JLabel(new ImageIcon(RoomScreen.class.getResource("../../img/master.PNG")));
	private JPanel[] panlist = new JPanel[5];
	private JLabel[] card1 = new JLabel[5];
	private JLabel[] card2 = new JLabel[5];
	JLabel[] nicText = new JLabel[5];
	JLabel[] moneyText = new JLabel[5];
	JLabel[] betText = new JLabel[5];
	JLabel[] profile = new JLabel[5];
	JLabel[] beticon = new JLabel[5];
	public static DecimalFormat fm = new DecimalFormat("###,###");
	private boolean gameStart = false;
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

		remove(panlist[index]);
		panlist[index] = new JPanel();

		int[] x = { 460, 0, 0, 915, 915 };
		int[] y = { 440, 215, 30, 30, 215 };

		panlist[index].setBounds(x[index], y[index], 350, 180);
		panlist[index].setLayout(null);
		panlist[index].setBackground(new Color(0, 0, 0));
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

		if(!gameStart && roomMaster==mySit)
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

		logger.debug(Arrays.toString(buttonArray));

		ActionListener action = new ActionListener() {
			// 배팅하면 사람들 돈 새로고침 브로드 캐스트
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btn[0]) {
					tf.setText(buttonArray[0]);
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[0]);
				} else if (e.getSource() == btn[1]) {
					tf.setText(buttonArray[1]);
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[1]);
				} else if (e.getSource() == btn[2]) {
					tf.setText(buttonArray[2]);
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[2]);
				} else if (e.getSource() == btn[3]) {
					tf.setText(buttonArray[3]);
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[3]);
				} else if (e.getSource() == btn[4]) {
					tf.setText(buttonArray[4]);
					Packing.sender(PlayerVO.myVO.getPwSocket(), Protocol.BET, buttonArray[4]);
				} else if (e.getSource() == btn[5]) {
					tf.setText(buttonArray[5]);
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

				logger.debug(buttonArray[i] + ": " + (buttonArray[i].indexOf("_") == -1));

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

	public void mat() {
		mat.setBounds(410, 100, 440, 300);
		mat.setBackground(new Color(0, 0, 0, 122));

		add(mat); // 배팅 금액 패널

		JLabel littleMoney = new JLabel(new ImageIcon(RoomScreen.class.getResource("../../img/littleMoney.PNG")));
		JLabel manyMoney = new JLabel(new ImageIcon(RoomScreen.class.getResource("../../img/manyMoney.PNG")));
		littleMoney.setBounds(10, 10, 420, 280);
		manyMoney.setBounds(10, 10, 420, 280);
		tf = new JTextField(15);
		tf.setBounds(300, 300, 300, 150);
		mat.add(manyMoney);
		mat.add(tf);
	}
	private boolean initialized = false;
	public void turn(int index) {
		index = (index - mySit + 5) % 5;
		for (int i = 0; i < 5; i++) {
			if(panlist[i]==null) continue;
			panlist[i].setBorder(null);	
		}
		panlist[index].setBorder(new LineBorder(Color.orange, 1));
	} //turn();
	
	// 게임시작 버튼
	public void startBtnSet() {
		ImageIcon gameStartBasic = new ImageIcon(RoomScreen.class.getResource("../../img/button/GameStartBasic.PNG"));
		ImageIcon gameStartEnter = new ImageIcon(RoomScreen.class.getResource("../../img/button/GameStartEnter.PNG"));
		JButton gameStart = new JButton(gameStartBasic);
		gameStart.setBounds(510, 230, 240, 140);
		gameStart.setBorderPainted(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setFocusPainted(false);
		gameStart.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				gameStart.setIcon(gameStartEnter);
				gameStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gameStart.setIcon(gameStartBasic);
				gameStart.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
					Packing.sender(PlayerVO.myVO.getPwSocket(), new Packet(Protocol.GAMESTART));
					gameStart.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "플레이어가 2명 이상일 때만 시작 가능합니다.", "알림", JOptionPane.WARNING_MESSAGE);
				}
			} // mousePressed();
		});
		add(gameStart);
	}
	
	public void mainScreen() {
		totalMoney.setBounds(420, 10, 420, 50);
		totalMoney.setFont(new Font("Rosewood Std", Font.PLAIN, 50));
		totalMoney.setForeground(Color.yellow);
		totalMoney.setHorizontalAlignment(JLabel.CENTER);
		add(totalMoney);
		
		for (int i = 0; i < 5; i++) {
			panlist[i] = new JPanel();
			if (i == 0) { panlist[i].setBounds(460, 440, 350, 180); } else 
			if (i == 1) { panlist[i].setBounds(  0, 215, 350, 180); } else 
			if (i == 2) { panlist[i].setBounds(  0,  30, 350, 180);	} else
			if (i == 3) { panlist[i].setBounds(915,  30,3590, 180); } else
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

		// 게임 시작 버튼
		startBtnSet();
		
		// 나가기 버튼
		JButton exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/gExitBtn.PNG")));
		exitBtn.setBounds(1105, 560, 150, 50);
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
	} //mainScreen();

	// 시작 돈을 걷고 Text에 적용
	public void startPay(long sMoney) {
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (moneyText[i] == null) {
				continue;
			}
			count++;
			moneyText[i].setText(Long.parseLong(moneyText[i].getText().replaceAll(",", "")) - sMoney + "");
		} // for
		totalMoney.setText(count * sMoney + "");
	} // startPay();

	public void betAlert(int idx, String bet, String money) {
		idx = (idx - mySit + 5) % 5;
		moneyText[idx].setText(money);
		long calcMoney = Long.parseLong(totalMoney.getText());
		totalMoney.setText(calcMoney + Long.parseLong(money) + "");
		betText[idx].setText(bet);
		betIcon(idx, bet);
	} // betAlert();

	public void betIcon(int idx, String bet) {
		ImageIcon iCon = new ImageIcon(RoomScreen.class.getResource("../../img/button/하프.png"));
		
		beticon[idx] = new JLabel(iCon);
		switch (idx) {
		case 0:
			beticon[idx].setBounds(729, 468, 95, 55);
			add(beticon[idx]);
			break;
		case 1:
			beticon[idx].setBounds(314, 70, 95, 55);
			add(beticon[idx]);
			break;
		case 2:
			beticon[idx].setBounds(314, 26, 95, 55);
			add(beticon[idx]);
			break;
		case 3:
			beticon[idx].setBounds(840, 70, 95, 55);
			add(beticon[idx]);
			break;
		case 4:
			beticon[idx].setBounds(840, 260, 95, 55);
			add(beticon[idx]);
			break;
		}
	} //betIcon();

	public ImageIcon cardFormat(float card) {
		return  new ImageIcon(RoomScreen.class.getResource("../../img/card/"+
				String.format("%." +((int) card == card ? "0" : "1")+"f", card) +".png"));
	}
	
	public void openCard(Map<Integer,PlayerVO> cardMap) {
		
		for (int i = 0; i < 5; i++) {
			if(cardMap.get(i)==null) continue;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) { e.printStackTrace(); }
			int idx = (i - mySit + 5) % 5;
			
			float c1 = cardMap.get(i).getCard1();
			float c2 = cardMap.get(i).getCard2();
			
			card1[idx].setIcon(cardFormat(c1));
			card2[idx].setIcon(cardFormat(c2));
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

		for (int i = 1; i < 5; i++) {
			if (profile[i] != null) {
				card1[i].setIcon(new ImageIcon(RoomScreen.class.getResource("../../img/card/Pae.png")));
			}
		} // for
	} // receiveCard1();

	public void gameOver(String winerMsg, int winerIdx, String winMoney) {
		JOptionPane.showMessageDialog(null, winerMsg, "알림", JOptionPane.WARNING_MESSAGE);
		winerIdx = (winerIdx - mySit + 5) % 5;
		moneyText[winerIdx].setText(winMoney);
		RoomScreen.getInstance().gameStart = false;
	}

	/**
	 * @param i     몇번째 자리
	 * @param setVO 자리에 앉을 사람의 VO
	 */
	public void setSit(int i, PlayerVO setVO) {
		try {
			card1[i] = new JLabel();
			card2[i] = new JLabel();

			nicText[i] = new JLabel(setVO.getNic());
			nicText[i].setForeground(Color.white);
			nicText[i].setHorizontalAlignment(JLabel.CENTER);
			nicText[i].setFont(new Font("휴먼옛체", Font.PLAIN, 15));
			System.out.println("nicText: "+nicText);
			
			moneyText[i] = new JLabel(fm.format(setVO.getMoney()));
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
			panlist[i].setBackground(new Color(0, 0, 0));
			
			if (i==0||i==1||i==2) {
				profile[i].setBounds(10, 10, 90, 100);
				card1[i].setBounds(115, 10, 110, 160);
				card2[i].setBounds(230, 10, 110, 160);
				betText[i].setBounds(10, 10, 90, 20);
				nicText[i].setBounds(10, 125, 90, 20);
				moneyText[i].setBounds(10, 150, 90, 20);

			} else if (i==3||i==4) {
				profile[i].setBounds(250, 10, 90, 100);
				card1[i].setBounds(10, 10, 110, 160);
				card2[i].setBounds(125, 10, 110, 160);
				betText[i].setBounds(250, 10, 90, 20);
				nicText[i].setBounds(250, 125, 90, 20);
				moneyText[i].setBounds(250, 150, 90, 20);
			}
			
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

			j = (mySit - i + 5) % 5;
			
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