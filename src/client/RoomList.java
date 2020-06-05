package client;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;

public class RoomList {
	private JTextField textField;

	public void roomList() {
		JFrame roList = new JFrame();
		roList.setSize(800, 950);
		roList.setLocation(600, 50);
		roList.setLayout(null);

		JTextField chatText = new JTextField();
		chatText.setBounds(0, 887, 450, 25);
		roList.add(chatText);
		chatText.requestFocus();
		
		JTextArea chatArea = new JTextArea();
		chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 609, 518, 280);
		roList.add(scrollPane);
		
		
		
		JButton chatBtn = new JButton("보내기");
		chatBtn.setBounds(448, 887, 70, 24);
		roList.add(chatBtn);
		chatBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == chatBtn) {
					chatArea.append(chatText.getText() + "\n");
					chatText.requestFocus();
					chatText.setText("");
				}
			}
		});

		JRootPane  rootPane  =  roList.getRootPane();
        rootPane.setDefaultButton(chatBtn);  
		

		roList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roList.setVisible(true);

	}

	public static void main(String[] args) {
		RoomList list = new RoomList();
		list.roomList();

	}

}
