package com.android.shareride.View;

/**
 * Created by Marshal on 2016/10/29.
 */
public class Trip {
    public int userID;
    public String from;
    public String to;
    public String date;
    public String time;
    public float individualFare;
    public int tripID;

    public Trip() {
    }

    public Trip(String date, String from, float individualFare, String time, String to, int
            userID) {
        this.date = date;
        this.from = from;
        this.individualFare = individualFare;
        this.time = time;
        this.to = to;
        this.userID = userID;
    }

    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */
    @Override
    public String toString() {
        return String.format("User: %s From: %s To: %s at %s %s", userID, from, to, time, date);
        //return "User: " + userID + "From: " + from + " To: " + to + " at " + time + " " + date;
    }
}
