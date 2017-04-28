package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 22/01/2017.
 */

public class Dossier {

    private int _id;
    private String numDoss;
    private String client;
    private long timeDuration;
    private long startTime;
    private long endTime;
    private int _idTask;

    public Dossier() {
    }

    public Dossier(int _id, String numDoss, String client, long timeDuration, long startTime, long endTime, int _idTask) {
        this._id = _id;
        this.numDoss = numDoss;
        this.client = client;
        this.timeDuration = timeDuration;
        this.startTime = startTime;
        this.endTime = endTime;
        this._idTask = _idTask;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public long getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int get_idTask() {
        return _idTask;
    }

    public void set_idTask(int _idTask) {
        this._idTask = _idTask;
    }

    @Override
    public String toString() {
        return "Dossier{" +
                "_id=" + _id +
                ", numDoss='" + numDoss + '\'' +
                ", client='" + client + '\'' +
                ", timeDuration=" + timeDuration +
                //", startTime=" + startTime +
               // ", endTime=" + endTime +
                ", _idTask=" + _idTask +
                '}';
    }
}
