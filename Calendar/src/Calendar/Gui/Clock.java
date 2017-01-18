package Calendar.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by Ali Yazdi on 25/05/2016.
 */

public class Clock extends JPanel {

    private JLabel timeField;
    private UserInterface Info;

    /**
     * Set Time of Now To the TextField
     */
    public Clock() {

        timeField = new JLabel();

        setLayout(new BorderLayout());
        timeField.setFont(new Font("sansserif", Font.PLAIN, 48));

        timeField.setFont(new Font("B Yekan", Font.PLAIN, 40));
        timeField.setBackground(new Color(255, 0, 0));
        timeField.setForeground(Color.WHITE);
        timeField.setOpaque(true);
        Calendar init = Calendar.getInstance();
        int h = init.get(Calendar.HOUR_OF_DAY);
        int m = init.get(Calendar.MINUTE);
        int s = init.get(Calendar.SECOND);
        timeField.setText("" + h + ":" + m + ":" + s);
        timeField.setHorizontalAlignment(SwingConstants.CENTER);
        timeField.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        add(timeField, BorderLayout.NORTH);


        revalidate();
        setBackground(new Color(255, 0, 0));
        setMinimumSize(new Dimension(260, 150));
        setPreferredSize(new Dimension(260, 150));
        setMaximumSize(new Dimension(260, 150));
        setForeground(Color.WHITE);
        setOpaque(true);

        javax.swing.Timer t = new javax.swing.Timer(1000, new ClockListener());
        t.start();
    }

    /**
     * This class has method that take Now Time
     */
    class ClockListener implements ActionListener {

        /**
         * This take Now Time
         * @ActionEvent e
         */
        public void actionPerformed(ActionEvent e) {

            Calendar now = Calendar.getInstance();
            int h = now.get(Calendar.HOUR_OF_DAY);
            int m = now.get(Calendar.MINUTE);
            int s = now.get(Calendar.SECOND);
            timeField.setText("" + h + ":" + m + ":" + s);


        }

    }
}