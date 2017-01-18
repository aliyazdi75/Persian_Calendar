package Calendar.Gui;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Ali Yazdi on 21/05/2016.
 */

public class UserInterface extends JComponent {

    public static JFrame Frm;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu insertMenu = new JMenu(), editMenuPop = new JMenu(), insertMenuPop = new JMenu(), editMenu = new JMenu();
    private JMenuItem newEventMenuItem, newNoteMenuItem, editEventNoteMenuItem, copyEventNoteMenuItem, copyCelebMenuItem;
    public JPopupMenu popup = new JPopupMenu();
    private static final String[] daysName = {"شنبه", "یک شنبه", "دو شنبه", "سه شنبه",
            "چهار شنبه", "پنچ شنبه", "جمعه"}, monthsName = {"فروردین", "اردیبهشت", "خرداد", "تیر",
            "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    };
    private static JPanel pnlDate, pnlDateTop, pnlDaysDate, pnlMainCalendar, pnlCalensrCenter, pnlBottomStatus,
            pnlMonth1, pnlMonth2, pnlMonth11, pnlMonth12, pnlMonth21, pnlMonth22, pnlNotesText, pnlEventNote,
            pnlTools, pnlEvent, pnlNote, lplNote;
    private static JLabel[] lblDaysDate, Month = new JLabel[12];
    private static JLabel nextMonth, previousMonth, lblDay, lblYear, lblCalendar, lblDateTop, lblHijri, lblGregorian, lblProgramName,
            lblEvent, lblNoteKarbar, lblEdit, lblEventTitle, lblNoteKarbarTitle, lblEventView, lblNoteView, lblCelebrate, lblCelebrateTitle;
    private static int displayedYear, displayedMonth, displayedDay, focusYear, focusMonth, focusDay;
    public static Calendar displayedCalendar, focusCalendar, constantCalendar;
    public JTextPane txtCelebrate, txtNotek = new JTextPane(), txtEvent = new JTextPane();
    private Clock clock = new Clock();
    private addEventDialog FrmAddEvent;
    private addNote FrmAddNote;
    private addReminder Rmnd;
    int forEnableNote, forEnableEvent;


    /**
     * The programm UI
     */
    public UserInterface() {
        CrtClnd();
        CrtGui();
        final UserInterface info = this;
        SystemTrayDemo tray = new SystemTrayDemo(info);
        tray.run();
        Popup();
    }

    /**
     * Set Persian Calendar
     */
    public void CrtClnd() {
        ULocale locale = new ULocale("fa_IR@calendar=persian");
        focusCalendar = Calendar.getInstance(locale);
        constantCalendar = Calendar.getInstance(locale);
        displayedCalendar = (Calendar) focusCalendar.clone();
        displayedYear = displayedCalendar.get(Calendar.YEAR);
        displayedMonth = displayedCalendar.get(Calendar.MONTH);
        displayedDay = displayedCalendar.get(Calendar.DAY_OF_MONTH);
        focusYear = displayedCalendar.get(Calendar.YEAR);
        focusMonth = displayedCalendar.get(Calendar.MONTH);
        focusDay = displayedCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Create Graphic UI and Component
     */
    public void CrtGui() {
        CrtFrm();
        MenuBarDemo();
        CrtTopUI();
        CrtCenterUI();
        refreshClnd();
        CrtCelebrate();
        CrtHijriMiladi();
        showDataFiles();
        addNote();
        addEvent();
        editNoteEvent();
        Frm.revalidate();
    }

    /**
     * Create Frame of Main Calendar
     */
    public void CrtFrm() {
        ImageIcon imgicon = new ImageIcon("src/images/Icon.png");
        Frm = new JFrame("تقویم پارسی");
        Frm.setSize(1800, 1720);
        Frm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Frm.setLocationRelativeTo(null);
        Frm.setIconImage(imgicon.getImage());
        Frm.setVisible(true);
    }

    /**
     * Crate ItemMenu of Frame
     */
    public void MenuItem() {


        insertMenu.setText("درج");
        insertMenu.setMnemonic('n');
        insertMenu.setDisplayedMnemonicIndex(0);
        insertMenu.setFont(new Font("IRCompset", Font.PLAIN, 30));
        insertMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        insertMenu.setMinimumSize(new Dimension(70, 50));
        insertMenu.setPreferredSize(new Dimension(70, 50));
        insertMenu.setMaximumSize(new Dimension(70, 50));
        insertMenu.revalidate();

        insertMenuPop.setText("درج");
        insertMenuPop.setMnemonic('n');
        insertMenuPop.setDisplayedMnemonicIndex(0);
        insertMenuPop.setFont(new Font("IRCompset", Font.PLAIN, 30));
        insertMenuPop.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        insertMenuPop.revalidate();

        editMenu.setText("ویرایش");
        editMenu.setMnemonic(',');
        editMenu.setDisplayedMnemonicIndex(0);
        editMenu.setFont(new Font("IRCompset", Font.PLAIN, 30));
        editMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        editMenu.setMinimumSize(new Dimension(90, 50));
        editMenu.setPreferredSize(new Dimension(90, 50));
        editMenu.setMaximumSize(new Dimension(90, 50));
        editMenu.revalidate();

        editMenuPop.setText("ویرایش");
        editMenuPop.setMnemonic(',');
        editMenuPop.setDisplayedMnemonicIndex(0);
        editMenuPop.setFont(new Font("IRCompset", Font.PLAIN, 30));
        editMenuPop.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        editMenuPop.revalidate();

        newEventMenuItem = new JMenuItem("اضافه کردن رویداد", SwingConstants.CENTER);
        newEventMenuItem.setMnemonic('v');
        newEventMenuItem.setDisplayedMnemonicIndex(11);
        newEventMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newEventMenuItem.setActionCommand("اضافه کردن رویداد");
        newEventMenuItem.setFont(new Font("IRCompset", Font.PLAIN, 25));
        newEventMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        newEventMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                NewaddEvent();
            }
        });

