package com.noname.setalarm.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.setalarm.R;
import com.noname.setalarm.databinding.RecyclerClockItemBinding;
import com.noname.setalarm.model.ClockModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClockAdapterDiff extends ListAdapter<ClockModel, ClockAdapterDiff.ClockViewHodler> {

    protected ClockAdapterDiff() {
        super(DIFF_CALLBACK);

    }

    @NonNull
    @Override
    public ClockViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_clock_item, viewGroup,false);
        return new ClockViewHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockViewHodler viewHodler, int i) {
        viewHodler.getRecyclerClockItemBinding().setObserver(new ClockObserver(getItem(i)));
    }

    public static final DiffUtil.ItemCallback<ClockModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ClockModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull ClockModel oldModel, @NonNull ClockModel newModel) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return (oldModel.getId() == newModel.getId());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull ClockModel oldModel, @NonNull ClockModel newModel) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldModel.equals(newModel);
                }
            };

    static class ClockViewHodler extends RecyclerView.ViewHolder{

        private RecyclerClockItemBinding recyclerClockItemBinding;

        public ClockViewHodler(View itemView) {
            super(itemView);
            recyclerClockItemBinding = DataBindingUtil.bind(itemView);
            recyclerClockItemBinding.executePendingBindings();
        }

        public RecyclerClockItemBinding getRecyclerClockItemBinding() {
            return recyclerClockItemBinding;
        }
    }
}
