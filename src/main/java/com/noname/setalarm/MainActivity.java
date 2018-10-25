package com.noname.setalarm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.noname.setalarm.databinding.ActivityMainBinding;
import com.noname.setalarm.model.AlarmModel;
import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;
import com.noname.setalarm.view.AddAlarmActivity;
import com.noname.setalarm.view.AlarmAdpater;
import com.noname.setalarm.viewmodel.AlarmRecycleViewModel;
import com.noname.setalarm.viewmodel.AlarmRoomViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding activityMainBinding;
    private AlarmRoomViewModel alarmRoomViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        alarmRoomViewModel = ViewModelProviders.of(this).get(AlarmRoomViewModel.class);
        activityMainBinding.setLifecycleOwner(this);

        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, view, "toAddAlarm");
                startActivity(intent, options.toBundle());
            }
        });

        AlarmAdpater alarmAdpater = new AlarmAdpater(this, alarmRoomViewModel);
        activityMainBinding.recycler.setAdapter(alarmAdpater);
        activityMainBinding.recycler.setLayoutManager(new LinearLayoutManager(this));

        alarmRoomViewModel.getListLiveData().observe(this, new Observer<List<AlarmRoom>>() {
            @Override
            public void onChanged(@Nullable List<AlarmRoom> alarmRooms) {
                for (AlarmRoom alarmRoom : alarmRooms) {
                    Collections.sort(alarmRoom.getTimeList() , new Comparator<ClockModel>() {

                        @Override
                        public int compare(ClockModel oldData, ClockModel newData) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return Integer.compare(newData.getId(), oldData.getId());
                        }
                    });
                }
                alarmAdpater.submitList(alarmRooms);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
