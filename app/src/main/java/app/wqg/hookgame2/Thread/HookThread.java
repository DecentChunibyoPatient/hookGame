package app.wqg.hookgame2.Thread;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import app.wqg.hookgame2.Api;
import app.wqg.hookgame2.tool.LoadSO;
import app.wqg.hookgame2.tool.ReflectionUtil;
import dalvik.system.DexClassLoader;

public class HookThread extends Thread {
    final static public String ACTION_HAAR="HAAR";
    final static public String ACTION_OCR="OCR";
    final static public String ACTION_BAIDU_OCR="baiduOCR";
    final static public String ACTION_INPUT="Input";
    final static public String ACTION_TOUCH_EVENT="TouchEvent";
    final static public String ACTION_INTENTION="Intention";

    final static public String INPUT_DEFAULT="DEFAULT";
    final static public String INPUT_OCR="OCR";

    final static public String TOUCH_EVENT_PLAY ="PLAY";
    final static public String TOUCH_EVENT_DEFAULT="DEFAULT";
    final static public String TOUCH_EVENT_HAAR="HAAR";

    final static public String HAAR_DEFAULT="DEFAULT";
    final static public String HAAR_CHECK="HAAR";

    final static public String INTENTION_ADD="addIntention";
    final static public String INTENTION_REMOVE="removeIntention";

    static public boolean run=false;
    View view;
    String Dir="/mnt/shared/Other/hookGame";
    String ActionName="app.wqg.hookgame2";
    DexClassLoader dexClassLoader;
    HashMap<String,Bitmap> ResDrawadle=new HashMap<>();
    public HookThread(DexClassLoader dexClassLoader, HashMap<String,Bitmap> ResDrawadle, View view){
        this.ResDrawadle=ResDrawadle;
        this.dexClassLoader=dexClassLoader;
        this.view=view;
    }
    static public String percent(int similar, Rect rect){
        int w=rect.right- rect.left;
        int h=rect.bottom-rect.top;
        int num1=similar;
        int num2=w*h*255*3;
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(10);
        String result = numberFormat.format((double) num1 / (double) num2 * 100);
        System.out.println("误差:" + result + "%");
        return result;
    }
    Bitmap getResBitmap(String ID){
        return ResDrawadle.get(ID);
    }
    Bitmap[] getResBitmaps(String[] ids){
        Bitmap[] bitmaps=new Bitmap[ids.length];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=ResDrawadle.get(ids[i]);
            System.out.println("getResBitmaps: bitmaps["+i+"]=" +bitmaps[i]);
        }
        return bitmaps;
    }

    Bitmap[] getResBitmaps(ArrayList<Integer> ids){
        Bitmap[] bitmaps=new Bitmap[ids.size()];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=ResDrawadle.get(ids.get(i));
        }
        return bitmaps;
    }
    Bitmap[] getResBitmaps(JSONArray ids) throws JSONException {
        Bitmap[] bitmaps=new Bitmap[ids.length()];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=ResDrawadle.get(ids.getInt(i));
        }
        return bitmaps;
    }
    Activity getActivity(){
        return  Api.activity;
    }
    Bitmap getBitmap(){
        byte[]bytes=Api.gameBitmap;
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        Bitmap bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        return bitmap;
    }
    Intent getIntent(){
        Activity activity=getActivity();
        return  activity.getIntent();
    }
    void putGameCurrency(int GameCurrency) {
        getIntent().putExtra("GameCurrency",GameCurrency);
    }
    int getGameCurrency() {
        return getIntent().getIntExtra("GameCurrency",0);
    }
    String[] OCR(Bitmap bitmap, int []Rect, int Materiallibrary){
        if (Rect.length%4==0){
            int [][]Rects=new int[Rect.length/4][4];
            for (int i=0;i<Rect.length;i++){
                Rects[i/4][i%4]=Rect[i];
            }
            return LoadSO.OcrData(dexClassLoader,bitmap,Rects,Materiallibrary);
        }else return null;
    }
    String[] baiduOCR(Bitmap bitmap, int []Rect){
        if (Rect.length%4==0){
            int [][]Rects=new int[Rect.length/4][4];
            for (int i=0;i<Rect.length;i++){
                Rects[i/4][i%4]=Rect[i];
            }
            return LoadSO.baiduOCR(dexClassLoader,bitmap,Rects);
        }else return null;
    }
    void onTouchEvent(final int action, final float x, final float y){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),action,x,y,0));
            }
        });
    }
    void onTouchEvent(final long downTime, final long eventTime, final int action, final float x, final float y, final int metaState){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.onTouchEvent(MotionEvent.obtain(downTime,eventTime,action,x,y,metaState));
            }
        });
    }
    void onTouchEvent(int pix,Bitmap bitmap) throws InterruptedException {
        int x=pix%800;
        int y=pix/800;
        onTouchEvent(MotionEvent.ACTION_DOWN,x+bitmap.getWidth()/2,y+bitmap.getHeight()/2);
        Thread.sleep(200);
        onTouchEvent(MotionEvent.ACTION_UP,x+bitmap.getWidth()/2,y+bitmap.getHeight()/2);
        Thread.sleep(200);
    }
    void sendhongbao(Context context,  String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name","hongbao");
        intent.putExtra("action",name);

        context.sendBroadcast(intent);
    }
    void sendhome(Context context,  String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name","home");
        intent.putExtra("action",name);
        context.sendBroadcast(intent);
    }
    
    void send(Context context,  String name, String value){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra(name,value);

        context.sendBroadcast(intent);
    }
    void send(Context context,  String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name",name);

        context.sendBroadcast(intent);
    }
    void Log( String text) {
        System.out.println("Log");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("Log",text);
        getActivity().sendBroadcast(intent);
    }
    void RootInput(String text)
    {
        Process process  =  null;
        DataOutputStream os  =  null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("input text \""+text+"\""+ "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    boolean Input(final String text) {
        Activity activity=getActivity();
        View view=activity.getCurrentFocus();
        if (view instanceof EditText){
            final EditText editText= (EditText) view;
           activity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   editText.setText(text);
               }
           });
        }
        return view instanceof EditText;
    }
    void sendgethongbaofile(Context context,  String fileName){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name","gethongbaofile");
        intent.putExtra("fileName",fileName);

        context.sendBroadcast(intent);
    }
    void sendsendhongbaofile(Context context,  String fileName){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name","sendhongbaofile");
        intent.putExtra("fileName",fileName);

        context.sendBroadcast(intent);
    }
    void sendfileWrite(Context context,  String path, String text){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);
        intent.putExtra("name","fileWrite");
        intent.putExtra("path",path);
        intent.putExtra("text",text);

        context.sendBroadcast(intent);
    }
    void sendFree(Context context,  String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(ActionName);

        intent.putExtra("name","Free");
        intent.putExtra("GameCurrency",name);
        context.sendBroadcast(intent);
    }
    static public Rect JSONObjectToRect(JSONObject jsonObject) throws JSONException {
        //int left, int top, int right, int bottom
        return new Rect(jsonObject.getInt("left"),jsonObject.getInt("top"),jsonObject.getInt("right"),jsonObject.getInt("bottom"));
    }
    int[][] haar(Bitmap bitmap , Bitmap[] sample) {
        return LoadSO.haar(dexClassLoader,bitmap,sample);
    }
    int[][] haarRect( Bitmap bitmap , Bitmap[] sample ,Rect[] rects) {

        return LoadSO.haarRect(dexClassLoader,bitmap,sample,rects);
    }

}
