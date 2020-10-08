package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import client.Background;

public class Invite {
	
	private Background imgP;
	private Container con;

	public void Invite() {
		
		JFrame roomJF = new JFrame("방 만들기");
		con = roomJF.getContentPane();
		imgP = new Background();
		imgP.lobbyImage();
		
		//Room의 큰 틀
		JLabel roomlbl = new JLabel("초 대 창");
		roomlbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		roomlbl.setForeground(new Color(255, 255, 255, 150));
		roomlbl.setBounds(175, 10, 150, 30);
		
		JPanel roomPan = new JPanel();
		roomPan.setBounds(10, 10, 464, 301);
		roomPan.setBackground(new Color(0, 0, 0, 0));
		roomPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		roomPan.setLayout(null);
		
		roomPan.add(roomlbl);
		
		
		//JFrame 정보
		con.add(roomPan);
		con.add(imgP, BorderLayout.CENTER);
		roomJF.setSize(500,360);
		roomJF.setVisible(true);
		roomJF.setResizable(false);
		roomJF.setLocationRelativeTo(null);
		roomJF.setLayout(null);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("../../img/titleIcon.jpg"));
		roomJF.setIconImage(img);
		
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	