package com.handsomemark.example;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.handsomemark.calendarlib.ForLog;
import com.handsomemark.calendarlib.interf.OnSelectDateListener;
import com.handsomemark.calendarlib.model.CalendarDate;
import com.handsomemark.calendarlib.view.VVPAdapter;
import com.handsomemark.calendarlib.view.VerticalViewPager;

public class MainActivity extends AppCompatActivity {
    VerticalViewPager vvp;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        vvp=findViewById(R.id.vvp);
        vvp.setAdapter(new VVPAdapter(this,new OnSelectDateListener(){

            @Override
            public void onSelectDate(CalendarDate date) {
                ForLog.e("变动了");
                VVPAdapter vvpAdapter= (VVPAdapter) vvp.getAdapter();
                assert vvpAdapter != null;
                vvpAdapter.onSelectedChange(date);
                vvpAdapter.notifyDataSetChanged();
            }
        }));
    }


    /*
     * 如果你想以周模式启动你的日历，请在onResume是调用
     * Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
     * calendarAdapter.switchToWeek(monthPager.getRowIndex());
     * */
    @Override
    protected void onResume() {
        super.onResume();
    }

}

