package Calendar.Gui;

import javax.swing.*;

/**
 * Created by Ali Yazdi on 21/05/2016.
 */

public class Main {

    /**
     * The Main of Program
     * @String args
     */
    public static void main(String[] args) {

        UserInterface cal = new UserInterface();
        cal.setVisible(true);
        cal.setSize(300, 400);
        cal.setLocation(320, 220);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
