package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen extends JFrame implements ActionListener {
	JButton b1, b2, b3, b4, b5, b6;
	JTextField tf;
	
	protected Screen() {
		JPanel pan1 = new JPanel();
		pan1.setBounds(0, 620, 1280, 60);
		pan1.setLayout(new GridLayout(2, 6));
		pan1.setOpaque(false);

		JButton b1 = new JButton(new ImageIcon(Screen.class.getResource("../img/Allin.PNG")));
		pan1.add(b1);
		JButton b2 = new JButton(new ImageIcon(Screen.class.getResource("../img/Half.PNG")));
		pan1.add(b2);
		JButton b3 = new JButton(new ImageIcon(Screen.class.getResource("../img/Quater.PNG")));
		pan1.add(b3);
		JButton b4 = new JButton(new ImageIcon(Screen.class.getResource("../img/Bbing.PNG")));
		pan1.add(b4);
		JButton b5 = new JButton(new ImageIcon(Screen.class.getResource("../img/Call.PNG")));
		pan1.add(b5);
		JButton b6 = new JButton(new ImageIcon(Screen.class.getResource("../img/Die.PNG")));
		pan1.add(b6);

		JPanel pan2 = new JPanel();

		JPanel pan3 = new JPanel();

		JPanel pan4 = new JPanel();

		JPanel pan5 = new JPanel();

		JPanel pan6 = new JPanel();
		tf = new JTextField(15);
		pan6.add(tf);
		tf.setBounds(10, 10, 100, 30);

	}

	public static void main(String[] args) {
		Dimension dim = new Dimension(1280, 720);
		JFrame frame = new JFrame("자바 온라인");
		frame.setBackground(bgColor);
		frame.setLocation(500, 300);
		frame.setPreferredSize(dim);
	}
}