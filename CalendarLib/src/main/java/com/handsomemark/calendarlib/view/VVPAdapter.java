package com.handsomemark.calendarlib.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.handsomemark.calendarlib.Const;
import com.handsomemark.calendarlib.ForLog;
import com.handsomemark.calendarlib.R;
import com.handsomemark.calendarlib.Utils;
import com.handsomemark.calendarlib.interf.OnSelectDateListener;
import com.handsomemark.calendarlib.model.CalendarDate;

import java.util.ArrayList;
import java.util.List;

import static com.handsomemark.calendarlib.Utils.getDay;
import static com.handsomemark.calendarlib.Utils.getMonth;
import static com.handsomemark.calendarlib.Utils.getNextCalendarDate;
import static com.handsomemark.calendarlib.Utils.getNextMonthCalendarDate;
import static com.handsomemark.calendarlib.Utils.getYear;

public class VVPAdapter  extends PagerAdapter {
    private int year;
    private int month;
    //上下文
    private Context mContext;
    private CalendarDate today=new CalendarDate();
    private OnSelectDateListener onSelectDateListener;
    private CalendarDate selectedCalendarDate=new CalendarDate();

    /**
     * 构造函数
     * 初始化上下文和数据
     *
     * @param context
     */
    public VVPAdapter(Context context,OnSelectDateListener onSelectDateListener) {
        mContext = context;
        year=getYear();
        month=getMonth();
        today.setYear(getYear());
        today.setMonth(getMonth());
        today.setDay(getDay());
        this.onSelectDateListener=onSelectDateListener;
    }
    public void onSelectedChange(CalendarDate selectedCalendarDate){
        this.selectedCalendarDate=selectedCalendarDate;
    }
    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }


    /**
     * 返回要滑动的VIew的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 1.将当前视图添加到container中
     * 2.返回当前View
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CalendarDate selectCD=getNextMonthCalendarDate(year,month,position);
        selectCD.setDay(1);
        int selectYear=selectCD.year;
        int selectMonth=selectCD.month;

        List<CalendarDate>  lists=new ArrayList<>();
        int lastMonthDays = Utils.getMonthDays(selectYear, selectMonth - 1);    // 上个月的天数
        int currentMonthDays = Utils.getMonthDays(selectYear, selectMonth);    // 当前月的天数
        // 得到当前月第一天在其周的位置
        int firstDayPosition = Utils.getFirstDayWeekPosition(
                selectYear,
                selectMonth,
                null
                );
        // 得到当前月最后一天天在其周的位置
        int lastDayPosition = Utils.getFirstDayWeekPosition(
                selectYear,
                selectMonth+1,
                null
        )-1;
        //31  30  4
        ForLog.e("日期",lastMonthDays,currentMonthDays,firstDayPosition,lastDayPosition);
        for(int i=firstDayPosition;i>0;i--){
            // 填充上个月在这个viewpage 的数据
            CalendarDate cd2=getNextCalendarDate(selectCD,-i);
            lists.add(cd2);
        }

        for(int i=0;i<currentMonthDays;i++){
            CalendarDate cd2=getNextCalendarDate(selectCD,i);
            lists.add(cd2);
        }
        for(int i=0;i<7-lastDayPosition;i++){
            CalendarDate cd2=getNextCalendarDate(selectCD,currentMonthDays+i);
            lists.add(cd2);
        }



//        ForLog.e("这一天",cd);
//        ForLog.e("下一个天数",getNextCalendarDate(cd,1));
//        ForLog.e("上个月天数",getNextCalendarDate(cd,-1));


        View view = View.inflate(mContext, R.layout.vvp, null);
        LinearLayout ll_all=view.findViewById(R.id.ll_all);
        ForLog.e("需要渲染的所有",lists);

        for (int row = 0; row < Const.TOTAL_ROW; row++) {
            //实例化一个LinearLayout
            LinearLayout linearLayout = new LinearLayout(mContext);
            //0:水平居中、1：垂直居中
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //设置LinearLayout属性(宽和高)
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置边距
//            layoutParams.setMargins(54, 0, 84, 0);
            //将以上的属性赋给LinearLayout
            linearLayout.setLayoutParams(layoutParams);

            for (int col = 0; col < Const.TOTAL_COL; col++) {
                ForLog.e("下标",row,col,lists.size());
                final CalendarDate son=lists.get(row*7+col);
                //实例化一个TextView
                TextView tv = new TextView(mContext);
                //设置宽高以及权重
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                //设置textview垂直居中
//                tvParams.gravity = Gravity.CENTER;
                tv.setLayoutParams(tvParams);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                if(son.getDay()==1){
                    tv.setText(son.getMonth()+"月");
                }else{
                    tv.setText(son.getDay()+"");
                }
//                tv.setText(son+"");
                if(today.big(son)>0){
                    //过去的日期样式
                    tv.setTextColor(Color.parseColor("#999999"));

                }else {
                    if(today.big(son)<0){
                        //将来日期的样式
                        tv.setTextColor(Color.parseColor("#000000"));
                    }else{
                        //今天的样式
                        tv.setBackgroundColor(Color.parseColor("#448ced"));
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setText("今天");
                    }
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectDateListener.onSelectDate(son);
                        }
                    });
                }
                if(son.big(selectedCalendarDate)==0){
                    tv.setBackgroundColor(Color.parseColor("#448ced"));
                }

                linearLayout.addView(tv);
            }
            ll_all.addView(linearLayout);
        }

//        tv.setText(mData.get(position));
//        Random random = new Random();
//        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
//        tv.setBackgroundColor(ranColor);
        container.addView(view);
        return view;
    }

    /**
     * 从当前container中删除指定位置（position）的View
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        // 把 Object 强转为 View，然后将 view 从 ViewGroup 中清除
        container.removeView((View) object);
    }

    /**
     * 确定页视图是否与特定键对象关联
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
