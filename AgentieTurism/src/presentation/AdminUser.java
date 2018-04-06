package presentation;

import activeRecord.ActiveRecordException;
import activeRecord.Reservation;
import activeRecord.User;
import activeRecord.UserActivity;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;

import java.sql.SQLException;
import java.util.Calendar;


/**
 * Created by plesha on 31-Mar-18.
 */
public class AdminUser {
    private JButton generateReportButton;
    private JTextArea addViewUpdateDeleteTextArea;
    private JTable table1;
    private JPanel adminPanel;
    private JScrollPane scrollPane1 = new JScrollPane();
    private JTextArea endDateForReportTextArea;
    private JTextArea startDateForReportTextArea;
    private JTextArea yearTextArea;
    private JTextArea monthTextArea;
    private JTextArea dayTextArea;
    private JTextArea yearTextArea1;
    private JTextArea monthTextArea1;
    private JTextArea dayTextArea1;
    private JTextField tfYearStart;
    private JTextField tfMonthStart;
    private JTextField tfDayStart;
    private JTextField tfYearEnd;
    private JTextField tfMonthEnd;
    private JTextField tfDayEnd;
    private JTextArea usernameTextArea;
    private JTextField textField1;
    private JTextArea userIDTextArea;
    private JTextArea usernameTextArea2;
    private JTextArea passwordTextArea;
    private JTextArea nameTextArea;
    private JTextField tfUserID;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfName;
    private JTextArea ageTextArea;
    private JTextField tfAge;
    private JButton showAllReportsButton;
    private JButton viewUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;
    private JButton addUserButton;
    private JTable table2;
    private JScrollPane scrollPane2 = new JScrollPane();
    private JButton viewAllUsersButton;
    private JButton logoutButton;
    private DefaultTableModel model, model2;


