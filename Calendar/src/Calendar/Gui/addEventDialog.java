package Calendar.Gui;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Date;

//import java.text.DateFormat;

/**
 * Created by Ali Yazdi on 27/05/2016.
 */
public class addEventDialog extends JXDatePicker {
    private JSpinner timeSpinner;
    private JPanel timePanel;
    private JTextArea textArea;
    private DateFormat timeFormat;
    private Calendar selectedCalendar;
    private String strDisplayN = "", filename;
    private UserInterface Info;
    private String Rooz, Mah;
    private JButton btn;
    JFrame frame = new JFrame();
    ULocale locale = new ULocale("fa_IR@calendar=persian");

    public addEventDialog(UserInterface info) {
        this.Info = info;
    }

    public void addEventDialog() {

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(" عنوان رویداد: ");
        label.setDisplayedMnemonic(KeyEvent.VK_N);
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textArea = new JTextArea();
        final JTextArea textArea = new JTextArea();
        label.setLabelFor(textArea);
        textArea.setPreferredSize(new Dimension(400, 200));
        textArea.setFont(new Font("IRCompset", Font.PLAIN, 40));
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        label.setSize(400, 200);
        panel.add(label, BorderLayout.EAST);
        panel.add(textArea, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.MINUTE);

        final JSpinner spinner = new JSpinner(sm);
        final JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
        de.getTextField().setEditable( false );
        spinner.setEditor(de);

        System.out.println("Spinner:      "+de.getFormat().format(spinner.getValue()));

        frame.add(spinner, BorderLayout.CENTER);

        btn = new JButton("ثبت");
        frame.add(btn, BorderLayout.SOUTH);
        frame.setSize(250, 150);
        frame.setVisible(true);

        Mah = String.valueOf(Info.focusCalendar.get(Calendar.MONTH));
        Rooz = String.valueOf(Info.focusCalendar.get(Calendar.DAY_OF_MONTH));
        filename = "src/Data/Month (" + Mah + ")/Day (" + Rooz + ")-Reminder.txt";

        btn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                File f = new File(filename);
                if (f.exists() && !f.isDirectory()) {
                    String lineN = null;
                    try {
                        strDisplayN="";
                        FileReader fileReader
                                = new FileReader(filename);

                        BufferedReader bufferedReader
                                = new BufferedReader(fileReader);
                        if ((lineN = bufferedReader.readLine()) != null) {
                            strDisplayN += lineN;
                            strDisplayN += "\r\n";
                        }
                        while ((lineN = bufferedReader.readLine()) != null) {

                            strDisplayN += lineN;
                            strDisplayN += "\r\n";
                        }
                        bufferedReader.close();
                    } catch (FileNotFoundException ex) {
                        //JOptionPane.showMessageDialog("");
                        System.out.println(
                                "Unable to open file '"
                                        + filename + "'");
                    } catch (IOException ex) {
                        System.out.println(
                                "Error reading file '"
                                        + filename + "'");
                    }
                    textArea.getText().replaceAll("\n", "\r\n");
                    strDisplayN += textArea.getText();
                    strDisplayN += "\r\n";
                    strDisplayN += de.getTextField().getText();
                    strDisplayN += "\r\n";

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter( new FileWriter( filename));
                        writer.write(strDisplayN);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    finally
                    {
                        if ( writer != null) {
                            try {
                                writer.close();
                            } catch (IOException ex) {
                            }
                        }
                    }

                } else {
                    strDisplayN="";

                    textArea.getText().replaceAll("\n", "\r\n");
                    strDisplayN += textArea.getText();
                    strDisplayN += "\r\n";
                    strDisplayN += de.getTextField().getText();
                    strDisplayN += "\r\n";


                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter( new FileWriter( filename));
                        writer.write(strDisplayN);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    finally
                    {
                        if ( writer != null) {
                            try {
                                writer.close();
                            } catch (IOException ex) {
                            }
                        }
                    }
                }
                frame.dispose();
                Info.showDataFiles();
            }
        });

    }

    public void run() {
        Date date = new Date();
        frame.setTitle("ثبت رویداد");

        addEventDialog();

        frame.pack();
        frame.setMaximumSize(new Dimension(1000, 1000));
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}