package tn.com.hitechart.eds.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BARA' on 02/05/2016.
 */
public class Task implements Parcelable {

   private int _id;
    private int _idUser;
    private String taskName;
    private String act;
    private String res;
    private String numDoss;
    private String client;
    private long timeStart, timeStop;
    private long timeA;
    private String designation;
    private float Montanttc;
    private String besoinComp;
    private String type;
    private int flagStatus;
    private String category;

    public Task(int _id, String numDoss, String client) {
        this._id = _id;
        this.numDoss = numDoss;
        this.client = client;

    }

    public Task() {
    }

    public Task(int _id, String act, String numDoss, String client, String type) {
        this._id = _id;
        this.act = act;
        this.numDoss = numDoss;
        this.client = client;
        this.type = type;
    }

    public Task(int _id, int _idUser, String taskName, String act, String res, String numDoss,
                String client, long timeStart, long timeStop, long timeA, String designation,
                float montanttc, String besoinComp, String type, int flagStatus, String category) {
        this._id = _id;
        this._idUser = _idUser;
        this.taskName = taskName;
        this.act = act;
        this.res = res;
        this.numDoss = numDoss;
        this.client = client;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.timeA = timeA;
        this.designation = designation;
        Montanttc = montanttc;
        this.besoinComp = besoinComp;
        this.type = type;
        this.flagStatus = flagStatus;
        this.category = category;
    }


    protected Task(Parcel in) {
        _id = in.readInt();
        _idUser = in.readInt();
        taskName = in.readString();
        act = in.readString();
        res = in.readString();
        numDoss = in.readString();
        client = in.readString();
        timeStart = in.readLong();
        timeStop = in.readLong();
        timeA = in.readLong();
        designation = in.readString();
        Montanttc = in.readFloat();
        besoinComp = in.readString();
        type = in.readString();
        flagStatus = in.readInt();
        category = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimeA() {
        return timeA;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimeA(long timeA) {
        this.timeA = timeA;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getNumDoss() {
        return numDoss;
    }

    public void setNumDoss(String numDoss) {
        this.numDoss = numDoss;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getMontanttc() {
        return Montanttc;
    }

    public void setMontanttc(float montanttc) {
        Montanttc = montanttc;
    }

    public String getBesoinComp() {
        return besoinComp;
    }

    public void setBesoinComp(String besoinComp) {
        this.besoinComp = besoinComp;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(long timeStop) {
        this.timeStop = timeStop;
    }

    public int getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(int flagStatus) {
        this.flagStatus = flagStatus;
    }

   @Override
    public String toString() {
        String data = "";
        data += "_id" + "[" + _id + "]";
        data += "FlagStatus" + "[" + flagStatus + "]";
       //data += "_idUser" + "[" + _idUser + "]";
       //data += "TaskName" + "[" + taskName + "]";
       data += "Act" + "[" + act + "]";
       data += "Category" + "[" + category + "]";
       //data += "Res" + "[" + res + "]";
       //data += "NumDoss" + "[" + numDoss + "]";
       //data += "Client" + "[" + client + "]";
       //data += "TimeStart" + "[" + timeStart + "]";
       //data += "TimeStop" + "[" + timeStop + "]";
       //data += "TimeA" + "[" + timeA + "]";
       //data += "Designation" + "[" + designation + "]";
       //data += "Montanttc" + "[" + Montanttc + "]";
       //data += "BesoinComp" + "[" + besoinComp + "]";
       //data += "FlagStatus" + "[" + flagStatus + "]";
       //data += "TaskType" + "[" + type + "]";
        data += "\n";
        return data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(_idUser);
        dest.writeString(taskName);
        dest.writeString(act);
        dest.writeString(res);
        dest.writeString(numDoss);
        dest.writeString(client);
        dest.writeLong(timeStart);
        dest.writeLong(timeStop);
        dest.writeLong(timeA);
        dest.writeString(designation);
        dest.writeFloat(Montanttc);
        dest.writeString(besoinComp);
        dest.writeString(type);
        dest.writeInt(flagStatus);
        dest.writeString(category);
    }
}
