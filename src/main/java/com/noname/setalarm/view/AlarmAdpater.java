package com.noname.setalarm.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.noname.setalarm.AlarmLogic;
import com.noname.setalarm.R;
import com.noname.setalarm.databinding.RecyclerItemAlarmBinding;
import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;
import com.noname.setalarm.viewmodel.AlarmRecycleViewModel;
import com.noname.setalarm.viewmodel.AlarmRoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdpater extends ListAdapter<AlarmRoom, AlarmAdpater.AlarmViewHodler> {

    private static String TAG = AlarmAdpater.class.getSimpleName();
    private AlarmLogic alarmLogic;
    private AlarmRoomViewModel alarmRoomViewModel;

    public AlarmAdpater(Context context, AlarmRoomViewModel alarmRoomViewModel) {
        super(DIFF_CALLBACK);
        alarmLogic = new AlarmLogic(context);
        this.alarmRoomViewModel = alarmRoomViewModel;
    }

    @NonNull
    @Override
    public AlarmViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_alarm, viewGroup, false);
        return new AlarmViewHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHodler alarmViewHodler, int i) {

        alarmViewHodler.getRecyclerItemAlarmBinding().setObserver(new AlarmObserver(getItem(i)));

        alarmViewHodler.getRecyclerItemAlarmBinding().onswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    for (ClockModel clockModel : getItem(i).getTimeList()){
                        alarmLogic.setToCalendar(clockModel.getHour(), clockModel.getMinute(),
                                clockModel.isAm_pm());
                        alarmLogic.newAlarm(alarmLogic.makeID(clockModel.getHour(), clockModel.getMinute()),
                                alarmLogic.getCalendarTime());
                    }

                }else {
                    //알람은 시스템에서 각각 id를 가진다.
                    //알람은 intent로 취소하며 intent는 id 로 만들어진다.
                    //switch를 누른 리스트 위치의 custommodel list의 알람을 전부끈다.
                    for (ClockModel clockModel : getItem(i).getTimeList()) {
                        alarmLogic.unregisterAlarm(alarmLogic.makeID(clockModel.getHour(), clockModel.getMinute()));
                    }
                }
            }
        });

        alarmViewHodler.getRecyclerItemAlarmBinding().cancleBtn.setOnClickListener(view -> {
            if (alarmViewHodler.getRecyclerItemAlarmBinding().onswitch.isChecked()){
                for (ClockModel clockModel : getItem(i).getTimeList()) {
                    alarmLogic.unregisterAlarm(alarmLogic.makeID(clockModel.getHour(), clockModel.getMinute()));
                }
            }

            alarmRoomViewModel.delete(getItem(i));
        });
    }

    public static final DiffUtil.ItemCallback<AlarmRoom> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AlarmRoom>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull AlarmRoom oldModel, @NonNull AlarmRoom newModel) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return (oldModel.getAlarmId() == newModel.getAlarmId());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull AlarmRoom oldModel, @NonNull AlarmRoom newModel) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldModel.equals(newModel);
                }
            };

    static class AlarmViewHodler extends RecyclerView.ViewHolder{

        private RecyclerItemAlarmBinding recyclerItemAlarmBinding;

        public AlarmViewHodler(View itemView) {
            super(itemView);
            recyclerItemAlarmBinding = DataBindingUtil.bind(itemView);
            recyclerItemAlarmBinding.executePendingBindings();
            recyclerItemAlarmBinding.onswitch.setChecked(true);


        }

        public RecyclerItemAlarmBinding getRecyclerItemAlarmBinding() {
            return recyclerItemAlarmBinding;
        }
    }
}
