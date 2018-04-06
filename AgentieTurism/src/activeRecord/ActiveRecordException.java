package activeRecord;

/**
 * Created by plesha on 28-Mar-18.
 */


public class ActiveRecordException extends Exception {

    // Constructor that accepts a message
    public ActiveRecordException(String message) {
        super(message);
    }

    public ActiveRecordException (String message, Exception e) {
        super (message, e);
        e.printStackTrace();
    }
}
