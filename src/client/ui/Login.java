package client.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.*;

import javax.swing.*;

import client.ReceiveServerPacket;
import dao.PlayerDAO;
import vo.PlayerVO;

public class Login extends JFrame {
	private PlayerDAO playerDAO = new PlayerDAO();
	private PlayerVO playerVO = new PlayerVO();
	Pattern idPt = null;
	Matcher match;

	boolean idCheck = false;
	boolean pwCheck = false;
	boolean pwCheck2 = false;
	boolean nickCheck = false;

	public void login() {

		JFrame logJF = new JFrame("로 그 인 창");
		logJF.setSize(416, 220);
		logJF.setLocation(550, 300);
		logJF.setLayout(null);
		logJF.setResizable(false);
		JLabel lbl = new JLabel("아이디와 비밀번호를 입력해주세요");
		lbl.setBounds(100, 0, 1100, 30);
		logJF.add(lbl);

		JButton adminBtn = new JButton(new ImageIcon(Login.class.getResource("../img/admin.jpg")));
		adminBtn.setBounds(380, 0, 20, 20);
		logJF.add(adminBtn);

		adminBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == adminBtn) {
					JFrame adminJF = new JFrame("관리자");
					adminJF.setSize(300, 200);
					adminJF.setLocation(610, 280);
					adminJF.setLayout(null);
					adminJF.setResizable(false);

					JLabel adminLbl = new JLabel("관리자 모드");
					adminJF.add(adminLbl);
					adminLbl.setBounds(100, 10, 100, 20);

					JButton adLogBtn = new JButton("로그인");
					adLogBtn.setBounds(200, 40, 70, 90);
					adminJF.add(adLogBtn);

					JPanel adIdPal = new JPanel();
					JLabel adIdLbl = new JLabel("아이디");
					JTextField adIdTxt = new JTextField(10);
					adIdPal.setBounds(6, 50, 200, 30);
					adminJF.add(adIdPal);
					adIdPal.add(adIdLbl);
					adIdPal.add(adIdTxt);

					JPanel adPwPal = new JPanel();
					JLabel adPwLbl = new JLabel("비밀번호");
					JPasswordField adPwTxt = new JPasswordField(10);
					adPwPal.setBounds(0, 90, 200, 30);
					adminJF.add(adPwPal);
					adPwPal.add(adPwLbl);
					adPwPal.add(adPwTxt);
					adPwTxt.setEchoChar('*');

					dispose();
					adminJF.setVisible(true);
				}
			}
		});

		JPanel idPal = new JPanel();
		JLabel idLbl = new JLabel("아이디");
		JTextField idTxt = new JTextField(10);
		idPal.setBounds(-5, 50, 215, 30);
		logJF.add(idPal);
		idPal.add(idLbl);
		idPal.add(idTxt);

		JPanel pwPal = new JPanel();
		JLabel pwLbl = new JLabel("비밀번호");
		JPasswordField pwTxt = new JPasswordField(10);
		pwPal.setBounds(-11, 80, 215, 30);
		logJF.add(pwPal);
		pwPal.add(pwLbl);
		pwPal.add(pwTxt);
		pwTxt.setEchoChar('*');

		JLabel logLbl = new JLabel();
		logLbl.setBounds(15, 110, 230, 30);
		logLbl.setForeground(Color.red);
		logJF.add(logLbl);

		JButton logBtn = new JButton("로그인");
		logBtn.setBounds(210, 40, 70, 100);
		logJF.add(logBtn);
		JRootPane rootPane = logJF.getRootPane();
		rootPane.setDefaultButton(logBtn);

		logBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == logBtn) {
					if (idTxt.getText().equals("") || (pwTxt.getText().equals(""))) {
						logLbl.setText("아이디 또는 비밀번호를 입력하세요.");
					} else if ((playerVO = playerDAO.login(idTxt.getText(), pwTxt.getText())) != null) {
						if (playerVO.isOnline() == false) {

							try {
								logJF.dispose();
								playerVO.setSocketWithBrPw(new Socket(playerDAO.getServerIP(), 4888));
								new Thread(new ReceiveServerPacket(playerVO.getSocket())).start();
								new Lobby(playerVO);

							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						} else if (playerVO.isOnline() == true) {
							logLbl.setText("이미 접속하고 있는 아이디입니다.");
						}
					} else if (playerDAO.login(idTxt.getText(), pwTxt.getText()) == null) {
						logLbl.setText("가입을 하지 않은 아이디 입니다.");
					}
				}
			}
		});

		JButton joinBtn = new JButton("회원가입");
		joinBtn.setBounds(290, 40, 85, 100);
		logJF.add(joinBtn);
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == joinBtn) {
					JFrame joinJF = new JFrame("회 원 가 입");
					joinJF.setResizable(false);
					joinJF.setSize(350, 380);
					joinJF.setLocation(600, 200);
					joinJF.setLayout(null);

					JLabel jLbl = new JLabel("새롭게 만들 아이디와 비밀번호를 입력해주세요");
					jLbl.setBounds(35, 5, 1100, 30);
					jLbl.setForeground(Color.PINK);
					joinJF.add(jLbl);

					JLabel exLbl = new JLabel("아이디는 5 ~ 10자리  비밀번호는 영어 + 숫자로만 가능합니다.");
					exLbl.setBounds(15, 30, 1100, 30);
					exLbl.setFont(exLbl.getFont().deriveFont(11.0f));
					exLbl.setForeground(Color.PINK);
					joinJF.add(exLbl);

					JPanel joIdPal = new JPanel();
					JLabel joIdLbl = new JLabel("아이디");
					JTextField joIdTxt = new JTextField(10);
					joIdPal.setBounds(19, 60, 200, 30);
					joinJF.add(joIdPal);
					joIdPal.add(joIdLbl);
					joIdPal.add(joIdTxt);

					JLabel joLbl = new JLabel();
					joLbl.setBounds(40, 85, 200, 30);
					joLbl.setForeground(Color.red);
					joinJF.add(joLbl);

					JButton orBtn = new JButton("중복체크");
					orBtn.setBounds(220, 65, 85, 20);
					joinJF.add(orBtn);

					orBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == orBtn) {
								idPt = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");

								match = idPt.matcher(joIdTxt.getText());
								if (match.find()) {
									if (joIdTxt.getText().equals("")) {
										joLbl.setText("아이디를 입력해주세요.");
									} else if (joIdTxt.getText().length() > 10 || joIdTxt.getText().length() < 5) {
										joLbl.setText("5 ~ 10자리 이하만 가능합니다.");
									} else if (playerDAO.selectID(joIdTxt.getText())) {
										joLbl.setText("이미 생성된 아이디 입니다.");
									} else if (joIdTxt.getText().length() <= 10 && joIdTxt.getText().length() >= 5) {
										joLbl.setText("생성 가능한 아이디 입니다.");
										idCheck = true;
									}
								} else if (!(joIdTxt.getText().matches("^[a-zA-Z0-9]*$"))) {
									joLbl.setText("영어와 숫자만 입력 가능합니다.");
								} else if (!(joIdTxt.getText().matches("^[a-zA-Z]{1}$"))) {
									joLbl.setText("앞자리는 영어만 가능합니다.");
								}
							}

						}
					});

					JPanel joPwPal = new JPanel();
					JLabel joPwLbl = new JLabel("비밀번호");
					JPasswordField joPwTxt = new JPasswordField(10);
					joPwPal.setBounds(13, 115, 200, 30);
					joinJF.add(joPwPal);
					joPwPal.add(joPwLbl);
					joPwPal.add(joPwTxt);
					joPwTxt.setEchoChar('*');

					JPanel joPwPal2 = new JPanel();
					JLabel joPwLbl2 = new JLabel("비밀번호 확인");
					JPasswordField joPwTxt2 = new JPasswordField(10);
					joPwPal2.setBounds(-1, 170, 200, 30);
					joPwLbl2.setFont(joPwLbl2.getFont().deriveFont(12.0f));
					joinJF.add(joPwPal2);
					joPwPal2.add(joPwLbl2);
					joPwPal2.add(joPwTxt2);
					joPwTxt2.setEchoChar('*');

					JLabel joLbl2 = new JLabel("5 ~ 10자리를 입력하세요.");
					joLbl2.setBounds(65, 140, 200, 30);
					joLbl2.setForeground(Color.red);
					joinJF.add(joLbl2);

					JLabel joLbl3 = new JLabel("비밀번호를 입력해주세요.");
					joLbl3.setBounds(65, 195, 200, 30);
					joLbl3.setForeground(Color.red);
					joinJF.add(joLbl3);

					joPwTxt.addKeyListener(new KeyListener() {

						@Override
						public void keyTyped(KeyEvent e) {

						}

						@Override
						public void keyPressed(KeyEvent e) {

							if (joPwTxt.getText().length() >= 4 && joPwTxt.getText().length() <= 9) {
								joLbl2.setText("가능한 비밀번호입니다.");
								pwCheck2 = true;
								joPwTxt2.addKeyListener(new KeyListener() {

									@Override
									public void keyTyped(KeyEvent e) {

									}

									@Override
									public void keyReleased(KeyEvent e) {
										if (joPwTxt.getText().equals(joPwTxt2.getText())) {
											joLbl3.setText("일치");
											pwCheck = true;
										} else if (!(joPwTxt.getText().equals(joPwTxt2.getText()))) {
											joLbl3.setText("불일치");
											pwCheck = false;
										}
									}

									@Override
									public void keyPressed(KeyEvent e) {
									}

								});
							} else {
								joLbl2.setText("5 ~ 10자리를 입력하세요.");
								pwCheck2 = false;
							}
						}

						@Override
						public void keyReleased(KeyEvent e) {

						}

					});

					JPanel joNickPal = new JPanel();
					JLabel joNickLbl = new JLabel("닉네임");
					JTextField joNickTxt = new JTextField(10);
					joNickPal.setBounds(19, 225, 200, 30);
					joinJF.add(joNickPal);
					joNickPal.add(joNickLbl);
					joNickPal.add(joNickTxt);

					JButton joOrBtn2 = new JButton("중복체크");
					joOrBtn2.setBounds(220, 230, 85, 20);
					joinJF.add(joOrBtn2);

					JLabel joLbl4 = new JLabel();
					joLbl4.setBounds(40, 250, 180, 30);
					joLbl4.setForeground(Color.red);
					joinJF.add(joLbl4);

					joOrBtn2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == joOrBtn2) {
								idPt = Pattern.compile("^[a-zA-Z0-9가-힣]*$");
								match = idPt.matcher(joNickTxt.getText());
								if (match.find()) {
									if (joNickTxt.getText().equals("")) {
										joLbl4.setText("닉네임을 입력해주세요.");
									} else if (joNickTxt.getText().length() > 7 && joNickTxt.getText().length() < 2) {
										joLbl4.setText("2 ~ 6자리 미만만 가능합니다.");
									} else if (playerDAO.selectNick(joNickTxt.getText())) {
										joLbl4.setText("이미 생성된 닉네임 입니다.");
									} else if (joNickTxt.getText().length() <= 6 && joNickTxt.getText().length() >= 2) {
										joLbl4.setText("생성 가능한 닉네임 입니다.");
										nickCheck = true;
									}
								} else {
									joLbl4.setText("특수문자는 불가능합니다.");
								}
							}
						}
					});

					JButton joBtn2 = new JButton("가 입 완 료");
					joBtn2.setBounds(50, 280, 150, 40);
					joinJF.add(joBtn2);

					joBtn2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == joBtn2) {
								if (!(joPwTxt.getText().equals(joPwTxt2.getText()))) {
									pwCheck2 = false;
									joLbl2.setText("");
									joLbl3.setText("비밀번호가 일치 하지 않습니다.");

								}
								if (idCheck && pwCheck && pwCheck2 && nickCheck) {

									JFrame sexJF = new JFrame("캐릭터 선택창");
									sexJF.setSize(500, 700);
									sexJF.setLayout(null);
									sexJF.setVisible(true);

									JLabel sexLbl = new JLabel("캐릭을 선택하세요.");
									sexLbl.setFont(new Font("d2coding", Font.BOLD, 20));
									sexLbl.setBounds(150, 20, 200, 20);
									sexJF.add(sexLbl);

									JRadioButton men = new JRadioButton("남자");
									JRadioButton women = new JRadioButton("여자");
									sexJF.add(men);
									sexJF.add(women);

									ButtonGroup bg = new ButtonGroup();
									bg.add(men);
									bg.add(women);

									men.setBounds(120, 520, 50, 50);
									women.setBounds(340, 520, 50, 50);

									JPanel sexPal = new JPanel();
									JLabel mLbl = new JLabel(new ImageIcon(Login.class.getResource("../img/men.png")));
									JLabel wLbl = new JLabel(
											new ImageIcon(Login.class.getResource("../img/women.png")));
									sexJF.add(mLbl);
									sexJF.add(wLbl);
									mLbl.setBounds(40, 80, 190, 430);
									wLbl.setBounds(260, 80, 190, 430);

									JButton okBtn = new JButton("선택");
									okBtn.setBounds(200, 580, 100, 40);
									sexJF.add(okBtn);

									okBtn.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
											if (e.getSource() == okBtn) {
												if (men.isSelected()) {
													playerVO.setCha(0);
												} else if (women.isSelected()) {
													playerVO.setCha(1);
												}
												try {
													playerVO.joinPlayer(joIdTxt.getText(), joPwTxt.getText(),
															joNickTxt.getText());
													if (1 == playerDAO.playerJoin(playerVO)) {
														JOptionPane.showMessageDialog(null, "회원가입을 축하드립니다.");
														sexJF.dispose();
														joinJF.dispose();

													}
												} catch (ClassNotFoundException e1) {
													e1.printStackTrace();
												}
											}

										}
									});

								} else
									JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호 또는 닉네임을 입력해주세요.");
							}
						}
					});
					dispose();
					joinJF.setVisible(true);
				}
			}
		});
		logJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logJF.setVisible(true);
	}

}
