package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;
import client.service.ClientPacketController;
import music.MusicPlayer;
import operator.ChattingOperator;
import vo.PlayerVO;

public class MainScreen extends JFrame {

	public final int SCREEN_WIDTH = 1280;
	public final int SCREEN_HEIGHT = 720;

	public static MainScreen ms = new MainScreen();

	private Container content;
	private Background back = new Background();
	private JTextField tf;
	private JPanel[] masterlist = new JPanel[5];
	private int master;
	private JButton btn1, btn2, btn3, btn4, btn5, btn6;
	private Map<Integer, PlayerVO> playerListMap = new HashMap<>();

	// JLabel frame = new JLabel(new
	// ImageIcon(MainScreen.class.getResource("../../img/fff.png")));
	JLabel masterSticker = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/master.PNG")));
	private JPanel[] panlist = new JPanel[5];
	private JLabel[] card1 = new JLabel[5];
	private JLabel[] card2 = new JLabel[5];
	JTextField[] nicText = new JTextField[5];
	JTextField[] moneyText = new JTextField[5];
	JLabel[] profile = new JLabel[5];

	public void exitPlayer(PlayerVO vo) {
		for (int i = 0; i < 5; i++) {
			if (playerListMap.get(i).getNo() == vo.getNo()) {
				playerListMap.remove(i);
				break;
			} // if
		} // for
	} // exitPlayer

	private MainScreen() {
	}

	// public static void removeMainScreen() {
	// if(ms1.isDisplayable()) {
	// ms1.dispose();
	// }
	// ms1 = null;
	// }

	// public static MainScreen getMainScreen() {
	// if(ms1==null) {
	// ms1 = new MainScreen();
	// ms1.mainScreen();
	// }
	// return ms1;
	// }

