package org.kamsoft.school.school.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.kamsoft.school.school.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SpinnerAdapter extends ArrayAdapter<Levels> {
    int groupid;
    Activity context;
    ArrayList<Levels> list;
    LayoutInflater inflater;
    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<Levels> list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
//        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
//        imageView.setImageResource(list.get(position).getId());

        TextView textView=(TextView)itemView.findViewById(R.id.text1);
        textView.setText(list.get(position).getText());
//        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), "hacen.ttf");
//        textView.setTypeface(externalFont);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
//        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), "hacen.ttf");
//        ((TextView) getView(position,convertView,parent)).setTypeface(externalFont);
        getView(position,convertView,parent).setBackgroundColor(getContext().getResources().getColor( R.color.primaryDarkColor));
        return getView(position,convertView,parent);

    }
}
