package com.threepillar.meetingroomfinder.network;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.fragment.SingleDayFragment;
import com.threepillar.meetingroomfinder.model.EventInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleDayAdapter extends RecyclerView.Adapter<SingleDayAdapter.SingleDayViewHolder> {

    private ArrayList<EventInfo> eventList;
    //    List<Date> dates = null;
    private Set<Integer> selectedPositions = new HashSet<>();
    private Context context;

    private OnSingleItemClickListener onSingleItemClickListener;

    public SingleDayAdapter(/*List<Date> dates*/ ArrayList<EventInfo> eventList, Context context) {
//        this.dates = dates;
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public SingleDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unmarked_rooms_single_row, parent, false);
        return new SingleDayAdapter.SingleDayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SingleDayViewHolder holder, final int position) {

        if (selectedPositions.contains(position)) {
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

//        holder.timeTv.setText(SingleDayFragment.TIME_FORMAT.format(dates.get(position)));
        holder.timeTv.setText(SingleDayFragment.TIME_FORMAT.format(eventList.get(position).getDateEvent()));
        holder.titleTv.setText("");
        holder.emailTv.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                int nextPosition = currentPosition + 1;
                int previousPosition = currentPosition - 1;

                if (selectedPositions.contains(currentPosition)) {
                    selectedPositions.remove(currentPosition);
                    notifyDataSetChanged();
                } else if (selectedPositions.contains(nextPosition) || selectedPositions.contains(previousPosition)) {
                    selectedPositions.add(currentPosition);
                    notifyDataSetChanged();
                } else {
                    selectedPositions.clear();
                    selectedPositions.add(currentPosition);
                    notifyDataSetChanged();
                }

                if (onSingleItemClickListener != null) {
                    onSingleItemClickListener.onSingleItemClick(holder.getAdapterPosition(), selectedPositions);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
//        return dates.size();
    }

    public void setOnSingleItemClickListener(OnSingleItemClickListener onSingleItemClickListener) {
        this.onSingleItemClickListener = onSingleItemClickListener;
    }

    public class SingleDayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.email_tv)
        TextView emailTv;

        public SingleDayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSingleItemClickListener {
        void onSingleItemClick(int position, Set<Integer> selectedPositions);
    }

}