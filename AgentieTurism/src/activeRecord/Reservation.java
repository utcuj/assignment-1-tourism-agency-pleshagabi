package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by plesha on 26-Mar-18.
 */
public class Reservation {

    //idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient
    private int id;
    private String destination;
    private String hotelName;
    private java.sql.Date finalPaymantDate;
    private int fullPrice;
    private int partialPrice;
    private int numberOfPersons;
    private int clientID;

    public Reservation(){

    }

    public Reservation( int id, String dst, String hN, java.sql.Date fPD, int fprice, int pprice, int numberOfPersons, int clientID  ){
        this.id = id;
        this.destination = dst;
        this.hotelName = hN;
        this.finalPaymantDate = fPD;
        this.fullPrice = fprice;
        this.partialPrice = pprice;
        this.numberOfPersons = numberOfPersons;
        this.clientID = clientID;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getId(){
        return id;
    }

    public int getFullPrice(){
        return fullPrice;
    }

    public int getPartialPrice(){
        return partialPrice;
    }

    public String getDestination(){
        return destination;
    }

    public String getHotelName(){
        return hotelName;
    }

    public java.sql.Date getFinalPaymantDate(){
        return finalPaymantDate;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setId(int id ){
        this.id = id;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setFinalPaymantDate(java.sql.Date finalPaymantDate) {
        this.finalPaymantDate = finalPaymantDate;
    }

    public void setFullPrice(int fullPrice) {
        this.fullPrice = fullPrice;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setPartialPrice(int partialPrice) {
        this.partialPrice = partialPrice;
    }


    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/


    @Override
    public String toString() {
        //return super.toString();
        return "ID: "+id + " Destination: " + destination + " HotelName: " + hotelName + " FinalPayMentDate: "+finalPaymantDate+
                " FullPrice: "+ fullPrice + " PartialPrice: "+partialPrice+ " NumberOfPersons: "+numberOfPersons +" ClientID: "+clientID;
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void update( int id, String dst, String hN, Date fPD, int fprice, int pprice, int numberOfPersons, int clientID  ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();

            // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient
            String statement = "UPDATE Reservation SET destination='"+dst+"',hotel='" + hN + "',finalPaymentDate='"+fPD +
                    "',fullPrice='"+fprice + "',partialPrice='"+pprice + "',numberOfPersons='"+numberOfPersons + "',client_idclient='"+ clientID+
                                                                                    "' WHERE idreservation="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated Reservation with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error updating Reservation into Database.", e);
        }
    }

    public synchronized void insert( int id, String dst, String hN, Date fPD, int fprice, int pprice, int numberOfPersons, int clientID  ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();


        // idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons, client_idclient
            String statement = "INSERT INTO Reservation (idreservation, destination, hotel, finalPaymentDate, fullPrice, partialPrice, numberofpersons,client_idclient) " +
                    "VALUES ("+id+",'"+dst+"','"+hN+"','"+fPD+"','"+fprice+"','"+pprice+"','"+numberOfPersons+"','"+clientID+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted Reservation with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting a Reservation.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM Reservation WHERE idreservation="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Reservation with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Reservation from Database.", e);
        }
    }

    public synchronized static Reservation findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM Reservation WHERE idReservation=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");

            while(rs.next()) {

                String destination = rs.getString("destination");
                String hotel = rs.getString("hotel");

                Date finalPaymentDate = rs.getDate("finalPaymentDate");

                int fullPrice = rs.getInt("fullPrice");
                int partialPrice = rs.getInt("partialPrice");
                int numberofpersons = rs.getInt("numberofpersons");
                int clientID = rs.getInt("client_idclient");

                Reservation res = new Reservation(uniqueID,destination,hotel,finalPaymentDate,fullPrice,partialPrice,numberofpersons,clientID);

                return res;
            }
            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding Reservation by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM Reservation ORDER BY idReservation";
            ResultSet rs = db.executeQuery(statement, "select");

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Reservations by ID.", e);
        }
    }

}
