package org.kamsoft.school.school.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.kamsoft.school.school.R;

import java.util.List;

/**
 * Created by Ahmed Fathy on 26/3/2018.
 */


public class ClassessAdapter extends BaseAdapter {

    Activity activity;
    List<Classess> classess;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public ClassessAdapter(Activity activity) {
        this.activity = activity;
    }

    public ClassessAdapter(Activity activity, List<Classess> users) {
        this.activity   = activity;
        this.classess      = users;

        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return classess.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.list_view_classess, viewGroup, false);

            holder = new ViewHolder();

            holder.txt_classname = (TextView)view.findViewById(R.id.txt_classname);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        Classess model = classess.get(i);

        holder.txt_classname.setText(model.get_ClassName());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<Classess> users){
        this.classess = users;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView txt_classname;
        ImageView ivCheckBox;

    }
}
