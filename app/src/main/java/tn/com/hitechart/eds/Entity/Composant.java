package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 02/01/2017.
 */

public class Composant {
    private int _id;
    private int _idTask;
    private String name;
    private int qte;
    private String numDoss;

    public Composant() {
    }

    public Composant(int _id, int _idTask, String name, int qte) {
        this._id = _id;
        this._idTask = _idTask;
        this.name = name;
        this.qte = qte;
    }

    public Composant(int _id, int _idTask, String name, int qte, String numDoss) {
        this._id = _id;
        this._idTask = _idTask;
        this.name = name;
        this.qte = qte;
        this.numDoss = numDoss;
    }

    public String getNumDoss() {
        return numDoss;
    }

    public void setNumDoss(String numDoss) {
        this.numDoss = numDoss;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_idTask() {
        return _idTask;
    }

    public void set_idTask(int _idTask) {
        this._idTask = _idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
