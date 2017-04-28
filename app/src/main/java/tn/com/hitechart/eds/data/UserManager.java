package tn.com.hitechart.eds.data;

import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;

public class UserManager {

    public static final int ADMIN_CODE= 001;
    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String ADMIN_NUMTEL = "55121212";
    public static final String ADMIN_PATHPHOTO = "drawable://" + R.drawable.img;
    public static final String ADMIN_TYPE = "E.D.S.";
    public static final String ADMIN_ISADIN= "1";


    public static final String USER_LOGIN = "user1";
    public static final String USER_PASSWORD = "user1";


    public static boolean isValidAdmin(String login, String password) {
        return ADMIN_LOGIN.equalsIgnoreCase(login) && ADMIN_PASSWORD.equalsIgnoreCase(password);
    }

    public static boolean isValidUser(String login, String password) {
        return USER_LOGIN.equalsIgnoreCase(login) && USER_PASSWORD.equalsIgnoreCase(password);
    }

    public static boolean isAdmin() {
        User user = load();
        return user != null && user.isAdmin();
    }

    public static boolean isConnect() {
        return SharedPreference.getInstance().contains(Key.USER);
    }

    public static void connect(User user) {
        SharedPreference.getInstance().putObject(Key.USER, user);
    }

    public static void disconnect() {
        SharedPreference.getInstance().remove(Key.USER);
    }

    public static User load() {
        return SharedPreference.getInstance().getObject(Key.USER, User.class);
    }

}
