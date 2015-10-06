package com.example.samuel.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samuel.schedule.DataBaseTool.DAO;

import java.util.HashMap;
import java.util.Map;


public class DetailSchedule extends Activity {
    private EditText editTitle;
    private EditText editContent;
    private Button buttonNew;
    private Button buttonDel;
    private String oldtag;
    private String oldtitle;
    private String oldcontent;
    private  int intentflag;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:             //插入成功
                    Toast.makeText(DetailSchedule.this, "新建成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:              //插入失败
                    Toast.makeText(DetailSchedule.this,"新建失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:             //修改成功
                    Toast.makeText(DetailSchedule.this,"修改成功",Toast.LENGTH_SHORT).show();
                    break;
                case 4:             //修改失败
                    Toast.makeText(DetailSchedule.this,"修改失败",Toast.LENGTH_SHORT).show();
                    break;
                case 5:             //删除成功
                    Toast.makeText(DetailSchedule.this,"删除成功",Toast.LENGTH_SHORT).show();
                    break;
                case 6:             //删除失败
                    Toast.makeText(DetailSchedule.this,"删除失败",Toast.LENGTH_SHORT).show();
                    break;
                case 10:            //传递搜索结果出来
                    Map<String,String> map = (HashMap)msg.obj;
                    oldtag = map.get("tag");
                    oldtitle = map.get("title");
                    oldcontent = map.get("content");
                    editTitle.setText(oldcontent);
                    editContent.setText(oldcontent);
                    break;
                case 11:
                    Toast.makeText(DetailSchedule.this,"没有搜索到内容",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        final Intent intent = getIntent();
        intentflag  =intent.getFlags();

        editTitle = (EditText)this.findViewById(R.id.edit_title);
        editContent = (EditText)this.findViewById(R.id.edit_content);
        buttonNew = (Button)this.findViewById(R.id.save);
        buttonDel = (Button)this.findViewById(R.id.delete);
        if(intentflag ==1){
            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new MyThread(1)).start();
                    finish();
                }
            });
        }else{
            new Thread(new MyThread(4,intentflag)).start();
            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new MyThread(2)).start();
                    finish();
                }
            });
        }
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DetailSchedule.this);
                builder.setTitle("提示");
                builder.setMessage("真的要删除吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new MyThread(3,intentflag)).start();
                        finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();

            }
        });

    }

//    View.OnClickListener OnClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.save:             //
//                    new Thread(new MyThread(1));
//                    break;
//                case R.id.delete:
//                    break;
//            }
//        }
//    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_schedule, menu);
        return true;
    }
    private class MyThread implements Runnable{
        private int flag;
        private int id;
        private Message message = Message.obtain();
        DAO db = new DAO(getBaseContext());

        public MyThread(int flag,int id) {
            this.flag = flag;
            this.id = id-1;

        }
        public MyThread(int flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            switch (flag){
                case 1:             //新建标记
                    ContentValues values = new ContentValues();
                    values.put("token","abc123");
                    values.put("title",editTitle.getText().toString());
                    values.put("content",editContent.getText().toString());
                    values.put("time",System.currentTimeMillis());
                    values.put("tag","默认");
                    values.put("status", "1");
                    if(db.insert(values)){
                        message.what = 1;
                    }else{
                        message.what = 2 ;
                    }
                    break;
                case 2:              //保存标记
                    ContentValues valuessave = new ContentValues();
                    valuessave.put("title",editTitle.getText().toString());
                    valuessave.put("content",editContent.getText().toString());
                    if(db.edit(valuessave,new String[]{String.valueOf(id)})){
                        message.what=3;
                    }else {
                        message.what = 4;
                    }
                    break;
                case 3:              //删除标记
                    ContentValues valuesdel = new ContentValues();
                    valuesdel.put("status",2);
                    if(db.edit(valuesdel,new String[]{String.valueOf(id)})){
                        message.what=5;
                    }else message.what=6;
                    break;
                case 4:                 //查询
                    Map<String,String> map = new HashMap();
                   map = db.select("id=?",new String[]{String.valueOf(id)});
                    if(map !=null){
                        message.obj = map;
                        message.what = 10;
                    }
                    else message.what = 11;
                    break;



            }
            handler.sendMessage(message);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            Log.d("oldtitle",oldtitle+"\n"+editTitle.getText().toString());
            if(editTitle.getText().toString().equals(oldtitle) && editContent.getText().toString().equals(oldcontent)){


                return super.onKeyDown(keyCode,event);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailSchedule.this);
                builder.setTitle("提示");
                builder.setMessage("是否保存？");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (oldcontent == null || oldcontent.length()<=0) {   //因为oldcontent没有赋值，不能和“”比较
                            new Thread(new MyThread(1)).start();
                            finish();
                        }else{
                        new Thread(new MyThread(2, intentflag)).start();
                        finish();}
                    }

                });
                builder.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create().show();
            }

        }
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
