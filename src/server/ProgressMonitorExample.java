package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class ProgressMonitorExample {

    public static void main(String[] args) {
        JFrame frame = createFrame("ProgressMonitor Example");
        JButton button = new JButton("차례 시작");
        button.addActionListener(createStartTaskActionListener(frame));
        frame.add(button, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private static ActionListener createStartTaskActionListener(Component parent) {
        //for progress monitor dialog title
        UIManager.put("ProgressMonitor.progressText", "섯다");
        return (ae) -> {
            new Thread(() -> {
                //creating ProgressMonitor instance
                ProgressMonitor pm = new ProgressMonitor(parent, "시간제한","2?", 0, 7);

                //decide after 100 millis whether to show popup or not
                pm.setMillisToDecideToPopup(7);
                //after deciding if predicted time is longer than 100 show popup
                pm.setMillisToPopup(100);
                for (int i = 7; i > 0; i--) {
                    //updating ProgressMonitor note
                    pm.setNote(i+"초");
                    //updating ProgressMonitor progress
                    pm.setProgress(i);
                    try {
                        //delay for task simulation
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
                pm.setNote("시간제한이 끝났습니다");
            }).start();
        };
    }

    public static JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 700));
        return frame;
    }
}