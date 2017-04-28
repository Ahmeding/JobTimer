package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 26/12/2016.
 */
public class Pointage {
    private int _id;
    private int _idUser;
    private boolean userIn;
    private boolean userOut;
    private int flag;
    private long date_timeInM;
    private long date_timeOutM;
    private long date_timeInAM;
    private long date_timeOutAM;
    private int flagCategory;
    private String datePointage;

    public Pointage() {
    }

    public Pointage(int _id, int _idUser, boolean userIn, boolean userOut,
                    int flag, long date_timeInM,
                    long date_timeOutM, long date_timeInAM, long date_timeOutAM,
                    int flagCategory, String datePointage) {
        this._id = _id;
        this._idUser = _idUser;
        this.userIn = userIn;
        this.userOut = userOut;
        this.flag = flag;
        this.date_timeInM = date_timeInM;
        this.date_timeOutM = date_timeOutM;
        this.date_timeInAM = date_timeInAM;
        this.date_timeOutAM = date_timeOutAM;
        this.flagCategory = flagCategory;
        this.datePointage = datePointage;
    }

    public Pointage(int _id, int _idUser, boolean userIn, boolean userOut, int flag, long date_timeInM,
                    long date_timeOutM, long date_timeInAM, long date_timeOutAM, int flagCategory) {
        this._id = _id;
        this._idUser = _idUser;
        this.userIn = userIn;
        this.userOut = userOut;
        this.flag = flag;
        this.date_timeInM = date_timeInM;
        this.date_timeOutM = date_timeOutM;
        this.date_timeInAM = date_timeInAM;
        this.date_timeOutAM = date_timeOutAM;
        this.flagCategory = flagCategory;
    }

    public String getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(String datePointage) {
        this.datePointage = datePointage;
    }

    public int getFlagCategory() {
        return flagCategory;
    }

    public void setFlagCategory(int flagCategory) {
        this.flagCategory = flagCategory;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_idUser() {
        return _idUser;
    }

    public void set_idUser(int _idUser) {
        this._idUser = _idUser;
    }

    public boolean isUserIn() {
        return userIn;
    }

    public void setUserIn(boolean userIn) {
        this.userIn = userIn;
    }

    public boolean isUserOut() {
        return userOut;
    }

    public void setUserOut(boolean userOut) {
        this.userOut = userOut;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getDate_timeInM() {
        return date_timeInM;
    }

    public void setDate_timeInM(long date_timeInM) {
        this.date_timeInM = date_timeInM;
    }

    public long getDate_timeOutM() {
        return date_timeOutM;
    }

    public void setDate_timeOutM(long date_timeOutM) {
        this.date_timeOutM = date_timeOutM;
    }

    public long getDate_timeInAM() {
        return date_timeInAM;
    }

    public void setDate_timeInAM(long date_timeInAM) {
        this.date_timeInAM = date_timeInAM;
    }

    public long getDate_timeOutAM() {
        return date_timeOutAM;
    }

    public void setDate_timeOutAM(long date_timeOutAM) {
        this.date_timeOutAM = date_timeOutAM;
    }
}

