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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;
import client.service.ClientPacketSender;
import operator.ChattingOperator;
import server.Room;
import util.Packing;
import vo.PlayerVO;
import vo.Protocol;

public class MakeRoom {
	
	private static MakeRoom instance;
	
	private Background imgP;
	private Container con;
	private int i = 5;
	private boolean make = false;
   
   private MakeRoom() {};
   
   public static MakeRoom getInstance() {
	   if(instance == null)
		   instance = new MakeRoom();
	return instance;
   }
   

   public void makeRoom() {
	   make = true;
      //Frame
      JFrame makeJF = new JFrame("방 만들기");
      con = makeJF.getContentPane();
      imgP = new Background();
      imgP.lobbyImage();
      
      //방제목       
      JLabel titlelbl = new JLabel("방  제  목 : ");
      titlelbl.setBounds(18, 0, 120, 30);
      titlelbl.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
      titlelbl.setForeground(new Color(255, 255, 255, 150));

      JTextField titleField = new JTextField();
      titleField.setBounds(130, 0, 305, 30);
      // 13글자 넘어가면 입력불가
      titleField.addKeyListener(new KeyAdapter() {
    	  public void keyTyped(KeyEvent e) {
    		  if(e.getSource()==titleField) {
    			  if(titleField.getText().length()>=13)
    				  e.consume();
    		  }
    	  }
	});
      
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
      // 10글자 넘어가면 입력불가
      pwField.addKeyListener(new KeyAdapter() {
    	  public void keyTyped(KeyEvent e) {
    		  if(e.getSource()==pwField) {
    			  if(pwField.getText().length()>=11)
    				  e.consume();
    		  }
    	  }
      });
      
      
      JPanel pwPan = new JPanel();
      pwPan.setBounds(0, 100, 465, 30);
      pwPan.setBackground(new Color(0, 0, 0, 0));
      pwPan.setLayout(null);
      pwPan.add(pwlbl);
      pwPan.add(pwField);
      
      long[] battingPoint = {10, 50, 100, 500};
      
      //시작 금액
      JRadioButton moneyCheck1 = new JRadioButton (battingPoint[0] + "만");
      moneyCheck1.setBounds(130, 0, 50, 30);
      moneyCheck1.setBackground(new Color(255, 255, 255));
      moneyCheck1.setForeground(new Color(0, 0, 0));

      JRadioButton moneyCheck2 = new JRadioButton  (battingPoint[1] + "만");
      moneyCheck2.setBounds(206, 0, 50, 30);
      moneyCheck2.setBackground(new Color(255, 255, 255));
      moneyCheck2.setForeground(new Color(0, 0, 0));
      
      JRadioButton moneyCheck3 = new JRadioButton (battingPoint[2] + "만");
      moneyCheck3.setBounds(283, 0, 60, 30);
      moneyCheck3.setBackground(new Color(255, 255, 255));
      moneyCheck3.setForeground(new Color(0, 0, 0));
      
      JRadioButton moneyCheck4 = new JRadioButton (battingPoint[3] + "만");
      moneyCheck4.setBounds(370, 0, 65, 30);
      moneyCheck4.setBackground(new Color(255, 255, 255));
      moneyCheck4.setForeground(new Color(0, 0, 0));

      ButtonGroup btnGroup = new ButtonGroup();
      btnGroup.add(moneyCheck1);
      btnGroup.add(moneyCheck2);
      btnGroup.add(moneyCheck3);
      btnGroup.add(moneyCheck4);
      
      if(PlayerVO.myVO.getMoney() < battingPoint[0])
    	  moneyCheck1.setEnabled(false);
      if(PlayerVO.myVO.getMoney() < battingPoint[1])
    	  moneyCheck2.setEnabled(false);
      if(PlayerVO.myVO.getMoney() < battingPoint[2])
    	  moneyCheck3.setEnabled(false);
      if(PlayerVO.myVO.getMoney() < battingPoint[3])
    	  moneyCheck4.setEnabled(false);
      
      
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
      
      JButton upBtn = new JButton(new ImageIcon(Lobby.class.getResource("/img/up.png")));
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
      
      JButton downBtn = new JButton(new ImageIcon(Lobby.class.getResource("/img/down.png")));
      downBtn.setBounds(230, 0, 30, 30);
      downBtn.setBackground(new Color(0, 0, 0, 0));
      downBtn.setFocusable(false);
      downBtn.setContentAreaFilled(false);
      downBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      
      downBtn.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            if(e.getSource()== downBtn) {
               if(i>=3)
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
      
      //만들기버튼
      JButton okBtn = new JButton("만들기");
      okBtn.setBounds(50, 235, 153, 50);
//      okBtn.setBackground(new Color(0, 0, 0, 0));
      okBtn.setFocusable(false);
//      okBtn.setContentAreaFilled(false);
      okBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      
      okBtn.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {            
        	 if(e.getSource()==okBtn) {
            	if(titleField.getText().trim().equals("")) {
            		JOptionPane.showMessageDialog(null, "방제목을 입력해주세요. ", "알림", JOptionPane.ERROR_MESSAGE);
            	}else if(!(moneyCheck1.isSelected() || moneyCheck2.isSelected() || moneyCheck3.isSelected() || moneyCheck4.isSelected())) {
            		JOptionPane.showMessageDialog(null, "배팅금액을 설정해주세요. ", "알림", JOptionPane.ERROR_MESSAGE);
            	}else {
            		Room room = new Room();
            		room.setTitle(titleField.getText());
            		
            		if(!pwField.getText().equals("")) {
            			room.setPassword(pwField.getText());
            			room.setPrivateRoom(true);
            		}
            		
            		room.setMaxPlayer(Integer.parseInt(pField.getText()));
            		
            		//체크된거에 따라 시작금액 설정
            		if(moneyCheck1.isSelected()) {
                		room.setStartMoney(10);
                	}else if(moneyCheck2.isSelected()) {
                		room.setStartMoney(50);
                	}else if(moneyCheck3.isSelected()) {
                		room.setStartMoney(100);
                	}else if(moneyCheck4.isSelected()) {
                		room.setStartMoney(500);
                	}
            		
            		makeJF.dispose();
            		make=false;
            		Lobby.getInstance().getLobbyJF().dispose();
            		ClientPacketSender.instance.makeRoom(room);
            	}
            }//if(e.getsorce())
         }//actionPerformed()
      });//addActionListener()
      
      //취소 버튼
      JButton cancelBtn = new JButton("취소");
      cancelBtn.setBounds(262, 235, 153, 50);
//    cancelBtn.setBackground(new Color(0, 0, 0, 0));
      cancelBtn.setFocusable(false);
//    cancelBtn.setContentAreaFilled(false);
      cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      
      cancelBtn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cancelBtn) {
				makeJF.dispose();
				make=false;
			}
		}
	});
      
      
      
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
      makeJF.setSize(500,360);
      makeJF.setVisible(true);
      makeJF.setResizable(false);
      makeJF.setLocationRelativeTo(null);
      makeJF.setLayout(null);
      makeJF.setDefaultCloseOperation(makeJF.EXIT_ON_CLOSE);
      
//      makeJF.setAlwaysOnTop(true);
      
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
      makeJF.setIconImage(img);
      
   }

public boolean isMake() { return make; }
   
}