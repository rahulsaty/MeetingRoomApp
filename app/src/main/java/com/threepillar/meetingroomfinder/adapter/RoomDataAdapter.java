package com.threepillar.meetingroomfinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.model.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomDataAdapter extends RecyclerView.Adapter<RoomDataAdapter.RoomViewHolder> {
    private ArrayList<Room> roomsList;
    private OnItemClickListener onItemClickListener;
    private int SELECTED_ITEM = -1;

    public RoomDataAdapter(ArrayList<Room> roomsList) {
        this.roomsList = roomsList;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_single_row, parent, false);
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RoomViewHolder holder, final int position) {
        holder.rbtn_room_name.setText(roomsList.get(position).getRoomName());
        holder.rbtn_room_name.setChecked(position == SELECTED_ITEM);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_ITEM = holder.getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition(),
                            SELECTED_ITEM > -1 ? String.valueOf(holder.rbtn_room_name.getText()) : null);
                }
                notifyDataSetChanged();
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

        @BindView(R.id.rbtn_room_name)
        RadioButton rbtn_room_name;

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