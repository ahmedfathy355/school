package org.kamsoft.school.school.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.kamsoft.school.school.R;


import java.util.List;

/**
 * Created by Ahmed Fathy on 26/3/2018.
 */


public class ClassessAdapter extends RecyclerView.Adapter<ClassessAdapter.MyViewHolder>{
    private Context mContext;
    List<Classess> classList;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView txt_classname;
        public ImageView ivCheckBox;

        public MyViewHolder(View view) {
            super(view);
            txt_classname = (TextView) itemView.findViewById(R.id.cardtxt);
            ivCheckBox = (ImageView) itemView.findViewById(R.id.carsimg);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            //listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public ClassessAdapter(Context mContext, List<Classess> classList) {
        this.mContext = mContext;
        this.classList = classList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Classess classess = classList.get(position);
        holder.txt_classname.setText(classess.get_ClassName());
        //holder.ivCheckBox.setImageResource(Integer.getInteger( classess.get_imgClass()));

        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.ivCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.txt_classname);
            }
        });
    }


    public int getItemCount() {
        return classList.size();
    }

    public Object getItem(int position) {
        return classList.get(position).getId();
    }

    private void showPopupMenu(View view) {
        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    //Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_search:
                    //Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }



    public void updateRecords(List<Classess> users){
        this.classList = users;

        notifyDataSetChanged();
    }


}
