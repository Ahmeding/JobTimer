package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 22/12/2016.
 */
public class Activitee {

    private int _id;
    private String name;

    public Activitee() {
    }

    public Activitee(String name) {
        this.name = name;
    }

    public Activitee(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
