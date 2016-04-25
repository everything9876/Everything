package com.everything.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.everything.R;
import com.everything.service.model.Computer;

import java.util.List;

/**
 * Created by Mirek on 2016-04-21.
 */
public class ApiAdapter extends ArrayAdapter<Computer> {

    private LayoutInflater layoutInflater;
    private Context context;
    private int resource;
    private List<Computer> computers;

    public ApiAdapter(Context context, int resource, List<Computer> computers) {
        super(context, resource, computers);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.resource = resource;
        this.computers = computers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = null;
        rowView = convertView;
        MyViewHolder myViewHolder = new MyViewHolder();

        if(rowView == null){
            rowView = layoutInflater.inflate(resource,null);

            myViewHolder.id = (TextView) rowView.findViewById(R.id.identifier);
            myViewHolder.name = (TextView) rowView.findViewById(R.id.comp_name);

            rowView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) rowView.getTag();
        }

        int id = computers.get(position).getId();
        String name = computers.get(position).getName();

        myViewHolder.id.setText(String.valueOf(id));
        myViewHolder.name.setText(name);

        return rowView;
    }

    static class MyViewHolder{
        TextView id;
        TextView name;
    }
}
