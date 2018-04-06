package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by plesha on 31-Mar-18.
 */
public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private int age;

    public User(){

    }

    public User( int id, String name,String username,String password, int age ){
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

            String statement = "UPDATE user SET name='"+name+ "',username='"+username+"',password='" + password + "',age="+age +" WHERE iduser="+id+";";
            db.executeQuery(statement, "update");

            System.out.println("Updated User with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error updating User into Database.", e);
        }
    }

    public synchronized void insert( int id, String name,String username,String password, int age ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "INSERT INTO User (iduser,name,username,password,age) VALUES ("+id+",'"+name+"','"+username+"','"+password+"','"+age+"');";
            db.executeQuery(statement, "insert");

            System.out.println("Inserted User with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting an User into Database.", e);
        }
    }

    public synchronized void delete( int id ) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "DELETE FROM User WHERE iduser="+id+";";
            db.executeQuery(statement, "delete");
            System.out.println("Deleted User with id: " + id + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error deleting an User from Database.", e);
        }
    }

    public synchronized static User findById(int uniqueID) throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM User WHERE iduser=" + uniqueID;
            ResultSet rs = db.executeQuery(statement, "select");

            while(rs.next()) {
                int id = rs.getInt("iduser");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");

                User user = new User(id,name,username,password,age);

                return user;
            }
            db.closeConnectionToDatabase();
            return null;

        } catch (SQLException e) {
            throw new ActiveRecordException("Error finding User by ID.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM user ORDER BY iduser";
            ResultSet rs = db.executeQuery(statement, "select");

            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Users by ID.", e);
        }
    }

    public synchronized boolean findByUserNameAndPassword( String username, String password )throws ActiveRecordException {
        try {
            boolean found = false;
            String getUsername = " ";
            String getPassword = " ";

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM user WHERE username='"+username+"'";
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
            throw new ActiveRecordException("Error finding a User by USERNAME.", e);
        }
    }

    public synchronized int findIDbyUsername( String username )throws ActiveRecordException {
        try {
            int id = 0;

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT iduser FROM user WHERE username='"+username+"'";
            ResultSet rs = db.executeQuery(statement, "select");

            while( rs.next() ) {
                id = rs.getInt("iduser");
            }

            return id;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding a User by USERNAME.", e);
        }
    }
}
