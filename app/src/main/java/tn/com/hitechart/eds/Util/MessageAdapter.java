package tn.com.hitechart.eds.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tn.com.hitechart.eds.DAO.MessageDAO;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;

/**
 * Created by BARA' on 18/01/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    List<Message> messages;
    MessageDAO messagesDAO = new MessageDAO(getContext());
    View v;

    private Handler myHandler = new Handler();

    public MessageAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
        this.messages = messages;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final MessageAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MessageAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.msg_item, parent, false);
            viewHolder.tvmsg = (TextView) convertView.findViewById(R.id.tvmsg);
            viewHolder.ivdeletemsg = (ImageView) convertView.findViewById(R.id.ivdeletemsg);
            viewHolder.iveditmsg = (ImageView) convertView.findViewById(R.id.iveditmsg);
            viewHolder.messageList = (ListView) convertView.findViewById(R.id.msgList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (MessageAdapter.ViewHolder) convertView.getTag();
        }
        final Message message = getItem(position);
        //Log.e("nmbr message",""+messages.size());
        viewHolder.tvmsg.setText(message.getMsg().substring(0,7)+"...");

            viewHolder.ivdeletemsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("n°Act", "============================================ N° Message " + message.get_id());
                    new AlertDialog.Builder(getContext())
                            .setTitle("Supprimer Message")
                            .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet Message ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    messagesDAO.deleteMessage(message);
                                    messages.remove(position);

                                    notifyDataSetChanged();
                                    Verif.toast("Message supprimé");
                                }
                            })
                            .setNegativeButton("Non", null).show();
                }
            });

         viewHolder.iveditmsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.i("hey", "I m here ! " + message.get_id());


                 final Dialog dialog = new Dialog(getContext());
                 dialog.setContentView(R.layout.add_msg_dialog_box);
                 dialog.setTitle("Modifier Message");
                 Button btnaddmessage = (Button) dialog.findViewById(R.id.btnaddmsg);
                 final EditText etmsgbody = (EditText) dialog.findViewById(R.id.etmsgbody);

                 etmsgbody.setText(message.getMsg());
                 boolean d = false, m = false;
                 btnaddmessage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (etmsgbody.getText().toString().isEmpty() || etmsgbody.getText().length() <= 2) {
                             etmsgbody.setError("Tapez un message svp !");

                         } else{
                             message.setMsg(etmsgbody.getText().toString());
                             messagesDAO.updateMessage(message);
                             notifyDataSetChanged();
                             dialog.dismiss();
                         }
                     }
                 });
                 //show dialog on screen
                 dialog.show();
             }
         });
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {

        TextView tvmsg;

        ImageView ivdeletemsg;
        ImageView iveditmsg;
        ListView messageList;
    }

}
