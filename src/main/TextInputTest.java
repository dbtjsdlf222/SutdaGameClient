package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TextInputTest extends JFrame implements ActionListener
{
 Container container;
 JButton btOK;
 JTextField tfTitle;
 JTextArea taBody;

 // Constructor
 TextInputTest () {
  container = getContentPane();
  btOK = new JButton("전송");
  tfTitle = new JTextField(10);
  taBody = new JTextArea(10, 30);

  container.add(tfTitle, "North");
  container.add(taBody, "Center");
  container.add(btOK, "South");

  btOK.addActionListener(this);

  addWindowListener(new WindowAdapter()
  {
   public void windowClosing(WindowEvent we)
   {
    System.exit(0);
   }
  }
 );

 pack();
 show();
 }

 public void actionPerformed(ActionEvent ae)
 {
  taBody.append( tfTitle.getText() );
  tfTitle.setText("");
 }

 public static void main (String [] args)
 {
  new TextInputTest();
 }
}
