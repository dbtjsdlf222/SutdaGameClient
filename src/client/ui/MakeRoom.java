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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;

public class MakeRoom {
		
	private Background imgP;
	private Container con;
	int i = 1; 
	
	public MakeRoom(){
		makeRoom();
	}

	private void makeRoom() {
		//Frame
		JFrame roomJF = new JFrame("방 만들기");
		con = roomJF.getContentPane();
		imgP = new Background();
		imgP.lobbyImage();
		
		//방제목 		
		JLabel titlelbl = new JLabel("방  제  목 : ");
		titlelbl.setBounds(18, 0, 120, 30);
		titlelbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		titlelbl.setForeground(new Color(255, 255, 255, 150));
		
		JTextField titleField = new JTextField();
		titleField.setBounds(130, 0, 305, 30);
		
		JPanel titlePan = new JPanel();
		titlePan.setBounds(0, 55, 465, 30);
		titlePan.setBackground(new Color(0, 0, 0, 0));
		titlePan.setLayout(null);
		titlePan.add(titlelbl);
		titlePan.add(titleField);
		
		//비밀번호
		JLabel pwlbl = new JLabel("비 밀 번 호 : ");
		pwlbl.setBounds(10, 0, 120, 30);
		pwlbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		pwlbl.setForeground(new Color(255, 255, 255, 150));
		
		JTextField pwField = new JTextField();
		pwField.setBounds(130, 0, 305, 30);
		
		JPanel pwPan = new JPanel();
		pwPan.setBounds(0, 100, 465, 30);
		pwPan.setBackground(new Color(0, 0, 0, 0));
		pwPan.setLayout(null);
		pwPan.add(pwlbl);
		pwPan.add(pwField);
		
		//시작 금액
		JRadioButton moneyCheck1 = new JRadioButton ("10만");
		moneyCheck1.setBounds(130, 0, 50, 30);
		moneyCheck1.setBackground(new Color(255, 255, 255));
		moneyCheck1.setForeground(new Color(0, 0, 0));

		JRadioButton moneyCheck2 = new JRadioButton  ("50만");
		moneyCheck2.setBounds(206, 0, 50, 30);
		moneyCheck2.setBackground(new Color(255, 255, 255));
		moneyCheck2.setForeground(new Color(0, 0, 0));
		
		JRadioButton moneyCheck3 = new JRadioButton ("100만");
		moneyCheck3.setBounds(283, 0, 60, 30);
		moneyCheck3.setBackground(new Color(255, 255, 255));
		moneyCheck3.setForeground(new Color(0, 0, 0));
		
		JRadioButton moneyCheck4 = new JRadioButton ("1000만");
		moneyCheck4.setBounds(370, 0, 65, 30);
		moneyCheck4.setBackground(new Color(255, 255, 255));
		moneyCheck4.setForeground(new Color(0, 0, 0));

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(moneyCheck1);
		btnGroup.add(moneyCheck2);
		btnGroup.add(moneyCheck3);
		btnGroup.add(moneyCheck4);
		
		JLabel moneylbl = new JLabel("시 작 금 액 : ");
		moneylbl.setBounds(10, 0, 120, 30);
		moneylbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		moneylbl.setForeground(new Color(255, 255, 255, 150));

		
		JPanel moneyPan = new JPanel();
		moneyPan.setBounds(0, 145, 465, 30);
		moneyPan.setBackground(new Color(0, 0, 0, 0));
		moneyPan.setLayout(null);

		moneyPan.add(moneylbl);
		moneyPan.add(moneyCheck1);
		moneyPan.add(moneyCheck2);
		moneyPan.add(moneyCheck3);
		moneyPan.add(moneyCheck4);
		
		//인원수
		JLabel plbl = new JLabel("인  원  수 : ");
		plbl.setBounds(18, 0, 120, 30);
		plbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		plbl.setForeground(new Color(255, 255, 255, 150));
		
		JTextField pField = new JTextField();
		
		pField.setBounds(130, 0, 30, 30);
		pField.setHorizontalAlignment(JTextField.CENTER);
		pField.setText(i+"");
		pField.setEnabled(false);
		
		JButton upBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/up.png")));
		upBtn.setBounds(180, 0, 30, 30);
		upBtn.setBackground(new Color(0, 0, 0, 0));
		upBtn.setFocusable(false);
		upBtn.setContentAreaFilled(false);
		upBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		upBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== upBtn) {
					if(i<=4)
						pField.setText((i+=1)+"");
				}
			}
		});
		
		JButton downBtn = new JButton(new ImageIcon(Lobby.class.getResource("../../img/down.png")));
		downBtn.setBounds(230, 0, 30, 30);
		downBtn.setBackground(new Color(0, 0, 0, 0));
		downBtn.setFocusable(false);
		downBtn.setContentAreaFilled(false);
		downBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		downBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== downBtn) {
					if(i>=2)
						pField.setText((i-=1)+"");
						
				}
			}
		});
		
		JPanel pPan = new JPanel();
		pPan.setBounds(0, 190, 465, 30);
		pPan.setBackground(new Color(0, 0, 0, 0));
		pPan.setLayout(null);

		pPan.add(plbl);
		pPan.add(pField);
		pPan.add(upBtn);
		pPan.add(downBtn);
		
		//만들기 / 취소 버튼
		JButton okBtn = new JButton("만들기");
		okBtn.setBounds(50, 235, 153, 50);
//		okBtn.setBackground(new Color(0, 0, 0, 0));
		okBtn.setFocusable(false);
//		okBtn.setContentAreaFilled(false);
		okBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JButton cancelBtn = new JButton("취소");
		cancelBtn.setBounds(262, 235, 153, 50);
//		cancelBtn.setBackground(new Color(0, 0, 0, 0));
		cancelBtn.setFocusable(false);
//		cancelBtn.setContentAreaFilled(false);
		cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		
		
		//Room의 큰 틀
		JLabel roomlbl = new JLabel("R O O M");
		roomlbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		roomlbl.setForeground(new Color(255, 255, 255, 150));
		roomlbl.setBounds(175, 10, 150, 30);
		
		JPanel roomPan = new JPanel();
		roomPan.setBounds(10, 10, 464, 301);
		roomPan.setBackground(new Color(0, 0, 0, 0));
		roomPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		roomPan.setLayout(null);
		
		roomPan.add(roomlbl);
		roomPan.add(titlePan);
		roomPan.add(moneyPan);
		roomPan.add(pwPan);
		roomPan.add(pPan);
		roomPan.add(okBtn);
		roomPan.add(cancelBtn);
		
		
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
