package presentation;

import activeRecord.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;


/**
 * Created by plesha on 28-Mar-18.
 */
public class RegularUser {
    private JButton addButton;
    private JButton updateButton;
    private JButton viewByIDButton;
    private JButton viewAllButton;
    private JTextPane clientInformationForAddTextPane;
    private JTextField tfAddID;
    private JTextField tfAddFirstName;
    private JTextPane firstnameClientTextPane;
    private JTextPane IDClientForInsertTextPane;
    private JTextPane lastnameClientTextPane;
    private JTextPane addressClientTextPane;
    private JTextPane CNPClientTextPane;
    private JTextField tfAddLastName;
    private JTextField tfAddAddress;
    private JTextField tfAddCnp;
    private JTextPane IDClientForUpdateTextPane;
    private JTextField tfUpdateDelete;
    public JPanel userPane;
    private JButton deleteButton;
    public JTable table1;
    private JTextPane ageClientTextPane;
    private JTextField tfAddAge;
    private JTextField tfAddRes;
    private JTable table2;
    private JTextPane holidayReservationsForClientsTextPane;
    private JTextPane reservationIDForInsertTextPane;
    private JTextPane destinationTextPane;
    private JTextPane hotelTextPane;
    private JTextPane finalPaymentDeadlineDateTextPane;
    private JTextField tfResID;
    private JTextField tfDest;
    private JTextField tfHotel;
    private JTextField tfFPD;
    private JTextField tfPayment;
    private JTextField tfPartp;
    private JTextField tfPersonID;
    private JTextPane totalPaymentTextPane;
    private JTextPane partialPaymentTextPane;
    private JButton addReservationButton;
    private JButton updateReservationButton;
    private JButton viewReservationButton;
    private JButton deleteReservationButton;
    private JButton viewAllReservationsButton;
    private JTextPane numberOfPersonsTextPane;
    private JTextField tfNrOfPers;
    private JTextField tfDeleteRes;
    private JButton logoutButton;
    private JTextPane clientIDTextPane;
    private JTextField tfClientIDforReservation;
    private JTextPane reservationIDForPartialTextPane;
    private JTextField tfIDPartialPay;
    private JButton acceptButton;
    private JButton missedDeadlineClientsButton;
    private JTextPane cancelHolidayForClientTextPane;
    private JTextField tfCancelID;
    private JButton cancelButton;

    private DefaultTableModel model, model2;


