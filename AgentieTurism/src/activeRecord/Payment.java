package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by plesha on 27-Mar-18.
 */
public class Payment {

    private int idPayment;
    private int idReservation;
    private int payment;
    private Date date;



    public Payment(){

    }

    // idPayment, Date, reservation_idreservation, payment
    public Payment( int idP, int idR, int p, Date d){
        this.idPayment = idP;
        this.idReservation = idR;
        this.payment = p;
        this.date = d;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getIdPayment(){
        return this.idPayment;
    }

    public int getIdReservation(){
        return this.idReservation;
    }

    public int getPayment(){
        return this.payment;
    }


    public void setIdPayment(int id){
        this.idPayment = id;
    }

    public void setIdReservation(int id){
        this.idReservation = id;
    }


    public void setPayment(int p){
        this.payment = p;
    }

    public void setDate(Date d){
        this.date = d;
    }


    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/

    @Override
    public String toString() {
        return idPayment + " " + idReservation + " " + payment + " " + date;
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void update( int id, Date date, int payment, int idReservation ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();

            String statement = "UPDATE payment SET Date='"+date+ "',payment='"+payment+"',reservation_idreservation='"+idReservation+
                                    "' WHERE idPayment="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated Payment with id: " + id + "\n");

            db.closeConnectionToDatabase();

        }catch (Exception e) {
            throw new ActiveRecordException("Error updating Client into Database.", e);
        }
    }

    public synchronized void insert( int id, Date date,int id_client, int id_res , int payment ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO Payment (idPayment, Date, client_idclient, reservation_idreservation, payment) " +
                                              "VALUES ("+id+",'"+date+"','"+id_client+"','"+id_res+"','"+payment+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted Payment with id: " + id + "\n");

            db.closeConnectionToDatabase();
        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting a Payment.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM payment WHERE idpayment="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Payment with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Payment from Database.", e);
        }
    }

    public synchronized void deleteByIdReservation( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM payment WHERE reservation_idreservation="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Payment with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Payment from Database.", e);
        }
    }

    public synchronized static Payment findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM payment WHERE idpayment=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");
            System.out.println("Found Payment with id: " + uniqueID + "\n");
            while(rs.next()) {

                Date date = rs.getDate("date");
                int payment = rs.getInt("payment");
                int id_res = rs.getInt("reservation_idreservation");

                Payment p = new Payment(uniqueID,id_res,payment,date);

                return p;
            }
            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding a Payment by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM payment ORDER BY idpayment";
            ResultSet rs = db.executeQuery(statement, "select");

            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Payments by ID.", e);
        }
    }

   // SELECT idPayment FROM Payment ORDER BY idPayment DESC LIMIT 1
   public synchronized int getIdForInsert()throws ActiveRecordException {
       try {

           DatabaseConnection db =	new DatabaseConnection();
           db.openConnectionToDatabase();

           String statement = "SELECT idPayment FROM Payment ORDER BY idPayment DESC LIMIT 1";
           ResultSet rs = db.executeQuery(statement, "select");

           rs.next();
           int id = rs.getInt("idPayment");

           //db.closeConnectionToDatabase();

           return id+1;

       } catch (Exception e) {
           throw new ActiveRecordException("Error finding ALL Payments by ID.", e);
       }
   }
}
