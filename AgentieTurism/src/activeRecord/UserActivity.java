package activeRecord;

import dbConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by plesha on 31-Mar-18.
 */
public class UserActivity {
    private int id;
    private Date date;
    private String information;
    private int idUser;

    public UserActivity(){

    }

    public UserActivity( int id, Date date, String info, int idUser ){
        this.id = id;
        this.date = date;
        this.information = info;
        this.idUser = idUser;
    }

    /***************************************************************
     *  Getters and Setters
     ***************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /***************************************************************
     *  Domain Logic Methods
     ***************************************************************/

    @Override
    public String toString() {
        return id + " " + date + " " + information;
    }

    /***************************************************************
     *  SQL Operation Methods
     ***************************************************************/

    public synchronized void insert( java.sql.Date date, String info, int user_iduser) throws ActiveRecordException{
        try {
            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statementIDS = "SELECT count(*) FROM useractivity;";
            ResultSet rs = db.executeQuery(statementIDS, "select");

            int nrOfIDS = 0;
            while(rs.next())
                nrOfIDS = rs.getInt("count(*)");


            String statement = "INSERT INTO UserActivity (iduserActivity,date,info,user_iduser) VALUES ("+(nrOfIDS+1)+",'"+date+"','"+info+"','"+user_iduser+"');";
            db.executeQuery(statement, "insert");


            System.out.println("Activity done by User with id: " + user_iduser + "\n");

            db.closeConnectionToDatabase();


        }catch (Exception e) {
            throw new ActiveRecordException("Error inserting an User into Database.", e);
        }
    }

    public synchronized ResultSet findAll()throws ActiveRecordException {
        try {

            DatabaseConnection db =	new DatabaseConnection();
            db.openConnectionToDatabase();

            String statement = "SELECT * FROM useractivity ORDER BY iduserActivity";
            ResultSet rs = db.executeQuery(statement, "select");

            //db.closeConnectionToDatabase();

            return rs;

        } catch (Exception e) {
            throw new ActiveRecordException("Error finding ALL Admins by ID.", e);
        }
    }
}
