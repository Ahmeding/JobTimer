package tn.com.hitechart.eds.Util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 22/12/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {

    ArrayList<User> users;
    UsersDAO usersDAO = new UsersDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.users = users;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
           // viewHolder.tvUID = (TextView) convertView.findViewById(R.id.tvUID);
            viewHolder.tvLogin = (TextView) convertView.findViewById(R.id.tvLogin);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvTel = (TextView) convertView.findViewById(R.id.tvTel);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
            viewHolder.userList = (ListView) convertView.findViewById(R.id.userList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final User user = getItem(position);
//        viewHolder.tvUID.setText(String.valueOf(user.get_id()));
        viewHolder.tvLogin.setText(String.valueOf(user.getLogin()));
        viewHolder.tvType.setText(String.valueOf(user.getType()));
        viewHolder.tvTel.setText(String.valueOf(user.getNumTel()));
        viewHolder.tvEmail.setText(String.valueOf(user.getEmail()));
        Log.e("isAdmin",""+user.isAdmin());
        //  viewHolder.btnclear.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //      public void onClick(View v) {
        //          Log.i("hey", "I m here ! " + task.get_id());
        //          new AlertDialog.Builder(getContext())
        //                  .setTitle("Task was achieved")
        //                  .setMessage("Are you sure ?")
        //                  .setPositiveButton("YES", new DialogInterface.OnClickListener() {
        //                      public void onClick(DialogInterface dialog, int whichButton) {
        //                          taskDAO.deleteTask(task.get_id());
        //                          tasks.remove(position);
        //                          notifyDataSetChanged();
        //                          Toast.makeText(getContext(), "Task was achieved !", Toast.LENGTH_SHORT).show();
        //                      }
        //                  })
        //                  .setNegativeButton("NO", null).show();
        //      }
        //  });
        v = convertView;
        return convertView;
    }

    private static class ViewHolder {

        TextView tvUID, tvLogin, tvType, tvTel, tvEmail;
        ListView userList;
    }
}
