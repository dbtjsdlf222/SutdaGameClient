package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame {
	
	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} //main();
	
	public LoginFrame() {
		initialize();
	} //LoginFrame();
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("로그인");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 640, 192, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 128, 64, 64, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/resources/img/Logo.jpg")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(255, 255, 255)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setFont(new Font("바탕", Font.BOLD, 32));
		textField.setCaretColor(Color.WHITE);
		textField.setBorder(new EmptyBorder(0, 8, 0, 0));
		textField.setBackground(Color.BLACK);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setMargin(new Insets(-3, -3, -3, -3));
		btnNewButton.setIcon(new ImageIcon(LoginFrame.class.getResource("/resources/img/BtnLoginJoin.png")));
		btnNewButton.setFocusPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridheight = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 3;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(255, 255, 255)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 4;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.BLACK);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("바탕", Font.BOLD, 32));
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBorder(new EmptyBorder(0, 8, 0, 0));
		panel_1.add(passwordField);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if((int)(e.getPoint().getX() / 186. * 108) + 7 < e.getPoint().getY())
					System.out.println("Login");
				else
					System.out.println("Join");
				
			}
		});
		
	} //initialize();

} //class LoginFrame;
