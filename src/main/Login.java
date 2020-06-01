package puzzle;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class Login extends JFrame {
	Pattern idPt = null;
	Matcher match;

	String DBID = "lee";
	String DBPW = "lee";
	String DBNIC = "lee";

	public void login() {

		JFrame logJF = new JFrame("로 그 인 창");
		logJF.setSize(416, 220);
		logJF.setLocation(550, 300);
		logJF.setLayout(null);

		JLabel jlbl = new JLabel("아이디와 비밀번호를 입력해주세요");
		jlbl.setBounds(100, 0, 1100, 30);
		logJF.add(jlbl);

		JButton imgbtn = new JButton(new ImageIcon("admin.jpg"));
		imgbtn.setBounds(380, 0, 20, 20);
		logJF.add(imgbtn);

		JPanel idpal = new JPanel();
		JLabel idlbl = new JLabel("아이디");
		JTextField idtxt = new JTextField(10);
		idpal.setBounds(-5, 50, 215, 30);
		logJF.add(idpal);
		idpal.add(idlbl);
		idpal.add(idtxt);

		JPanel pwpal = new JPanel();
		JLabel pwlbl = new JLabel("비밀번호");
		JPasswordField pwtxt = new JPasswordField(10);
		pwpal.setBounds(-11, 80, 215, 30);
		logJF.add(pwpal);
		pwpal.add(pwlbl);
		pwpal.add(pwtxt);
		pwtxt.setEchoChar('*');

		JLabel loglbl = new JLabel();
		loglbl.setBounds(15, 110, 230, 30);
		loglbl.setForeground(Color.red);
		logJF.add(loglbl);

		JButton logBtn = new JButton("로그인");
		logBtn.setBounds(210, 40, 70, 100);
		logJF.add(logBtn);
		logBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == logBtn) {
					if (idtxt.getText().equals(DBID) && pwtxt.getText().equals(DBPW)) {

						logJF.setVisible(false);
						JFrame listJF = new JFrame("방 목록");
						listJF.setSize(400, 600);
						listJF.setLocation(550, 150);
						listJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						listJF.setVisible(true);
					} else if (idtxt.getText().equals("") || (pwtxt.getText().equals(""))) {
						loglbl.setText("아이디 또는 비밀번호를 입력하세요.");
					} else
						loglbl.setText("아이디 또는 비밀번호가 틀립니다.");
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
					joinJF.setSize(350, 380);
					joinJF.setLocation(600, 200);
					joinJF.setLayout(null);

					JLabel jlbl = new JLabel("새롭게 만들 아이디와 비밀번호를 입력해주세요");
					jlbl.setBounds(35, 5, 1100, 30);
					jlbl.setForeground(Color.PINK);
					joinJF.add(jlbl);

					JLabel exlbl = new JLabel("아이디는 5 ~ 10자리  비밀번호는 영어 + 숫자로만 가능합니다.");
					exlbl.setBounds(15, 30, 1100, 30);
					exlbl.setFont(exlbl.getFont().deriveFont(11.0f));
					exlbl.setForeground(Color.PINK);
					joinJF.add(exlbl);

					JPanel idpal = new JPanel();
					JLabel idlbl = new JLabel("아이디");
					JTextField idtxt = new JTextField(10);
					idpal.setBounds(19, 60, 200, 30);
					joinJF.add(idpal);
					idpal.add(idlbl);
					idpal.add(idtxt);

					JLabel joinlbl = new JLabel();
					joinlbl.setBounds(40, 85, 200, 30);
					joinlbl.setForeground(Color.red);
					joinJF.add(joinlbl);

					JButton orBtn = new JButton("중복체크");
					orBtn.setBounds(220, 65, 85, 20);
					joinJF.add(orBtn);

					orBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == orBtn) {
								idPt = Pattern.compile("^[a-zA-Z0-9]*$");
								match = idPt.matcher(idtxt.getText());
								if (match.find()) {
									if (idtxt.getText().equals(DBID)) {
										joinlbl.setText("이미 생성된 아이디 입니다.");
									} else if (idtxt.getText().length() <= 10 && idtxt.getText().length() >= 5) {
										joinlbl.setText("생성 가능한 아이디 입니다.");
									} else if (idtxt.getText().equals("")) {
										joinlbl.setText("아이디를 입력해주세요.");
									} else if (idtxt.getText().length() > 10 || idtxt.getText().length() < 5) {
										joinlbl.setText("5 ~ 10자리 이하만 가능합니다.");
									}
								} else
									joinlbl.setText("영어와 숫자로 입력 가능합니다.");
							}

						}
					});

					JPanel pwpal = new JPanel();
					JLabel pwlbl = new JLabel("비밀번호");
					JPasswordField pwtxt = new JPasswordField(10);
					pwpal.setBounds(13, 115, 200, 30);
					joinJF.add(pwpal);
					pwpal.add(pwlbl);
					pwpal.add(pwtxt);
					pwtxt.setEchoChar('*');

					JPanel pwpal2 = new JPanel();
					JLabel pwlbl2 = new JLabel("비밀번호 확인");
					JPasswordField pwtxt2 = new JPasswordField(10);
					pwpal2.setBounds(-1, 170, 200, 30);
					pwlbl2.setFont(pwlbl2.getFont().deriveFont(12.0f));
					joinJF.add(pwpal2);
					pwpal2.add(pwlbl2);
					pwpal2.add(pwtxt2);
					pwtxt2.setEchoChar('*');

					JLabel joinlbl2 = new JLabel("일치하지 않습니다.");
					joinlbl2.setBounds(65, 195, 200, 30);
					joinlbl2.setForeground(Color.red);
					joinJF.add(joinlbl2);

					pwtxt2.addKeyListener(new KeyListener() {

						@Override
						public void keyTyped(KeyEvent e) {
						}

						@Override
						public void keyReleased(KeyEvent e) {
							if (pwtxt.getText().equals(pwtxt2.getText())) {
								joinlbl2.setText("일치");
							} else {
								joinlbl2.setText("불일치");
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}

					});

					JPanel nicpal = new JPanel();
					JLabel niclbl = new JLabel("닉네임");
					JTextField nictxt = new JTextField(10);
					nicpal.setBounds(19, 225, 200, 30);
					joinJF.add(nicpal);
					nicpal.add(niclbl);
					nicpal.add(nictxt);

					JButton orBtn2 = new JButton("중복체크");
					orBtn2.setBounds(220, 220, 85, 20);
					joinJF.add(orBtn2);

					JLabel joinlbl3 = new JLabel();
					joinlbl3.setBounds(40, 250, 180, 30);
					joinlbl3.setForeground(Color.red);
					joinJF.add(joinlbl3);

					orBtn2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == orBtn2) {
								idPt = Pattern.compile("^[가-힣]*$");
								match = idPt.matcher(nictxt.getText());
								if (match.find()) {
									if (nictxt.getText().equals(DBNIC)) {
										joinlbl3.setText("이미 생성된 닉네임 입니다.");
									} else if (nictxt.getText().length() <= 6 && nictxt.getText().length() >= 2) {
										joinlbl3.setText("생성 가능한 닉네임 입니다.");
									} else if (nictxt.getText().equals("")) {
										joinlbl3.setText("닉네임을 입력해주세요.");
									} else if (nictxt.getText().length() > 7 || nictxt.getText().length() < 2) {
										joinlbl3.setText("2 ~ 7자리 미만만 가능합니다.");
									}
								} else {
									joinlbl3.setText("한글로만 입력 가능합니다.");
								}
							}
						}
					});

					JButton joinBtn2 = new JButton("가 입");
					joinBtn2.setBounds(30, 280, 200, 50);
					joinJF.add(joinBtn2);

					dispose();
					joinJF.setVisible(true);
				}
			}
		});

		logJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logJF.setVisible(true);

	}

	public static void main(String[] args) {

		Login log = new Login();
		log.login();

	}

}
