package Calendar.Gui;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Calendar.Gui.UserInterface.Frm;

/**
 * Created by Ali Yazdi on 22/05/2016.
 */

public class SystemTrayDemo {

    private String Rooz;
    private UserInterface Info;

    /**
     * Take Info from UI Project and Use of UI Method
     * @UseiInterface info
     */
    public SystemTrayDemo(UserInterface info) {
        this.Info = info;
    }

    /**
     * Sysstem Try that has day Icon and tooltip of celebration and clicking that open main Calendar
     */
    public void SystemTrayDemo() {


        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported !!! ");
            return;
        }

        SystemTray systemTray = SystemTray.getSystemTray();


        ULocale locale = new ULocale("fa_IR@calendar=persian");
        Info.focusCalendar = Calendar.getInstance(locale);
        Info.displayedCalendar = (Calendar) Info.focusCalendar.clone();
        Rooz = String.valueOf(Info.displayedCalendar.get(Calendar.DAY_OF_MONTH));
        Image image = Toolkit.getDefaultToolkit().getImage("src/images/" + Rooz + ".png");


        PopupMenu trayPopupMenu = new PopupMenu();


        MenuItem action = new MenuItem("نمایش پنجره اصلی");
        action.setFont(new Font("B Yekan", Font.PLAIN, 32));

        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frm.setVisible(true);
            }
        });
        trayPopupMenu.add(action);


        MenuItem addNote = new MenuItem("اضافه کردن یادداشت امروز");
        addNote.setFont(new Font("B Yekan", Font.PLAIN, 32));

        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Info.focusCalendar = Info.constantCalendar;
                Info.NewaddNote();
            }
        });
        trayPopupMenu.add(addNote);


        MenuItem addEvent = new MenuItem("اضافه کردن رویداد امروز");
        addEvent.setFont(new Font("B Yekan", Font.PLAIN, 32));

        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Info.focusCalendar = Info.constantCalendar;
                Info.NewaddEvent();

            }
        });
        trayPopupMenu.add(addEvent);

        MenuItem edit = new MenuItem("ویرایش امروز");
        edit.setFont(new Font("B Yekan", Font.PLAIN, 32));

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Info.focusCalendar = Info.constantCalendar;

                if(Info.Frm.isVisible()==false){
                    Info.Frm.setVisible(true);
                }
                Info.editeFunction();

            }
        });
        trayPopupMenu.add(edit);

        MenuItem close = new MenuItem("خروج");
        close.setFont(new Font("B Yekan", Font.PLAIN, 32));

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);

        String strdisplay = Info.MakeToolTip(Info.displayedCalendar.get(Calendar.DAY_OF_MONTH), Info.displayedCalendar.get(Calendar.MONTH));
        strdisplay = strdisplay.replaceAll("<br>", "\n");
        strdisplay = strdisplay.replaceAll("<html>", "");
        strdisplay = strdisplay.replaceAll("</html>", "");
        TrayIcon trayIcon = new TrayIcon(image, strdisplay, trayPopupMenu);


        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    Frm.setVisible(true);
                }
            }
        });
        try {
            systemTray.add(trayIcon);
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }

    }

    /**
     * run use for calling from UI and run SystemTray
     */
    public void  run(){

        SystemTrayDemo();

    }

}