    public RegularUser(JFrame frame, String username, int id_user) {

        $$$setupUI$$$();

        frame.setContentPane(userPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);


        viewByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfAddID.getText().equals("")) {

                    Client c1 = new Client();
                    Integer idToFind = Integer.parseInt(tfAddID.getText());

                    try {
                        c1 = c1.findById(idToFind);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("View By ID Button exception: " + ex);
                    }

                    /// Print client data
                    // idclient, firstName, lastName, cnp, address, age
                    JOptionPane.showMessageDialog(null, "Client found successfully !\n" +
                            "Client Data:  \n\n" +
                            "ID: " + c1.getId() + "\n" +
                            "FirstName: " + c1.getNume() + "\n" +
                            "LastName: " + c1.getPrenume() + "\n" +
                            "CNP: " + c1.getCnp() + "\n" +
                            "Address: " + c1.getAdresa() + "\n" +
                            "Age: " + c1.getVarsta() + "\n"
                    );

                    // Save activity
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(date, username + " Found by id the user with Data: " + c1.allData(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from ViewButton Listener in User GUI");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "You did not completed the ID Client Field !");
                }
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Client c1 = new Client();

                // remove all rows
                while (table1.getRowCount() > 0) {
                    ((DefaultTableModel) table1.getModel()).removeRow(0);
                }

                try {

                    ResultSet rs = c1.findAll();

                    while (rs.next()) {

                        String id = rs.getString("idClient");
                        String fn = rs.getString("firstName");
                        String ln = rs.getString("lastName");
                        String cnp = rs.getString("cnp");
                        String adr = rs.getString("address");
                        String age = rs.getString("age");

                        String[] rowData = {id, fn, ln, cnp, adr, age};
                        model.addRow(rowData);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("View By ID Button exception: " + ex);
                }

                //JOptionPane.showMessageDialog(null, "Clients found successfully !");

                // Save activity
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                try {
                    new UserActivity().insert(date, username + " viewed all clients", id_user);
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("Exception findIDbyUsername method from viewAllButton Listener in User GUI");
                }

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfAddID.getText().equals("") && !tfAddAddress.getText().equals("") && !tfAddCnp.getText().equals("") && !tfAddFirstName.getText().equals("")
                        && !tfAddLastName.getText().equals("")) {

                    Client c1 = new Client();
                    // int id, String firstName,String lastName,String cnp,String address, int age

                    Integer id = Integer.parseInt(tfAddID.getText());
                    String firstName = tfAddFirstName.getText();
                    String lastName = tfAddLastName.getText();
                    String cnp = tfAddCnp.getText();
                    String address = tfAddAddress.getText();
                    Integer age = Integer.parseInt(tfAddAge.getText());
                    try {
                        c1.insert(id, firstName, lastName, cnp, address, age);

                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("ADD Button exception: " + ex);
                    }

                    // no exception
                    String[] rowData = {Integer.toString(id), firstName, lastName, cnp, address,
                            Integer.toString(age)};

                    model.addRow(rowData);

                    JOptionPane.showMessageDialog(null, "Client successfully inserted into Database!");

                    // Save activity
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(date, username + " added client with: " + c1.findById(id).allData(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from AddButton Listener in User GUI");
                    }


                }// end if equals
                else {
                    JOptionPane.showMessageDialog(null, "You did not complete all the client fields !");
                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfAddAddress.getText().equals("") && !tfAddCnp.getText().equals("") && !tfAddFirstName.getText().equals("")
                        && !tfAddLastName.getText().equals("") && !tfUpdateDelete.getText().equals("")) {

                    Client c1 = new Client();
                    // int id, String firstName,String lastName,String cnp,String address, int age,int reservationID

                    Integer id = Integer.parseInt(tfUpdateDelete.getText());
                    String firstName = tfAddFirstName.getText();
                    String lastName = tfAddLastName.getText();
                    String cnp = tfAddCnp.getText();
                    String address = tfAddAddress.getText();
                    Integer age = Integer.parseInt(tfAddAge.getText());

                    c1 = new Client(id, firstName, lastName, cnp, address, age, 0);
                    try {
                        c1.update(id, firstName, lastName, cnp, address, age, 0);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Update Button exception: " + ex);
                    }

                    // no exception
                    // {"ID", "FirstName", "LastName", "CNP", "Address", "Age", "Reservation ID"};
                    model.setValueAt(id, getRowToDelete(id, model), 0);
                    model.setValueAt(firstName, getRowToDelete(id, model), 1);
                    model.setValueAt(lastName, getRowToDelete(id, model), 2);
                    model.setValueAt(cnp, getRowToDelete(id, model), 3);
                    model.setValueAt(address, getRowToDelete(id, model), 4);
                    model.setValueAt(age, getRowToDelete(id, model), 5);

                    JOptionPane.showMessageDialog(null, "Client successfully updated into Database !");

                    // Save activity
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(date, username + " updated client with: " + c1.allData(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from updateButton Listener in User GUI");
                    }


                } // end if equals text field
                else {
                    JOptionPane.showMessageDialog(null, "You did not complete all the client fields !");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfUpdateDelete.getText().equals("")) {
                    int id = Integer.parseInt(tfUpdateDelete.getText());
                    Client c1 = new Client();
                    String clientData = "";
                    try {
                        clientData = c1.findById(id).allData(); // save data for report then delete
                        c1.delete(id);

                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Delete Button exception: " + ex);
                    }
                    // no exception
                    model.removeRow(getRowToDelete(id, model));
                    JOptionPane.showMessageDialog(null, "Client DELETED successfully !");

                    // Save activity
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(date, username + " deleted client with: " + clientData, id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from deleteButton Listener in User GUI");
                    }

                }// end if
                else {
                    JOptionPane.showMessageDialog(null, "The Update/Delete ID field can't be empty !");
                }
            }
        });


        /*
         --------------------------------- RESERVATION & ReservationPerson listeners -------------------------------------------------------------------------------------------------------------------------
         */

        addReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* String ye, mo, da;
                try {
                    System.out.println(tfFPD.getText() + "\n");
                    System.out.println(tfFPD.getText(2, 2) + " " +
                            tfFPD.getText(5, 2) + " " +
                            tfFPD.getText(8, 2) + " ");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
*/
                if (!tfResID.getText().equals("") && !tfDest.getText().equals("") && !tfHotel.getText().equals("") && !tfFPD.getText().equals("")
                        && !tfPayment.getText().equals("") && !tfPartp.getText().equals("") && !tfNrOfPers.getText().equals("")
                        && !tfClientIDforReservation.getText().equals("")) {

                    Reservation r1 = new Reservation();
                    ReservationPerson rp = new ReservationPerson();
                    // int id, String firstName,String lastName,String cnp,String address, int age,int reservationID

                    Integer id = Integer.parseInt(tfResID.getText());
                    String dest = tfDest.getText();
                    String hotel = tfHotel.getText();

                    String dateStr = tfFPD.getText();
                    Integer year = 0, mon = 0, day = 0;
                    try {
                        year = Integer.parseInt(tfFPD.getText(2, 2));
                        mon = Integer.parseInt(tfFPD.getText(5, 2));
                        day = Integer.parseInt(tfFPD.getText(8, 2));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.print("DATE get exception" + ex);
                    }

                    Date date = new Date(year + 100, mon, day);

                    Integer fprice = Integer.parseInt(tfPayment.getText());
                    Integer pprice = Integer.parseInt(tfPartp.getText());
                    Integer nrofpers = Integer.parseInt(tfNrOfPers.getText());

                    Integer clientID = Integer.parseInt(tfClientIDforReservation.getText());

                    // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient
                    try { // insert reservation r1
                        r1.insert(id, dest, hotel, date, fprice, pprice, nrofpers, clientID);

                        int idReservationPerson = rp.findNumberOfIDS();
                        // insert ReservationPerson rp
                        idReservationPerson += 1;
                        rp.insert(idReservationPerson, id);

                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("ADD Reservation Button exception: " + ex);
                    }

                    // no exception, insert data to table
                    //idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient
                    String[] rowData = {Integer.toString(id), dest, hotel, dateStr, Integer.toString(fprice), Integer.toString(pprice),
                            Integer.toString(nrofpers), Integer.toString(clientID)};

                    model2.addRow(rowData);
                    JOptionPane.showMessageDialog(null, "Reservation succesfully INSERTET into Database !");

                    // Save activity
                    Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(dateActivity, username + " inserted reservation: " + r1.toString(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from addReservationButton Listener in User GUI");
                    }

                }// end if equals
                else {
                    JOptionPane.showMessageDialog(null, "Nu ati completat toate campurile");
                }
            }
        });

        updateReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfDeleteRes.getText().equals("") && !tfDest.getText().equals("") && !tfHotel.getText().equals("") && !tfFPD.getText().equals("")
                        && !tfPayment.getText().equals("") && !tfPartp.getText().equals("") && !tfNrOfPers.getText().equals("")
                        && !tfClientIDforReservation.getText().equals("")) {

                    Reservation r1 = new Reservation();
                    ReservationPerson rp = new ReservationPerson();

                    Integer id = Integer.parseInt(tfDeleteRes.getText());
                    String dest = tfDest.getText();
                    String hotel = tfHotel.getText();

                    String dateStr = tfFPD.getText();
                    Integer year = 0, mon = 0, day = 0;
                    try {
                        year = Integer.parseInt(tfFPD.getText(2, 2));
                        mon = Integer.parseInt(tfFPD.getText(5, 2));
                        day = Integer.parseInt(tfFPD.getText(8, 2));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.print("DATE get exception" + ex);
                    }

                    Date date = new Date(year + 100, mon, day);

                    Integer fprice = Integer.parseInt(tfPayment.getText());
                    Integer pprice = Integer.parseInt(tfPartp.getText());
                    Integer clientID = Integer.parseInt(tfClientIDforReservation.getText());
                    Integer nrofpers = Integer.parseInt(tfNrOfPers.getText());

                    // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, person_idperson, numberofpersons
                    try {
                        // update reservation r1
                        r1.update(id, dest, hotel, date, fprice, pprice, nrofpers, clientID);

                        // update reservationperson for id
                        //   rp.update(id, id, personID);

                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Delete/Update Button exception: " + ex);
                    }

                    // no exception, updata data from table
                    // {"ID", "Destination", "Hotel", "FinalPaymentDate", "FullPrice", "PartialPrice", "Person ID", "Number of Persons"};
                    model2.setValueAt(id, getRowToDelete(id, model2), 0);
                    model2.setValueAt(dest, getRowToDelete(id, model2), 1);
                    model2.setValueAt(hotel, getRowToDelete(id, model2), 2);
                    model2.setValueAt(dateStr, getRowToDelete(id, model2), 3);
                    model2.setValueAt(fprice, getRowToDelete(id, model2), 4);
                    model2.setValueAt(pprice, getRowToDelete(id, model2), 5);
                    model2.setValueAt(nrofpers, getRowToDelete(id, model2), 6);
                    model2.setValueAt(clientID, getRowToDelete(id, model2), 7);

                    JOptionPane.showMessageDialog(null, "Reservation succesfully UPDATED into Database !");

                    // Save activity
                    Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(dateActivity, username + " inserted reservation: " + r1.toString(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception UserActivity().insert from updateReservationButton Listener in User GUI");
                    }
                }// end if equals
                else {
                    JOptionPane.showMessageDialog(null, " YOu need to complete all fields to update a reservation !");
                }
            }
        });

        viewReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfResID.getText().equals("")) {

                    Reservation r1 = new Reservation();
                    Integer idToFind = Integer.parseInt(tfResID.getText());

                    try {
                        r1 = r1.findById(idToFind);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("View Reservation By ID Button exception: " + ex);
                    }

                    /// Print client data
                    JOptionPane.showMessageDialog(null, "Client found successfully !\n" +
                            "Client Data:  \n\n" +
                            "ID: " + r1.getId() + "\n" +
                            "Destination: " + r1.getDestination() + "\n" +
                            "Hotel: " + r1.getHotelName() + "\n" +
                            "Final Payment Date: " + r1.getFinalPaymantDate() + "\n" +
                            "Full price: " + r1.getFullPrice() + "\n" +
                            "Partial price: " + r1.getPartialPrice() + "\n" +
                            "Number of Persons: " + r1.getNumberOfPersons() + "\n"
                    );

                    // Save activity
                    Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(dateActivity, username + " viewed reservation " + r1.findById(idToFind).toString() + " by id ", id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception UserActivity().insert from viewReservationButton Listener in User GUI");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "You did not completed the ID Reservation Field !");
                }

            }
        });

        deleteReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfDeleteRes.getText().equals("")) {
                    Integer id = Integer.parseInt(tfDeleteRes.getText());
                    Reservation r = new Reservation();
                    ReservationPerson rp = new ReservationPerson();
                    // Save activity
                    Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(dateActivity, username + " deleted reservation " + r.findById(id).toString(), id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception UserActivity().insert from deleteReservationButton Listener in User GUI");
                    }

                    // remove data from db
                    try {
                        rp = rp.findById(id);
                        int idRP = rp.getIdReservationPerson();

                        rp.delete(idRP);
                        r.delete(id);

                        model2.removeRow(getRowToDelete(id, model2));
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Delete/Update Reservation Button exception: " + ex);
                    }
                    JOptionPane.showMessageDialog(null, "Reservation deleted successfully !");

                }// end if
                else {
                    JOptionPane.showMessageDialog(null, "[ERROR] You need to complete Delete/Update field !");
                }
            }
        });

        viewAllReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation r1 = new Reservation();

                // remove all rows
                while (table2.getRowCount() > 0) {
                    ((DefaultTableModel) table2.getModel()).removeRow(0);
                }

                try {
                    ResultSet rs = r1.findAll();

                    while (rs.next()) {

                        // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons
                        String id = rs.getString("idreservation");
                        String dest = rs.getString("destination");
                        String hotel = rs.getString("hotel");
                        String fpd = rs.getString("finalPaymentDate");
                        String fprice = rs.getString("fullPrice");
                        String pprice = rs.getString("partialPrice");
                        String numberofpersons = rs.getString("numberofpersons");
                        String clientID = rs.getString("client_idclient");

                        String[] rowData = {id, dest, hotel, fpd, fprice, pprice, numberofpersons, clientID};
                        model2.addRow(rowData);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("View all Reservation Exception: " + ex);
                }
              //  JOptionPane.showMessageDialog(null, "Reservations found successfully !");

                // Save activity
                Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                try {
                    new UserActivity().insert(dateActivity, username + " viewed all reservations ", id_user);
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("Exception UserActivity().insert from viewAllReservationsButton Listener in User GUI");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Save activity
                Date dateActivity = new Date(Calendar.getInstance().getTime().getTime());
                try {
                    new UserActivity().insert(dateActivity, username + " logged out ", id_user);
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("Exception UserActivity().insert from logoutButton Listener in User GUI");
                }
                new TourismAgency(frame);
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!tfIDPartialPay.getText().equals("")) {
                    Reservation res = new Reservation();
                    Payment pay = new Payment();

                    String nume = "";
                    int acceptedPayment = 0;
                    try {
                        res = res.findById(Integer.parseInt(tfIDPartialPay.getText()));
                        int partialPrice = res.getPartialPrice();
                        int totalPrice = res.getFullPrice();

                        Client c = new Client();
                        c = c.findById(res.getClientID());

                        nume = c.getNume() + " " + c.getPrenume();
                        acceptedPayment = 0;

                        if (totalPrice > partialPrice) {
                            totalPrice -= partialPrice;
                            acceptedPayment = partialPrice;

                            partialPrice = 0;
                        } else {
                            partialPrice -= totalPrice;
                            acceptedPayment = totalPrice;

                            totalPrice = 0;
                        }

                        int id = res.getId();

                        Date date = new Date(Calendar.getInstance().getTime().getTime());
                        int idPayment = pay.getIdForInsert();
                        pay.insert(idPayment, date, Integer.parseInt(tfIDPartialPay.getText()), id, acceptedPayment);

                        res.update(id, res.getDestination(), res.getHotelName(), res.getFinalPaymantDate(), totalPrice, partialPrice, res.getNumberOfPersons(), res.getClientID());

                        model2.setValueAt(totalPrice, getRowToDelete(id, model2), 4);
                        model2.setValueAt(partialPrice, getRowToDelete(id, model2), 5);

                        JOptionPane.showMessageDialog(null, "You accepted partial payment of: " + acceptedPayment + "$ from client: " + nume + "\n");


                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception from acceptButton Listener in User GUI");
                    }

                    // Save activity
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    try {
                        new UserActivity().insert(date, username + " accept partial payment of: " + acceptedPayment + "$ from: " + nume, id_user);
                    } catch (ActiveRecordException ex) {
                        ex.printStackTrace();
                        System.out.println("Exception findIDbyUsername method from missedDeadlineClientsButton Listener in User GUI");
                    }

                }   // end if equals
                else {
                    JOptionPane.showMessageDialog(null, "You need to complete Reservation ID for partial payment field !");
                }
            }
        });

        missedDeadlineClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client c1 = new Client();

                // remove all rows
                while (table1.getRowCount() > 0) {
                    ((DefaultTableModel) table1.getModel()).removeRow(0);
                }

                try {

                    ResultSet rs = c1.findAll();

                    while (rs.next()) {
                        String id = rs.getString("idClient");
                        String fn = rs.getString("firstName");
                        String ln = rs.getString("lastName");
                        String cnp = rs.getString("cnp");
                        String adr = rs.getString("address");
                        String age = rs.getString("age");

                        Reservation res = new Reservation();
                        try {
                            res = res.findById(Integer.parseInt(id));


                        } catch (ActiveRecordException ex) {
                            ex.printStackTrace();
                            System.out.println("Exception from Reservation().findById in missedDeadlineClientsButton !");
                        }

                        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
                        Date deadlineDate = res.getFinalPaymantDate();


                        if (deadlineDate.compareTo(currentDate) < 0) {
                            String[] rowData = {id, fn, ln, cnp, adr, age};
                            model.addRow(rowData);
                        }
                        c1.update(Integer.parseInt(id), fn, ln, cnp, adr, Integer.parseInt(age), 1);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("View By ID Button exception: " + ex);
                }

                //JOptionPane.showMessageDialog(null, "Clients who missed the deadline payment found successfully !");

                // Save activity
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                try {
                    new UserActivity().insert(date, username + " viewed all clients who missed the deadline payment", id_user);
                } catch (ActiveRecordException ex) {
                    ex.printStackTrace();
                    System.out.println("Exception findIDbyUsername method from missedDeadlineClientsButton Listener in User GUI");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfCancelID.getText().equals("")) {
                    int id = Integer.parseInt(tfCancelID.getText());
                    Client client = new Client();
                    Reservation res = new Reservation();
                    Person person = new Person();
                    Payment pay = new Payment();
                    try {
                        // client = client.findById(id);
                        ResultSet rs = res.findAll();
                        while (rs.next()) {
                            int idReservation = rs.getInt("idreservation");
                            int idClientFromRes = rs.getInt("client_idclient");

                            if (id == idClientFromRes) {
                                client = client.findById(id);
                                if (client.getMissedDeadline() == 1) {
                                    ReservationPerson rp = new ReservationPerson();
                                    rp = rp.findById(id);
                                    int idRP = rp.getIdReservationPerson();

                                    pay.deleteByIdReservation(idRP);
                                    person.deleteByReservationID(idRP);
                                    rp.delete(idRP);
                                    res.delete(idReservation);
                                    client.delete(id);

                                   // int idToRemoveRow = getRowToDelete(id, model);
                                  //  model.removeRow(idToRemoveRow);
                                    missedDeadlineClientsButton.doClick();
                                    viewAllReservationsButton.doClick();

                                    JOptionPane.showMessageDialog(null, "You canceled the holiday for the following client: \n"
                                            + client.toString() + "\n ");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Client: " + client.toString() + "\n Did not missed the deadline date, yet !");
                                }
                            }
                        }
                        // Save activity
                        Date date = new Date(Calendar.getInstance().getTime().getTime());
                        try {
                            new UserActivity().insert(date, username + " canceled the reservation from Client " + client.toString(), id_user);
                        } catch (ActiveRecordException ex) {
                            ex.printStackTrace();
                            System.out.println("Exception Useractivity()  from cancelButton Listener in User GUI");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Exception on cancelButton listener in user gui !");
                    }
                } // end if equals
                else {
                    JOptionPane.showMessageDialog(null, "You did not complete the ClientID from who you want to cancel the holiday reservation !");
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

        String column[] = {"ID Client", "FirstName", "LastName", "CNP", "Address", "Age"};
        model = new DefaultTableModel(column, 0);
        table1 = new JTable(model);

        // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient

        String column2[] = {"ID Reservation", "Destination", "Hotel", "FinalPaymentDate", "FullPrice", "PartialPrice", "Number of Persons", "For Client ID"};
        model2 = new DefaultTableModel(column2, 0);

        table2 = new JTable(model2);
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
        userPane = new JPanel();
        userPane.setLayout(new GridLayoutManager(23, 19, new Insets(0, 0, 0, 0), -1, -1));
        userPane.setEnabled(false);
        final Spacer spacer1 = new Spacer();
        userPane.add(spacer1, new GridConstraints(0, 1, 1, 16, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        userPane.add(spacer2, new GridConstraints(0, 0, 23, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        clientInformationForAddTextPane = new JTextPane();
        clientInformationForAddTextPane.setEditable(false);
        clientInformationForAddTextPane.setText("Client information for Add/View");
        userPane.add(clientInformationForAddTextPane, new GridConstraints(2, 1, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfAddID = new JTextField();
        tfAddID.setText("");
        userPane.add(tfAddID, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfAddFirstName = new JTextField();
        tfAddFirstName.setText("");
        userPane.add(tfAddFirstName, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        firstnameClientTextPane = new JTextPane();
        firstnameClientTextPane.setEditable(false);
        firstnameClientTextPane.setText("Firstname Client:");
        userPane.add(firstnameClientTextPane, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        IDClientForInsertTextPane = new JTextPane();
        IDClientForInsertTextPane.setEditable(false);
        IDClientForInsertTextPane.setText("ID Client for insert/view:");
        userPane.add(IDClientForInsertTextPane, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        reservationIDForInsertTextPane = new JTextPane();
        reservationIDForInsertTextPane.setEditable(false);
        reservationIDForInsertTextPane.setText("Reservation ID for Insert/View");
        userPane.add(reservationIDForInsertTextPane, new GridConstraints(4, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        destinationTextPane = new JTextPane();
        destinationTextPane.setEditable(false);
        destinationTextPane.setText("Destination");
        userPane.add(destinationTextPane, new GridConstraints(5, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        hotelTextPane = new JTextPane();
        hotelTextPane.setEditable(false);
        hotelTextPane.setText("Hotel");
        userPane.add(hotelTextPane, new GridConstraints(6, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfResID = new JTextField();
        userPane.add(tfResID, new GridConstraints(4, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfDest = new JTextField();
        userPane.add(tfDest, new GridConstraints(5, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfHotel = new JTextField();
        userPane.add(tfHotel, new GridConstraints(6, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addReservationButton = new JButton();
        addReservationButton.setText("Add Reservation");
        userPane.add(addReservationButton, new GridConstraints(2, 13, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteReservationButton = new JButton();
        deleteReservationButton.setText("Delete Reservation");
        userPane.add(deleteReservationButton, new GridConstraints(2, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lastnameClientTextPane = new JTextPane();
        lastnameClientTextPane.setEditable(false);
        lastnameClientTextPane.setText("Lastname Client:");
        userPane.add(lastnameClientTextPane, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfAddLastName = new JTextField();
        tfAddLastName.setText("");
        userPane.add(tfAddLastName, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        holidayReservationsForClientsTextPane = new JTextPane();
        holidayReservationsForClientsTextPane.setEditable(false);
        holidayReservationsForClientsTextPane.setText("Holiday Reservations for Clients");
        userPane.add(holidayReservationsForClientsTextPane, new GridConstraints(2, 12, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer3 = new Spacer();
        userPane.add(spacer3, new GridConstraints(0, 18, 22, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        userPane.add(spacer4, new GridConstraints(1, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        finalPaymentDeadlineDateTextPane = new JTextPane();
        finalPaymentDeadlineDateTextPane.setEditable(false);
        finalPaymentDeadlineDateTextPane.setText("Final Payment Deadline Date");
        userPane.add(finalPaymentDeadlineDateTextPane, new GridConstraints(7, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfFPD = new JTextField();
        userPane.add(tfFPD, new GridConstraints(7, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        totalPaymentTextPane = new JTextPane();
        totalPaymentTextPane.setEditable(false);
        totalPaymentTextPane.setText("Total Payment");
        userPane.add(totalPaymentTextPane, new GridConstraints(8, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addressClientTextPane = new JTextPane();
        addressClientTextPane.setEditable(false);
        addressClientTextPane.setText("Address Client:");
        userPane.add(addressClientTextPane, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        CNPClientTextPane = new JTextPane();
        CNPClientTextPane.setEditable(false);
        CNPClientTextPane.setText("CNP Client: ");
        userPane.add(CNPClientTextPane, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        ageClientTextPane = new JTextPane();
        ageClientTextPane.setEditable(false);
        ageClientTextPane.setText("Age Client:");
        userPane.add(ageClientTextPane, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfAddAddress = new JTextField();
        tfAddAddress.setText("");
        userPane.add(tfAddAddress, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfAddCnp = new JTextField();
        tfAddCnp.setText("");
        userPane.add(tfAddCnp, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfAddAge = new JTextField();
        userPane.add(tfAddAge, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfPayment = new JTextField();
        userPane.add(tfPayment, new GridConstraints(8, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        partialPaymentTextPane = new JTextPane();
        partialPaymentTextPane.setEditable(false);
        partialPaymentTextPane.setText("Partial Payment");
        userPane.add(partialPaymentTextPane, new GridConstraints(9, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfPartp = new JTextField();
        userPane.add(tfPartp, new GridConstraints(9, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        numberOfPersonsTextPane = new JTextPane();
        numberOfPersonsTextPane.setEditable(false);
        numberOfPersonsTextPane.setText("Number of Persons");
        userPane.add(numberOfPersonsTextPane, new GridConstraints(10, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfNrOfPers = new JTextField();
        userPane.add(tfNrOfPers, new GridConstraints(10, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setText("Add Client");
        userPane.add(addButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateButton = new JButton();
        updateButton.setText("Update Client");
        userPane.add(updateButton, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        IDClientForUpdateTextPane = new JTextPane();
        IDClientForUpdateTextPane.setEditable(false);
        IDClientForUpdateTextPane.setText("ID Client for Update/Delete");
        userPane.add(IDClientForUpdateTextPane, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfUpdateDelete = new JTextField();
        tfUpdateDelete.setText("");
        userPane.add(tfUpdateDelete, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete Client");
        userPane.add(deleteButton, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        userPane.add(scrollPane1, new GridConstraints(1, 1, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1.setAutoCreateRowSorter(true);
        scrollPane1.setViewportView(table1);
        final JScrollPane scrollPane2 = new JScrollPane();
        userPane.add(scrollPane2, new GridConstraints(1, 12, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table2.setAutoCreateRowSorter(true);
        table2.setBackground(new Color(-1));
        scrollPane2.setViewportView(table2);
        final JTextPane textPane1 = new JTextPane();
        textPane1.setEditable(false);
        textPane1.setText("ID Reservation for Update/Delete");
        userPane.add(textPane1, new GridConstraints(4, 15, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfDeleteRes = new JTextField();
        tfDeleteRes.setText("");
        userPane.add(tfDeleteRes, new GridConstraints(4, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        viewReservationButton = new JButton();
        viewReservationButton.setText("View Reservation by ID");
        userPane.add(viewReservationButton, new GridConstraints(2, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateReservationButton = new JButton();
        updateReservationButton.setText("Update Reservation");
        userPane.add(updateReservationButton, new GridConstraints(2, 15, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewAllReservationsButton = new JButton();
        viewAllReservationsButton.setText("View All Reservations");
        userPane.add(viewAllReservationsButton, new GridConstraints(3, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewAllButton = new JButton();
        viewAllButton.setText("View All Clients");
        userPane.add(viewAllButton, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewByIDButton = new JButton();
        viewByIDButton.setText("View Client by ID");
        userPane.add(viewByIDButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clientIDTextPane = new JTextPane();
        clientIDTextPane.setEditable(false);
        clientIDTextPane.setText("Client ID: ");
        userPane.add(clientIDTextPane, new GridConstraints(11, 13, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfClientIDforReservation = new JTextField();
        userPane.add(tfClientIDforReservation, new GridConstraints(11, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        userPane.add(logoutButton, new GridConstraints(20, 17, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reservationIDForPartialTextPane = new JTextPane();
        reservationIDForPartialTextPane.setEditable(false);
        reservationIDForPartialTextPane.setText("Reservation ID for partial payment accept:");
        userPane.add(reservationIDForPartialTextPane, new GridConstraints(7, 15, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfIDPartialPay = new JTextField();
        userPane.add(tfIDPartialPay, new GridConstraints(7, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        acceptButton = new JButton();
        acceptButton.setText("Accept");
        userPane.add(acceptButton, new GridConstraints(8, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        missedDeadlineClientsButton = new JButton();
        missedDeadlineClientsButton.setText("Missed Deadline Clients");
        userPane.add(missedDeadlineClientsButton, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelHolidayForClientTextPane = new JTextPane();
        cancelHolidayForClientTextPane.setEditable(false);
        cancelHolidayForClientTextPane.setText("Cancel Holiday For Client with ID:");
        userPane.add(cancelHolidayForClientTextPane, new GridConstraints(11, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        tfCancelID = new JTextField();
        userPane.add(tfCancelID, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        userPane.add(cancelButton, new GridConstraints(12, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return userPane;
    }
}
