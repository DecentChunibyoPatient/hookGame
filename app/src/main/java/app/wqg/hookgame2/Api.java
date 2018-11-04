package app.wqg.hookgame2;

import android.app.Activity;
import android.content.BroadcastReceiver;
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
import org.json.JSONObject;

import app.wqg.hookgame2.tool.AppUtils;
import app.wqg.hookgame2.tool.LoadSO;
import app.wqg.hookgame2.tool.ReflectionUtil;
import dalvik.system.DexClassLoader;

public class Api extends BroadcastReceiver {
    static public int EyeThreadTime=200;
    static public int pageTime=0;
    static public  byte[]  gameBitmap;
    final static String TAG="Api: ";
    static String Action="app.wqg.hookgame2";
    private GLSurfaceView glSurfaceView= findViewById(getID("glSurfaceView"));
    static public Activity activity;
    private DexClassLoader dexClassLoader;
    public Api() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
    void setDexClassLoader( DexClassLoader dexClassLoader){
        this.dexClassLoader=dexClassLoader;
    }
    DexClassLoader getDexClassLoader(){
       return dexClassLoader;
    }
    void send(Context context, String name){
        System.out.println(TAG+"send:"+name);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name",name);
        context.sendBroadcast(intent);
    }
    void sendConfigure (Context context){
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Configure");
        intent.putExtra("EyeThreadTime",Api.EyeThreadTime);
        intent.putExtra("pageTime",Api.pageTime);
        context.sendBroadcast(intent);
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
    void onTouchEvent(final int action, final float x, final float y){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                glSurfaceView.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),action,x,y,0));
            }
        });
    }
    int[][] HAAR(Bitmap bitmap , Bitmap[] sample) {
        return LoadSO.haar(dexClassLoader,bitmap,sample);
    }
    int[][] HAARRect(Bitmap bitmap , Bitmap[] sample,Rect[] rects) {
        return LoadSO.haarRect(dexClassLoader,bitmap,sample,rects);
    }
    String[] OCR(Bitmap bitmap, int []ints, int libI){
        if (ints.length%4==0){
            int [][]ints1=new int[ints.length/4][4];
            for (int i=0;i<ints.length;i++){
                ints1[i/4][i%4]=ints[i];
            }
            return LoadSO.OcrData(dexClassLoader,bitmap,ints1,libI);
        }else return null;
    }
    void setAction(String Action){
        this.Action=Action;
    }
    static Activity getActivity(){
        return  activity;
    }
    <T extends View> T findViewById(int id){
        return  getActivity().findViewById(id);
    }
    Intent getIntent(){
        return  getActivity().getIntent();
    }
    int getID(String name){
        return  getActivity().getIntent().getIntExtra(name,0);
    }
    void initCmd(){
        AppUtils.stopService(activity,"android.intent.action.START_PCM_PLAY_SERVICE");
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AppUtils.startService(activity,"android.intent.action.START_PCM_PLAY_SERVICE");
            }
        }.start();
    }
    void sendWrite(Context context, byte[]bytes){
        System.out.println("Write");
        int length=500000;
        for (int i=0;i<bytes.length/length;i++){
            byte[]bytes1=new byte[length];
            System.arraycopy(bytes,i*length,bytes1,0,bytes1.length);
            Intent intent=new Intent();
            intent.setAction(Action);
            intent.putExtra("name","Write");
            intent.putExtra("length",bytes.length);
            intent.putExtra("destPos",i*length);
            intent.putExtra("data",bytes1);
            context.sendBroadcast(intent);
        }
        if (bytes.length%length>0){
            byte[]bytes1=new byte[bytes.length%length];
            System.arraycopy(bytes,bytes.length-bytes1.length,bytes1,0,bytes1.length);
            Intent intent=new Intent();
            intent.setAction(Action);
            intent.putExtra("name","Write");
            intent.putExtra("length",bytes.length);
            intent.putExtra("destPos",bytes.length-bytes1.length);
            intent.putExtra("data",bytes1);
            context.sendBroadcast(intent);
        }
    }


    static public void Start(){
        Log.d(TAG+Action,"Start");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Start");
        getActivity().sendBroadcast(intent);
    }

    static public void Stop( ){
        Log.d(TAG+Action,"Stop");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Stop");
        getActivity().sendBroadcast(intent);
    }
    static public void CompletionOfIntention(String intention ,String jsonArrayAction,String data){
        Log.d(TAG+Action,"CompletionOfIntention");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","CompletionOfIntention");
        intent.putExtra("intention",intention);
        intent.putExtra("jsonArrayAction",jsonArrayAction);
        intent.putExtra("data",data);
        getActivity().sendBroadcast(intent);
    }
    static public void RemoveIntention(String intention, String intentionNmae ){
        Log.d(TAG+Action,"RemoveIntention");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","RemoveIntention");
        intent.putExtra("intention",intention);
        intent.putExtra("intentionNmae",intentionNmae);
        getActivity().sendBroadcast(intent);
    }
    static public void AddIntention(String intention ,String jsonArrayAction,String data){
        Log.d(TAG+Action,"CompletionOfIntention");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","CompletionOfIntention");
        intent.putExtra("intention",intention);
        intent.putExtra("jsonArrayAction",jsonArrayAction);
        intent.putExtra("data",data);
        getActivity().sendBroadcast(intent);
    }
    static public void findPageMessage( int CurrentPage, JSONArray ints){
       Log.d(TAG+Action,"unfindCurrentPageMessage CurrentPage="+CurrentPage+" ints="+ints);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","findPage");
        intent.putExtra("CurrentPage",CurrentPage);
        intent.putExtra("ints",ints.toString());
        getActivity().sendBroadcast(intent);
    }
    static  public void unfindPageMessage( JSONArray ints){
        Log.d(TAG+Action,"unfindCurrentPageMessage ints="+ints);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","unfindPage");
        intent.putExtra("ints",ints.toString());
        getActivity().sendBroadcast(intent);
    }
    static public void findCurrentPageMessage(  JSONArray ints){
        Log.d(TAG+Action,"unfindCurrentPageMessage ints="+ints);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","findCurrentPage");
        intent.putExtra("ints",ints.toString());
        getActivity().sendBroadcast(intent);
    }
    static public void unfindCurrentPageMessage(JSONArray ints){
        Log.d(TAG+Action,"unfindCurrentPageMessage ints="+ints);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","unfindCurrentPage");
        intent.putExtra("ints",ints.toString());
        getActivity().sendBroadcast(intent);
    }
}
