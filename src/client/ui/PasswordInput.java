package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;
import client.service.ClientPacketSender;
import server.Room;

public class PasswordInput {
	private static PasswordInput instance;
	private JFrame getInJF = new JFrame("방 입장");
	private Background imgP;
	private Container con;
	private int no;
	private final int x = 384;
	private final int y = 161;
	
	private PasswordInput() {};
	
	public static PasswordInput getInstance() {
		if(instance == null) 
			instance = new PasswordInput();
		return instance;
	}
	
	public void getIn() {
		
		con = getInJF.getContentPane();
		imgP = new Background();
		imgP.lobbyImage();
		
		//비밀번호
		JLabel pwlbl = new JLabel("비 밀 번 호 : ");
	    pwlbl.setBounds(10, 55, 120, 30);
	    pwlbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
	    pwlbl.setForeground(new Color(255, 255, 255, 150));
	    
	    JTextField pwField = new JTextField();
	    pwField.setBounds(100, 55, 270, 30);
	    // 10글자 넘어가면 입력불가
	    pwField.addKeyListener(new KeyAdapter() {
	    	
	    	public void keyTyped(KeyEvent e) {
	    		if(e.getSource()==pwField) {
	    			if(pwField.getText().length()>=11)
	    				e.consume();
	    		}
	    	}
	    });
		
	  //수락/거절버튼
	    JButton okBtn = new JButton("입 장");
	  	okBtn.setBounds(33, 100, 120, 50);
//	  	okBtn.setBackground(new Color(0, 0, 0, 0));
	  	okBtn.setFocusable(false);
//	  	okBtn.setContentAreaFilled(false);
	  	okBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	  		
	  	okBtn.addActionListener(new ActionListener() {
	  			
	  		@Override
	  		public void actionPerformed(ActionEvent e) {
	  			if(e.getSource()==okBtn) {
	  				if (pwField.getText().trim().equals("")) {
	  					ShowErrorPane errorPane = new ShowErrorPane("비밀번호를 입력해주세요.");
	  				}else {
	  					Room room = new Room();
	  					room.setPassword(pwField.getText());
	  					room.setRoomNo(no);
	  					ClientPacketSender.instance.password(room);
	  				}
	  			}
	  		}
	  	});

	  	JButton cancelBtn = new JButton("취 소");
	  	cancelBtn.setBounds(231, 100, 120, 50);
//	  	cancelBtn.setBackground(new Color(0, 0, 0, 0));
	  	cancelBtn.setFocusable(false);
//	  	cancelBtn.setContentAreaFilled(false);
	  	cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	  	    
	  	cancelBtn.addActionListener(new ActionListener() {
	  			
	  		@Override
	  		public void actionPerformed(ActionEvent e) {
	  			if(e.getSource()==cancelBtn) {
	  				getInJF.dispose();
	  			}
	  		}
	  	});

		// Room의 큰 틀
		JLabel roomlbl = new JLabel("방 입 장");
		roomlbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		roomlbl.setForeground(new Color(255, 255, 255, 150));
		roomlbl.setBounds(0, 10, 384, 30);
		roomlbl.setHorizontalAlignment(JLabel.CENTER);

		JPanel roomPan = new JPanel();
		roomPan.setSize(x, y);
		roomPan.setBackground(new Color(0, 0, 0, 0));
		roomPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		roomPan.setLayout(null);

		roomPan.add(roomlbl);
		roomPan.add(pwlbl);
		roomPan.add(pwField);
		roomPan.add(okBtn);
		roomPan.add(cancelBtn);
		
		// JFrame 정보
		con.add(roomPan);
		con.add(imgP, BorderLayout.CENTER);
		getInJF.setSize(x, y);
		getInJF.setResizable(false);
		getInJF.setLocationRelativeTo(null);
//		getInJF.setLayout(null);
		getInJF.setAlwaysOnTop(true);
		getInJF.setUndecorated(true);
		getInJF.setVisible(true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		getInJF.setIconImage(img);
		
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void dispose() {
		getInJF.dispose();
		Lobby.getInstance().getLobbyJF().dispose();
	}
	
	
	
}