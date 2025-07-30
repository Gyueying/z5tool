package com.iqooz5.ying;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;


public class MainActivity extends Activity { 
	//充电监测
	private static final String TAG = "PowerMonitor";
    private TextView powerTextView;
    private BatteryReceiver batteryReceiver;
    private IntentFilter intentFilter;
    private Handler updateHandler;
    private String lastDisplayText = "";
	//
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		//充电监测
		powerTextView = findViewById(R.id.powerTextView);
        powerTextView.setText(getString(R.string.initializing));

        batteryReceiver = new BatteryReceiver();
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        updateHandler = new Handler(new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					if (msg.what == 1) {
						Intent batteryIntent = registerReceiver(null, intentFilter);
						if (batteryIntent != null) {
							batteryReceiver.onReceive(MainActivity.this, batteryIntent);
						}
						updateHandler.sendEmptyMessageDelayed(1, 1000);
					}
					return true;
				}
			});
			//
		
	}
	// 满血
	public void z5fuelsummary(View view) {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName("com.vivo.fuelsummary", "com.vivo.fuelsummary.FuelSummary");
		intent.setComponent(componentName);
		startActivity(intent);
		//Button button1 = findViewById(R.id.z5fuelsummary);

	}
	// 手机信息
	public void z5phonesetsms(View view) {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName("com.android.phone", "com.android.phone.settings.RadioInfo");
		intent.setComponent(componentName);
		startActivity(intent);
		//Button button1 = findViewById(R.id.z5phonesetsms);

	}
	// 工厂
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
	// 跳转
	public void z5acgo(View view) {
		Toast.makeText(getApplication(), "正在跳转...", Toast.LENGTH_SHORT).show();
        try {
            startActivity(new Intent(this, Class.forName("com.iqooz5.ying.MainActivity2")));
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
	}
	// 作者信息
	public void developer_ying_show(View view) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("coolmarket://u/21838623"));
            startActivity(intent);
            finish();
        } catch (Exception unused) {
            Toast.makeText(this, "打开酷安失败，请检查是否已安装", 0).show();
        }
    }
	// 退出
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
	
	//充电监测
	@Override
    protected void onResume() {
        super.onResume();
        registerReceiver(batteryReceiver, intentFilter);
        updateHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
        updateHandler.removeMessages(1);
    }

    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                return;
            }
			

            // 获取电量
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPercent = -1;
            if (level != -1 && scale != -1) {
                batteryPercent = (level * 100) / scale;
            }

            // 电压获取逻辑（单位：mV，直接使用原始值，无需转换）
            int voltageMicro = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);

            // 电流获取逻辑（根据充电状态修正符号）
            double currentMa = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
                if (batteryManager != null) {
                    int currentMicro = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    currentMa = currentMicro / 1000.0;

                    // 充电状态检测
                    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
                    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
						|| status == BatteryManager.BATTERY_STATUS_FULL;

                    // 强制修正符号：充电时为正，耗电时为负
                    if (isCharging) {
                        currentMa = Math.abs(currentMa);
                    } else {
                        currentMa = -Math.abs(currentMa);
                    }
                }
            }

            // 充电状态文本
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
				|| status == BatteryManager.BATTERY_STATUS_FULL;
            String statusText = isCharging ? "充电中" : "未充电";

            // 功率计算（跟随电流符号）
            double watts = -1;
            if (voltageMicro != -1 && currentMa != 0) {
                // 电压单位转换为V（mV -> V）参与计算
                watts = (voltageMicro / 1000.0) * (currentMa / 1000.0);
            }

            // 构建显示文本（电压显示为整数mV，无括号）
            String powerText;
            if (voltageMicro == -1) {
                powerText = String.format("状态: %s\n电量: %d%%\n电压: 获取失败\n电流: %.1f mA\n功率: 无法计算",
										  statusText, batteryPercent, currentMa);
            } else if (currentMa == 0) {
                powerText = String.format("状态: %s\n电量: %d%%\n电压: %d mV\n电流: 0 mA\n功率: 0 W",
										  statusText, batteryPercent, voltageMicro);
            } else {
                powerText = String.format("状态: %s\n电量: %d%%\n电压: %d mV\n电流: %.1f mA\n功率: %.2f W",
										  statusText, batteryPercent, voltageMicro, currentMa, watts);
            }

            if (!powerText.equals(lastDisplayText)) {
                lastDisplayText = powerText;
                powerTextView.setText(powerText);
            }
			
        }
    }
	//
} 
