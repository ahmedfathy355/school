package org.kamsoft.school.school.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.kamsoft.school.school.R;
import org.kamsoft.school.school.ScanPagerActivity;

import java.util.List;

public class HomeCardViewAdapter extends RecyclerView.Adapter<HomeCardViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<HomeCardViewItems> mData ;


    public HomeCardViewAdapter(Context mContext, List<HomeCardViewItems> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_home_button,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_button_title.setText(mData.get(position).getTitle());
        holder.img_button_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mData.get(position).getTitle() != "Absence")
                    return;
                Intent intent = new Intent(mContext,ScanPagerActivity.class);

                // passing data to the book activity
                //intent.putExtra("Title",mData.get(position).getTitle());
                //intent.putExtra("Description",mData.get(position).getDescription());
                //intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_button_title;
        ImageView img_button_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_button_title = (TextView) itemView.findViewById(R.id.home_button_txt_id) ;
            img_button_thumbnail = (ImageView) itemView.findViewById(R.id.home_button_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }



}
