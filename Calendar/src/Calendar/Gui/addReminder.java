package Calendar.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task to execute once 5
 * seconds have passed.
 */

public class addReminder {

    Timer timer;
    String OnvanAlarm;

    /**
     * This add Alarm for a during time
     *
     * @int seconds
     * @String Onvan
     */
    public addReminder(int seconds, String Onvan) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds * 1000);
        OnvanAlarm = Onvan;
    }

    /**
     * This work in system notification Bar when alarm is set
     *
     * @throws AWTException
     */
    public void displayTray() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon("src/images/calendar-icon.png");
        Image image = icon.getImage();
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(OnvanAlarm);
        tray.add(trayIcon);
        trayIcon.displayMessage("زنگ شما :)", OnvanAlarm, TrayIcon.MessageType.INFO);
    }

    /**
     * Has the method of Run the class
     */
    class RemindTask extends TimerTask {

        /**
         * Calling the Method of the addReminder Class
         */
        public void run() {
            try {
                displayTray();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
}