    public AdminUser(JFrame frame) {


        $$$setupUI$$$();

        frame.setContentPane(adminPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField1.getText().equals("") && !tfYearStart.getText().equals("") && !tfMonthStart.getText().equals("") && !tfDayStart.getText().equals("") &&
                        !tfYearEnd.getText().equals("") && !tfMonthEnd.getText().equals("") && !tfDayEnd.getText().equals("")) {

                    String username = textField1.getText();
                    int startYear = Integer.parseInt(tfYearStart.getText()) - 1900;
                    int startMonth = Integer.parseInt(tfMonthStart.getText()) - 1;
                    int startDay = Integer.parseInt(tfDayStart.getText());

                    int endYear = Integer.parseInt(tfYearEnd.getText()) - 1900;
                    int endMonth = Integer.parseInt(tfMonthEnd.getText()) - 1;
                    int endDay = Integer.parseInt(tfDayEnd.getText());

                    Date startDate = new Date(startYear, startMonth, startDay);
                    Date endDate = new Date(endYear, endMonth, endDay);

                    try {
                        createPdf(startDate, endDate, username);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("CreatePDF exception !");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You need to complet all Year/Month/Day fields and Username !!");
                }

            }
        });

        showAllReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    UserActivity activity = new UserActivity();
                    ResultSet rs = activity.findAll();

                    while (rs.next()) {

                        String id = rs.getString("iduserActivity");
                        String date = rs.getString("date");
                        String info = rs.getString("info");
                        String user = rs.getString("user_iduser");

                        String[] rowData = {id, date, info, user};
                        model.addRow(rowData);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("View all useractivity Exception: " + ex);
                }
                JOptionPane.showMessageDialog(null, "Found all users activity !");

            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfUserID.getText().equals("") && !tfUsername.getText().equals("") && !tfPassword.getText().equals("") && !tfName.getText().equals("")
                        && !tfAge.getText().equals("")) {

                    User user = new User();

                    Integer id = Integer.parseInt(tfUserID.getText());
                    String username = tfUsername.getText();
                    String password = tfPassword.getText();

                    String name = tfName.getText();
                    String age = tfAge.getText();

                    // iduser, username, password, name, age
                    try { // insert reservation r1
                        user.insert(id, name, username, password, Integer.parseInt(age));
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("ADD User Button exception: " + ex);
                    }

                    // no exception, insert data to table
                    String[] rowData = {Integer.toString(id), username, password, name, age};
                    model2.addRow(rowData);

                    JOptionPane.showMessageDialog(null, "User succesfully INSERTET into Database !");

                }// end if equals
                else {
                    JOptionPane.showMessageDialog(null, "You need to complete all User information fields !");
                }
            }
        });

        viewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfUserID.getText().equals("")) {

                    User user = new User();
                    Integer idToFind = Integer.parseInt(tfUserID.getText());

                    try {
                        user = user.findById(idToFind);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("View User By ID Button exception: " + ex);
                    }

                    /// Print user data
                    JOptionPane.showMessageDialog(null, "User found successfully !\n\n" +
                            "User Data:  \n\n" +
                            "ID: " + user.getId() + "\n" +
                            "Username: " + user.getUsername() + "\n" +
                            "Password: " + user.getPassword() + "\n" +
                            "Name: " + user.getName() + "\n" +
                            "Age: " + user.getAge() + "\n"
                    );

                } else {
                    JOptionPane.showMessageDialog(null, "You did not completed the User ID field !");
                }

            }
        });
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfUserID.getText().equals("") && !tfUsername.getText().equals("") && !tfPassword.getText().equals("") && !tfName.getText().equals("")
                        && !tfAge.getText().equals("")) {

                    User user = new User();

                    Integer id = Integer.parseInt(tfUserID.getText());
                    String username = tfUsername.getText();
                    String password = tfPassword.getText();

                    String name = tfName.getText();
                    String age = tfAge.getText();

                    // iduser, username, password, name, age
                    try { // insert reservation r1
                        user.update(id, name, username, password, Integer.parseInt(age));
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Update User Button exception: " + ex);
                    }

                    // no exception, update table

                    model2.setValueAt(Integer.toString(id), getRowToDelete(id, model2), 0);
                    model2.setValueAt(username, getRowToDelete(id, model2), 1);
                    model2.setValueAt(password, getRowToDelete(id, model2), 2);
                    model2.setValueAt(name, getRowToDelete(id, model2), 3);
                    model2.setValueAt(age, getRowToDelete(id, model2), 4);

                    JOptionPane.showMessageDialog(null, "User succesfully Updated !");

                }// end if equals
                else {
                    JOptionPane.showMessageDialog(null, "You need to complete all User information fields !");
                }
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfUserID.getText().equals("")) {

                    Integer id = Integer.parseInt(tfUserID.getText());
                    User user = new User();

                    // remove data from db
                    try {
                        user.delete(id);
                        model2.removeRow(getRowToDelete(id, model2));
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Delete/Update User Button exception: " + ex);
                    }
                    JOptionPane.showMessageDialog(null, "User deleted successfully !");

                }// end if
                else {
                    JOptionPane.showMessageDialog(null, "[ERROR] You need to complete User ID field !");
                }
            }
        });
        viewAllUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                try {
                    ResultSet rs = user.findAll();

                    while (rs.next()) {

                        //iduser, username, password, name, age
                        String id = rs.getString("iduser");
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        String name = rs.getString("name");
                        String age = rs.getString("age");


                        String[] rowData = {id, username, password, name, age};
                        model2.addRow(rowData);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("View all Users Exception: " + ex);
                }
                JOptionPane.showMessageDialog(null, "Users found successfully !");

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TourismAgency(frame);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        // iduserActivity, date, info, user_iduser
        String column[] = {"ID UserActivity", "Date when action was performed", "Activity Information"};
        model = new DefaultTableModel(column, 0);
        table1 = new JTable(model);

        final TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(2).setPreferredWidth(900);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        //columnModel.getColumn(0).setPreferredWidth(10);
        table1.setColumnModel(columnModel);

        // iduser, username, password, name, age
        String column2[] = {"User ID", "Username", "Password", "Name", "Age"};
        model2 = new DefaultTableModel(column2, 0);
        table2 = new JTable(model2);


    }

    private void createPdf(Date startDate, Date endDate, String username) throws IOException {

        String destination = "E:\\Tourism Agency Report.pdf";

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(destination);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        // Add a Paragraph
        document.add(new Paragraph("Tourism Agency report between Start Date: " + startDate + " and End Date: " + endDate + " for USER '" + username + "' is:").add(" \n").setFont(font));

        // Create a List
        UserActivity activity = new UserActivity();
        try {
            ResultSet rs = null;
            try {
                rs = activity.findAll();
            } catch (ActiveRecordException ex) {
                ex.printStackTrace();
                System.out.println("Resultest activity.findall() exception in createPdf() method !");
            }

            while (rs.next()) {

                String id = rs.getString("iduserActivity");
                Date activityDate = rs.getDate("date");
                String info = rs.getString("info");
                String user = rs.getString("user_iduser");

                int userID = Integer.parseInt(user);
                User u = new User();
                String userTest = "";
                try {
                    u = u.findById(userID);
                    userTest = u.getUsername();
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("userTest.findById(userID) exception in createPdf() method !");
                }

                if (activityDate.compareTo(startDate) >= 0 && activityDate.compareTo(endDate) <= 0 && userTest.equals(username)) {
                    Paragraph p = new Paragraph("ID User Activity: " + id + "\n")
                            .add("Date when Activity was performed: " + activityDate + "\n")
                            .add("Activity information: " + info + "\n")
                            .add("User ID: " + user + "\n")
                            .add(" \n");

                    document.add(p);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL exception in createPdf() method !");
        }

        //Close document
        document.close();

        JOptionPane.showMessageDialog(null, "Report generated for user activity in\n E:\\Tourism Agency Report.pdf");
    }

    private int getRowToDelete(int id, DefaultTableModel model) {
        //int cols = model.getColumnCount();
        int rows = model.getRowCount();

        int id_delete = 0;

        for (int i = 0; i < rows; i++) {
            String tmp = model.getValueAt(i, 0).toString();
            if (id == Integer.parseInt(tmp)) {
                id_delete = i;
            }
        }
        return id_delete;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayoutManager(9, 13, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setHorizontalScrollBarPolicy(30);
        adminPanel.add(scrollPane1, new GridConstraints(0, 5, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(table1);
        endDateForReportTextArea = new JTextArea();
        endDateForReportTextArea.setEditable(false);
        endDateForReportTextArea.setText("End Date for Report:");
        adminPanel.add(endDateForReportTextArea, new GridConstraints(2, 9, 1, 4, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        startDateForReportTextArea = new JTextArea();
        startDateForReportTextArea.setEditable(false);
        startDateForReportTextArea.setText("Start Date for Report:");
        adminPanel.add(startDateForReportTextArea, new GridConstraints(2, 5, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        yearTextArea = new JTextArea();
        yearTextArea.setEditable(false);
        yearTextArea.setText("Year");
        adminPanel.add(yearTextArea, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        monthTextArea = new JTextArea();
        monthTextArea.setEditable(false);
        monthTextArea.setText("Month");
        adminPanel.add(monthTextArea, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        dayTextArea = new JTextArea();
        dayTextArea.setEditable(false);
        dayTextArea.setText("Day");
        adminPanel.add(dayTextArea, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        yearTextArea1 = new JTextArea();
        yearTextArea1.setEditable(false);
        yearTextArea1.setText("Year");
        adminPanel.add(yearTextArea1, new GridConstraints(4, 9, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        monthTextArea1 = new JTextArea();
        monthTextArea1.setEditable(false);
        monthTextArea1.setText("Month");
        adminPanel.add(monthTextArea1, new GridConstraints(4, 10, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        dayTextArea1 = new JTextArea();
        dayTextArea1.setEditable(false);
        dayTextArea1.setText("Day");
        adminPanel.add(dayTextArea1, new GridConstraints(4, 11, 1, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfYearStart = new JTextField();
        adminPanel.add(tfYearStart, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfMonthStart = new JTextField();
        adminPanel.add(tfMonthStart, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfDayStart = new JTextField();
        adminPanel.add(tfDayStart, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfYearEnd = new JTextField();
        adminPanel.add(tfYearEnd, new GridConstraints(5, 9, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfMonthEnd = new JTextField();
        adminPanel.add(tfMonthEnd, new GridConstraints(5, 10, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfDayEnd = new JTextField();
        adminPanel.add(tfDayEnd, new GridConstraints(5, 11, 1, 2, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addViewUpdateDeleteTextArea = new JTextArea();
        addViewUpdateDeleteTextArea.setEditable(false);
        addViewUpdateDeleteTextArea.setText("Add/View/Update/Delete/  RegularUser information");
        adminPanel.add(addViewUpdateDeleteTextArea, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        userIDTextArea = new JTextArea();
        userIDTextArea.setEditable(false);
        userIDTextArea.setText("User ID:");
        adminPanel.add(userIDTextArea, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        usernameTextArea2 = new JTextArea();
        usernameTextArea2.setEditable(false);
        usernameTextArea2.setText("Username:");
        adminPanel.add(usernameTextArea2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        passwordTextArea = new JTextArea();
        passwordTextArea.setEditable(false);
        passwordTextArea.setText("Password: ");
        adminPanel.add(passwordTextArea, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        nameTextArea = new JTextArea();
        nameTextArea.setEditable(false);
        nameTextArea.setEnabled(true);
        nameTextArea.setText("Name:");
        adminPanel.add(nameTextArea, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfUserID = new JTextField();
        tfUserID.setText("");
        adminPanel.add(tfUserID, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfUsername = new JTextField();
        adminPanel.add(tfUsername, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfPassword = new JTextField();
        adminPanel.add(tfPassword, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfName = new JTextField();
        adminPanel.add(tfName, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        usernameTextArea = new JTextArea();
        usernameTextArea.setEditable(false);
        usernameTextArea.setText("Username");
        adminPanel.add(usernameTextArea, new GridConstraints(6, 5, 1, 8, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        adminPanel.add(textField1, new GridConstraints(7, 5, 1, 8, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        generateReportButton = new JButton();
        generateReportButton.setText("Generate Report");
        adminPanel.add(generateReportButton, new GridConstraints(8, 5, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ageTextArea = new JTextArea();
        ageTextArea.setEditable(false);
        ageTextArea.setText("Age:");
        adminPanel.add(ageTextArea, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfAge = new JTextField();
        adminPanel.add(tfAge, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        showAllReportsButton = new JButton();
        showAllReportsButton.setText("Show All Reports");
        adminPanel.add(showAllReportsButton, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewUserButton = new JButton();
        viewUserButton.setText("View User By ID");
        adminPanel.add(viewUserButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateUserButton = new JButton();
        updateUserButton.setText("Update User");
        adminPanel.add(updateUserButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteUserButton = new JButton();
        deleteUserButton.setText("Delete User");
        adminPanel.add(deleteUserButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addUserButton = new JButton();
        addUserButton.setText("Add User");
        adminPanel.add(addUserButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminPanel.add(scrollPane2, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(table2);
        viewAllUsersButton = new JButton();
        viewAllUsersButton.setText("View All Users");
        adminPanel.add(viewAllUsersButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        adminPanel.add(logoutButton, new GridConstraints(8, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return adminPanel;
    }

    /**
     * Creates a PDF document.
     *
     * @param deste the path to the new PDF document
     * @throws IOException
     */
    /*
    public void createPdf(String deste)
            throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(deste);

        PdfDocument pdf = new PdfDocument(writer);


        Document document = new Document(pdf);

        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        // Add a Paragraph
        document.add(new Paragraph("iText is:").setFont(font));
        // Create a List

        Paragraph p = new Paragraph("The quick brown ")
                .add(" jumps over the lazy ");

        document.add(p);

        //Close document
        document.close();
    }
*/

}
