package client.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import client.listener.DocumentUpdateListener;
import dao.PlayerDAO;
import vo.PlayerVO;

public class JoinFrame {
	
	private PlayerDAO playerDAO;
	
	private boolean nicknameCheck;
	private boolean idCheck;
	private boolean passwordCheck;
	private boolean passwordCheck2;
	
	private JFrame frame;
	private JTextField textFieldNickname;
	private JTextField textFieldId;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCheck;
	
	private JFrame sexJF;
	
	public JoinFrame(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
		initialize();
		frame.setVisible(true);
	} //JoinFrame();
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 444, 444);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{16, 0, 0, 64, 0, 16};
		gridBagLayout.rowHeights = new int[]{32, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 64, 16};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
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
		
		JLabel lblHint2 = new JLabel("아이디는 5~10자리 비밀번호는 영어 + 숫자로만 가능합니다.");
		lblHint2.setFont(new Font("굴림", Font.BOLD, 12));
		lblHint2.setForeground(Color.PINK);
		GridBagConstraints gbc_lblHint2 = new GridBagConstraints();
		gbc_lblHint2.gridwidth = 4;
		gbc_lblHint2.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint2.gridx = 1;
		gbc_lblHint2.gridy = 2;
		frame.getContentPane().add(lblHint2, gbc_lblHint2);
		
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
		
		JButton btnJoin = new JButton("회원가입");
		GridBagConstraints gbc_btnJoin = new GridBagConstraints();
		gbc_btnJoin.fill = GridBagConstraints.BOTH;
		gbc_btnJoin.gridwidth = 2;
		gbc_btnJoin.insets = new Insets(0, 0, 5, 5);
		gbc_btnJoin.gridx = 3;
		gbc_btnJoin.gridy = 12;
		frame.getContentPane().add(btnJoin, gbc_btnJoin);
		
		textFieldNickname.getDocument().addDocumentListener(new DocumentUpdateListener() {
			
			private Pattern nicknamePattern = Pattern.compile("^[a-zA-Z0-9가-힣]*$");
			
			@Override
			public void action(String text) {
				
				nicknameCheck = false;
				
				Matcher match = nicknamePattern.matcher(text);
				if (match.find()) {
					if (text.equals("")) {
						lblNicknameErrorMessage.setForeground(Color.RED);
						lblNicknameErrorMessage.setText("닉네임을 입력해주세요.");
					} else if (2 > text.length() || text.length() > 6) {
						lblNicknameErrorMessage.setForeground(Color.RED);
						lblNicknameErrorMessage.setText("2 ~ 6자리 미만만 가능합니다.");
					} else if (playerDAO.selectNick(text)) {
						lblNicknameErrorMessage.setForeground(Color.RED);
						lblNicknameErrorMessage.setText("이미 생성된 닉네임 입니다.");
					} else if (text.length() <= 6 && text.length() >= 2) {
						lblNicknameErrorMessage.setForeground(Color.GREEN);
						lblNicknameErrorMessage.setText("생성 가능한 닉네임 입니다.");
						nicknameCheck = true;
					}
				} else {
					lblNicknameErrorMessage.setForeground(Color.RED);
					lblNicknameErrorMessage.setText("특수문자는 불가능합니다.");
				}
				
			}
		});
		
		textFieldId.getDocument().addDocumentListener(new DocumentUpdateListener() {
			
			private Pattern idPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");
			
			@Override
			public void action(String text) {
				
				idCheck = false;
				
				Matcher match = idPattern.matcher(text);
				if (match.find()) {
					if (text.equals("")) {
						lblIdErrorMessage.setForeground(Color.RED);
						lblIdErrorMessage.setText("아이디를 입력해주세요.");
					} else if (text.length() > 10 && text.length() < 5) {
						lblIdErrorMessage.setForeground(Color.RED);
						lblIdErrorMessage.setText("5 ~ 10자리 이하만 가능합니다.");
					} else if (playerDAO.selectID(text)) {
						lblIdErrorMessage.setForeground(Color.RED);
						lblIdErrorMessage.setText("이미 생성된 아이디 입니다.");
					} else if (text.length() <= 10 && text.length() >= 5) {
						lblIdErrorMessage.setForeground(Color.GREEN);
						lblIdErrorMessage.setText("생성 가능한 아이디 입니다.");
						idCheck = true;
					}
				} else if (!(text.matches("^[a-zA-Z0-9]*$"))) {
					lblIdErrorMessage.setForeground(Color.RED);
					lblIdErrorMessage.setText("영어와 숫자만 입력 가능합니다.");
				} else if (!(text.matches("^[a-zA-Z]{1}$"))) {
					lblIdErrorMessage.setForeground(Color.RED);
					lblIdErrorMessage.setText("앞자리는 영어만 가능합니다.");
				}
				
			}
			
		});
		
		passwordField.getDocument().addDocumentListener(new DocumentUpdateListener() {
			
			@Override
			public void action(String text) {
				
				passwordCheck = text.length() >= 4 && text.length() <= 9;
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
		
		passwordFieldCheck.getDocument().addDocumentListener(passwordCheckListener);
		passwordFieldCheck.getDocument().addDocumentListener(new DocumentUpdateListener() {

			@Override
			public void action(String text) {
				
				passwordField.getDocument().addDocumentListener(passwordCheckListener);
				passwordFieldCheck.getDocument().removeDocumentListener(this);
				
			}
			
		});
		
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (idCheck && passwordCheck && passwordCheck2 && nicknameCheck) {
					
					sexJF = new JFrame("캐릭터 선택창");
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
					
					JLabel mLbl = new JLabel(new ImageIcon(JoinFrame.class.getResource("/img/men.png")));
					JLabel wLbl = new JLabel(
							new ImageIcon(JoinFrame.class.getResource("/img/women.png")));
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
							
							PlayerVO playerVO = new PlayerVO();
							
							if (e.getSource() == okBtn) {
								if (men.isSelected()) {
									playerVO.setCha(0);
								} else if (women.isSelected()) {
									playerVO.setCha(1);
								}
								try {
									playerVO.joinPlayer(textFieldId.getText(), new String(passwordField.getPassword()),
											textFieldNickname.getText());
									if (1 == playerDAO.playerJoin(playerVO)) {
										JOptionPane.showMessageDialog(null, "회원가입을 축하드립니다.");
										sexJF.dispose();
										frame.dispose();
										
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
		});
		
	} //initialize();
	
	
	public void dispose() {
		if(sexJF != null && sexJF.isDisplayable())
			sexJF.dispose();
		frame.dispose();
	}

	public JFrame getFrame() { return frame; }
	
} //class JoinFrame;
