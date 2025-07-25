package com.iqooz5.ying;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.util.List;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity { 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
	}
	
	public void z5fuelsummary(View view) {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName("com.vivo.fuelsummary", "com.vivo.fuelsummary.FuelSummary");
		intent.setComponent(componentName);
		startActivity(intent);
		//Button button1 = findViewById(R.id.z5fuelsummary);

	}
	
	public void z5phonesetsms(View view) {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName("com.android.phone", "com.android.phone.settings.RadioInfo");
		intent.setComponent(componentName);
		startActivity(intent);
		//Button button1 = findViewById(R.id.z5phonesetsms);

	}
	
	public void z5engineermode(View view) {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName("com.iqoo.engineermode", "com.iqoo.engineermode.EngineerMode");
		intent.setComponent(componentName);
		startActivity(intent);
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run() {
					try {
						EngineerVerifyTest();
					} catch (ClassNotFoundException e) {}
				}
			}, 1000);
		//Button button2 = findViewById(R.id.z5engineermode);
	}
	public void z5acgo(View view) {
		Toast.makeText(getApplication(), "正在跳转...", Toast.LENGTH_SHORT).show();
        try {
            startActivity(new Intent(this, Class.forName("com.iqooz5.ying.MainActivity2")));
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
	}
	public void z5exit(View view) {
		System.exit(1);
		//Button button3 = findViewById(R.id.z5exit);
	}
	public void EngineerVerifyTest() throws ClassNotFoundException {

        Intent intent = new Intent(this, Class.forName("com.iqoo.engineermode.verifytest.EngineerVerifyTest"));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
        startActivity(intent);

    }
} 
