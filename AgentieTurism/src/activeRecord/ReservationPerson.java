package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by plesha on 28-Mar-18.
 */
public class ReservationPerson {
    private int idReservationPerson;
    private int idReservation;

    public ReservationPerson(){

    }

    public ReservationPerson(int idReservationPerson, int idReservation){
        this.idReservationPerson = idReservationPerson;
        this.idReservation = idReservation;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/



    public int getIdReservation() {
        return this.idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }


    public int getIdReservationPerson() {
        return idReservationPerson;
    }

    public void setIdReservationPerson(int idReservationPerson) {
        this.idReservationPerson = idReservationPerson;
    }

    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/
    @Override
    public String toString() {

        return idReservationPerson + " " + idReservation + " " ;
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void update( int id ,int idr ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();

            //idResevationPerson, reservation_idreservation

            String statement = "UPDATE ReservationPerson SET reservation_idreservation='"+idr+"'" +
                                            " WHERE idResevationPerson="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated ReservationPerson with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error updating ReservationPerson into Database.", e);
        }
    }

    public synchronized void insert( int id,int idr ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO ReservationPerson (idResevationPerson, reservation_idreservation) " +
                    "VALUES ("+id+",'"+idr+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted ReservationPerson with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting a ReservationPerson.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM ReservationPerson WHERE idResevationPerson="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted ResevationPerson with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a ReservationPerson from Database.", e);
        }
    }

    public synchronized static ReservationPerson findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM ReservationPerson WHERE idResevationPerson=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");
            System.out.println("Found ReservationPerson with id: " + uniqueID + "\n");
            while(rs.next()) {

                int id2 = rs.getInt("reservation_idreservation");

                ReservationPerson r = new ReservationPerson(uniqueID,id2);

                return r;
            }
            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding a ReservationPerson by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM ReservationPerson ORDER BY idResevationPerson";
            ResultSet rs = db.executeQuery(statement, "select");

            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL ReservationPerson by ID.", e);
        }
    }

    public synchronized static int findNumberOfIDS() throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statementIDS = "SELECT count(*) FROM reservationperson;";
            ResultSet rs = db.executeQuery(statementIDS, "select");

            int nrOfIDS = 0;
            while(rs.next())
                nrOfIDS = rs.getInt("count(*)");

            return nrOfIDS;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding a ReservationPerson by ID.", e);
        }
    }
}
