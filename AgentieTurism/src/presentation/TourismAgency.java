package presentation;

import activeRecord.ActiveRecordException;
import activeRecord.Administrator;
import activeRecord.User;
import activeRecord.UserActivity;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dbConnection.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by plesha on 28-Mar-18.
 */
public class TourismAgency {
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    public JPanel loginPanel;
    private JTextPane usernameTextPane;
    private JTextPane passwordTextPane;
    private JTextPane welcomeToOurTourismTextPane;


    public TourismAgency(JFrame frame) {


        //frame.setVisible(false);

        // JFrame frameAdmin1 = new JFrame("Tourism Agency");
        // new AdminUser(frameAdmin1);

        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Administrator adm = new Administrator();
                User user = new User();

                String username = textField1.getText();
                String password = passwordField1.getText();

                //System.out.println("parola e " + password);

                try {
                    if (adm.findByUserNameAndPassword(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login succesfully as an Administrator !\n\n   Welcome " + username + " !");
                        frame.setVisible(false);

                        JFrame frameAdmin = new JFrame("Tourism Agency");
                        new AdminUser(frameAdmin);

                    } else if (user.findByUserNameAndPassword(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login succesfully as a User !\n\n   Welcome " + username + " !");
                        frame.setVisible(false);

                        Date date = new Date(Calendar.getInstance().getTime().getTime());
                        UserActivity ua = new UserActivity();
                        int id_user = user.findIDbyUsername(username);

                        ua.insert(date, username + " Logged in", id_user);

                        JFrame fRegUser = new JFrame("Tourism Agency");
                        new RegularUser(fRegUser, username, id_user);

                    } else {
                        JOptionPane.showMessageDialog(null, "INVALID Username or Password !");
                    }
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("Exception from Admin/user findyUserNameAndPassword method !");
                }


            }
        });
    }

    public static void main(String[] args) {

        DatabaseConnection db = new DatabaseConnection();
        db.openConnectionToDatabase();
        String numberOfIDS = "SELECT count(*) FROM useractivity;";
        ResultSet rs = db.executeQuery(numberOfIDS, "select");
        int ids = 0;
        try {
            while (rs.next())
                ids = rs.getInt("count(*)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("nr of ids = " + ids);

        Date date = new Date(Calendar.getInstance().getTime().getTime());
        java.util.Date javaDate = new java.util.Date(date.getTime());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Date date = new Date();
        System.out.println(dateFormat.format(javaDate));
        //System.out.println(javaDate);

        /*try{
           new Main().createPdf("E:\\ick_astley.pdf");
        } catch( IOException e){
            e.printStackTrace();

        }
        */

        JFrame frame = new JFrame("Tourism Agency");
        new TourismAgency(frame);
        //new TourismAgency(frame);
        //new RegularUser(frame,"Antonia",1);
        // frame,"Antonia",1
      /*  AdminUser adm = new AdminUser(frame);
       try{
            adm.createPdf("E:\\ick_astley.pdf");
        } catch( IOException e){
            e.printStackTrace();

        }
*/

       /* frame.setContentPane(new TourismAgency().loginPanel );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
*/
        // write your code here
       /* DatabaseConnection con = new DatabaseConnection();

        con.openConnectionToDatabase();

        String statement = "Select * from client";

        ResultSet rs = con.executeQuery(statement, "select");
       // System.out.println(rs);

        try
        {
            while (rs.next())
            {
                String firstname = rs.getString("firstName");

                System.out.println(firstname + " ---- merge ");
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
*/

       /* Administrator adm = new Administrator(1,"Plesa Gabi","213","plesha",25);

        try{
            adm = adm.findById(1);
            adm.update(1,"Plesa Gabi","valore","plesha",22);
           // adm.insert(2,"Alexandra Tintelecan", "tinki", "tinki",21);
            adm.insert(3,"Blabla Asta", "zxs", "axaxa",55);
            adm.delete(3);
            ResultSet rs1 = adm.findAll();
        }
        catch(Exception e){

        }
         System.out.println(adm.toString());
        */


        // Client c = new Client(1,"Plesa","Gabriel","1960110125786","parang 19",22,1);

      /*  Client c = new Client(1, "Plesa", "Gabriel", "1960110125786", "parang 19", 22, 1);
      //  ResultSet rs = null;


        try {

            c = c.findById(2);

        }
        catch(Exception e){
            System.out.println("ADD Button exception: " + e);
        }
        System.out.println("AM GAIT PE ASTA" + c.toString());
        */

       /* Client c = new Client(1,"Plesa","Gabriel","1960110125786","parang 19",22,1);
      try{


           c.update(1,"Plesa Gabi","valore","plesha","alaBalaFifa18",22,1);
           // c.insert(2,"Anotnia","Madalina","1940110125726","zzz csa",25,1);
           c.insert(3,"Asta","Basta","1940110125726","zaeq",19,1);
            c.delete(3);
            c = c.findById(2);
        //   ResultSet tmp = c.findAll();
           // ResultSet rs1 = c.findAll();
        }
        catch(Exception e){
            System.out.println("\nExceptie = " + e);
        }
        System.out.println("\nClientul gasit este = " + c.toString());
    */

/*
        Person p = new Person(1,"Plesa Gabriel","1960110125786",22,Gender.Male);
        try{
            p.update(1,"Antonia Bagaboanta","1930110125785",22,Gender.Female);
             // p.insert(3,"Van Gogh","1440110125785",90,Gender.Male);
            p.insert(4,"Costel Prostel","1950110125785",26,Gender.Male);
            p.delete(4);
             p = p.findById(3);
            ResultSet tmp = p.findAll();
        }
        catch(Exception e){

        }
        System.out.println("\nClientul gasit este = " + p.toString());
        */

       /* Date d = new Date(18+100,04,24);
        Date d1 = new Date(20180424);
        Payment pay = new Payment(1,1,1,400, d);
        try{
            pay.update(1,d,6969);
             //pay.insert(3,d,2,2,500);
            pay.insert(4,d,2,2,500);
            pay.delete(4);
            pay = pay.findById(2);
            ResultSet tmp = pay.findAll();

        }
        catch(Exception e){
        }

    System.out.println("\nClientul gasit este = " + pay.toString());
    */

        /*ReservationPerson res = new ReservationPerson(1,1,1);
        try{
            res.update(1,1,2);
          // res.insert(2,2,2);
            //res.delete(2);
            res = res.findById(1); /////////// ------------------------------ ----------EROOOOOOOARE LA FIND-----------
           // ResultSet tmp = res.findAll();

        }
        catch(Exception e){
        }

        System.out.println("\nClientul gasit este = " + res.toString());
        */

       /* Reservation res = new Reservation(1, "Hawai", "Grand Hotel Hawai",  new Date(18+100,05,25), 1000, 0, 3);
        try{
            res.update(1, "Saint-Tropez", "Grand Hotel Hawai",  new Date(18+100,05,25), 1000, 0, 3);
            // res.insert(3, "Hawai", "Grand Hotel Hawai",  new Date(18+100,05,25), 69690, 0, 6,3);
             res.delete(3);
            res = res.findById(2);
             ResultSet tmp = res.findAll();

        }
        catch(Exception e){
        }

        System.out.println("\nClientul gasit este = " + res.toString());

*/

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1, true, true));
        loginPanel.setAutoscrolls(false);
        loginPanel.setBackground(new Color(-2104346));
        loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        passwordTextPane = new JTextPane();
        passwordTextPane.setBackground(new Color(-2104346));
        passwordTextPane.setEditable(false);
        passwordTextPane.setForeground(new Color(-16777216));
        passwordTextPane.setText("                                     Password");
        loginPanel.add(passwordTextPane, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        textField1 = new JTextField();
        loginPanel.add(textField1, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField1 = new JPasswordField();
        passwordField1.setEditable(true);
        loginPanel.add(passwordField1, new GridConstraints(4, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        loginPanel.add(separator1, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        loginPanel.add(separator2, new GridConstraints(5, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        usernameTextPane = new JTextPane();
        usernameTextPane.setBackground(new Color(-2104346));
        usernameTextPane.setEditable(false);
        usernameTextPane.setForeground(new Color(-16777216));
        usernameTextPane.setText("                                      Username: ");
        loginPanel.add(usernameTextPane, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        welcomeToOurTourismTextPane = new JTextPane();
        welcomeToOurTourismTextPane.setBackground(new Color(-1));
        welcomeToOurTourismTextPane.setEditable(false);
        welcomeToOurTourismTextPane.setForeground(new Color(-16777216));
        welcomeToOurTourismTextPane.setText(" Welcome to our tourism agency ! Please Login");
        loginPanel.add(welcomeToOurTourismTextPane, new GridConstraints(1, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        loginButton = new JButton();
        loginButton.setText("Login");
        loginPanel.add(loginButton, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        loginPanel.add(spacer1, new GridConstraints(0, 0, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        loginPanel.add(spacer2, new GridConstraints(0, 5, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        loginPanel.add(spacer3, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        loginPanel.add(spacer4, new GridConstraints(7, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return loginPanel;
    }
}
