package app.wqg.hookgame2;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.opengl.GLSurfaceView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import app.wqg.hookgame2.tool.LoadSO;
import app.wqg.hookgame2.tool.ReflectionUtil;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by ibm on 2018/4/20.
 */

public class hook implements IXposedHookLoadPackage {

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam)
    {
        if (lpparam.packageName.indexOf("com.yhxk.qpgame.")!=-1) {
            {
                Class<?> thiz = XposedHelpers.findClass("org.cocos2dx.lib.Cocos2dxActivity",lpparam.classLoader);
                HookActivit(thiz);
            }
            {
                Class<?> thiz = XposedHelpers.findClass("org.cocos2dx.lib.Cocos2dxRenderer",lpparam.classLoader);
                HookRenderer(thiz);
            }

        }

    }

    private void HookHelper(final Class cl) {
        for (final Method method1:cl.getMethods())
            switch (method1.getName()){
                case "init":HookMethod(cl,method1,new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                    }

                    @Override
                    protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                        try {


                        }catch (Exception e){}
                    }
                });
                    break;
            }

    }


    private void HookActivit(final Class cl) {
        for (final Method method1:cl.getMethods())
            switch (method1.getName()){
                case "init":HookMethod(cl,method1,new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        LoadSO.invokeMethod("org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge","evalString",new Class[]{String.class},new Object[]{"ScriptingCore.getInstance().enableDebugger()"});//在然后就是加载它
                    }

                    @Override
                    protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        try {
                            Activity activity=(Activity) param.thisObject;
                            Api.activity= activity;
                            Object object= ReflectionUtil.getValue(param.thisObject,"glSurfaceView");
                            if (object!=null){
                                GLSurfaceView glSurfaceView=(GLSurfaceView) object;
                                glSurfaceView.setId((int)(Math.random()*Integer.MAX_VALUE));
                                activity.getIntent().putExtra("glSurfaceView",glSurfaceView.getId());
                                activity.registerReceiver(new HookApiBroadcastReceiver(), new IntentFilter(activity.getPackageName()));//注册广播
                            }
                            Toast.makeText(Api.activity,"HOOK成功没有异常 Api.activity"+Api.activity, Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}


                    }
                });
                break;
            }

    }
    private void HookRenderer(final Class cl){
        for (final Method method1:cl.getMethods())
            switch (method1.getName()){
                case "onDrawFrame":HookMethod(cl,method1,new XC_MethodHook()
                {
                    @Override
                    protected void beforeHookedMethod( MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        try {
                            upDate((GL10)param.args[0],800,480);
                        }catch (Exception e){}
                    }
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });break;

            }

    }
    private void upDate(GL10 gl, int width, int height) throws InterruptedException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, buffer);
        // 因为上下颠倒，所以上下反转
        byte[] tmp1 = new byte[width * 4];
        byte[] tmp2 = new byte[width * 4];
        int h = (int) height / 2;
        for (int y = 0; y < h; y++) {
            buffer.position(y * width * 4);
            buffer.get(tmp1, 0, tmp1.length);
            buffer.position((height - 1 - y) * width * 4);
            buffer.get(tmp2, 0, tmp2.length);
            buffer.position((height - 1 - y) * width * 4);
            buffer.put(tmp1);
            buffer.position(y * width * 4);
            buffer.put(tmp2);
        }
        buffer.position(0);
        tmp1 = tmp2 = null;
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes, 0, bytes.length);
        Api.gameBitmap=bytes;
        Thread.sleep(Api.pageTime);
    }
    private void HookMethod(final Class cl, final Method method1, final Object object){
        try {
            XposedHelpers.findAndHookMethod(cl, method1.getName(),add(method1.getParameterTypes(),object));

        } catch (Throwable e) {
           // e.printStackTrace();
        }
    }
    public Object[]add(Class[]A, Object a){
        Object[] B=new Object[A.length+1];//新数组
        for (int i=0;i<A.length;i++){
            B[i]=A[i];
        }
        B[B.length-1]=a;
        return B;
    }

}
