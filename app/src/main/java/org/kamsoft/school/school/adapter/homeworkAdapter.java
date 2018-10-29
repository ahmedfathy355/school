package org.kamsoft.school.school.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kamsoft.school.school.R;

import java.util.ArrayList;
import java.util.List;

public class homeworkAdapter extends RecyclerView.Adapter<homeworkAdapter.MyViewHolder>{
    private List<subjectsItems> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView subject_id, subject , homework , teacher;
        public MyViewHolder(View itemView) {
            super(itemView);
            subject_id = (TextView)itemView.findViewById(R.id.subject_id);
            subject = (TextView)itemView.findViewById(R.id.subject_name);
            homework = (TextView)itemView.findViewById(R.id.homework);
            teacher = (TextView)itemView.findViewById(R.id.teacher);
        }
    }

    public homeworkAdapter(List<subjectsItems> myDataset)
    {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public homeworkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View _itemView  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(_itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull homeworkAdapter.MyViewHolder holder, int position) {
        //holder.itemView.setBackgroundColor(Color.red(position));
        //Typeface tf = Typeface.createFromAsset( holder.itemView.getContext().getAssets(), "fonts/hacen.ttf");
        //holder.subject.setTypeface(tf);
        subjectsItems sub = mDataset.get(position);
        holder.subject_id.setText(String.valueOf( sub.getSubject_id()));
        holder.subject.setText(sub.getSubjectName());
        holder.homework.setText(sub.getHomework());
        holder.teacher.setText(sub.getTeacher());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
