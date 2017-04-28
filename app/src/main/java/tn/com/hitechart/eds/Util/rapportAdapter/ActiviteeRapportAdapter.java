package tn.com.hitechart.eds.Util.rapportAdapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 07/01/2017.
 */

public class ActiviteeRapportAdapter extends ArrayAdapter<Task> {


    Context context;
    Taches taches = new Taches();
    long startTime = 0L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    List<Task> tasks;
    //TaskDAO taskDAO = new TaskDAO(getContext());
    View v;
    String tmps = "00:00:00";
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
    private Handler myHandler = new Handler();

    public ActiviteeRapportAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ActiviteeRapportAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ActiviteeRapportAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autre_act_item, parent, false);

            viewHolder.tvtaskname = (TextView) convertView.findViewById(R.id.tvtaskname);
            viewHolder.tvdureeautreact = (TextView) convertView.findViewById(R.id.tvdureeautreact);

            viewHolder.taskAutreActtList = (ListView) convertView.findViewById(R.id.taskAutreActtList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ActiviteeRapportAdapter.ViewHolder) convertView.getTag();
        }
        final Task task = getItem(position);
        viewHolder.tvtaskname.setText(String.valueOf(task.getAct()));
        long t = task.getTimeA();
        int seconds = (int) (t / 1000)%60;
        int minutes = (int) ((t/(1000* 60))%60);
        int hours = (int) ((t/(1000*60*60))%24);
        viewHolder.tvdureeautreact.setText(""
                + String.format("%02d", hours) + "H"
                + String.format("%02d", minutes));
                //+ ":" + String.format("%02d", seconds));
        //viewHolder.tvTimeA.setText(String.valueOf(df.format(task.getTimeA().getTime().getHours())));
        // viewHolder.tvtimestart.setText(String.valueOf("hh:mm:ss"));
        //viewHolder.tvtimestop.setText(String.valueOf("hh:mm:ss"));
        //viewHolder.ivstop.
        // viewHolder.ivplay.setOnClickListener(new View.OnClickListener(){
        //     @Override
        //     public void onClick(View view){
        //         Log.i("01","---------------------------------------------------ok START -->");
        //         startTime = SystemClock.uptimeMillis();
        //         myHandler.postDelayed(updateTimerMethod,0);
        //     }
        // });
        // viewHolder.ivpause.setOnClickListener(new View.OnClickListener(){
        //     @Override
        //     public void onClick(View view){
        //         Log.i("01","---------------------------------------------------ok PAUSE -->");
        //         timeSwap+=timeInMillies;
        //        myHandler.removeCallbacks(updateTimerMethod);
        //     }
        // });
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
        //viewHolder.tvTimeA.setText(String.valueOf(task.getTimeA()));
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {
        TextView tvtaskname;
        TextView tvdureeautreact;
        ListView taskAutreActtList;
    }

}
