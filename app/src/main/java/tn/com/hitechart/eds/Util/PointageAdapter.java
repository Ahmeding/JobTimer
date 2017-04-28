package tn.com.hitechart.eds.Util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 27/12/2016.
 */
public class PointageAdapter extends ArrayAdapter<Pointage> {

    ArrayList<Pointage> pointages;
    PointagesDAO pointagesDAO = new PointagesDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public PointageAdapter(Context context, ArrayList<Pointage> pointages) {
        super(context, 0, pointages);
        this.pointages = pointages;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final PointageAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new PointageAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.point_item, parent, false);
            viewHolder.tvTimeInM = (TextView) convertView.findViewById(R.id.tvTimeInM);
            viewHolder.tvTimeOutM = (TextView) convertView.findViewById(R.id.tvTimeOutM);
            viewHolder.tvTimeInAM = (TextView) convertView.findViewById(R.id.tvTimeInM);
            viewHolder.tvTimeOutAM = (TextView) convertView.findViewById(R.id.tvTimeOutM);
            // viewHolder.ivdeleteAct= (ImageView) convertView.findViewById(R.id.ivdeleteAct);
            // viewHolder.iveditAct= (ImageView) convertView.findViewById(R.id.iveditAct);
            // viewHolder.pointList = (ListView) convertView.findViewById(R.id.pointList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (PointageAdapter.ViewHolder) convertView.getTag();
        }
        final Pointage pointage = getItem(position);
        // viewHolder.tvActID.setText(String.valueOf(pointage.get_id()));
        viewHolder.tvTimeInM.setText(String.valueOf(pointage.getDate_timeInM()));
        viewHolder.tvTimeOutM.setText(String.valueOf(pointage.getDate_timeOutM()));
        //  viewHolder.tvActName.setText(String.valueOf(pointage.getDate_timeIn()));
        //viewHolder.tvActName.setText(String.valueOf(pointage.getDate_timeOut()));
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {

        TextView tvTimeInM,
                tvTimeOutM,
                tvTimeInAM,
                tvTimeOutAM;
        //ImageView ivdeleteAct,
        //        iveditAct;
        ListView pointList;
    }
}
