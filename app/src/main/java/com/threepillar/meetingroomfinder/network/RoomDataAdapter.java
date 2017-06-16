package com.threepillar.meetingroomfinder.network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.model.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomDataAdapter extends RecyclerView.Adapter<RoomDataAdapter.RoomViewHolder> {
    private ArrayList<Room> roomsList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RoomDataAdapter(ArrayList<Room> roomsList, Context context) {
        this.roomsList = roomsList;
        this.context = context;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_single_row, parent, false);
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RoomViewHolder holder, int position) {
        holder.labelTv.setText(roomsList.get(position).getRoom_label());
        holder.headingTv.setText(roomsList.get(position).getHeading());
        holder.subHeadingTv.setText(roomsList.get(position).getSub_heading());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), ""+holder.labelTv.getText(), Toast.LENGTH_SHORT).show();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), String.valueOf(holder.labelTv.getText()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.label_tv)
        TextView labelTv;
        @BindView(R.id.heading_tv)
        TextView headingTv;
        @BindView(R.id.sub_heading_tv)
        TextView subHeadingTv;

        public RoomViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String itemName);
    }
}