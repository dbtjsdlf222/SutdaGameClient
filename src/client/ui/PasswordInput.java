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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;
import client.service.ClientPacketController;
import client.service.ClientPacketSender;
import server.Room;

public class PasswordInput {
	private Background imgP;
	private Container con;
	private int no;
	
	public PasswordInput(int no) {
		this.no = no;
		getIn();
	}
	
	public void getIn() {
		JFrame getInJF = new JFrame("방 입장");
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
	  				getInJF.dispose();
	  				Lobby.getInstance().getLobbyJF().dispose();
	  				
	  				Room room = new Room();
	  				room.setPassword(pwField.getText());
	  				room.setRoomNo(no);
	  				ClientPacketSender.instance.password(room);
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
		roomlbl.setForeground(new Color(0, 0, 0, 150));
		roomlbl.setBounds(0, 10, 384, 30);
		roomlbl.setHorizontalAlignment(JLabel.CENTER);

		JPanel roomPan = new JPanel();
		roomPan.setBounds(0, 0, 384, 161);
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
		getInJF.setSize(400, 200);
		getInJF.setVisible(true);
		getInJF.setResizable(false);
		getInJF.setLocationRelativeTo(null);
		getInJF.setLayout(null);
		getInJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		getInJF.setIconImage(img);
		
	}
	
}
