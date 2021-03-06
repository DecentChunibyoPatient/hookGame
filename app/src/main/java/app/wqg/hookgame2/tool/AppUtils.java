package app.wqg.hookgame2.tool;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

/**
 * 作者：lzy on 2016/10/9 11:10
 * 邮箱：1556342503@qq.com
 */

public class AppUtils {

    /**
     * 方法描述：判断某一应用是否正在运行
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法描述：判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
    public static String getCurrentAppPackage(Context context) {
        String result = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT < 21) {
            // 如果没有就用老版本
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
            if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
                result = runningTaskInfos.get(0).topActivity.getPackageName();
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> runningApp = manager.getRunningAppProcesses();
            if (runningApp != null && runningApp.size() > 0) {
                result = runningApp.get(0).processName;
            }
        }
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }
    /*
     * 启动一个app的Service
     */
    static public void startService(Context context, String Action){
        try{
            Intent intent=new Intent();
            intent.setAction(Action) ;
            intent.putExtra("PackageName",context.getPackageName());
            context.startService(intent);
        }catch(Exception e){
            Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
        }
    }
    /*
     * 关闭一个app的Service
     */
    static public void stopService(Context context, String Action){
        try{
            Intent intent=new Intent();
            intent.setAction(Action) ;
            intent.putExtra("PackageName",context.getPackageName());
            context.stopService(intent);
        }catch(Exception e){
            Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
        }
    }
}