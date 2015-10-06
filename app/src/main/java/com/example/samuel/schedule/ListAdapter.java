package com.example.samuel.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by user05 on 2015/10/5.
 */
public class ListAdapter extends ArrayAdapter<ScheduleThum>{
    private int resourceId;
    public ListAdapter(Context context,int textViewResourceId, ArrayList<ScheduleThum> arrayList) {
        super(context, textViewResourceId, arrayList);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleThum scheduleThum = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {

            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.adapter_tag = (TextView)view.findViewById(R.id.adapter_tag);
            viewHolder.adapter_title = (TextView)view.findViewById(R.id.adapter_title);
            viewHolder.adapter_time = (TextView)view.findViewById(R.id.adapter_time);
            view.setTag(viewHolder);

        }else {
            view= convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.adapter_tag.setText(scheduleThum.getTag());
        viewHolder.adapter_title.setText(scheduleThum.getTitle());
        viewHolder.adapter_time.setText(scheduleThum.getTime());


        return view;
    }
    class ViewHolder{
        TextView adapter_tag;
        TextView adapter_title;
        TextView adapter_time ;
    }
}
