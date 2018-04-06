package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by plesha on 27-Mar-18.
 */

public class Person {
    private int id;
    private String name;
    private String cnp;
    private int age;
    private Gender sex;
    private int idResevationPerson;



    public Person(){

    }

    // idperson, name, age, cnp, sex, reservationperson_idResevationPerson
    public Person(int id, String name, String cnp, int age, Gender sex, int idResevationPerson){
        this.id = id;
        this.name = name;
        this.cnp = cnp;
        this.age = age;
        this.sex = sex;
        this.idResevationPerson = idResevationPerson;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCnp(){
        return this.cnp;
    }

    public int getAge(){
        return this.age;
    }

    public Gender getSex(){
        return this.sex;
    }

    public int getIdResevationPerson() {
        return idResevationPerson;
    }

    public void setIdResevationPerson(int idResevationPerson) {
        this.idResevationPerson = idResevationPerson;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }


    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/

    @Override
    public String toString() {
       // return super.toString();
        return id + " " + name + " " + age + " " + sex + " ";
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void update( int id, String name,String cnp, int age,Gender sex, int idResevationPerson) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();


            String statement = "UPDATE person SET name='"+name+ "',cnp='"+cnp+"',age='"+age+"',sex='"+sex+"'," +
                    "reservationperson_idResevationPerson='"+idResevationPerson+"' WHERE idPerson="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated Person with id: " + id + "\n");

            db.closeConnectionToDatabase();

        }catch (Exception e) {
            throw new ActiveRecordException("Error updating Client into Database.", e);
        }
    }

    public synchronized void insert( int id, String name,String cnp, int age,Gender sex, int idResevationPerson ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO Person (idperson, name, age, cnp, sex,reservationperson_idResevationPerson) " +
                    "VALUES ("+id+",'"+name+"','"+age+"','"+cnp+"','"+sex+"','"+idResevationPerson+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted Person with id: " + id + "\n");

            db.closeConnectionToDatabase();
        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting a Person into database.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM person WHERE idperson="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Person with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Person from Database.", e);
        }
    }

    public synchronized void deleteByReservationID( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM person WHERE reservationperson_idResevationPerson="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Person with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting a Person from Database.", e);
        }
    }

    public synchronized static Person findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM person WHERE idPerson=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");
            System.out.println("Found Person with id: " + uniqueID + "\n");
            while(rs.next()) {

                int id = rs.getInt("idPerson");
                String fn = rs.getString("name");
                String cnp = rs.getString("cnp");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int idResevationPerson = rs.getInt("reservationperson_idResevationPerson");

                Gender g = sex.equals("Male") ? Gender.Male : Gender.Female;

                Person p = new Person(id,fn,cnp,age,g,idResevationPerson);


                return p;
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

            String statement = "SELECT * FROM person ORDER BY idperson";
            ResultSet rs = db.executeQuery(statement, "select");

            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Persons by ID.", e);
        }
    }

}

