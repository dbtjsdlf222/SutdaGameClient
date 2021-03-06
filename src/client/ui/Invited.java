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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.Background;
import client.service.ClientPacketSender;
import server.Room;
import vo.PlayerVO;

public class Invited {
	private static Invited instance;
	private JFrame inviteJF;
	private Background imgP;
	private Container con;
	private JProgressBar progressBar;
	private PlayerVO vo;
	private Room room;
	private int i=10;
	private TimerTask tt;
	private final int x = 385;
	private final int y = 215;
	private boolean receiving = false;
	
	private Invited() {}
	
	public static Invited getInstance() {
		if(instance == null)
			instance = new Invited();
		return instance;
		
	}
	
	public void setVOROOM(PlayerVO vo, Room room) {
		this.vo = vo;
		this.room = room;
	}
	
	public void runUI() {
		//초대중으로 변경
		receiving = true;
		//JFrame
		inviteJF = new JFrame("초대장");
		con = inviteJF.getContentPane();
		
		
		//닉네임
		JLabel nicLbl = new JLabel();
		nicLbl.setText(vo.getNic()+"님이 " + room.getRoomNo() + "번방에서 초대하셨습니다.");
		nicLbl.setBounds(-3, 0, 400, 30);
		nicLbl.setBackground(Color.blue);
		nicLbl.setHorizontalAlignment(JLabel.CENTER);
		nicLbl.setForeground(new Color(255, 255, 255));
		nicLbl.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 13));
		
		JPanel nicPan = new JPanel();
		nicPan.setBounds(-3, 55, 400, 30);
		nicPan.setBackground(new Color(255, 0, 0, 0));
		nicPan.setLayout(null);
		nicPan.add(nicLbl);
		
		
		//수락/거절버튼
		JButton okBtn = new JButton("수 락");
		okBtn.setBounds(33, 135, 120, 50);
//	    okBtn.setBackground(new Color(0, 0, 0, 0));
		okBtn.setFocusable(false);
//	    okBtn.setContentAreaFilled(false);
		okBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==okBtn) {
					tt.cancel();
					receiving = false;
					inviteJF.dispose();
					Lobby.getInstance().getLobbyJF().dispose();
					ClientPacketSender.instance.enterRoom(room.getRoomNo());
					
				}
			}
		});
		
		
		
		JButton cancelBtn = new JButton("취소");
	    cancelBtn.setBounds(231, 135, 120, 50);
//	    cancelBtn.setBackground(new Color(0, 0, 0, 0));
	    cancelBtn.setFocusable(false);
//	    cancelBtn.setContentAreaFilled(false);
	    cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    
	    cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==cancelBtn) {
					inviteJF.dispose();
					receiving = false;
				}
			}
		});
	    
	    //포로세스바
	    progressBar = new JProgressBar(0,i);
	    progressBar.setBounds(30, 95, 330, 15);
		

		// Room의 큰 틀
		JLabel roomlbl = new JLabel("초 대 창");
		roomlbl.setFont(new Font("Rosewood Std", Font.PLAIN, 30));
		roomlbl.setForeground(new Color(255, 255, 255, 150));
		roomlbl.setBounds(0, 10, 400, 30);
		roomlbl.setHorizontalAlignment(JLabel.CENTER);

		JPanel roomPan = new JPanel();
		roomPan.setBounds(0, 0, x, y);
		roomPan.setBackground(new Color(0, 0, 0, 0));
		roomPan.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
		roomPan.setLayout(null);

		roomPan.add(roomlbl);
		roomPan.add(nicPan);
		roomPan.add(okBtn);
		roomPan.add(cancelBtn);
		roomPan.add(progressBar);
		con.add(roomPan);

		// JFrame 정보
		imgP = new Background();
		imgP.lobbyImage();
		con.add(imgP, BorderLayout.CENTER);
		inviteJF.setSize(x, y);
//		inviteJF.setResizable(false);
		inviteJF.setLocationRelativeTo(null);
		inviteJF.setUndecorated(true);
		inviteJF.setAlwaysOnTop(true);
//		inviteJF.setDefaultCloseOperation(inviteJF.EXIT_ON_CLOSE);
		inviteJF.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		inviteJF.setIconImage(img);
		
		inviteJF.repaint();
		progressBar_start();
	}

	
	
	
	 public void progressBar_start(){
		 Timer t = new Timer();
	     tt = new TimerTask() {
	    	
	    	 @Override
	    	 public void run() {
	    		 if(i>0) {
	    			 progressBar.setValue(i);
	    			 inviteJF.repaint();
	        		i--;
	    		 } else {
	    			 i=10;
	    			 receiving = false;
	    			 inviteJF.dispose();
	    			 tt.cancel();
	    		 }
	    	 }
	      };
    		t.schedule(tt,0,1000);
	         
	    } //progress_start()끝

	public boolean isReceiving() { return receiving; }

	 
}