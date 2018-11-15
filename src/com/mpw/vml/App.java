package com.mpw.vml;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class App {
    private JTabbedPane tabbedPane;
    private JPanel panel;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton generateButton;
    private JTextField localhostTextField;
    private JTextField portTextField;
    private JTextField videoMlTextField;
    private JTextField storeIdtextField;
    private JLabel error;
    private JTextField noOfEventsForDay;
    private JButton generateButton1;
    private JPanel datapanel;
    private JPanel loadingpanel;


    public App() {
        generateButton.addActionListener(e -> {
            if (localhostTextField.getText() == null || localhostTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Host");
                return;
            }
            if (portTextField.getText() == null || portTextField.getText().isEmpty() || !portTextField.getText().matches("-?\\d+(.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Invalid Port");
                return;
            }
            if (videoMlTextField.getText() == null || videoMlTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Database name");
                return;
            }
            if (storeIdtextField.getText() == null || storeIdtextField.getText().isEmpty() || !storeIdtextField.getText().matches("-?\\d+(.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Invalid Store Id");
                return;
            }
            if (startDateField.getText() == null || startDateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Start Date");
                return;
            }
            if (endDateField.getText() == null || endDateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid End Date");
                return;
            }

            DateTimeFormatter inputDateTimeFormat = DateTimeFormat.forPattern("MM/dd/YYYY");
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
            DateTime localStartTime = inputDateTimeFormat.parseDateTime(startDateField.getText());
            DateTime localEndTime = inputDateTimeFormat.parseDateTime(endDateField.getText());
            MongoClient mongo = null;
            try {
                mongo = new MongoClient(localhostTextField.getText(), Integer.parseInt(portTextField.getText()));
                DB db = mongo.getDB(videoMlTextField.getText());
                DBCollection table = db.getCollection("STORE_DAILY_SALES_SUMMARY");


                Calendar cal = Calendar.getInstance();
                cal.setTime(localStartTime.toDate());
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR_OF_DAY, 0);

                int storeId = Integer.parseInt(storeIdtextField.getText());

                do {
                    Date dateTime = cal.getTime();
                    String value = dateFormat.format(dateTime);

                    BasicDBObject document = new BasicDBObject();
                    document.put("TOTAL_ORDER_VALUE", ThreadLocalRandom.current().nextDouble(7300.0, 16000.0));
                    document.put("SLD_QTY", ThreadLocalRandom.current().nextInt(2300, 6000));
                    document.put("LOC_NBR", storeId);
                    document.put("TM_DIM_KY_DTE", value);
                    document.put("TIMESTAMP", new Date());
                    table.insert(document);
                    cal.add(Calendar.DATE, 1);
                } while (cal.getTimeInMillis() <= localEndTime.getMillis());


            } catch (Exception err) {
                error.setText(err.getMessage());
            } finally {
                if (mongo != null) {
                    mongo.close();
                }
            }
        });


        generateButton1.addActionListener(e -> {
            if (localhostTextField.getText() == null || localhostTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Host");
                return;
            }
            if (portTextField.getText() == null || portTextField.getText().isEmpty() || !portTextField.getText().matches("-?\\d+(.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Invalid Port");
                return;
            }
            if (videoMlTextField.getText() == null || videoMlTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Database name");
                return;
            }
            if (storeIdtextField.getText() == null || storeIdtextField.getText().isEmpty() || !storeIdtextField.getText().matches("-?\\d+(.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Invalid Store Id");
                return;
            }
            if (startDateField.getText() == null || startDateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Start Date");
                return;
            }
            if (endDateField.getText() == null || endDateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid End Date");
                return;
            }
            if (noOfEventsForDay.getText() == null || noOfEventsForDay.getText().isEmpty() || !noOfEventsForDay.getText().matches("-?\\d+(.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Invalid No of events Number");
                return;
            }
            DateTimeFormatter inputDateTimeFormat = DateTimeFormat.forPattern("MM/dd/YYYY");
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
            DateFormat mongoFormat = new SimpleDateFormat("YYYY-MM-dd");
            DateTime localStartTime = inputDateTimeFormat.parseDateTime(startDateField.getText());
            DateTime localEndTime = inputDateTimeFormat.parseDateTime(endDateField.getText());
            MongoClient mongo = null;

            try {
                mongo = new MongoClient(localhostTextField.getText(), Integer.parseInt(portTextField.getText()));
                DB db = mongo.getDB(videoMlTextField.getText());
                DBCollection table = db.getCollection("inout");


                Calendar cal = Calendar.getInstance();
                cal.setTime(localStartTime.toDate());
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                Random rand = new Random();
                String[] age = new String[]{"0-15", "16-35", "36-54", "55+"};
                String[] gender = new String[]{"male", "female"};


                int storeId = Integer.parseInt(storeIdtextField.getText());
                long eventId = System.currentTimeMillis();
                do {
                    Date dateTime = cal.getTime();
                    String value = mongoFormat.format(dateTime);
                    String formattedDateTime = value + "T" + ThreadLocalRandom.current().nextInt(8, 21) + ":" + ThreadLocalRandom.current().nextInt(0, 60) + ":" + ThreadLocalRandom.current().nextInt(0, 60) + "." + ThreadLocalRandom.current().nextInt(1, 999999);

                    for (int i = 0; i < Integer.parseInt(noOfEventsForDay.getText()); i++) {
                        long eid = eventId + 1;
                        String formattedEventTime = value + "T" + ThreadLocalRandom.current().nextInt(8, 21) + ":" + ThreadLocalRandom.current().nextInt(0, 60) + ":" + ThreadLocalRandom.current().nextInt(0, 60) + "." + ThreadLocalRandom.current().nextInt(1, 999999);

                        String uuid = UUID.randomUUID().toString();
                        DBObject dbObject = (DBObject) JSON
                                .parse("{'dateTime':'" + formattedDateTime + "','storeId':" + storeId + ",'inoutId':'" + uuid + "','person':{'gender':'" + gender[rand.nextInt(2)] + "','age':'" + age[rand.nextInt(4)] + "','id':" + eid + ",'events':[{'eventId':" + eid + ",'eventTime':'" + formattedEventTime + "','inout':'in'}]},'version':1.1,'cameraId':3,'isActive':true,'processed':false,'status':1,'_class':'com.kohls.mlsiasyncjobs.model.inout.InOutModel'}");
                        table.insert(dbObject);
                    }
                    cal.add(Calendar.DATE, 1);
                } while (cal.getTimeInMillis() <= localEndTime.getMillis());


            } catch (Exception err) {
                error.setText(err.getMessage());
            } finally {
                if (mongo != null) {
                    mongo.close();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VML Test Data Generator");
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new App().panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
