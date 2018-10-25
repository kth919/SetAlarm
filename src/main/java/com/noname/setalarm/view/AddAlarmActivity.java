package com.noname.setalarm.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TimePicker;


import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.moldedbits.dialpicker.DialView;
import com.noname.setalarm.AlarmLogic;
import com.noname.setalarm.R;
import com.noname.setalarm.databinding.ActivityAddalarmBinding;
import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;
import com.noname.setalarm.viewmodel.ClockViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class AddAlarmActivity extends AppCompatActivity {

    private static String TAG = AddAlarmActivity.class.getSimpleName();
    private ActivityAddalarmBinding activityAddalarmBinding;

    private AlarmLogic alarmLogic;
    private boolean multi = false;
    private static String uid = java.util.UUID.randomUUID().toString();
    private ClockViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddalarmBinding = DataBindingUtil.setContentView(this, R.layout.activity_addalarm);
        activityAddalarmBinding.setLifecycleOwner(this);
        activityAddalarmBinding.setProvider(new ImageProvider(R.drawable.hills, R.drawable.moon));

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 12){
            activityAddalarmBinding.pm.setVisibility(View.INVISIBLE);
        }else {
            activityAddalarmBinding.pm.setVisibility(View.VISIBLE);
        }

        alarmLogic = new AlarmLogic(this);
//        activityAddalarmBinding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(ClockViewModel.class);

        ArrayList<ClockModel> models = new ArrayList<>();
        alarmLogic.getCurrentHour();
        alarmLogic.getCurrentMinute();

        Log.d(TAG, "HOUR + " + alarmLogic.getCurrentHour() + "MINUTE : " + alarmLogic.getCurrentMinute());
        ClockModel clockModel = new ClockModel(alarmLogic.makeID(alarmLogic.getCurrentHour(), alarmLogic.getCurrentMinute(), alarmLogic.getCurrentSecond()),
                alarmLogic.getCurrentHour(), alarmLogic.getCurrentMinute(), alarmLogic.getCurrentHour()<12);
        models.add(clockModel);
        viewModel.insert(clockModel);

        ClockAdapterDiff clockAdapterDiff = new ClockAdapterDiff();
        viewModel.getListLiveData().observe(this, clockModels -> {
            
            clockAdapterDiff.submitList(clockModels);
            Log.d(TAG , "모델추가");
        });
        activityAddalarmBinding.recycler.setAdapter(clockAdapterDiff);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        activityAddalarmBinding.recycler.setLayoutManager(horizontalLayout);

        activityAddalarmBinding.timer.setIs24HourView(true);
        activityAddalarmBinding.timer.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minutes) {
                Log.d(TAG, "시" + hourOfDay + ":" + minutes + "분");

                viewModel.updateHour(hourOfDay, minutes);
                viewModel.updateMinute(hourOfDay, minutes);

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minutes);

                revealam(hourOfDay);

            }
        });

        activityAddalarmBinding.timeSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                multi = isOn;
                Log.d(TAG, "체크" + isOn);
            }
        });


        activityAddalarmBinding.confirm.setOnClickListener(v -> {

            if (multi){

                Calendar tmp = Calendar.getInstance();
                Log.d(TAG, "추가");

                ClockModel tmpClock = new ClockModel(alarmLogic.makeID(tmp.get(Calendar.HOUR_OF_DAY), tmp.get(Calendar.MINUTE), tmp.get(Calendar.SECOND)),
                        tmp.get(Calendar.HOUR_OF_DAY), tmp.get(Calendar.MINUTE), tmp.get(Calendar.HOUR_OF_DAY)<12);
                models.add(tmpClock);
                viewModel.insert(tmpClock);

            }else {
                List<ClockModel> tmpList = viewModel.getListLiveData().getValue();

                for (int i = 0; i<tmpList.size(); i++) {
                    alarmLogic.setToCalendar(tmpList.get(i).getHour(),
                            tmpList.get(i).getMinute(),
                            tmpList.get(i).getHour() >= 12);
                    alarmLogic.newAlarm(tmpList.get(i).getHour() +
                                    tmpList.get(i).getMinute(),
                            alarmLogic.getCalendarTime());
                }
                viewModel.insertAlarm(new AlarmRoom(UUID.randomUUID().toString(), tmpList));
                viewModel.deleteAll();
                super.onBackPressed();

            }

        });
    }

    private void revealam(int hour){

        //am_pm true : 오후 false: 오전
        if( (hour <= 12 && !activityAddalarmBinding.pm.isShown())){
            revealTheam();
        } else if (hour >= 12 && activityAddalarmBinding.pm.isShown()) {
            hideTheam();
        }
    }

    private Animator createRevealAnimator(boolean show, View view ) {
        final int cx = view.getWidth() ;
        final int cy = view.getHeight() ;

        final int radius = (int) Math.hypot(cx, cy);
        final Animator animator;

        if (show) {
            animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, radius);
            animator.setInterpolator(new DecelerateInterpolator());
        } else {
            animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0);
            animator.setInterpolator(new AccelerateInterpolator());
        }

        animator.setDuration(getResources().getInteger(R.integer.default_anim_duration));
        return animator;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.deleteAll();
    }

    private void revealTheam() {
        activityAddalarmBinding.pm.setVisibility(View.VISIBLE);
        Animator reveal = createRevealAnimator(true, activityAddalarmBinding.pm);
        reveal.start();
    }

    /**
     * Hide the am
     */
    private void hideTheam() {
        Animator hide = createRevealAnimator(false, activityAddalarmBinding.pm);
        hide.setStartDelay(getResources().getInteger(R.integer.default_anim_duration));
        hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                activityAddalarmBinding.pm.setVisibility(View.INVISIBLE);

            }
        });
        hide.start();
    }

    private void sortClockModelList(ArrayList<ClockModel> target){

    }

}
