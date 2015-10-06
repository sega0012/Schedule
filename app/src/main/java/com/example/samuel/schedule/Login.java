package com.example.samuel.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends Activity {
    private Button blogin;
    private Button bsign;
    private EditText eusername;
    private EditText epasswd;
    private TextView tfindpasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eusername = (EditText) this.findViewById(R.id.username);
        epasswd = (EditText) this.findViewById(R.id.passwd);
        tfindpasswd = (TextView) this.findViewById(R.id.findpasswd);
        blogin = (Button) this.findViewById(R.id.login);
        bsign = (Button) this.findViewById(R.id.sign);

        blogin.setOnClickListener(new MyOnclick());


    }
    private class MyOnclick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login:
                    Intent intent = new Intent(Login.this,ShowSchedule.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.sign:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("关于");
            builder.setMessage("本产品是一款日程管理APP。用于制定，跟踪日程，并带有提醒功能。");
            builder.setPositiveButton("确定",null);
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
