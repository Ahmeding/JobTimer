package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 17/01/2017.
 */

public class Rapport {
    private int _id;
    private boolean isSend;
    private String dateofCreation;
    private long dateOfSending;
    private int userId;

    public Rapport() {
    }

    public Rapport(int _id, boolean isSend, String dateofCreation, long dateOfSending, int userId) {
        this._id = _id;
        this.isSend = isSend;
        this.dateofCreation = dateofCreation;
        this.dateOfSending = dateOfSending;
        this.userId = userId;
    }

    public Rapport(int _id, boolean isSend, long dateOfSending, int userId) {
        this._id = _id;
        this.isSend = isSend;
        this.dateOfSending = dateOfSending;
        this.userId = userId;
    }

    public String getDateofCreation() {
        return dateofCreation;
    }

    public void setDateofCreation(String dateofCreation) {
        this.dateofCreation = dateofCreation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(long dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    @Override
    public String toString() {
        return "Rapport{" +
                "_id=" + _id +
                ", isSend=" + isSend +
                ", dateofCreation='" + dateofCreation + '\'' +
                ", dateOfSending=" + dateOfSending +
                ", userId=" + userId +
                '}';
    }
}
