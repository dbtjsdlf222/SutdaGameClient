package server;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.MainScreen;
import vo.PlayerVO;

public class test extends JFrame {
	JPanel[] panlist = new JPanel[5]; 
	public void enterPlayer(ArrayList<PlayerVO> voList) {
		JLabel card1 = new JLabel(new ImageIcon(MainScreen.class.getResource("../img/Pae.PNG")));
		JLabel card2 = new JLabel(new ImageIcon(MainScreen.class.getResource("../img/Pae.PNG")));
		
		for (int i = 0; i < panlist.length; i++) {
			panlist[i] = new JPanel();
		}
		
	      for (int i = 0; i < voList.size(); i++) {
	         JTextField nicText = new JTextField(voList.get(i).getNic());
	         JTextField moneyText = new JTextField(voList.get(i).getMoney() + "");
	         JLabel profile = new JLabel(
	               new ImageIcon(MainScreen.class.getResource("../img/" + voList.get(i).getCha() + ".PNG")));

	         if (i == 1 || i == 2) {
	            card1.setBounds(170, 10, 110, 160);
	            card1.setOpaque(false);
	            card2.setBounds(230, 10, 110, 160);
	            card2.setOpaque(false);
	            profile.setBounds(10, 20, 110, 100);
	            profile.setOpaque(false);
	            nicText.setBounds(220, 120, 110, 20);
	            nicText.setOpaque(false);
	            moneyText.setBounds(220, 150, 110, 20);
	            moneyText.setOpaque(false);

	         } else if (i == 3 || i == 4) {
	            card1.setBounds(10, 10, 110, 160);
	            card1.setOpaque(false);
	            card2.setBounds(70, 10, 110, 160);
	            card2.setOpaque(false);
	            profile.setBounds(220, 20, 110, 100);
	            profile.setOpaque(false);
	            nicText.setBounds(10, 120, 110, 20);
	            nicText.setOpaque(false);
	            moneyText.setBounds(10, 150, 110, 20);
	            moneyText.setOpaque(false);
	         }
	         
	         panlist[i].add(card1);
	         panlist[i].add(card2);
	         panlist[i].add(profile);
	         panlist[i].add(nicText);
	         panlist[i].add(moneyText);
	         add(panlist[i]);
	         
	         
	         
	         if(i==0) {
	         card1.setBounds(180, 10, 110, 160);
	         card1.setOpaque(false);
	         card2.setBounds(300, 10, 110, 160);
	         card2.setOpaque(false);
	         profile.setBounds(10, 20, 130, 100);
	         profile.setOpaque(false);
	         nicText.setBounds(20, 120, 110, 20);
	         nicText.setOpaque(false);
	         moneyText.setBounds(20, 150, 110, 20);
	         moneyText.setOpaque(false);
	         nicText.setHorizontalAlignment(JLabel.CENTER);
	         moneyText.setHorizontalAlignment(JLabel.CENTER);
	         nicText.setForeground(Color.white);
	         moneyText.setForeground(Color.white);
	         panlist[i].add(card1);
	         panlist[i].add(card2);
	         panlist[i].add(profile);
	         panlist[i].add(nicText);
	         panlist[i].add(moneyText);
	         }
	      }
	   }
}
