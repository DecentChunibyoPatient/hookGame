package app.wqg.hookgame2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import app.wqg.hookgame2.Thread.EyeThread;
import app.wqg.hookgame2.Thread.HaarState;
import app.wqg.hookgame2.Thread.Main;
import app.wqg.hookgame2.tool.LoadSO;
import dalvik.system.DexClassLoader;

public class HookApiBroadcastReceiver extends Api {

    String TAG="HookApiBroadcastReceiver";
    static public Main main;
    byte[]buffer;
    Bitmap[]bitmaps;
    ArrayList<Bitmap[]> arrayListBitmaps=new ArrayList<>();
    ArrayList<String[]> arrayListNames=new ArrayList<>();
    HashMap<String,Bitmap> ResDrawadle=new HashMap<String, Bitmap>();
    public HookApiBroadcastReceiver() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        initCmd();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {//重写抽象的onReceive（Context context, Intent intent）方法
        Log.d(TAG,intent.getStringExtra("name"));
        // TODO Auto-generated method stub
        switch (intent.getStringExtra("name")){
            case "isOK":
                setAction(intent.getStringExtra("PackageName"));
                send(context,"init");
                break;
            case "init":
                if (buffer!=null)
                    LoadSO.init(context.getPackageName(),buffer);
                break;
            case "DexClassLoader":
                setDexClassLoader(new DexClassLoader(context.getFilesDir().getPath()+"/app-debug.apk", context.getFilesDir().getPath(), context.getFilesDir().getPath()+"/lib/"+ LoadSO.getCpuInfo(), ClassLoader.getSystemClassLoader()));
                break;
            case "ResDrawadle":
                if (buffer!=null){
                    ResDrawadle.put(intent.getStringExtra("sampleName"),BitmapFactory.decodeByteArray(buffer,0,buffer.length));
                }
                break;
            case "names":
                arrayListNames.add(intent.getStringArrayExtra("namesData"));
                break;
            case "Bitmaps":
                if (buffer!=null){
                    int length=intent.getIntExtra("length",0);
                    int destPos=intent.getIntExtra("destPos",0);
                    if (bitmaps==null){
                        bitmaps=new Bitmap[length];
                        System.out.println("new Bitmap");
                    }else if (length!=bitmaps.length){
                        bitmaps=new Bitmap[length];
                        System.out.println("new Bitmap");
                    }
                    bitmaps[destPos]= BitmapFactory.decodeByteArray(buffer,0,buffer.length);
                }
                break;
            case "MakeImageLib":
                if (bitmaps!=null)
                    if (bitmaps.length>0){
                        arrayListBitmaps.add(bitmaps);
                        bitmaps=null;
                    }

                break;
            case "MakeImageLibInput":
                if (arrayListBitmaps.size()>0){
                 new MakeImageLibInput().start();
                }
                break;
            case "Write":
                int length=intent.getIntExtra("length",0);
                int destPos=intent.getIntExtra("destPos",0);
                Log.d(TAG,"Write("+destPos+","+length+")");
                if (buffer==null){
                    buffer=new byte[length];
                    Log.d(TAG,"buffer=new byte");
                }else if (length!=buffer.length){
                    buffer=new byte[length];
                    Log.d(TAG,"buffer=new byte");
                }
                byte[] bytes=intent.getByteArrayExtra("buffer");
                System.arraycopy(bytes,0,buffer,destPos,bytes.length);
                break;
            case "screenshot":
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            byte[]bytes=Api.gameBitmap;
                            ByteBuffer buffer = ByteBuffer.wrap(bytes);
                            Bitmap bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888);
                            bitmap.copyPixelsFromBuffer(buffer);
                            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                            sendWrite(activity,baos1.toByteArray());
                            send(activity,"screenshot");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
                break;

            case "Main":
                String Material=intent.getStringExtra("Material");
                main=new Main(getDexClassLoader(),ResDrawadle,findViewById(getID("glSurfaceView")),Material);
                break;
            case "addIntention":
                try {
                    JSONObject intention=new JSONObject(intent.getStringExtra("intention"));
                    if (main!=null){
                        main.addIntention(intention);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "close":
            {
                System.exit(0);
            }break;
            case "setConfigure":
            {
                int EyeThreadTime=intent.getIntExtra("EyeThreadTime",200);
                int pageTime=intent.getIntExtra("pageTime",200);
                Api.EyeThreadTime=EyeThreadTime;
                Api.pageTime=pageTime;
            }break;
            case "getConfigure":
            {
                sendConfigure(context);
            }break;

        }
    }


    class MakeImageLibInput extends Thread{
        @Override
        public void run() {
            Bitmap[][]bitmapS=new Bitmap[arrayListBitmaps.size()][];
            String[][]nameS=new String[arrayListNames.size()][];
            for (int i=0;i<arrayListBitmaps.size();i++){
                bitmapS[i]=arrayListBitmaps.get(i);
                nameS[i]=arrayListNames.get(i);
            }
            LoadSO.MakeImageLibS(getDexClassLoader(),bitmapS,nameS);
            arrayListBitmaps=new ArrayList<>();
            arrayListNames=new ArrayList<>();
        }
    }


}