        newNoteMenuItem = new JMenuItem("اضافه کردن یادداشت", SwingConstants.CENTER);
        newNoteMenuItem.setMnemonic('d');
        newNoteMenuItem.setDisplayedMnemonicIndex(11);
        newNoteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        newNoteMenuItem.setActionCommand("اضافه کردن یادداشت");
        newNoteMenuItem.setFont(new Font("IRCompset", Font.PLAIN, 25));
        newNoteMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        newNoteMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                NewaddNote();
            }
        });

        editEventNoteMenuItem = new JMenuItem("ویرایش رویدادها و یادداشت های روز مربوطه"
                , SwingConstants.CENTER);
        editEventNoteMenuItem.setMnemonic(',');
        editEventNoteMenuItem.setDisplayedMnemonicIndex(0);
        editEventNoteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        editEventNoteMenuItem.setActionCommand("ویرایش رویدادها و یادداشت های روز مربوطه");
        editEventNoteMenuItem.setFont(new Font("IRCompset", Font.PLAIN, 25));
        editEventNoteMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        editEventNoteMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                editeFunction();
            }
        });

        copyEventNoteMenuItem = new JMenuItem("کپی کردن متن تاریخ روز انتخاب شده"
                , SwingConstants.CENTER);
        copyEventNoteMenuItem.setMnemonic('j');
        copyEventNoteMenuItem.setDisplayedMnemonicIndex(13);
        copyEventNoteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        copyEventNoteMenuItem.setActionCommand("کپی کردن متن تاریخ روز انتخاب شده");
        copyEventNoteMenuItem.setFont(new Font("IRCompset", Font.PLAIN, 25));
        copyEventNoteMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        copyEventNoteMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                String myString = String.valueOf(focusCalendar.get(Calendar.DAY_OF_MONTH) + " " +
                        monthsName[displayedCalendar.get(Calendar.MONTH)]) + " " + focusCalendar.get(Calendar.YEAR);
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        copyCelebMenuItem = new JMenuItem("کپی کردن متن مناسبت روز انتخاب شده"
                , SwingConstants.CENTER);
        copyCelebMenuItem.setMnemonic('l');
        copyCelebMenuItem.setDisplayedMnemonicIndex(13);
        copyCelebMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copyCelebMenuItem.setActionCommand("کپی کردن متن مناسبت روز انتخاب شده");
        copyCelebMenuItem.setFont(new Font("IRCompset", Font.PLAIN, 25));
        copyCelebMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        copyCelebMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                String myString = txtCelebrate.getText();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        insertMenu.add(newEventMenuItem);
        insertMenu.addSeparator();
        insertMenu.add(newNoteMenuItem);
        editMenu.add(editEventNoteMenuItem);
        editMenu.addSeparator();
        editMenu.add(copyEventNoteMenuItem);
        editMenu.addSeparator();
        editMenu.add(copyCelebMenuItem);

    }

    /**
     * Creat MenuBar
     */
    public void MenuBarDemo() {

        MenuItem();
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuBar.add(insertMenu);
        menuBar.add(editMenu);

        Frm.setJMenuBar(menuBar);
    }

    /**
     * Create the top of Frame include main calendar
     */
    public void CrtTopUI() {
        pnlCalensrCenter = new JPanel();
        pnlCalensrCenter.setLayout(new BorderLayout());
        pnlDate = new JPanel();
        pnlMonth1 = new JPanel();
        pnlMonth2 = new JPanel();
        pnlMonth11 = new JPanel();
        pnlMonth12 = new JPanel();
        pnlMonth21 = new JPanel();
        pnlMonth22 = new JPanel();
        pnlDate.setLayout(new BorderLayout());
        pnlMonth1.setLayout(new BorderLayout());
        pnlMonth2.setLayout(new BorderLayout());
        pnlMonth11.setLayout(new BorderLayout());
        pnlMonth12.setLayout(new BorderLayout());
        pnlMonth21.setLayout(new BorderLayout());
        pnlMonth22.setLayout(new BorderLayout());
        pnlDateTop = new JPanel();
        pnlDateTop.setLayout(new BorderLayout());
        pnlMainCalendar = new JPanel();
        pnlMainCalendar.setLayout(new BorderLayout());
        lblDateTop = new JLabel("", SwingConstants.CENTER);
        lblDateTop.setText(daysName[displayedCalendar.get(Calendar.DAY_OF_WEEK) % 7]);
        lblDateTop.setMinimumSize(new Dimension(260, 50));
        lblDateTop.setPreferredSize(new Dimension(260, 50));
        lblDateTop.setMaximumSize(new Dimension(260, 50));
        lblDateTop.revalidate();
        lblDateTop.setFont(new Font("IRCompset", Font.PLAIN, 32));
        pnlDateTop.add(lblDateTop, BorderLayout.CENTER);
        pnlDate.add(pnlDateTop, BorderLayout.NORTH);
        pnlMainCalendar.add(clock, BorderLayout.NORTH);
        lblCalendar = new JLabel("", SwingConstants.CENTER);
        lblCalendar.setFont(new Font("IRCompset", Font.BOLD, 100));
        lblCalendar.setForeground(Color.WHITE);
        lblCalendar.setMinimumSize(new Dimension(260, 100));
        lblCalendar.setPreferredSize(new Dimension(260, 100));
        lblCalendar.setMaximumSize(new Dimension(260, 100));
        lblYear = new JLabel("1395", SwingConstants.CENTER);

        lblYear.setFont(new Font("IRCompset", Font.PLAIN, 35));
        lblYear.setForeground(Color.WHITE);
        lblYear.setMinimumSize(new Dimension(260, 60));
        lblYear.setPreferredSize(new Dimension(260, 60));
        lblYear.setMaximumSize(new Dimension(260, 60));
        lblDay = new JLabel("1", SwingConstants.CENTER);
        lblDay.setFont(new Font("B Yekan", Font.PLAIN, 170));
        lblDay.setForeground(Color.WHITE);
        lblDay.setMinimumSize(new Dimension(260, 130));
        lblDay.setPreferredSize(new Dimension(260, 130));
        lblDay.setMaximumSize(new Dimension(260, 130));
        lblDay.setText(String.valueOf(displayedCalendar.get(Calendar.DAY_OF_MONTH)));
        pnlMainCalendar.setBackground(new Color(255, 0, 0));
        pnlMainCalendar.setOpaque(true);
        clock.add(lblCalendar, BorderLayout.SOUTH);
        pnlMainCalendar.add(lblDay, BorderLayout.CENTER);
        pnlMainCalendar.add(lblYear, BorderLayout.SOUTH);
        pnlDate.add(pnlMainCalendar, BorderLayout.CENTER);
        lblDateTop.setOpaque(true);
        lblDateTop.setBackground(new Color(5, 50, 50));
        lblDateTop.setForeground(Color.WHITE);
        pnlDate.setBackground(new Color(255, 0, 0));
        pnlDate.setForeground(Color.WHITE);
        pnlDate.setMinimumSize(new Dimension(260, 410));
        pnlDate.setPreferredSize(new Dimension(260, 410));
        pnlDate.setMaximumSize(new Dimension(260, 130));
        pnlDate.setOpaque(true);
        pnlMonth1.setBackground(new Color(255, 0, 0));
        pnlMonth1.setForeground(Color.WHITE);
        pnlMonth1.setOpaque(true);
        pnlMonth2.setBackground(new Color(255, 0, 0));
        pnlMonth2.setForeground(Color.WHITE);
        pnlMonth2.setOpaque(true);
        pnlMonth12.setBackground(new Color(255, 0, 0));
        pnlMonth12.setForeground(Color.WHITE);
        pnlMonth12.setOpaque(true);
        pnlMonth11.setBackground(new Color(255, 0, 0));
        pnlMonth11.setForeground(Color.WHITE);
        pnlMonth11.setOpaque(true);
        pnlMonth22.setBackground(new Color(255, 0, 0));
        pnlMonth22.setForeground(Color.WHITE);
        pnlMonth22.setOpaque(true);
        pnlMonth21.setBackground(new Color(255, 0, 0));
        pnlMonth21.setForeground(Color.WHITE);
        pnlMonth21.setOpaque(true);
        nextMonth = new JLabel("> ", SwingConstants.LEFT);
        nextMonth.setToolTipText("رفتن به ماه بعد");
        nextMonth.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        nextMonth.setForeground(new Color(200, 200, 200));
        nextMonth.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (displayedMonth == 11) {
                        displayedYear++;
                        displayedMonth = 0;
                    } else {
                        displayedMonth++;
                    }
                    int hardDay = focusCalendar.get(Calendar.DAY_OF_WEEK);
                    focusYear = displayedYear;
                    focusMonth = displayedMonth;
                    focusCalendar.set(focusYear, focusMonth, focusDay);
                    UpdateHijriMiladi();
                    refreshClnd();
                }

            }

            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(109, 109, 109));
                }
            }

            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(200, 200, 200));
                }
            }
        });
        Month[0] = new JLabel("فروردین", SwingConstants.CENTER);
        Month[1] = new JLabel("اردیبهشت", SwingConstants.CENTER);
        Month[2] = new JLabel("خرداد", SwingConstants.CENTER);
        Month[3] = new JLabel("تیر", SwingConstants.CENTER);
        Month[4] = new JLabel("مرداد", SwingConstants.CENTER);
        Month[5] = new JLabel("شهریور", SwingConstants.CENTER);
        Month[6] = new JLabel("مهر", SwingConstants.CENTER);
        Month[7] = new JLabel("آبان", SwingConstants.CENTER);
        Month[8] = new JLabel("آذر", SwingConstants.CENTER);
        Month[9] = new JLabel("دی", SwingConstants.CENTER);
        Month[10] = new JLabel("بهمن", SwingConstants.CENTER);
        Month[11] = new JLabel("اسفند", SwingConstants.CENTER);
        for (int i = 0; i < 12; i++) {
            Month[i].setFont(new Font("IRCompset", Font.PLAIN, 40));
            Month[i].setBackground(new Color(200, 0, 0));
            Month[i].setForeground(Color.WHITE);
            Month[i].setMinimumSize(new Dimension(150, 60));
            Month[i].setPreferredSize(new Dimension(150, 60));
            Month[i].setMaximumSize(new Dimension(150, 60));
            Month[i].setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
            Month[i].setOpaque(true);
        }
        previousMonth = new JLabel(" <", SwingConstants.RIGHT);
        previousMonth.setToolTipText("رفتن به ماه قبل");
        previousMonth.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        previousMonth.setForeground(new Color(200, 200, 200));
        previousMonth.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (displayedMonth == 0) {
                        displayedYear--;
                        displayedMonth = 11;
                    } else {
                        displayedMonth--;
                    }
                    int hardDay = focusCalendar.get(Calendar.DAY_OF_WEEK);
                    focusYear = displayedYear;
                    focusMonth = displayedMonth;
                    focusCalendar.set(focusYear, focusMonth, focusDay);
                    UpdateHijriMiladi();
                    refreshClnd();
                }
            }

            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(109, 109, 109));
                }
            }

            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(200, 200, 200));
                }
            }
        });

        pnlDate.add(pnlMonth1, BorderLayout.EAST);
        pnlMonth1.add(pnlMonth11, BorderLayout.NORTH);
        pnlMonth11.add(Month[0], BorderLayout.NORTH);
        pnlMonth11.add(Month[1], BorderLayout.CENTER);
        pnlMonth11.add(Month[2], BorderLayout.SOUTH);
        pnlMonth1.add(pnlMonth12, BorderLayout.SOUTH);
        pnlMonth12.add(Month[3], BorderLayout.NORTH);
        pnlMonth12.add(Month[4], BorderLayout.CENTER);
        pnlMonth12.add(Month[5], BorderLayout.SOUTH);

        pnlDate.add(pnlMonth2, BorderLayout.WEST);
        pnlMonth2.add(pnlMonth21, BorderLayout.NORTH);
        pnlMonth21.add(Month[6], BorderLayout.NORTH);
        pnlMonth21.add(Month[7], BorderLayout.CENTER);
        pnlMonth21.add(Month[8], BorderLayout.SOUTH);
        pnlMonth2.add(pnlMonth22, BorderLayout.SOUTH);
        pnlMonth22.add(Month[9], BorderLayout.NORTH);
        pnlMonth22.add(Month[10], BorderLayout.CENTER);
        pnlMonth22.add(Month[11], BorderLayout.SOUTH);

        pnlMainCalendar.add(nextMonth, BorderLayout.EAST);
        pnlMainCalendar.add(previousMonth, BorderLayout.WEST);
        pnlDate.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 4));
        pnlCalensrCenter.add(pnlDate, BorderLayout.NORTH);

    }

    /**
     * Crate the buttom of Frame include the days Table
     */
    public void CrtCenterUI() {
        pnlDaysDate = new JPanel();
        pnlDaysDate.setBackground(new Color(247, 247, 247));
        pnlDaysDate.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 4));
        pnlDaysDate.setLayout(new GridLayout(7, 7));
        lblDaysDate = new JLabel[49];
        String daysNames[] = {"ش", "ی", "د", "س", "چ", "پ", "ج"};
        for (int i = 6; i >= 0; i--) {
            lblDaysDate[i] = new JLabel(daysNames[i], SwingConstants.CENTER);
            lblDaysDate[i].setFont(new Font("IRCompset", Font.BOLD, 30));
            lblDaysDate[i].setForeground(new Color(109, 109, 109));
            lblDaysDate[i].setBackground(new Color(247, 247, 247));
            lblDaysDate[i].setOpaque(true);
            pnlDaysDate.add(lblDaysDate[i]);
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 6; j >= 0; j--) {
                int idx = i * 7 + j;
                lblDaysDate[idx] = new JLabel(String.valueOf(1), SwingConstants.CENTER);
                lblDaysDate[idx].setFont(new Font("IRCompset", Font.PLAIN, 27));
                lblDaysDate[idx].setForeground(new Color(109, 109, 109));
                lblDaysDate[idx].setOpaque(true);
                pnlDaysDate.add(lblDaysDate[idx]);
            }
        }
        pnlCalensrCenter.add(pnlDaysDate, BorderLayout.CENTER);
        pnlEventNote = new JPanel();
        pnlEventNote.setBackground(new Color(247, 247, 247));
        pnlEventNote.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 4));
        pnlEventNote.setLayout(new GridLayout(1, 2));
        pnlEventNote.setMinimumSize(new Dimension(300, 500));
        pnlEventNote.setPreferredSize(new Dimension(300, 500));
        pnlEventNote.setMaximumSize(new Dimension(300, 1000));
        pnlCalensrCenter.add(pnlEventNote, BorderLayout.SOUTH);
        Frm.add(pnlCalensrCenter, BorderLayout.CENTER);
        refreshClnd();
    }

    /**
     * This refresh all calender such az table with clicking
     */
    public void refreshClnd() {
        displayedCalendar.set(displayedYear, displayedMonth, displayedDay);
        lblCalendar.setText(monthsName[displayedCalendar.get(Calendar.MONTH)]);
        lblYear.setText(String.valueOf(displayedCalendar.get(Calendar.YEAR)));
        lblDateTop.setText(daysName[displayedCalendar.get(Calendar.DAY_OF_WEEK) % 7]);


        MonthHandler handlerM = new MonthHandler();
        for (int i = 0; i < 12; i++) {
            if (i == displayedMonth) {
                Month[i].setForeground(Color.WHITE);
                Month[i].setBackground(new Color(100, 50, 0));
                Month[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 4));
            } else {
                Month[i].setForeground(Color.LIGHT_GRAY);
                Month[i].setBackground(new Color(200, 0, 0));
                Month[i].setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
            }

            Month[i].addMouseListener(handlerM);
            Month[i].addMouseMotionListener(handlerM);
        }
        refreshDaysDateTable();
    }

    /**
     * Create Note of Celebrations in the Frame
     */
    public void CrtCelebrate() {
        lplNote = new JPanel();
        //lplNote.setLayout(new GridLayout(7, 7));
        lplNote.setLayout(new BorderLayout());
        lplNote.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 4));
        lblCelebrate = new JLabel("", SwingConstants.RIGHT);
        lblCelebrate.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblCelebrate.setAlignmentY(JLabel.TOP_ALIGNMENT);
        lblCelebrate.setMinimumSize(new Dimension(700, 500));
        lblCelebrate.setPreferredSize(new Dimension(700, 500));
        lblCelebrate.setMaximumSize(new Dimension(700, 500));
        lblCelebrate.setFont(new Font("IRCompset", Font.PLAIN, 40));
        lblCelebrate.setBackground(new Color(247, 247, 247));
        lblCelebrate.setForeground(new Color(109, 109, 109));
        lblCelebrate.setOpaque(true);

        lblCelebrateTitle = new JLabel("مناسبت ها", SwingConstants.CENTER);
        lblCelebrateTitle.setMinimumSize(new Dimension(1100, 70));
        lblCelebrateTitle.setPreferredSize(new Dimension(1100, 70));
        lblCelebrateTitle.setMaximumSize(new Dimension(1300, 70));
        lblCelebrateTitle.revalidate();
        lblCelebrateTitle.setFont(new Font("IRCompset", Font.BOLD, 50));
        lblCelebrateTitle.setBackground(new Color(0, 0, 250));
        lblCelebrateTitle.setForeground(Color.WHITE);
        lblCelebrateTitle.setOpaque(true);
        lplNote.add(lblCelebrateTitle, BorderLayout.NORTH);
        pnlNotesText = new JPanel();
        //pnlNotesText.setLayout(new GridLayout(7, 7));
        pnlNotesText.setLayout(new BorderLayout());
        pnlNotesText.add(lblCelebrate, BorderLayout.CENTER);
        lplNote.setMinimumSize(new Dimension(1100, 500));
        lplNote.setPreferredSize(new Dimension(1100, 500));
        lplNote.setMaximumSize(new Dimension(1300, 1000));
        /*lplNote.setMinimumSize(new Dimension(1100, 500));
        lplNote.setPreferredSize(new Dimension(1100, 500));
        lplNote.setMaximumSize(new Dimension(1300, 1000));*/
        lplNote.add(pnlNotesText, BorderLayout.CENTER);
        txtCelebrate = new JTextPane();

        txtCelebrate.setEditable(false);
        txtCelebrate.setFont(new Font("IRCompset", Font.BOLD, 30));

        txtCelebrate.setMinimumSize(new Dimension(1100, 500));
        txtCelebrate.setPreferredSize(new Dimension(1100, 500));
        txtCelebrate.setMaximumSize(new Dimension(1300, 1000));
        txtCelebrate.setBackground(new Color(247, 247, 247));
        txtCelebrate.setForeground(new Color(109, 109, 109));

        txtCelebrate.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        txtCelebrate.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


        pnlNotesText.add(txtCelebrate, BorderLayout.CENTER);

        pnlCalensrCenter.add(lplNote, BorderLayout.EAST);
    }

    /**
     * Create Hijri and Miladi Calendar
     */
    public void CrtHijriMiladi() {
        pnlBottomStatus = new JPanel();
        pnlBottomStatus.setLayout(new BorderLayout());
        lblHijri = new JLabel("", SwingConstants.CENTER);
        lblGregorian = new JLabel("", SwingConstants.CENTER);
        lblProgramName = new JLabel("", SwingConstants.CENTER);
        pnlDateTop.add(lblHijri, BorderLayout.WEST);
        ULocale Hlocale = new ULocale("fa_IR@calendar=islamic");
        Calendar HijriCalendar = Calendar.getInstance(Hlocale);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(HijriCalendar.getTime());

        DateFormat dt = DateFormat.getDateInstance(DateFormat.LONG, Hlocale);
        HijriCalendar.add(Calendar.DAY_OF_MONTH, -1);
        String s = dt.format(HijriCalendar.getTime());
        s = s.substring(0, s.length() - 5);

        lblHijri.setText(s + "          ");
        lblHijri.setBackground(new Color(5, 50, 50));
        lblHijri.setFont(new Font("IRCompset", Font.PLAIN, 28));
        lblHijri.setOpaque(true);
        lblHijri.setForeground(Color.WHITE);
        lblHijri.setMinimumSize(new Dimension(270, 50));
        lblHijri.setPreferredSize(new Dimension(270, 50));
        lblHijri.setMaximumSize(new Dimension(270, 50));
        lblHijri.revalidate();

        pnlDateTop.add(lblGregorian, BorderLayout.EAST);
        ULocale Glocale = new ULocale("en_US@calendar=gregorian");
        Calendar GregorianCalendar = Calendar.getInstance(Glocale);

        DateFormat gdt = DateFormat.getDateInstance(DateFormat.LONG, Glocale);
        String gs = gdt.format(GregorianCalendar.getTime());
        lblGregorian.setText(gs + "   ");
        lblGregorian.setBackground(new Color(5, 50, 50));
        lblGregorian.setFont(new Font("IRCompset", Font.PLAIN, 28));
        lblGregorian.setOpaque(true);
        lblGregorian.setForeground(Color.WHITE);
        lblGregorian.setMinimumSize(new Dimension(290, 50));
        lblGregorian.setPreferredSize(new Dimension(290, 50));
        lblGregorian.setMaximumSize(new Dimension(290, 50));
        lblGregorian.revalidate();

        pnlBottomStatus.add(lblProgramName, BorderLayout.CENTER);
        lblProgramName.setText("تقویم پارسی");
        lblProgramName.setBackground(new Color(55, 100, 50));
        lblProgramName.setFont(new Font("B Yekan", Font.PLAIN, 28));
        lblProgramName.setOpaque(true);
        lblProgramName.setForeground(Color.WHITE);

       /* DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        pnlBottomStatus.add(lblTime, BorderLayout.CENTER);
        lblTime.setText(dateFormat.format(date));
        lblTime.setBackground(new Color(55, 100, 50));
        lblTime.setFont(new Font("IRCompset", Font.PLAIN, 28));
        lblTime.setOpaque(true);
        lblTime.setForeground(Color.WHITE);
        lblTime.setMinimumSize(new Dimension(270, 50));
        lblTime.setPreferredSize(new Dimension(270, 50));
        lblTime.setMaximumSize(new Dimension(270, 50));
        lblTime.revalidate();*/

        Frm.add(pnlBottomStatus, BorderLayout.SOUTH);
    }

    /**
     * Update them with refresh
     */
    public void UpdateHijriMiladi() {

        ULocale Hlocale = new ULocale("fa_IR@calendar=islamic");
        Calendar HijriCalendar = Calendar.getInstance(Hlocale);
        HijriCalendar = (Calendar) focusCalendar.clone();
        HijriCalendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(HijriCalendar.getTime());
        DateFormat dt = DateFormat.getDateInstance(DateFormat.LONG, Hlocale);
        String s = dt.format(HijriCalendar.getTime());
        s = s.substring(0, s.length() - 5);
        lblHijri.setText(s + "          ");
        ULocale Glocale = new ULocale("en_US@calendar=gregorian");
        Calendar GregorianCalendar = Calendar.getInstance(Glocale);
        GregorianCalendar = (Calendar) focusCalendar.clone();
        DateFormat gdt = DateFormat.getDateInstance(DateFormat.LONG, Glocale);
        String gs = gdt.format(GregorianCalendar.getTime());
        lblGregorian.setText(gs + "   ");
        lblGregorian.revalidate();
        lblHijri.revalidate();
        showDataFiles();
    }

    /**
     * Action on click in Month of years
     */
    class MonthHandler extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "" && label.getForeground().equals(Color.LIGHT_GRAY)) {
                    String a = label.getText();
                    displayedMonth = (a == "فروردین") ? 0 : (a == "اردیبهشت") ? 1 : (a == "خرداد") ? 2 : (a == "تیر") ? 3 : (a == "مرداد") ? 4
                            : (a == "شهریور") ? 5 : (a == "مهر") ? 6 : (a == "آبان") ? 7 : (a == "آذر") ? 8 : (a == "دی") ? 9
                            : (a == "بهمن") ? 10 : 11;
                    int hardDay = focusCalendar.get(Calendar.DAY_OF_WEEK);
                    focusYear = displayedYear;
                    focusMonth = displayedMonth;
                    focusCalendar.set(focusYear, focusMonth, focusDay);
                    UpdateHijriMiladi();
                    refreshClnd();
                }
                if (txtNotek.getText().equals("") || txtEvent.getText().equals(""))
                    lblEdit.setForeground(Color.GRAY);
                else
                    lblEdit.setForeground(Color.WHITE);
            }
        }

        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label.getText() != "" && !label.getForeground().equals(Color.WHITE)) {
                label.setBackground(new Color(100, 50, 0));
                label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 4));
            }
        }

        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label.getText() != "" && !label.getForeground().equals(Color.WHITE)) {
                label.setForeground(Color.LIGHT_GRAY);
                label.setBackground(new Color(200, 0, 0));
                label.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
            }
        }
    }

    /**
     * Java popUp Menu Bar get MenuItem
     */
    public void Popup() {

        MenuItem();
        JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
        popup.add(insertMenuPop);
        popup.add(editMenuPop);
        insertMenuPop.add(newEventMenuItem);
        insertMenuPop.add(seperator);
        insertMenuPop.add(newNoteMenuItem);
        editMenuPop.add(editEventNoteMenuItem);
        editMenuPop.add(seperator);
        editMenuPop.add(copyEventNoteMenuItem);
        editMenuPop.add(seperator);
        editMenuPop.add(copyCelebMenuItem);
        popup.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        popup.setBorder(new BevelBorder(BevelBorder.RAISED));

    }

    /**
     * Action on click in table of days
     */
    class lblDaysDateHandler extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label.getText() != "" && label.getForeground().equals(new Color(109, 109, 109))
                    || label.getForeground().equals(new Color(0, 0, 255))) {
                focusYear = displayedYear;
                focusMonth = displayedMonth;
                focusDay = Integer.parseInt(label.getText());
                focusCalendar.set(focusYear, focusMonth, focusDay);
                lblDateTop.setText(daysName[focusCalendar.get(Calendar.DAY_OF_WEEK) % 7]);
                lblDay.setText(String.valueOf(focusCalendar.get(Calendar.DAY_OF_MONTH)));
                UpdateHijriMiladi();
                refreshDaysDateTable();
            }
            if (e.getButton() == MouseEvent.BUTTON3)
                label.setComponentPopupMenu(popup);
            if (txtNotek.getText().equals("") || txtEvent.getText().equals(""))
                lblEdit.setForeground(Color.GRAY);
            else
                lblEdit.setForeground(Color.WHITE);
        }

        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label.getText() != "" && label.getForeground().equals(new Color(109, 109, 109))) {
                label.setBorder(BorderFactory.createLineBorder(new Color(223, 223, 223), 2));
                MakeToolTip(displayedCalendar.get(Calendar.DAY_OF_MONTH), displayedCalendar.get(Calendar.MONTH));
            }
        }

        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label.getText() != "" && label.getForeground().equals(new Color(109, 109, 109))) {
                label.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }

    }

    /**
     * Read Data of Files for celebration and Event and Note
     */
    public void showDataFiles() {

        ///////////Celebration

        String strPath = String.format("src/Data/Month (%d)/Day (%d).txt",
                focusCalendar.get(Calendar.MONTH), focusCalendar.get(Calendar.DAY_OF_MONTH));
        String strDisplay = "";
        String line = null;
        try {
            FileReader fileReader
                    = new FileReader(strPath);

            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);
            if ((line = bufferedReader.readLine()) != null) {
                strDisplay += line;
            }
            while ((line = bufferedReader.readLine()) != null) {

                strDisplay += line;
                strDisplay += "\n";
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                            + strPath + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + strPath + "'");
        }
        strDisplay = strDisplay.replaceAll("BAD", "\n");
        txtCelebrate.setText(strDisplay);

        ///////////// Note

        String strPathN = String.format("src/Data/Month (%d)/Day (%d)-Note.txt",
                focusCalendar.get(Calendar.MONTH), focusCalendar.get(Calendar.DAY_OF_MONTH));
        File fN = new File(strPathN);
        if (fN.exists() && !fN.isDirectory()) {
            String strDisplayN = "";
            String lineN = null;
            try {
                FileReader fileReader
                        = new FileReader(strPathN);

                BufferedReader bufferedReader
                        = new BufferedReader(fileReader);
                if ((lineN = bufferedReader.readLine()) != null) {
                    strDisplayN += lineN;
                    strDisplayN += "\n";
                }
                while ((lineN = bufferedReader.readLine()) != null) {

                    strDisplayN += lineN;
                    strDisplayN += "\n";
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                //JOptionPane.showMessageDialog("");
                System.out.println(
                        "Unable to open file '"
                                + strPathN + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + strPathN + "'");
            }
            txtNotek.setText(strDisplayN);
        } else {
            txtNotek.setText("");
        }
        //////////// Event

        String strPathE = String.format("src/Data/Month (%d)/Day (%d)-Reminder.txt",
                focusCalendar.get(Calendar.MONTH), focusCalendar.get(Calendar.DAY_OF_MONTH));
        File fE = new File(strPathE);
        String[][] Reminder = new String[100][2];
        if (fE.exists() && !fE.isDirectory()) {
            int ln = 0, i = 0;
            String lineE = null;
            try {
                FileReader fileReader
                        = new FileReader(strPathE);

                BufferedReader bufferedReader
                        = new BufferedReader(fileReader);

                while ((lineE = bufferedReader.readLine()) != null) {
                    if (ln % 2 == 0) {
                        Reminder[i][0] = lineE;
                    } else {
                        Reminder[i][1] = lineE;
                        i++;
                    }
                    ln++;
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '"
                                + strPathE + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + strPathE + "'");
            }
            for (int j = 0; j < i; j++) {
                strDisplay += Reminder[j][1] + " : " + Reminder[j][0] + "\n";
            }
            txtEvent.setText("[##:##] : [###########] (نحوه ویرایش)\n" + strDisplay);

            java.util.Calendar init = java.util.Calendar.getInstance();
            int hM = init.get(java.util.Calendar.HOUR_OF_DAY);
            int mM = init.get(java.util.Calendar.MINUTE);
            for (int j = 0; j < i; j++) {
                int h = Integer.parseInt(Reminder[j][1].substring(0, 2));
                int tafazolH = h - hM;
                int m = Integer.parseInt(Reminder[j][1].substring(3, 5));
                int tafazolM = m - mM;
                if (tafazolH >= 0 && tafazolM >= 0 && displayedCalendar.get(Calendar.MONTH) == focusCalendar.get(Calendar.MONTH)
                        && displayedCalendar.get(Calendar.DAY_OF_MONTH) == focusCalendar.get(Calendar.DAY_OF_MONTH)) {
                    Rmnd = new addReminder(tafazolH * 3600 + tafazolM * 60, Reminder[j][0]);
                }
            }

        } else {
            txtEvent.setText("");
        }
    }

    /**
     * Refresh the table of days from refresh calendar
     */
    public void refreshDaysDateTable() {
        for (int i = 7; i < 49; i++) {
            lblDaysDate[i].setText("");
            lblDaysDate[i].setBackground(new Color(247, 247, 247));
            lblDaysDate[i].setForeground(new Color(109, 109, 109));
        }
        displayedCalendar.set(displayedYear, displayedMonth, 1);
        int startOfWeek = displayedCalendar.get(Calendar.DAY_OF_WEEK);
        if (startOfWeek == 7)
            startOfWeek = 0;
        displayedCalendar.set(displayedYear, displayedMonth, displayedDay);
        int numberOfDays = displayedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        displayedCalendar.add(Calendar.MONTH, -1);
        int numberOfDaysPastMonth = displayedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        displayedCalendar.add(Calendar.MONTH, 1);
        lblDaysDateHandler handler = new lblDaysDateHandler();
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j <= 6; j++) {
                int idx = i * 7 + j;
                int dis = (i - 1) * 7 + j + 1 - startOfWeek;
                if (dis <= 0) {
                    lblDaysDate[idx].setText(String.valueOf(numberOfDaysPastMonth + dis));
                    lblDaysDate[idx].setForeground(new Color(170, 170, 170));
                    lblDaysDate[idx].setToolTipText(null);
                    lblDaysDate[idx].setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                if (dis > 0 && dis <= numberOfDays) {
                    String strDisplay = MakeToolTip(dis, displayedCalendar.get(Calendar.MONTH));
                    if (strDisplay == "امروز هیچ رویدادی ندارد.") {
                        lblDaysDate[idx].setToolTipText(null);
                    }
                    lblDaysDate[idx].setToolTipText(strDisplay);
                    lblDaysDate[idx].setText(String.valueOf(dis));
                    lblDaysDate[idx].addMouseListener(handler);
                    lblDaysDate[idx].addMouseMotionListener(handler);
                    lblDaysDate[idx].setBorder(BorderFactory.createLineBorder(Color.RED));
                    if (dis == focusCalendar.get(Calendar.DAY_OF_MONTH)) {
                        lblDaysDate[idx].setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 4));
                        lblDaysDate[idx].setForeground(new Color(0, 0, 0));
                    }
                    if (dis == displayedCalendar.get(Calendar.DAY_OF_MONTH)
                            && constantCalendar.get(Calendar.MONTH) == displayedCalendar.get(Calendar.MONTH)
                            && constantCalendar.get(Calendar.YEAR) == displayedCalendar.get(Calendar.YEAR)) {
                        lblDaysDate[idx].setBackground(new Color(250, 0, 249));
                        lblDaysDate[idx].setForeground(new Color(0, 0, 255));
                        if (dis == focusCalendar.get(Calendar.DAY_OF_MONTH)
                                && constantCalendar.get(Calendar.MONTH) == displayedCalendar.get(Calendar.MONTH)
                                && constantCalendar.get(Calendar.YEAR) == displayedCalendar.get(Calendar.YEAR)) {
                            lblDaysDate[idx].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 4));
                        }
                    }
                }
                if (dis > numberOfDays) {
                    lblDaysDate[idx].setText(String.valueOf(dis - numberOfDays));
                    lblDaysDate[idx].setForeground(new Color(170, 170, 170));
                    lblDaysDate[idx].setToolTipText(null);
                    lblDaysDate[idx].setBorder(BorderFactory.createLineBorder(Color.RED));
                }
            }
        }
    }

    /**
     * @return String that take day and month then give of celebration in Tooltip
     * @int day
     * @int month
     */
    public static String MakeToolTip(int day, int month) {

        String strpath = String.format("src/Data/Month (%d)/Day (%d).txt",
                month, day);
        String strdisplay = "<html>";
        String Line = null;
        try {
            FileReader fileReader = new FileReader(strpath);

            BufferedReader bufferedreader
                    = new BufferedReader(fileReader);
            if ((Line = bufferedreader.readLine()) != null) {
                strdisplay += Line;
            }
            while ((Line = bufferedreader.readLine()) != null) {

                strdisplay += Line;
                strdisplay += "<br>";
            }

            bufferedreader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                            + strpath + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error Reading File '"
                            + strpath + "'");
        }

        strdisplay += "</html>";
        strdisplay.trim();
        strdisplay = strdisplay.replaceAll("BAD", "<br>");

        String strPathN = String.format("src/Data/Month (%d)/Day (%d)-Note.txt",
                focusCalendar.get(Calendar.MONTH), focusCalendar.get(Calendar.DAY_OF_MONTH));
        File fN = new File(strPathN);
        if (fN.exists() && !fN.isDirectory()) {
            String lineN = null;
            try {
                FileReader fileReader
                        = new FileReader(strPathN);

                BufferedReader bufferedReader
                        = new BufferedReader(fileReader);
                if ((lineN = bufferedReader.readLine()) != null) {
                    strdisplay += lineN;
                    strdisplay += "\n";
                }
                while ((lineN = bufferedReader.readLine()) != null) {

                    strdisplay += lineN;
                    strdisplay += "\n";
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                //JOptionPane.showMessageDialog("");
                System.out.println(
                        "Unable to open file '"
                                + strPathN + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + strPathN + "'");
            }
        }
        if (strdisplay == "<html>") {
            return "امروز هیچ رویدادی ندارد.";
        }
        return strdisplay;

    }

    /**
     * Add note from User UI
     */
    public void addNote() {
        //pnlTool pnlToolC = new pnlTool();

        pnlTools = new JPanel();
        pnlNote = new JPanel();
        pnlNote.setLayout(new BorderLayout());
        pnlNote.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
        pnlTools.setLayout(new BorderLayout());
        lblNoteKarbar = new JLabel("افزودن یادداشت", SwingConstants.CENTER);
        lblNoteKarbar.setToolTipText("افزودن برای روز انتخاب شده");
        lblNoteKarbar.setFont(new Font("IRCompset", Font.BOLD, 60));
        lblNoteKarbar.setBackground(new Color(0, 100, 255));
        lblNoteKarbar.setForeground(Color.WHITE);
        lblNoteKarbar.setMinimumSize(new Dimension(150, 60));
        lblNoteKarbar.setPreferredSize(new Dimension(350, 150));
        lblNoteKarbar.setMaximumSize(new Dimension(150, 60));
        lblNoteKarbar.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
        lblNoteKarbar.setOpaque(true);

        lblNoteKarbarTitle = new JLabel("یادداشت ها", SwingConstants.CENTER);
        lblNoteKarbarTitle.setMinimumSize(new Dimension(1100, 70));
        lblNoteKarbarTitle.setPreferredSize(new Dimension(1100, 70));
        lblNoteKarbarTitle.setMaximumSize(new Dimension(1300, 70));
        lblNoteKarbarTitle.revalidate();
        lblNoteKarbarTitle.setFont(new Font("IRCompset", Font.BOLD, 50));
        lblNoteKarbarTitle.setBackground(new Color(0, 0, 250));
        lblNoteKarbarTitle.setForeground(Color.WHITE);
        lblNoteKarbarTitle.setOpaque(true);

        pnlNote.add(lblNoteKarbarTitle, BorderLayout.NORTH);
        pnlTools.add(lblNoteKarbar, BorderLayout.EAST);
        pnlEventNote.add(pnlNote, BorderLayout.EAST);


        lblNoteView = new JLabel("", SwingConstants.RIGHT);
        lblNoteView.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblNoteView.setAlignmentY(JLabel.TOP_ALIGNMENT);
        lblNoteView.setMinimumSize(new Dimension(700, 500));
        lblNoteView.setPreferredSize(new Dimension(700, 500));
        lblNoteView.setMaximumSize(new Dimension(700, 500));
        lblNoteView.setFont(new Font("IRCompset", Font.PLAIN, 40));
        lblNoteView.setBackground(new Color(247, 247, 247));
        lblNoteView.setForeground(new Color(109, 109, 109));
        lblNoteView.setOpaque(true);
        pnlNote.add(lblNoteView, BorderLayout.CENTER);

        txtNotek.setEditable(false);
        txtNotek.setFont(new Font("IRCompset", Font.BOLD, 30));
        txtNotek.setBackground(new Color(247, 247, 247));
        txtNotek.setForeground(new Color(109, 109, 109));
        txtNotek.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        txtNotek.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JScrollPane sp = new JScrollPane(txtNotek);

        pnlNote.add(sp, BorderLayout.CENTER);

//        JScrollPane scrollPane = new JScrollPane(txtNotek);
//        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
//        txtNotek.add(scrollPane);

        lblNoteKarbar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //FrmAddEvent = new addEventDialog();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    NewaddNote();
                }
            }

            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(109, 109, 109));
                }
            }

            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(Color.WHITE);
                }
            }
        });

    }

    /**
     * New Object from add note
     */
    public void NewaddNote() {
        final UserInterface info = this;

        FrmAddNote = new addNote(info);
        FrmAddNote.run();
    }

    /**
     * Add Event from User UI
     */
    public void addEvent() {
        //pnlTool pnlToolC = new pnlTool();

        pnlEvent = new JPanel();
        pnlEvent.setLayout(new BorderLayout());
        pnlEvent.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
        lblEvent = new JLabel("افزودن رویداد", SwingConstants.CENTER);
        lblEvent.setToolTipText("افزودن برای روز انتخاب شده");
        lblEvent.setFont(new Font("IRCompset", Font.BOLD, 60));
        lblEvent.setBackground(new Color(0, 100, 255));
        lblEvent.setForeground(Color.WHITE);
        lblEvent.setMinimumSize(new Dimension(150, 60));
        lblEvent.setPreferredSize(new Dimension(350, 150));
        lblEvent.setMaximumSize(new Dimension(150, 60));
        lblEvent.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
        lblEvent.setOpaque(true);

        lblEventTitle = new JLabel("رویداد ها", SwingConstants.CENTER);
        lblEventTitle.setMinimumSize(new Dimension(1100, 70));
        lblEventTitle.setPreferredSize(new Dimension(1100, 70));
        lblEventTitle.setMaximumSize(new Dimension(1300, 70));
        lblEventTitle.revalidate();
        lblEventTitle.setFont(new Font("IRCompset", Font.BOLD, 50));
        lblEventTitle.setBackground(new Color(0, 0, 250));
        lblEventTitle.setForeground(Color.WHITE);
        lblEventTitle.setOpaque(true);

        pnlEvent.add(lblEventTitle, BorderLayout.NORTH);
        pnlTools.add(lblEvent, BorderLayout.CENTER);
        pnlEventNote.add(pnlEvent, BorderLayout.WEST);

        lblEventView = new JLabel("", SwingConstants.RIGHT);
        lblEventView.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblEventView.setAlignmentY(JLabel.TOP_ALIGNMENT);
        lblEventView.setMinimumSize(new Dimension(700, 500));
        lblEventView.setPreferredSize(new Dimension(700, 500));
        lblEventView.setMaximumSize(new Dimension(700, 500));
        lblEventView.setFont(new Font("IRCompset", Font.PLAIN, 40));
        lblEventView.setBackground(new Color(247, 247, 247));
        lblEventView.setForeground(new Color(109, 109, 109));
        lblEventView.setOpaque(true);
        pnlEvent.add(lblEventView, BorderLayout.CENTER);


        txtEvent.setEditable(false);
        txtEvent.setFont(new Font("IRCompset", Font.BOLD, 30));

        txtEvent.setMinimumSize(new Dimension(1100, 500));
        txtEvent.setPreferredSize(new Dimension(1100, 500));
        txtEvent.setMaximumSize(new Dimension(1300, 1000));
        txtEvent.setBackground(new Color(247, 247, 247));
        txtEvent.setForeground(new Color(109, 109, 109));

        txtEvent.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        txtEvent.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JScrollPane sp = new JScrollPane(txtEvent);

        pnlEvent.add(sp, BorderLayout.CENTER);
//        pnlEvent.add(Table , BorderLayout.CENTER);

        /*final UserInterface table = this;
        Table = new ReminderTable(table);
        Table.initUI();*/

        lblEvent.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //FrmAddEvent = new addEventDialog();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    NewaddEvent();
                }
            }

            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(new Color(109, 109, 109));
                }
            }

            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    label.setForeground(Color.WHITE);
                }
            }
        });
    }

    /**
     * New Object from add event
     */
    public void NewaddEvent() {
        final UserInterface info = this;

        FrmAddEvent = new addEventDialog(info);
        FrmAddEvent.run();
    }

    /**
     * Edit Note and Event from User UI
     */
    public void editNoteEvent() {
        //pnlTool pnlToolC = new pnlTool();

        lblEdit = new JLabel("ویرایش", SwingConstants.CENTER);
        lblEdit.setToolTipText("اعمال تغییرات بوسیله\nنوشتن روی نوشته");
        lblEdit.setFont(new Font("IRCompset", Font.BOLD, 60));
        lblEdit.setBackground(new Color(0, 100, 255));
        if (txtNotek.getText().equals("") || txtEvent.getText().equals(""))
            lblEdit.setForeground(Color.GRAY);
        else
            lblEdit.setForeground(Color.WHITE);
        lblEdit.setMinimumSize(new Dimension(150, 60));
        lblEdit.setPreferredSize(new Dimension(350, 150));
        lblEdit.setMaximumSize(new Dimension(150, 60));
        lblEdit.setBorder(BorderFactory.createLineBorder(new Color(3, 3, 3), 1));
        lblEdit.setOpaque(true);

        pnlTools.add(lblEdit, BorderLayout.WEST);
        lplNote.add(pnlTools, BorderLayout.SOUTH);

        lblEdit.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!txtNotek.getText().equals("") || !txtEvent.getText().equals("")) {
                        JLabel label = (JLabel) e.getSource();
                        editeFunction();
                        label.setForeground(Color.WHITE);
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    if (!txtNotek.getText().equals("") || !txtEvent.getText().equals(""))
                        label.setForeground(new Color(109, 109, 109));
                }
            }

            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                if (label.getText() != "") {
                    if (!txtNotek.getText().equals("") || !txtEvent.getText().equals("")) {
                        label.setForeground(Color.WHITE);
                    }
                }
            }
        });

    }

    /**
     * It Edit the Event and Notes Function
     */
    public void editeFunction() {

        if (lblEdit.getText() == "ویرایش") {
            if (!txtNotek.getText().equals("")) {
                lblEdit.setText("ثبت ویرایش");
                txtNotek.setBackground(Color.WHITE);
                txtNotek.setForeground(Color.BLACK);
                txtNotek.setEditable(true);
                forEnableNote = 1;
            }
            if (!txtEvent.getText().equals("")) {
                lblEdit.setText("ثبت ویرایش");
                txtEvent.setBackground(Color.WHITE);
                txtEvent.setForeground(Color.BLACK);
                txtEvent.setEditable(true);
                forEnableEvent = 1;
            }
        } else {

            String Mah = String.valueOf(focusCalendar.get(Calendar.MONTH));
            String Rooz = String.valueOf(focusCalendar.get(Calendar.DAY_OF_MONTH));
            String filename;
            String strDisplayN = "";

            if (forEnableNote == 1) {

                lblEdit.setText("ویرایش");
                txtNotek.setBackground(new Color(247, 247, 247));
                txtNotek.setForeground(new Color(109, 109, 109));
                txtNotek.setEditable(false);

                ////// Save Notes

                {
                    filename = "src/Data/Month (" + Mah + ")/Day (" + Rooz + ")-Note.txt";

                    File f = new File(filename);
                    strDisplayN = "";
                    txtNotek.getText().replaceAll("\n", "\r\n");
                    strDisplayN += txtNotek.getText();
                    strDisplayN += "\r\n";

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter(filename));
                        writer.write(strDisplayN);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException ex) {
                            }
                        }
                    }
                }
            }
            if (forEnableEvent == 1) {

                lblEdit.setText("ویرایش");
                txtEvent.setBackground(new Color(247, 247, 247));
                txtEvent.setForeground(new Color(109, 109, 109));
                txtEvent.setEditable(false);

                // Save Event

                {
                    filename = "src/Data/Month (" + Mah + ")/Day (" + Rooz + ")-Reminder.txt";

                    String save = "";

                    Scanner scanner = new Scanner(txtEvent.getText());
                    String line = scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        save += line.substring(8, line.length());
                        save += "\r\n";
                        save += line.substring(0, 5);
                        save += "\r\n";
                    }
                    scanner.close();

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter(filename));
                        writer.write(save);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException ex) {
                            }
                        }
                    }
                }

            }
            showDataFiles();
        }
    }

}


