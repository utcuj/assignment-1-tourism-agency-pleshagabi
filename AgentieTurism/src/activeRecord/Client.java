package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by plesha on 26-Mar-18.
 */
public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private int age;
    private int missedDeadline;

    public Client(){

    }
    // idclient, firstName, lastName, cnp, address, age

    public Client( int id , String fN , String lN , String cnp , String adr , int age, int missedDeadline ) {
        this.id = id;
        this.firstName = fN;
        this.lastName = lN;
        this.cnp = cnp;
        this.address = adr;
        this.age = age;
        this.missedDeadline = missedDeadline;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getId(){
        return id;
    }

    public int getVarsta() {
        return age;
    }

    public String getAdresa() {
        return address;
    }

    public String getCnp() {
        return cnp;
    }

    public String getNume() {
        return firstName;
    }

    public String getPrenume() {
        return lastName;
    }

    public int getMissedDeadline() {
        return missedDeadline;
    }

    public void setMissedDeadline(short missedDeadline) {
        this.missedDeadline = missedDeadline;
    }

    public void setAdresa(String adresa) {
        this.address = adresa;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.firstName = nume;
    }

    public void setPrenume(String prenume) {
        this.lastName = prenume;
    }

    public void setVarsta(int varsta) {
        this.age = varsta;
    }


    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/

    @Override
    public String toString() {
        //return super.toString();
        return "ID: "+id + " FirstName: " + firstName + " LastName:" + lastName;
    }

    public String allData(){
        return "ID: "+id + " FirstName: " + firstName + " LastName: " + lastName + " CNP: "+cnp +
                " Address: "+address + " Age: "+ age;
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/


    public synchronized void update( int id, String firstName,String lastName,String cnp,String address, int age,  int missed ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();

            String statement = "UPDATE client SET firstName='"+firstName+ "',lastName='"+lastName+"',cnp='"+cnp+"',address='"+address+"',age='"+age+
                    "',missedDeadline='"+missed+"' WHERE idClient="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated Client with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error updating Client into Database.", e);
        }
    }


   //    @ YOU have to ADD a reservation FIRST and insert a client after
    public synchronized void insert( int id, String firstName,String lastName,String cnp,String address, int age ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO client (idclient, firstName, lastName, cnp, address, age) " +
                                           "VALUES ("+id+",'"+firstName+"','"+lastName+"','"+cnp+"','"+address+"','"+age+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted Client with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting a Client.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM client WHERE idclient="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Client with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Client from Database.", e);
        }
    }

    public synchronized static Client findById(int uniqueID) throws ActiveRecordException {

        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM client WHERE idclient=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");
            System.out.println("Found Client with id: " + uniqueID + "\n");

            while(rs.next()) {

                int id = rs.getInt("idClient");
                String fn = rs.getString("firstName");
                String ln = rs.getString("lastName");
                String cnp = rs.getString("cnp");
                String adr = rs.getString("address");
                int age = rs.getInt("age");
                short missed = rs.getShort("missedDeadline");

                Client c = new Client(id,fn,ln,cnp,adr,age,missed);

                return c;
            }

            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding a Client by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM client ORDER BY idClient";
            ResultSet rs = db.executeQuery(statement, "select");

           // db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Clients by ID.", e);
        }
    }
}
