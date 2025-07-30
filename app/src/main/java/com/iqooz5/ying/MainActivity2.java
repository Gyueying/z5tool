package com.iqooz5.ying;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity2 extends Activity {

	private int yingbox = 0;
	
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
		setContentView(R.layout.activity_main2);
        packname_edit = findViewById(R.id.packname_edit);
		classname_edit = findViewById(R.id.classname_edit);
		
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
							batteryReceiver.onReceive(MainActivity2.this, batteryIntent);
						}
						updateHandler.sendEmptyMessageDelayed(1, 1000);
					}
					return true;
				}
			});
		//
		
    }

	//变量
	EditText packname_edit = null;

	EditText classname_edit = null;

	public String packname;

	public String classname;
	
	String phoneset = "com.android.phone";
	
	String phoneset_class = "com.android.phone.settings.RadioInfo";
	
	String engineermode = "com.iqoo.engineermode";
	
	String engineermode_class = "com.iqoo.engineermode.EngineerMode";
	
	String fuelsummary = "com.vivo.fuelsummary";
	
	String fuelsummary_class = "com.vivo.fuelsummary.FuelSummary";

	public void iiii(View view) {
		packname = packname_edit.getText().toString();
		classname = classname_edit.getText().toString();
		if (packname_edit == null) {
			Toast.makeText(getApplication(), "请输入包名和活动类", Toast.LENGTH_SHORT).show();
		} else {
			sa();}
	}
	
	//手机移动网络信息
	public void phoneset () {
		packname_edit.setText(phoneset);
		classname_edit.setText(phoneset_class);
		
	}
	//工厂测试
	public void engineermode () {
		packname_edit.setText(engineermode);
		classname_edit.setText(engineermode_class);

	}
	//满血充电
	public void fuelsummary () {
		packname_edit.setText(fuelsummary);
		classname_edit.setText(fuelsummary_class);

	}
	//清空填充
	public void killedit (View v) {
		Toast.makeText(getApplication(), "已清空", Toast.LENGTH_SHORT).show();
		packname_edit.setText(null);
		classname_edit.setText(null);
	}
	
	public final void sa() {
		Intent intent = new Intent();
		ComponentName componentName=new ComponentName(packname, classname);
		intent.setComponent(componentName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
	}

	private void plintPkgAndCls(List<ResolveInfo> resolveInfos) {
        PackageManager packageManager = getPackageManager();

        Log.i("pkg", "####################start######################");
        for (int i = 0; i < resolveInfos.size(); i++) {
            String pkg = resolveInfos.get(i).activityInfo.packageName;
            String cls = resolveInfos.get(i).activityInfo.name;
            String title = null;

            try {
                ApplicationInfo applicationInfo = packageManager.getPackageInfo(pkg, i).applicationInfo;
                title = applicationInfo.loadLabel(packageManager).toString();
            } catch (Exception e) {

            }


            Log.i("pkg", title + "：" + pkg + "/" + cls);
        }
        Log.i("pkg", "#####################end#######################");
	}

	public void viqoo(final View view) {
        CharSequence[] items = {"工厂测试","满血充电","手机移动网络信息"};
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("请选择一个填充方案")
            .setCancelable(false)
            .setSingleChoiceItems(items, yingbox, new DialogInterface.OnClickListener() {



                @Override
                public void onClick(DialogInterface dia, int which) {
                    yingbox = which;
                }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {

                    if (yingbox == 0) {//工厂
                        Toast.makeText(getApplication(), "工厂测试填充", Toast.LENGTH_SHORT).show();
                        engineermode();
                    }
                    if (yingbox == 1) {//满血
                        Toast.makeText(getApplication(), "满月充电填充", Toast.LENGTH_SHORT).show();
                        fuelsummary();
                    }
					if (yingbox == 2) {//手机信息
                        Toast.makeText(getApplication(), "手机移动网络信息填充", Toast.LENGTH_SHORT).show();
                        phoneset();
                    }

                }
            })

            .setNeutralButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {
                    dia.cancel();
                }
            })

            .create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#ff66ccff"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff66ccff"));
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

}
