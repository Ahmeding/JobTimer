package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 10/01/2017.
 */

public class ParamAdresseEmail {
    private int _id;
    private String adresseEmail;

    public ParamAdresseEmail() {

    }

    public ParamAdresseEmail(int _id, String adresseEmail) {
        this._id = _id;
        this.adresseEmail = adresseEmail;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
}
