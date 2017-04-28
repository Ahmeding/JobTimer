package tn.com.hitechart.eds.Entity;

/**
 * Created by BARA' on 22/12/2016.
 */
public class User {

    private int _id = 0;
    private String type;
    private String email;
    private String login;
    private String password;
    private String pathPhoto;
    private String numTel;
    private boolean admin;
    private int code;

    public User() {
    }


    public User(int id, String type, String email, String login, String pathPhoto, String numTel) {
        this._id = id;
        this.type = type;
        this.email = email;
        this.login = login;
        this.pathPhoto = pathPhoto;
        this.numTel = numTel;
    }

    public User(int _id, String type, String email, String login, String password, String pathPhoto, String numTel, boolean admin, int code) {
        this._id = _id;
        this.type = type;
        this.email = email;
        this.login = login;
        this.password = password;
        this.pathPhoto = pathPhoto;
        this.numTel = numTel;
        this.admin = admin;
        this.code = code;
    }

    public User(int _id, String type, String email, String login,
                String pathPhoto, String numTel, boolean admin, int code) {
        this._id = _id;
        this.type = type;
        this.email = email;
        this.login = login;
        this.pathPhoto = pathPhoto;
        this.numTel = numTel;
        this.admin = admin;
        this.code = code;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }


}
