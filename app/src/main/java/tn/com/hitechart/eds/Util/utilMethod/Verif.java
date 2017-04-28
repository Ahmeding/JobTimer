package tn.com.hitechart.eds.Util.utilMethod;

import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static tn.com.hitechart.eds.data.App.getContext;

/**
 * Created by BARA' on 10/01/2017.
 */

public class Verif {

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

    }

    public static void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

   public static String timeConvertor(long finalTime){
       String timetodisplay="";
       int seconds = (int) (finalTime / 1000);
       int minutes = seconds / 60;
       int hours = seconds / 3600;
       seconds = seconds % 60;
       int milliseconds = (int) (finalTime % 1000);
       timetodisplay =
                 String.format("%02d", hours) + ":"
               + String.format("%02d", minutes) + ":"
               + String.format("%02d", seconds);
       return timetodisplay;
    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

    public boolean isToday(String day) {
        return dateFormat.format(System.currentTimeMillis()).equals(day);
    }


}
