package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 03/01/2017.
 */

public class Achat {
    private int _id;
    private String numDoss;

    private String designation;
    private float prix;
    private float total;
    private int _idTask;

    public Achat() {
    }

    public Achat(int _id, float total, float prix, String designation,int _idTask) {
        this._id = _id;
        this._idTask = _idTask;
        this.total = total;
        this.prix = prix;
        this.designation = designation;
    }

    public Achat(int _id, String numDoss, String designation, float prix, float total, int _idTask) {
        this._id = _id;
        this.numDoss = numDoss;
        this.designation = designation;
        this.prix = prix;
        this.total = total;
        this._idTask = _idTask;
    }

    public String getNumDoss() {
        return numDoss;
    }

    public void setNumDoss(String numDoss) {
        this.numDoss = numDoss;
    }

    public int get_idTask() {
        return _idTask;
    }

    public void set_idTask(int _idTask) {
        this._idTask = _idTask;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
