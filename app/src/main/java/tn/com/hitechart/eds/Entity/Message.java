package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 16/01/2017.
 */

public class Message {

    private  int _id;
    private String msg;
    private String numDoss;
    private int idTask;

    public Message() {
    }

    public Message(int _id, String msg, String numDoss, int idTask) {
        this._id = _id;
        this.msg = msg;
        this.numDoss = numDoss;
        this.idTask = idTask;
    }

    public Message(int _id, String msg, int idTask) {
        this._id = _id;
        this.msg = msg;
        this.idTask = idTask;
    }

    public String getNumDoss() {
        return numDoss;
    }

    public void setNumDoss(String numDoss) {
        this.numDoss = numDoss;
    }

    public Message(String msg, int idTask) {
        this.msg = msg;
        this.idTask = idTask;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
