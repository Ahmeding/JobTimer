package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 22/12/2016.
 */
public class Resultat {

    private int _id;
    private String name;

    public Resultat() {
    }

    public Resultat(String name) {
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
