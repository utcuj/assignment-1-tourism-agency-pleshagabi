

package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.*;

/**
 * Created by plesha on 27-Mar-18.
 */
public class Administrator {

    private int id;
    private String name;
    private String username;
    private String password;
    private int age;

    public Administrator(){

    }

    public Administrator( int id, String name,String username,String password, int age ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getId(){
        return this.id;
    }

    public int getAge(){
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }


    public void setId(int id){
        this.id =id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/

    @Override
    public String toString() {
        return id + " " + name + " " + username;
    }


    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void update( int id, String name,String username,String password, int age ) throws ActiveRecordException{
        try {
           DatabaseConnection db =	new DatabaseConnection() ;
            db.openConnectionToDatabase();

            String statement = "UPDATE administrator SET name='"+name+ "',username='"+username+"',password='" + password + "',age="+age +" WHERE idAdministrator="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated Admin with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error updating Admin into Database.", e);
        }
    }

    public synchronized void insert( int id, String name,String username,String password, int age ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO administrator (idAdministrator,name,username,password,age) VALUES ("+id+",'"+name+"','"+username+"','"+password+"','"+age+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted Admin with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting an Administrator into Database.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM administrator WHERE idAdministrator="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted Admin with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting an Admin from Database.", e);
        }
    }

    public synchronized static Administrator findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM administrator WHERE idAdministrator=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");

            while(rs.next()) {
                int id = rs.getInt("idAdministrator");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");

                Administrator admn = new Administrator(id,name,username,password,age);

                return admn;
            }
            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding Admin by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM administrator ORDER BY idAdministrator";
            ResultSet rs = db.executeQuery(statement, "select");


            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Admins by ID.", e);
        }
    }

    public synchronized boolean findByUserNameAndPassword( String username, String password )throws ActiveRecordException {
        try {
            boolean found = false;
            String getUsername = " ";
            String getPassword = " ";

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM administrator WHERE username='"+username+"'";
            ResultSet rs = db.executeQuery(statement, "select");

            while( rs.next() ) {
                getUsername = rs.getString("username");
                getPassword = rs.getString("password");
            }

            if( getUsername.equals(username) && getPassword.equals(password)  ){
                found = true;
            }

            return found;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding an Admin by USERNAME.", e);
        }
    }




}
