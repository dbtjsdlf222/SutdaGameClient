package client.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimeTypeParameterList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import client.service.login.DocumentUpdateListener;
import dao.PlayerDAO;
import vo.PlayerVO;

public class JoinFrame {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinFrame.class);

	private PlayerDAO playerDAO;

	private boolean nicknameCheck= false;
	private boolean idCheck= false;
	private boolean mailCheck = false;
	private boolean mailCodeCheck = false;
	private boolean passwordCheck= false;
	private boolean passwordCheck2= false;
	private JFrame frame;
	private JTextField textFieldNickname;
	private JTextField textFieldId;
	private JTextField textFieldMail;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCheck;

	private JFrame characterJF;

	public JoinFrame(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
		initialize();
		frame.setVisible(true);
	} // JoinFrame();

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(700, 300, 444, 470);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		frame.setIconImage(img);
		frame.setTitle("회원가입");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 16, 0, 0, 64, 0, 16 };
		gridBagLayout.rowHeights = new int[] { 32, 0, 0, 32, 0, 0};
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0 };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblHint1 = new JLabel("새롭게 만들 아이디와 비밀번호를 입력해주세요.");
		lblHint1.setFont(new Font("굴림", Font.BOLD, 12));
		lblHint1.setForeground(Color.PINK);
		GridBagConstraints gbc_lblHint1 = new GridBagConstraints();
		gbc_lblHint1.gridwidth = 4;
		gbc_lblHint1.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint1.gridx = 1;
		gbc_lblHint1.gridy = 1;
		frame.getContentPane().add(lblHint1, gbc_lblHint1);

		JLabel lblHint2 = new JLabel("아이디와 비밀번호는 5~10자리 내로 입력 가능합니다.");
		lblHint2.setFont(new Font("굴림", Font.BOLD, 12));
		lblHint2.setForeground(Color.PINK);
		GridBagConstraints gbc_lblHint2 = new GridBagConstraints();
		gbc_lblHint2.gridwidth = 4;
		gbc_lblHint2.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint2.gridx = 1;
		gbc_lblHint2.gridy = 2;
		frame.getContentPane().add(lblHint2, gbc_lblHint2);
		
		JLabel lblHint3 = new JLabel("");
		lblHint3.setFont(new Font("굴림", Font.BOLD, 12));
		lblHint3.setForeground(Color.PINK);
		GridBagConstraints gbc_lblHint3 = new GridBagConstraints();
		gbc_lblHint3.gridwidth = 4;
		gbc_lblHint3.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint3.gridx = 1;
		gbc_lblHint3.gridy = 3;
		frame.getContentPane().add(lblHint3, gbc_lblHint3);

		JLabel lblNickName = new JLabel("닉네임");
		lblNickName.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNickName = new GridBagConstraints();
		gbc_lblNickName.fill = GridBagConstraints.VERTICAL;
		gbc_lblNickName.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickName.gridx = 1;
		gbc_lblNickName.gridy = 4;
		frame.getContentPane().add(lblNickName, gbc_lblNickName);

		textFieldNickname = new JTextField();
		GridBagConstraints gbc_textFieldNickname = new GridBagConstraints();
		gbc_textFieldNickname.gridwidth = 3;
		gbc_textFieldNickname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNickname.fill = GridBagConstraints.BOTH;
		gbc_textFieldNickname.gridx = 2;
		gbc_textFieldNickname.gridy = 4;
		frame.getContentPane().add(textFieldNickname, gbc_textFieldNickname);
		textFieldNickname.setColumns(10);

		JLabel lblNicknameErrorMessage = new JLabel("");
		lblNicknameErrorMessage.setFont(new Font("굴림", Font.BOLD, 12));
		lblNicknameErrorMessage.setForeground(Color.RED);
		GridBagConstraints gbc_lblNicknameErrorMessamge = new GridBagConstraints();
		gbc_lblNicknameErrorMessamge.gridwidth = 3;
		gbc_lblNicknameErrorMessamge.anchor = GridBagConstraints.WEST;
		gbc_lblNicknameErrorMessamge.insets = new Insets(0, 0, 5, 5);
		gbc_lblNicknameErrorMessamge.gridx = 2;
		gbc_lblNicknameErrorMessamge.gridy = 5;
		frame.getContentPane().add(lblNicknameErrorMessage, gbc_lblNicknameErrorMessamge);

		JLabel lblId = new JLabel("아이디");
		lblId.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.fill = GridBagConstraints.VERTICAL;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 6;
		frame.getContentPane().add(lblId, gbc_lblId);

		textFieldId = new JTextField();
		GridBagConstraints gbc_textFieldId = new GridBagConstraints();
		gbc_textFieldId.gridwidth = 3;
		gbc_textFieldId.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldId.fill = GridBagConstraints.BOTH;
		gbc_textFieldId.gridx = 2;
		gbc_textFieldId.gridy = 6;
		frame.getContentPane().add(textFieldId, gbc_textFieldId);
		textFieldId.setColumns(10);

		JLabel lblIdErrorMessage = new JLabel("");
		lblIdErrorMessage.setForeground(Color.RED);
		lblIdErrorMessage.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblIdErrorMessage = new GridBagConstraints();
		gbc_lblIdErrorMessage.gridwidth = 3;
		gbc_lblIdErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblIdErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdErrorMessage.gridx = 2;
		gbc_lblIdErrorMessage.gridy = 7;
		frame.getContentPane().add(lblIdErrorMessage, gbc_lblIdErrorMessage);

		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.fill = GridBagConstraints.VERTICAL;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 8;
		frame.getContentPane().add(lblPassword, gbc_lblPassword);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 8;
		frame.getContentPane().add(passwordField, gbc_passwordField);

		JLabel lblPasswordErrorMessage = new JLabel("");
		lblPasswordErrorMessage.setForeground(Color.RED);
		lblPasswordErrorMessage.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblPasswordErrorMessage = new GridBagConstraints();
		gbc_lblPasswordErrorMessage.gridwidth = 3;
		gbc_lblPasswordErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblPasswordErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswordErrorMessage.gridx = 2;
		gbc_lblPasswordErrorMessage.gridy = 9;
		frame.getContentPane().add(lblPasswordErrorMessage, gbc_lblPasswordErrorMessage);

		JLabel lblPasswordCheck = new JLabel("비밀번호 확인");
		lblPasswordCheck.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPasswordCheck = new GridBagConstraints();
		gbc_lblPasswordCheck.fill = GridBagConstraints.VERTICAL;
		gbc_lblPasswordCheck.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswordCheck.gridx = 1;
		gbc_lblPasswordCheck.gridy = 10;
		frame.getContentPane().add(lblPasswordCheck, gbc_lblPasswordCheck);

		passwordFieldCheck = new JPasswordField();
		GridBagConstraints gbc_passwordFieldCheck = new GridBagConstraints();
		gbc_passwordFieldCheck.gridwidth = 3;
		gbc_passwordFieldCheck.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldCheck.fill = GridBagConstraints.BOTH;
		gbc_passwordFieldCheck.gridx = 2;
		gbc_passwordFieldCheck.gridy = 10;
		frame.getContentPane().add(passwordFieldCheck, gbc_passwordFieldCheck);

		JLabel lblPasswordCheckErrorMessage = new JLabel("");
		lblPasswordCheckErrorMessage.setForeground(Color.RED);
		lblPasswordCheckErrorMessage.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblPasswordCheckErrorMessage = new GridBagConstraints();
		gbc_lblPasswordCheckErrorMessage.gridwidth = 3;
		gbc_lblPasswordCheckErrorMessage.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordCheckErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswordCheckErrorMessage.gridx = 2;
		gbc_lblPasswordCheckErrorMessage.gridy = 11;
		frame.getContentPane().add(lblPasswordCheckErrorMessage, gbc_lblPasswordCheckErrorMessage);

		
		/* -------이메일--------------*/
		JLabel lblMail = new JLabel("이메일");
		lblMail.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMail.insets = new Insets(0, 0, 5, 5);
		gbc_lblMail.gridx = 1;
		gbc_lblMail.gridy = 12;
		frame.getContentPane().add(lblMail, gbc_lblMail);
		
		textFieldMail = new JTextField();
		GridBagConstraints gbc_textFieldMail = new GridBagConstraints();
		gbc_textFieldMail.gridwidth = 3;
		gbc_textFieldMail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldMail.gridx = 2;
		gbc_textFieldMail.gridy = 12;
		frame.getContentPane().add(textFieldMail, gbc_textFieldMail);
		textFieldMail.setColumns(10);

		JLabel lblMailErrorMessage = new JLabel("");
		lblMailErrorMessage.setForeground(Color.RED);
		lblMailErrorMessage.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblMailErrorMessage = new GridBagConstraints();
		gbc_lblMailErrorMessage.gridwidth = 3;
		gbc_lblMailErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblMailErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMailErrorMessage.gridx = 2;
		gbc_lblMailErrorMessage.gridy = 13;
		frame.getContentPane().add(lblMailErrorMessage, gbc_lblMailErrorMessage);
		
		JButton btnMail = new JButton("이메일 인증");
		GridBagConstraints gbc_btnMail = new GridBagConstraints();
		gbc_btnMail.fill = GridBagConstraints.NONE;
		gbc_btnMail.gridwidth = 1;
		gbc_btnMail.insets = new Insets(0, 0, 5, 5);
		gbc_btnMail.gridx = 2;
		gbc_btnMail.gridy =14;
		
		JButton btnCode = new JButton("인증번호 입력");
		GridBagConstraints gbc_btnCode = new GridBagConstraints();
		gbc_btnCode.fill = GridBagConstraints.NONE;
		gbc_btnCode.gridwidth = 1;
		gbc_btnCode.insets = new Insets(0, 0, 5, 5);
		gbc_btnCode.gridx = 2;
		gbc_btnCode.gridy =14;
		
		JButton btnJoin = new JButton("회원가입");
		GridBagConstraints gbc_btnJoin = new GridBagConstraints();
		gbc_btnJoin.fill = GridBagConstraints.BOTH;
		gbc_btnJoin.gridwidth = 2;
		gbc_btnJoin.insets = new Insets(0, 0, 5, 5);
		gbc_btnJoin.gridx = 3;
		gbc_btnJoin.gridy = 15;
		frame.getContentPane().add(btnJoin, gbc_btnJoin);

		textFieldNickname.getDocument().addDocumentListener(new DocumentUpdateListener() {

			private Pattern nicknamePattern = Pattern.compile("^[a-zA-Z0-9가-힣]*$");
			private Thread t;

			@Override
			public void action(String text) {
				
				if(t != null && !t.isInterrupted()) t.interrupt();
				t = new Thread(() -> {
					try {
						nicknameCheck = false;
						String _text = null;
						
						Matcher match = nicknamePattern.matcher(text);
						if (match.find()) {
							if (text.equals("")) {
								_text = "닉네임을 입력해주세요.";
							} else if (2 > text.length() || text.length() > 6) {
								_text = "2 ~ 6자리 미만만 가능합니다.";
							} else if (playerDAO.selectNick(text)) {
								_text = "이미 생성된 닉네임 입니다.";
							} else if (text.length() <= 6 && text.length() >= 2) {
								_text = "생성 가능한 닉네임 입니다.";
								Thread.sleep(0);
								nicknameCheck = true;
							}
						} else {
							_text = "자음 및 모음, 특수문자는 불가능합니다.";
						}
						
						Thread.sleep(0);
						lblNicknameErrorMessage.setForeground(nicknameCheck ? Color.GREEN : Color.RED);
						lblNicknameErrorMessage.setText(_text);
						
					}catch (InterruptedException e) { logger.debug("nickname validate check intruppeted"); }
				});
				t.start();
			}
		});

		textFieldId.getDocument().addDocumentListener(new DocumentUpdateListener() {
			
			private Pattern idPattern = Pattern.compile("^[a-zA-Z0-9]*$");
			private Thread t;
			
			@Override
			public void action(String text) {
				if(t != null && !t.isInterrupted()) t.interrupt();
				t = new Thread(() -> {
					try {
						
						idCheck = false;
						String _text = null;
						
						Matcher match = idPattern.matcher(text);
						if (match.find()) {
							if (text.equals("")) {
								_text = "아이디를 입력해주세요.";
							}else if (text.substring(0,1).matches("^[0-9]$")){
								_text = "첫 글자는 영문만 가능합니다.";
							} else if (text.length() < 6 || text.length() > 12) {
								_text = "6 ~ 12자리 이하만 가능합니다.";
							} else if (playerDAO.selectID(text)) {
								_text = "이미 생성된 아이디 입니다.";
							} else if (text.length() <= 12 && text.length() >= 6) {
								_text = "생성 가능한 아이디 입니다.";
								Thread.sleep(0);
								idCheck = true;
							}
						} else {
							_text = "영문과 숫자만 입력 가능합니다.";
						}
						
						Thread.sleep(0);
						lblIdErrorMessage.setForeground(idCheck ? Color.GREEN : Color.RED);
						lblIdErrorMessage.setText(_text);
						
					}catch (InterruptedException e) { logger.debug("id validate check intruppeted"); }
				});
				t.start();
			}
		});

		passwordField.getDocument().addDocumentListener(new DocumentUpdateListener() {
			
			@Override
			public void action(String text) {
				passwordCheck = text.length() >= 5 && text.length() <= 10;
				lblPasswordErrorMessage.setForeground(passwordCheck ? Color.GREEN : Color.RED);
				lblPasswordErrorMessage.setText(passwordCheck ? "가능한 비밀번호입니다." : "5 ~ 10자리를 입력하세요.");
			}
		});
		
		DocumentListener passwordCheckListener = new DocumentUpdateListener() {
			@Override
			public void action(String text) {
				passwordCheck2 = Arrays.equals(passwordField.getPassword(), passwordFieldCheck.getPassword());
				lblPasswordCheckErrorMessage.setForeground(passwordCheck2 ? Color.GREEN : Color.RED);
				lblPasswordCheckErrorMessage.setText(passwordCheck2 ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.");
			}
		};

		//비밀번호체크 필드 입력 후에 비교 리스너 추가
		passwordFieldCheck.getDocument().addDocumentListener(passwordCheckListener);
		passwordFieldCheck.getDocument().addDocumentListener(new DocumentUpdateListener() {
			@Override
			public void action(String text) {
				passwordField.getDocument().addDocumentListener(passwordCheckListener);
				passwordFieldCheck.getDocument().removeDocumentListener(this);
			}
		});
		
		DocumentUpdateListener dl = new DocumentUpdateListener() {
			
			private Pattern mailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
			private Thread t;
			
			@Override
			public void action(String text) {
				if(t != null && !t.isInterrupted()) t.interrupt();
				
				t = new Thread(() -> {
					
					try {
						mailCheck = false;
						String _text = null;
						
						Matcher match = mailPattern.matcher(text);
						if (match.find()) {
							
							if (playerDAO.selectMail(text)) {
								_text = "사용중인 아이디 입니다";
								frame.getContentPane().remove(btnMail);
								mailCheck=false;
							} else {
								_text = "사용 가능한 메일 입니다. 인증을 진행해주세요";
								frame.getContentPane().add(btnMail, gbc_btnMail);
								mailCheck=true;
							}
						} else {
							_text = "메일을 확인해주세요";
							frame.getContentPane().remove(btnMail);
							mailCheck=false;
						}
						
						Thread.sleep(0);
						lblMailErrorMessage.setForeground(mailCheck ? Color.GREEN : Color.RED);
						lblMailErrorMessage.setText(_text);
						
					}catch (InterruptedException e) { logger.debug("mail validate check intruppeted"); }
				});
				t.start();
			}
		};
	textFieldMail.getDocument().addDocumentListener(dl);

	btnMail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mailCheck) {
					frame.getContentPane().remove(btnMail);
					lblMail.setText("인증번호");
					lblMailErrorMessage.setForeground(Color.YELLOW);
					lblMailErrorMessage.setText("인증번호 전송중...");
					textFieldMail.getDocument().removeDocumentListener(dl);
					playerDAO.sendMailCode(textFieldMail.getText());
					textFieldMail.setText("");
					frame.getContentPane().add(btnCode, gbc_btnCode);
					lblMailErrorMessage.setForeground(Color.GREEN);
					lblMailErrorMessage.setText("이메일로 전송한 인증번호를 입력해주세요.");
				} else {
					lblMailErrorMessage.setForeground(Color.RED);
					lblMailErrorMessage.setText("오류가 발생하였습니다. 재접속해주세요");
				}
			}
		});
		
		
		btnCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblMailErrorMessage.setText("인증번호 확인중...");
				if(playerDAO.checkMailCode(textFieldMail.getText())) {
					lblMailErrorMessage.setForeground(Color.GREEN);
					lblMailErrorMessage.setText("이메일 인증이 완료 되었습니다.");
					frame.getContentPane().remove(btnCode);
					textFieldMail.setEnabled(false);
					mailCodeCheck=true;
				} else {
					textFieldMail.setText("");
					lblMailErrorMessage.setForeground(Color.RED);
					lblMailErrorMessage.setText("인증번호를 확인해주세요.");
				}
			}
		});
		
	
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (idCheck && passwordCheck && passwordCheck2 && nicknameCheck && mailCodeCheck) {

					characterJF = new JFrame("캐릭터 선택창");
					Container con = characterJF.getContentPane();
					con.setLayout(null);
					con.setBackground(Color.black);
					characterJF.setSize(500, 700);
					characterJF.setLocationRelativeTo(null);
					characterJF.setVisible(true);
					Toolkit tool = Toolkit.getDefaultToolkit();
					Image img = tool.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
					characterJF.setIconImage(img);

					JLabel charLbl = new JLabel("캐릭을 선택하세요.");
					charLbl.setFont(new Font("d2coding", Font.BOLD, 20));
					charLbl.setForeground(Color.white);
					charLbl.setBackground(Color.white);
					charLbl.setBounds(150, 20, 200, 20);
					characterJF.add(charLbl);

					JRadioButton cha0Btn = new JRadioButton("심슨");
					cha0Btn.setForeground(Color.white);
					cha0Btn.setBackground(Color.black);
					characterJF.add(cha0Btn);
					cha0Btn.setBounds(120, 160, 70, 50);

					JRadioButton cha1Btn = new JRadioButton("마지");
					cha1Btn.setForeground(Color.white);
					cha1Btn.setBackground(Color.black);
					characterJF.add(cha1Btn);
					cha1Btn.setBounds(330, 160, 70, 50);

					JRadioButton cha2Btn = new JRadioButton("고시생");
					cha2Btn.setForeground(Color.white);
					cha2Btn.setBackground(Color.black);
					characterJF.add(cha2Btn);
					cha2Btn.setBounds(110, 340, 130, 50);

					JRadioButton cha3Btn = new JRadioButton("할아버지");
					cha3Btn.setForeground(Color.white);
					cha3Btn.setBackground(Color.black);
					characterJF.add(cha3Btn);
					cha3Btn.setBounds(310, 340, 130, 50);

					JRadioButton cha5Btn = new JRadioButton("동네삼촌");
					cha5Btn.setForeground(Color.white);
					cha5Btn.setBackground(Color.black);
					characterJF.add(cha5Btn);
					cha5Btn.setBounds(110, 510, 130, 50);

					JRadioButton cha6Btn = new JRadioButton("나빛나");
					cha6Btn.setForeground(Color.white);
					cha6Btn.setBackground(Color.black);
					characterJF.add(cha6Btn);
					cha6Btn.setBounds(310, 510, 130, 50);

					ButtonGroup bg = new ButtonGroup();
					bg.add(cha1Btn);
					bg.add(cha0Btn);
					bg.add(cha2Btn);
					bg.add(cha3Btn);
					bg.add(cha5Btn);
					bg.add(cha6Btn);

					JLabel cha0 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha0.png")));
					cha0.setBounds(80, 50, 130, 100);
					characterJF.add(cha0);

					JLabel cha1 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha1.png")));
					cha1.setBounds(290, 50, 130, 100);
					characterJF.add(cha1);

					JLabel cha2 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha2.png")));
					cha2.setBounds(80, 230, 130, 100);
					characterJF.add(cha2);

					JLabel cha3 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha3.png")));
					cha3.setBounds(290, 230, 130, 100);
					characterJF.add(cha3);

					JLabel cha5 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha5.png")));
					cha5.setBounds(80, 400, 130, 100);
					characterJF.add(cha5);

					JLabel cha6 = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/character/cha6.png")));
					cha6.setBounds(290, 400, 130, 100);
					characterJF.add(cha6);

					JButton okBtn = new JButton("선택");
					okBtn.setBounds(200, 580, 100, 40);
					characterJF.add(okBtn);

					okBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							PlayerVO playerVO = new PlayerVO();

							if (e.getSource() == okBtn) {
								if (bg.isSelected(null)) {
									JOptionPane.showMessageDialog(null, "캐릭터를 선택해주세요.");
									return;
								} else 
								if (cha0Btn.isSelected()) { playerVO.setCha(0); } else 
								if (cha1Btn.isSelected()) { playerVO.setCha(1); } else
								if (cha2Btn.isSelected()) { playerVO.setCha(2); } else
								if (cha3Btn.isSelected()) { playerVO.setCha(3); } else 
								if (cha5Btn.isSelected()) { playerVO.setCha(5); } else 
								if (cha6Btn.isSelected()) { playerVO.setCha(6); }
							}
							try {
								playerVO.joinPlayer(textFieldId.getText(), new String(passwordField.getPassword()),
										textFieldNickname.getText());
								if (1 == playerDAO.playerJoin(playerVO)) {
									JOptionPane.showMessageDialog(null, "회원가입을 축하드립니다.");
									characterJF.dispose();
									frame.dispose();

								}
							} catch (ClassNotFoundException e1) {
								logger.error(e1.getMessage(), e1);
							}

						}
					});

				} else if(idCheck && passwordCheck && passwordCheck2 && nicknameCheck) {
					JOptionPane.showMessageDialog(null, "이메일 인증를 완료해주세요.");
				} else {
					JOptionPane.showMessageDialog(null, "입력 값을 확인해 주세요");
				}
			}
		});

	} // initialize();

	public void dispose() {
		if (characterJF != null && characterJF.isDisplayable())
			characterJF.dispose();
		frame.dispose();
	}

	public JFrame getFrame() {
		return frame;
	}

} // class JoinFrame;
