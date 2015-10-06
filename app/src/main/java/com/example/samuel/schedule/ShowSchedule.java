package com.example.samuel.schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samuel.schedule.DataBaseTool.DAO;

import java.util.ArrayList;


public class ShowSchedule extends Activity {
    private ListView schedule_list;
    private Button newSchedule;
    private ArrayAdapter<ScheduleThum> adapter;
    private ArrayList<ScheduleThum> data;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            data = (ArrayList<ScheduleThum>)msg.obj;
            adapter = new ListAdapter(ShowSchedule.this,R.layout.forlistadapter,data);
            schedule_list.setAdapter(adapter);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);
        newSchedule = (Button)this.findViewById(R.id.newschedule);
        newSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowSchedule.this,DetailSchedule.class);
                intent.addFlags(1);       //传flag，如果是1，表示新建。其他list元素传递之前先加1，那边接收后判断大于1，就减去1之后再去查询数据库
                startActivity(intent);
            }
        });
        schedule_list = (ListView)this.findViewById(R.id.schedule_list);
        schedule_list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.list_header, null, false));

        new Thread(new MyThread()).start();
        schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    return;
                }
                Intent intent = new Intent(ShowSchedule.this,DetailSchedule.class);
                intent.setFlags(data.get(i-1).getId()+1);  //这里有问题
                startActivity(intent);
//                Toast.makeText(getApplication(),data.get(i-1).toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }
    private class MyThread implements Runnable{

        @Override
        public void run() {
            DAO db=null ;
            ArrayList<ScheduleThum> data;
            try{
                db = new DAO(getBaseContext());
               data = db.thumArrayList("status=?",new String[]{"1"});
                Message message = Message.obtain();
                message.obj = data;
                handler.sendMessage(message);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new MyThread()).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_schedule, menu);
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
