package com.iqooz5.ying;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class MainActivity2 extends Activity {

	private int yingbox = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
        packname_edit = findViewById(R.id.packname_edit);
		classname_edit = findViewById(R.id.classname_edit);
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

}
