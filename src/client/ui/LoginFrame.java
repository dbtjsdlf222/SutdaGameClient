package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import client.Background;
import client.service.login.LoginResultHandler;
import client.service.login.LoginService;
import vo.PlayerVO;

public class LoginFrame implements LoginResultHandler {

	private static final Logger logger = LoggerFactory.getLogger(LoginFrame.class);
	
	private LoginService loginService = new LoginService(this);
	
	private JFrame frame;
	private JLabel lblErrorMessage;
	private JTextField textFieldId;
	private JPasswordField passwordField;
	private Background back = new Background();
	private JoinFrame joinFrame;
	
	public LoginFrame() {
		initialize();
		frame.setVisible(true);
	} //LoginFrame();
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Image img = tool.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		frame.setIconImage(img);
		
		
		frame.setTitle("섯다 온라인");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 640, 192, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 128, 64, 64, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(RoomScreen.class.getResource("/img/resources/Logo.jpg")));
		GridBagConstraints gbc_lblLogo = new GridBagConstraints();
		gbc_lblLogo.gridwidth = 2;
		gbc_lblLogo.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogo.gridx = 1;
		gbc_lblLogo.gridy = 1;
		frame.getContentPane().add(lblLogo, gbc_lblLogo);
		
		JPanel panelErrorMessage = new JPanel();
		panelErrorMessage.setBorder(new EmptyBorder(0, 0, 5, 0));
		panelErrorMessage.setOpaque(false);
		GridBagConstraints gbc_panelErrorMessage = new GridBagConstraints();
		gbc_panelErrorMessage.anchor = GridBagConstraints.SOUTH;
		gbc_panelErrorMessage.gridwidth = 2;
		gbc_panelErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_panelErrorMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelErrorMessage.gridx = 1;
		gbc_panelErrorMessage.gridy = 2;
		frame.getContentPane().add(panelErrorMessage, gbc_panelErrorMessage);
		
		lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setFont(new Font("돋움", Font.PLAIN, 24));
		panelErrorMessage.add(lblErrorMessage); 
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(255, 255, 255)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		textFieldId = new JTextField();
		textFieldId.setForeground(Color.WHITE);
		textFieldId.setFont(new Font("바탕", Font.BOLD, 32));
		textFieldId.setCaretColor(Color.WHITE);
		textFieldId.setBorder(new EmptyBorder(0, 8, 0, 0));
		textFieldId.setBackground(Color.BLACK);
		panel.add(textFieldId);
		textFieldId.setColumns(10);
		
		JButton btnLoginJoin = new JButton("");
		btnLoginJoin.setMargin(new Insets(-3, -3, -3, -3));
		btnLoginJoin.setIcon(new ImageIcon(RoomScreen.class.getResource("/img/resources/BtnLoginJoin.png")));
		btnLoginJoin.setFocusPainted(false);
		btnLoginJoin.setContentAreaFilled(false);
		btnLoginJoin.setBorderPainted(false);
		GridBagConstraints gbc_btnLoginJoin = new GridBagConstraints();
		gbc_btnLoginJoin.fill = GridBagConstraints.BOTH;
		gbc_btnLoginJoin.gridheight = 2;
		gbc_btnLoginJoin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoginJoin.gridx = 2;
		gbc_btnLoginJoin.gridy = 3;
		frame.getContentPane().add(btnLoginJoin, gbc_btnLoginJoin);
		
		JPanel panelPaswwordField = new JPanel();
		panelPaswwordField.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(255, 255, 255)));
		GridBagConstraints gbc_panelPaswwordField = new GridBagConstraints();
		gbc_panelPaswwordField.insets = new Insets(0, 0, 5, 5);
		gbc_panelPaswwordField.fill = GridBagConstraints.BOTH;
		gbc_panelPaswwordField.gridx = 1;
		gbc_panelPaswwordField.gridy = 4;
		frame.getContentPane().add(panelPaswwordField, gbc_panelPaswwordField);
		panelPaswwordField.setLayout(new BorderLayout(0, 0));
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.BLACK);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("바탕", Font.BOLD, 32));
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBorder(new EmptyBorder(0, 8, 0, 0));
		panelPaswwordField.add(passwordField);
		frame.revalidate();
		frame.repaint();
		btnLoginJoin.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if((int)(e.getPoint().getX() / 186. * 108) + 7 < e.getPoint().getY())
					try {
						loginService.login(textFieldId.getText(), new String(passwordField.getPassword()));
					} catch (IOException e1) {
						logger.error(e1.getMessage(), e1);
					}
				else {
					new JoinFrame(loginService.getPlayerDAO());
				}
				
			}
		});
		
	} //initialize();
	
	public void dispose() {
		
		if(joinFrame != null)
			joinFrame.dispose();
		frame.dispose();
	}
	
	@Override
	public void loginSuccess(PlayerVO player) {
		if(frame.isDisplayable()) {
			dispose();
			Lobby.getInstance().lobbyScreen();
		}
	} //loginSuccess();

	@Override
	public void loginFailure(Focus focus, String reason) {
		
		if(focus != null) {
			
			passwordField.setText("");
			
			switch (focus) {
			case ID:
				textFieldId.setText("");
				textFieldId.requestFocus();
				break;
			case PASSWORD:
				passwordField.requestFocus();
				break;
			}
			
		}
		
		lblErrorMessage.setText(reason);
		
	} //loginFailure();

} //class LoginFrame;
