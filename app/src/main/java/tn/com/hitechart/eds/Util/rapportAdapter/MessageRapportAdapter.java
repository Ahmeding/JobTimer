package tn.com.hitechart.eds.Util.rapportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

/**
 * Created by BARA' on 20/01/2017.
 */

public class MessageRapportAdapter  extends ArrayAdapter<Message> {

    Context context;
    User user = UserManager.load();
    List<Message> messages;
    View v;

    public MessageRapportAdapter(Context context, List<Message> messages) {
        super(context, 0,messages);
        this.context=context;
        this.messages=messages;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final MessageRapportAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MessageRapportAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item_rapport, parent, false);

            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdossier);
            viewHolder.tvmsg = (TextView) convertView.findViewById(R.id.tvmsg);
            viewHolder.messageRapportList = (ListView) convertView.findViewById(R.id.msgRapportList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MessageRapportAdapter.ViewHolder) convertView.getTag();
        }
        final Message message = getItem(position);
        TasksDAO tasksDAO = new TasksDAO(context);
        Task task = new Task();
        tasksDAO.getTachesByUser(user);


        viewHolder.tvnumdossier.setText(String.valueOf(message.getNumDoss()));
        viewHolder.tvmsg.setText(String.valueOf(message.getMsg()));
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {
        TextView tvnumdossier;
        TextView tvmsg;
        ListView messageRapportList;
    }

}
