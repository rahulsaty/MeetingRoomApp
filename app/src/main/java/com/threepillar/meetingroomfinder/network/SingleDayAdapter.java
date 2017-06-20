package com.threepillar.meetingroomfinder.network;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.fragment.SingleDayFragment;
import com.threepillar.meetingroomfinder.model.EventInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleDayAdapter extends RecyclerView.Adapter<SingleDayAdapter.SingleDayViewHolder> {

    private ArrayList<EventInfo> eventList;
    //    List<Date> dates = null;
    private Set<Integer> selectedPositions = new HashSet<>();
    private Context context;

    private OnSingleItemClickListener onSingleItemClickListener;

    public SingleDayAdapter(/*List<Date> dates*/Context context) {
//        this.dates = dates;
        // this.eventList = eventList;
        this.context = context;
    }

    public void addEventsList(ArrayList<EventInfo> eventList) {

        if (this.eventList == null) {
            this.eventList = new ArrayList<>();
        }
        this.eventList.addAll(eventList);
        notifyDataSetChanged();
    }

    public void addEvent(EventInfo eventInfo) {

        if (eventList == null) {
            this.eventList = new ArrayList<>();
        }
        this.eventList.add(eventInfo);
        notifyDataSetChanged();
    }

    public void clearEvents() {
        if (eventList != null) {
            eventList.clear();
        }
    }

    public void updateEvent(EventInfo eventInfo, int index) {

        if (eventList != null && eventList.size() > 0) {
            EventInfo listEvent = eventList.get(index);
            listEvent.setEmailEvent(eventInfo.getEmailEvent());
            listEvent.setTitleEvent(eventInfo.getTitleEvent());
            notifyDataSetChanged();
        }
    }

    public void updateMultipleEvents(EventInfo eventInfo) {

        for (Integer selectedPosition : selectedPositions) {
            updateEvent(eventInfo, selectedPosition);
        }
    }


    @Override
    public SingleDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unmarked_rooms_single_row, parent, false);
        return new SingleDayAdapter.SingleDayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SingleDayViewHolder holder, final int position) {

        if (selectedPositions.contains(position)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_ff4200));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        EventInfo eventInfo = eventList.get(position);

//        holder.timeTv.setText(SingleDayFragment.TIME_FORMAT.format(dates.get(position)));
        holder.timeTv.setText(SingleDayFragment.TIME_FORMAT.format(eventInfo.getDateEvent()));
        holder.titleTv.setText(eventInfo.getTitleEvent());
        holder.emailTv.setText(eventInfo.getEmailEvent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.titleTv.getText().toString().equals("") && holder.emailTv.getText().toString().equals("")) {
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
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.room_already_booked), Toast.LENGTH_SHORT).show();
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_a5a6a8));
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
        return eventList == null ? 0 : eventList.size();
//        return dates.size();
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

    public void setOnSingleItemClickListener(OnSingleItemClickListener onSingleItemClickListener) {
        this.onSingleItemClickListener = onSingleItemClickListener;
    }

}