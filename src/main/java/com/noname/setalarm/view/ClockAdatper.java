package com.noname.setalarm.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.setalarm.R;
import com.noname.setalarm.databinding.RecyclerClockItemBinding;
import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.viewmodel.AlarmRoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClockAdatper extends RecyclerView.Adapter{

    private ArrayList<ClockModel> models = new ArrayList<>();
    private Context context;
    private AlarmRoomViewModel viewModel;

    public ClockAdatper(ArrayList<ClockModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_clock_item, viewGroup, false);
        return new ClcokViewHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof ClcokViewHodler){
            ((ClcokViewHodler) viewHolder).getRecyclerClockItemBinding().setObserver(new ClockObserver(models.get(i)));
            ((ClcokViewHodler) viewHolder).getRecyclerClockItemBinding().executePendingBindings();

            ((ClcokViewHodler) viewHolder).getRecyclerClockItemBinding().addClock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return models.size() > 0 ? models.size() : 0;
    }

    private class ClcokViewHodler extends RecyclerView.ViewHolder{

        private RecyclerClockItemBinding recyclerClockItemBinding;

        public ClcokViewHodler(View itemView) {
            super(itemView);
            recyclerClockItemBinding = DataBindingUtil.bind(itemView);
        }

        public RecyclerClockItemBinding getRecyclerClockItemBinding() {
            return recyclerClockItemBinding;
        }
    }
}