	public void turn(int k) {
		if (k == 0) {
			// frame.setBounds(450, 430, 370, 190);
			masterSticker.setBounds(430, 440, 15, 15);
			masterSticker.setOpaque(true);
		} else if (k == 1) {
			// frame.setBounds(-5, 205, 370, 190);
			masterSticker.setBounds(370, 220, 15, 15);
			masterSticker.setOpaque(true);
		} else if (k == 2) {
			// frame.setBounds(-5, 20, 370, 190);
			masterSticker.setBounds(370, 30, 15, 15);
			masterSticker.setOpaque(true);
		} else if (k == 3) {
			// frame.setBounds(905, 20, 370, 190);
			masterSticker.setBounds(890, 30, 15, 15);
			masterSticker.setOpaque(true);
		} else if (k == 4) {
			// frame.setBounds(905, 205, 370, 190);
			masterSticker.setBounds(890, 220, 15, 15);
			masterSticker.setOpaque(true);
		}
		// frame.setOpaque(false);
		// add(frame);
		add(masterSticker);

		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } //시간 제한
	} // 게임 진행 순서

	public void buttonSet() {
		JPanel pan = new JPanel();
		pan.setBounds(0, 620, 1265, 60);
		pan.setLayout(new GridLayout(1, 6));
		pan.setOpaque(false);
		add(pan); // 배팅 버튼 패널

//	       if (k == 0) {
		JButton btn1 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Allin.PNG")));
		btn1.setOpaque(false);
		pan.add(btn1);
		JButton btn2 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Half.PNG")));
		btn2.setOpaque(false);
		pan.add(btn2);
		JButton btn3 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Quater.PNG")));
		btn3.setOpaque(false);
		pan.add(btn3);
		JButton btn4 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Bbing.PNG")));
		btn4.setOpaque(false);
		pan.add(btn4);
		JButton btn5 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Call.PNG")));
		btn5.setOpaque(false);
		pan.add(btn5);
		JButton btn6 = new JButton(new ImageIcon(MainScreen.class.getResource("../../img/Die.PNG")));
		btn6.setOpaque(false);
		pan.add(btn6);
		// } else {
		// JButton btn1 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Allin_.PNG")));
		// btn1.setOpaque(false);
		// pan.add(btn1);
		// JButton btn2 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Half_.PNG")));
		// btn2.setOpaque(false);
		// pan.add(btn2);
		// JButton btn3 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Quater_.PNG")));
		// btn3.setOpaque(false);
		// pan.add(btn3);
		// JButton btn4 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Bbing_.PNG")));
		// btn4.setOpaque(false);
		// pan.add(btn4);
		// JButton btn5 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Call_.PNG")));
		// btn5.setOpaque(false);
		// pan.add(btn5);
		// JButton btn6 = new JButton(new
		// ImageIcon(MainScreen.class.getResource("../../img/Die_.PNG")));
		// btn6.setOpaque(false);
		// pan.add(btn6);
		// }
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getSource());
				System.out.println(btn1);
				if (e.getSource() == btn1) {
					tf.setText("올인");
				} else if (e.getSource() == btn2) {
					tf.setText("하프");
				} else if (e.getSource() == btn3) {
					tf.setText("쿼터");
				} else if (e.getSource() == btn4) {
					tf.setText("삥");
				} else if (e.getSource() == btn5) {
					tf.setText("콜");
				} else if (e.getSource() == btn6) {
					tf.setText("다이");
				}
			}
		};

		btn1.addActionListener(action);
		btn2.addActionListener(action);
		btn3.addActionListener(action);
		btn4.addActionListener(action);
		btn5.addActionListener(action);
		btn6.addActionListener(action); // 버튼 클릭 시 텍스트 표시

		// }else {
		// JLabel btn1 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Allin_.PNG")));
		// btn1.setOpaque(false);
		// pan.add(btn1);
		// JLabel btn2 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Half_.PNG")));
		// btn2.setOpaque(false);
		// pan.add(btn2);
		// JLabel btn3 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Quater_.PNG")));
		// btn3.setOpaque(false);
		// pan.add(btn3);
		// JLabel btn4 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Bbing_.PNG")));
		// btn4.setOpaque(false);
		// pan.add(btn4);
		// JLabel btn5 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Call_.PNG")));
		// btn5.setOpaque(false);
		// pan.add(btn5);
		// JLabel btn6 = new JLabel(new
		// ImageIcon(MainScreen.class.getResource("../../img/Die_.PNG")));
		// btn6.setOpaque(false);
		// pan.add(btn6);
		// } //비활성화 버튼

		setTitle("섯다 온라인");

		back.mainImage();
		content = getContentPane();
		content.add(back, BorderLayout.CENTER);
		back.setOpaque(false);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setResizable(false);
		setVisible(true); // 배경화면
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(MainScreen.class.getResource("../../img/titleIcon.jpg"));
		setIconImage(img);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 1274, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 691, Short.MAX_VALUE));
		getContentPane().setLayout(groupLayout);

	}

	public void mat() {
		JPanel pan6 = new JPanel();
		pan6.setBounds(410, 100, 440, 300);
		pan6.setBackground(new Color(0, 0, 0, 122));

		add(pan6); // 배팅 금액 패널

		JLabel littleMoney = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/littleMoney.PNG")));
		JLabel manyMoney = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/manyMoney.PNG")));
		littleMoney.setBounds(10, 10, 420, 280);
		manyMoney.setBounds(10, 10, 420, 280);
		tf = new JTextField(15);
		tf.setBounds(300, 300, 400, 150);

		// if(stackMoney >= 10000000) {
		// pan6.add(littleMoney);
		// }else {
		pan6.add(manyMoney);
		// }
		pan6.add(tf);
//	      pan.add(tf);
	}

	public void mainScreen() {
		// turn(0);
		// turn(1);
		// turn(2);
		// turn(3);
		// turn(4);

		for (int i = 0; i < panlist.length; i++) {
			panlist[i] = new JPanel();

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
			panlist[i].setLayout(null);
			panlist[i].setBackground(new Color(0, 0, 0, 122));
			add(panlist[i]);
		}

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
		chatText.setBorder(new LineBorder(Color.orange, 3));
		ChattingOperator co = ChattingOperator.getInstance();

		ChattingOperator.chatArea.setEditable(false);
		ChattingOperator.chatArea.setLineWrap(true);
		ChattingOperator.chatArea.setBorder(new LineBorder(Color.orange, 3));
		ClientPacketController.scrollPane.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		ClientPacketController.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ClientPacketController.scrollPane.setBounds(10, 10, 400, 165);
		ClientPacketController.scrollPane.setBorder(null);
		chatPan.add(ClientPacketController.scrollPane);

		// 채팅 보내기 버튼
		JButton chatBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/Send.PNG")));
		chatBtn.setBounds(340, 186, 70, 25);
		chatPan.add(chatBtn);
		JRootPane rootPane = this.getRootPane();
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

		// 나가기 버튼
		JButton exitBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/exitBtn.PNG")));
		exitBtn.setBounds(1105, 560, 150, 50);
		add(exitBtn);

		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == exitBtn) {
					System.exit(0);
				}
			}
		});
		
		new Thread(new MusicPlayer()).start(); // 배경음악
		
	}

	public void enterPlayerList(Map<Integer, PlayerVO> voList) {
		System.out.println("voList" + voList);
		System.out.println("voList" + voList);
		playerListMap = voList;
		for (int i = 0; i < 5; i++) {
			if (voList.get(i) == null) {
				continue;
			}
			try {
				// JTextField nicText = new JTextField(voList.get(i).getNic());
				// JTextField nicText2 = new JTextField(voList.get(i).getNic());
				// JTextField nicText3 = new JTextField(voList.get(i).getNic());
				// JTextField nicText4 = new JTextField(voList.get(i).getNic());
				card1[i] = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/Pae.PNG")));
				card2[i] = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/Pae.PNG")));
				nicText[i] = new JTextField(voList.get(i).getNic());
				moneyText[i] = new JTextField(voList.get(i).getMoney() + "");
				if (i == 1 || i == 2) {
					profile[i] = new JLabel(new ImageIcon(MainScreen.class
							.getResource("../../img/character/cha" + voList.get(i).getCha() + "_.PNG")));
				} else {
					profile[i] = new JLabel(new ImageIcon(
							MainScreen.class.getResource("../../img/character/cha" + voList.get(i).getCha() + ".PNG")));
				}
				if (i == 1) {
					panlist[i].add(nicText[i]);
					panlist[i].add(moneyText[i]);
					panlist[i].add(card1[i]);
					panlist[i].add(card2[i]);
					panlist[i].add(profile[i]);

					profile[i].setBounds(10, 10, 90, 100);
					profile[i].setBackground(new Color(35, 60, 3, 122));
					profile[i].setOpaque(true);

					card1[i].setBounds(115, 10, 110, 160);
					card1[i].setOpaque(false);

					card2[i].setBounds(230, 10, 110, 160);
					card2[i].setOpaque(false);

					nicText[i].setBounds(10, 125, 90, 20);
					nicText[i].setOpaque(true);

					moneyText[i].setBounds(10, 150, 90, 20);
					moneyText[i].setOpaque(true);

				} else if (i == 2) {
					panlist[i].add(nicText[i]);
					panlist[i].add(moneyText[i]);
					panlist[i].add(card1[i]);
					panlist[i].add(card2[i]);
					panlist[i].add(profile[i]);

					profile[i].setBounds(10, 10, 90, 100);
					profile[i].setBackground(new Color(35, 60, 3, 122));
					profile[i].setOpaque(true);

					card1[i].setBounds(115, 10, 110, 160);
					card1[i].setOpaque(false);

					card2[i].setBounds(230, 10, 110, 160);
					card2[i].setOpaque(false);

					nicText[i].setBounds(10, 125, 90, 20);
					nicText[i].setOpaque(true);

					moneyText[i].setBounds(10, 150, 90, 20);
					moneyText[i].setOpaque(true);

				} else if (i == 3) {
					panlist[i].add(nicText[i]);
					panlist[i].add(moneyText[i]);
					panlist[i].add(card1[i]);
					panlist[i].add(card2[i]);
					panlist[i].add(profile[i]);

					profile[i].setBounds(250, 10, 90, 100);
					profile[i].setBackground(new Color(35, 60, 3, 122));
					profile[i].setOpaque(true);

					card1[i].setBounds(10, 10, 110, 160);
					card1[i].setOpaque(false);

					card2[i].setBounds(125, 10, 110, 160);
					card2[i].setOpaque(false);

					nicText[i].setBounds(250, 125, 90, 20);
					nicText[i].setOpaque(true);

					moneyText[i].setBounds(250, 150, 90, 20);
					moneyText[i].setOpaque(true);

				} else if (i == 4) {
					panlist[i].add(nicText[i]);
					panlist[i].add(moneyText[i]);
					panlist[i].add(card1[i]);
					panlist[i].add(card2[i]);
					panlist[i].add(profile[i]);

					profile[i].setBounds(250, 10, 90, 100);
					profile[i].setBackground(new Color(35, 60, 3, 122));
					profile[i].setOpaque(true);

					card1[i].setBounds(10, 10, 110, 160);
					card1[i].setOpaque(false);

					card2[i].setBounds(125, 10, 110, 160);
					card2[i].setOpaque(false);

					nicText[i].setBounds(250, 125, 90, 20);
					nicText[i].setOpaque(true);

					moneyText[i].setBounds(250, 150, 90, 20);
					moneyText[i].setOpaque(true);

				} else if (i == 0) {
					panlist[i].add(nicText[i]);
					panlist[i].add(moneyText[i]);
					panlist[i].add(card1[i]);
					panlist[i].add(card2[i]);
					panlist[i].add(profile[i]);

					profile[i].setBounds(10, 10, 90, 100);
					profile[i].setBackground(new Color(35, 60, 3, 122));
					profile[i].setOpaque(true);

					card1[i].setBounds(115, 10, 110, 160);
					card1[i].setOpaque(false);

					card2[i].setBounds(230, 10, 110, 160);
					card2[i].setOpaque(false);

					nicText[i].setBounds(10, 125, 90, 20);
					nicText[i].setOpaque(true);

					moneyText[i].setBounds(10, 150, 90, 20);
					moneyText[i].setOpaque(true);

				}

				// nicText.setHorizontalAlignment(JLabel.CENTER);
				// moneyText.setHorizontalAlignment(JLabel.CENTER);
				// nicText.setForeground(Color.white);
				// moneyText.setForeground(Color.white);
				// add(panlist[i]);

			} catch (Exception e) {
				System.out.println("빈자리 감지|MainScreen 324");
				break;
			}
		}

	} // enterPlayerList

	public void enterPlayerOther(PlayerVO vo) {
		int i = 0;

		for (int j = 0; j < 5; j++) {
			if (playerListMap.get(j) == null) {
				i = j;
				break;
			} // if
		} // for

		playerListMap.put(i, vo);

		try {

			card1[i] = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/Pae.PNG")));
			card2[i] = new JLabel(new ImageIcon(MainScreen.class.getResource("../../img/Pae.PNG")));
			nicText[i] = new JTextField(vo.getNic());
			moneyText[i] = new JTextField(vo.getMoney() + "");
			if (i == 1 || i == 2) {
				profile[i] = new JLabel(
						new ImageIcon(MainScreen.class.getResource("../../img/character/cha" + vo.getCha() + "_.png")));
			} else {
				profile[i] = new JLabel(
						new ImageIcon(MainScreen.class.getResource("../../img/character/cha" + vo.getCha() + ".png")));
			}
			if (i == 1) {
				panlist[i].add(nicText[i]);
				panlist[i].add(moneyText[i]);
				panlist[i].add(card1[i]);
				panlist[i].add(card2[i]);
				panlist[i].add(profile[i]);

				profile[i].setBounds(10, 10, 90, 100);
				profile[i].setBackground(new Color(35, 60, 3, 122));
				profile[i].setOpaque(true);

				card1[i].setBounds(115, 10, 110, 160);
				card1[i].setOpaque(false);

				card2[i].setBounds(230, 10, 110, 160);
				card2[i].setOpaque(false);

				nicText[i].setBounds(10, 125, 90, 20);
				nicText[i].setOpaque(true);

				moneyText[i].setBounds(10, 150, 90, 20);
				moneyText[i].setOpaque(true);

			} else if (i == 2) {
				panlist[i].add(nicText[i]);
				panlist[i].add(moneyText[i]);
				panlist[i].add(card1[i]);
				panlist[i].add(card2[i]);
				panlist[i].add(profile[i]);

				profile[i].setBounds(10, 10, 90, 100);
				profile[i].setBackground(new Color(35, 60, 3, 122));
				profile[i].setOpaque(true);

				card1[i].setBounds(115, 10, 110, 160);
				card1[i].setOpaque(false);

				card2[i].setBounds(230, 10, 110, 160);
				card2[i].setOpaque(false);

				nicText[i].setBounds(10, 125, 90, 20);
				nicText[i].setOpaque(true);

				moneyText[i].setBounds(10, 150, 90, 20);
				moneyText[i].setOpaque(true);

			} else if (i == 3) {
				panlist[i].add(nicText[i]);
				panlist[i].add(moneyText[i]);
				panlist[i].add(card1[i]);
				panlist[i].add(card2[i]);
				panlist[i].add(profile[i]);

				profile[i].setBounds(250, 10, 90, 100);
				profile[i].setBackground(new Color(35, 60, 3, 122));
				profile[i].setOpaque(true);

				card1[i].setBounds(10, 10, 110, 160);
				card1[i].setOpaque(false);

				card2[i].setBounds(125, 10, 110, 160);
				card2[i].setOpaque(false);

				nicText[i].setBounds(250, 125, 90, 20);
				nicText[i].setOpaque(true);

				moneyText[i].setBounds(250, 150, 90, 20);
				moneyText[i].setOpaque(true);

			} else if (i == 4) {
				panlist[i].add(nicText[i]);
				panlist[i].add(moneyText[i]);
				panlist[i].add(card1[i]);
				panlist[i].add(card2[i]);
				panlist[i].add(profile[i]);

				profile[i].setBounds(250, 10, 90, 100);
				profile[i].setBackground(new Color(35, 60, 3, 122));
				profile[i].setOpaque(true);

				card1[i].setBounds(10, 10, 110, 160);
				card1[i].setOpaque(false);

				card2[i].setBounds(125, 10, 110, 160);
				card2[i].setOpaque(false);

				nicText[i].setBounds(250, 125, 90, 20);
				nicText[i].setOpaque(true);

				moneyText[i].setBounds(250, 150, 90, 20);
				moneyText[i].setOpaque(true);

			} else if (i == 0) {
				panlist[i].add(nicText[i]);
				panlist[i].add(moneyText[i]);
				panlist[i].add(card1[i]);
				panlist[i].add(card2[i]);
				panlist[i].add(profile[i]);

				profile[i].setBounds(10, 10, 90, 100);
				profile[i].setBackground(new Color(35, 60, 3, 122));
				profile[i].setOpaque(true);

				card1[i].setBounds(115, 10, 110, 160);
				card1[i].setOpaque(false);

				card2[i].setBounds(230, 10, 110, 160);
				card2[i].setOpaque(false);

				nicText[i].setBounds(10, 125, 90, 20);
				nicText[i].setOpaque(true);

				moneyText[i].setBounds(10, 150, 90, 20);
				moneyText[i].setOpaque(true);

			}

			// nicText.setHorizontalAlignment(JLabel.CENTER);
			// moneyText.setHorizontalAlignment(JLabel.CENTER);
			// nicText.setForeground(Color.white);
			// moneyText.setForeground(Color.white);
			// add(panlist[i]);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("빈자리 감지|MainScreen 324");
		}

	} // enterPlayerList

	public static void main(String[] args) {
		Map<Integer, PlayerVO> voList = new HashMap<Integer, PlayerVO>();

		voList.put(0, new PlayerVO("1hyo", 3, 600000));
		voList.put(1, new PlayerVO("2hyo", 3, 34600000));
		voList.put(2, new PlayerVO("3hyo", 3, 1345000));
		voList.put(3, new PlayerVO("4hyo", 3, 120000));
		voList.put(4, new PlayerVO("5hyo", 3, 4000));

		MainScreen ms = new MainScreen();
		ms.mainScreen();
		ms.enterPlayerList(voList);
		ms.buttonSet();
		ms.mat();
	}
}