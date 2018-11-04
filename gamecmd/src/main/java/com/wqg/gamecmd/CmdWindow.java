package com.wqg.gamecmd;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EasyUtils;
import com.wqg.gamecmd.child_view.ChildListView;
import com.wqg.gamecmd.child_view.ImageEdit;
import com.wqg.gamecmd.child_view.LogView;
import com.wqg.gamecmd.child_view.addOcrMaterial;
import com.wqg.gamecmd.dialog.LoginDialog;
import com.wqg.gamecmd.ui.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.modificator.waterwave_progress.WaterWaveProgress;
import dalvik.system.DexClassLoader;


/**
 * Created by ibm on 2017/4/9.
 */

public class CmdWindow extends WindowManagerService {
    static public int BS=100;
    static public int x=1;
    int G;
    static
    String TAG="CmdWindow";
    static public String Room="页面9_child1";
    static public ImageButton button;
    static public Animation gearAnim;
    static public View buttonbar;
    static public TextView seep;
    static public WaterWaveProgress waveProgress;

    static public String packageName;
    static public CmdInterface cmdInterface;
    static public Connect.Interface anInterface;
    RelativeLayout relativeLayout;
    ImageEdit imageEdit;
    static LogView logView;
    static public DexClassLoader dexClassLoader;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("WindowManager_view", "onDestroy");
        send(getApplicationContext(),packageName,"close");
        AppUtils.sotpAPP(packageName);
        Connect.isConnect=false;
        //System.exit(0);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            seep.setText(msg.obj.toString());
        }
    };

    static public Handler Progress=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println(msg.what);
            waveProgress.setProgress(msg.what);
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        packageName=intent.getStringExtra("PackageName");
        if (packageName==null){
            packageName= AppUtils.getCurrentAppPackage(getApplicationContext());
        }
        send(getApplicationContext(),packageName,"isOK");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(R.layout.button);

        logView=new LogView(getApplicationContext());

     
        if (!AppUtils.isServiceWork(getApplicationContext(),Connect.class.getName())){
            /* 服务开机自启动 */
            Intent service = new Intent(getApplicationContext(), Connect.class);
            getApplicationContext().startService(service);
        }else {
            Connect.isConnect=false;
        }

        new Thread(){
            @Override
            public void run() {
                LoadSO.init(getApplicationContext(),"app-debug.apk");
                dexClassLoader=LoadSO.getDexClassLoader(getApplicationContext());
                System.out.println("dexClassLoader="+dexClassLoader);
            }
        }.start();
        gearAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.running_gear);
        relativeLayout=findViewById(R.id.RelativeLayout);
        button= findViewById(R.id.imageButton);
        waveProgress =findViewById(R.id.waterWaveProgress);

        waveProgress.setShowProgress(true);
        waveProgress.setShowNumerical(false);
        waveProgress.animateWave();
        seep=findViewById(R.id.seep);
        seep.setClickable(false);

        button=findViewById(R.id.imageButton);
        button.setClickable(false);
        findViewById(R.id.imageButton).setOnTouchListener(new TouchListener(new Touch() {
            @Override
            public void open() {
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(70,0,0,0);
              openChildView(buttonbar,layoutParams);
            }

            @Override
            public void close() {
               closeChildView();
            }

            @Override
            public void move(View v, MotionEvent event) {
                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                WindowManager.LayoutParams layoutParams=getLayoutParams();
                layoutParams.x = (int) (event.getRawX() - v.getMeasuredWidth()/2);
                //25为状态栏的高度
                layoutParams.y = (int) event.getRawY() - v.getMeasuredHeight()/2 - 25;
                //刷新
                getWindowManager().updateViewLayout(getRootView(), layoutParams);
            }
        }));


        cmdInterface=new CmdInterface() {
            JSONObject This;
            JSONObject toThis;

            {
                anInterface=new Connect.Interface() {
                    boolean issendAction=true;
                    @Override
                    public void isConnect() {
                        if (issendAction){
                            issendAction=false;
                            try {
                                new Input(getApplicationContext(), packageName,FileSaveAndLoad.FileLoad(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName), new Input.InputInterface() {
                                    @Override
                                    public void end() {
                                        try {
                                            sendMain(getApplicationContext(),packageName,FileSaveAndLoad.loadJSONArray(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/"+"test.txt").toString());
                                            new InputOcrLibrary(getApplicationContext(), packageName, new File(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/Ocr"), new InputOcrLibrary.InputInterface() {
                                                @Override
                                                public void end() {
                                                    try {
                                                        sendAddIntention(getApplicationContext(),packageName,Action.IntoRoom());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                           /* if (Connect.isConnect){
                                                anInterface.isConnect();
                                            }*/
                                                }
                                            }).start();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onMessageReceived(String var0, String var1) {
                        try {
                            JSONObject jsonObject=new JSONObject(var0);
                            String name=jsonObject.getString("name");
                            switch (name){
                                case "随机分配下注":{
                                    toThis=jsonObject;
                                }break;
                                case "金币不足":
                                {
                                    int g=jsonObject.getInt("g");
                                    if (G/2>300000){
                                        sendAddIntention(getApplicationContext(),packageName,Action.sendhongbao(G/2));
                                    }else if (G-300000>101000){
                                        sendAddIntention(getApplicationContext(),packageName,Action.sendhongbao(G-300000-((int)(Math.random()*1000))));
                                    }
                                }break;
                                case "红包":
                                {
                                    String ID=jsonObject.getInt("红包ID")+"";
                                    String G=jsonObject.getInt("g")+"";
                                    sendAddIntention(getApplicationContext(),packageName,Action.gethongbao(ID,G));
                                }break;
                                case "恭喜你猜中了":
                                {
                                    sendAddIntention(getApplicationContext(),packageName,Action.FreeHongBao());
                                }break;
                                case "发放红包":
                                {
                                    sendAddIntention(getApplicationContext(),packageName,Action.IntoRoom());
                                }break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Connect.sharedPreferences!=null){
                            int EyeThreadTime=Connect.sharedPreferences.getInt("EyeThreadTime",0);
                            int pageTime=Connect.sharedPreferences.getInt("pageTime",0);
                            sendSetConfigure(getApplicationContext(),packageName,EyeThreadTime,pageTime);
                        }
                        //send(getApplicationContext(),packageName,"getConfigure");
                        Connect.getConnect();
                    }
                }.start();
            }

            @Override
            public void CompletionOfIntention(JSONObject intention,JSONArray jsonArrayAction,JSONObject data) {
                //Log.d(TAG,"CompletionOfIntention   intention="+intention+" jsonArrayAction="+jsonArrayAction);
                //Log.d(TAG,"CompletionOfIntention   data="+data);
                try {
                    if (intention.getString("name").equals("恭喜您发送红包成功")){
                        JSONArray jsonArray=data.getJSONArray("红包ID");
                        String string=jsonArray.getString(0);
                        int ID=Integer.parseInt(string);
                        Log.d(TAG,"CompletionOfIntention   红包ID="+ID  +"jsonArray="+jsonArray);
                        JSONObject jsonObject=new JSONObject();
                        String name="红包";
                        jsonObject.put("name",name);
                        jsonObject.put("红包ID",ID);
                        jsonObject.put("g",intention.getInt("data"));
                        if (Connect.toChatUsername!=null&&Connect.actionInterface!=null&&Connect.actionInterface!=null)Connect.actionInterface.createTxtSendMessage(jsonObject.toString(),Connect.toChatUsername);
                    }
                    if (intention.getString("name").equals("恭喜你猜中了")){
                        Log.d(TAG,"CompletionOfIntention  恭喜你猜中了");
                        JSONObject jsonObject=new JSONObject();
                        String name="恭喜你猜中了";
                        jsonObject.put("name",name);
                        if (Connect.toChatUsername!=null&&Connect.actionInterface!=null)Connect.actionInterface.createTxtSendMessage(jsonObject.toString(),Connect.toChatUsername);
                    }
                    if (intention.getString("name").equals("发放红包")){
                        sendAddIntention(getApplicationContext(),packageName,Action.IntoRoom());
                        JSONObject jsonObject=new JSONObject();
                        String name="发放红包";
                        jsonObject.put("name",name);
                        if (Connect.toChatUsername!=null&&Connect.actionInterface!=null)Connect.actionInterface.createTxtSendMessage(jsonObject.toString(),Connect.toChatUsername);
                    }
                    if (intention.getString("name").equals("下注时间")){
                        System.out.println("随机分配下注"+This+" "+toThis);
                        if (This!=null&&toThis!=null){
                            if (Math.abs(This.getLong("currentTimeMillis")-toThis.getLong("currentTimeMillis"))<10000){
                                JSONObject SynthesisMath=SynthesisMath(This.getJSONArray("math"),toThis.getJSONArray("math"));
                                System.out.println("SynthesisMath="+SynthesisMath);
                                sendAddIntention(getApplicationContext(),packageName,Action.Bets(SynthesisMath));
                            }
                            This=null;
                            toThis=null;
                        }
                    }
                    if (intention.getString("name").equals("四小下注")){
                        sendAddIntention(getApplicationContext(),packageName,Action.FourMixContinue());
                    }
                    if (intention.getString("name").equals("空闲时间查询金币")){
                        try {
                            String string=data.getJSONArray("游戏金币").getString(0);
                            int g=Integer.parseInt(string);
                            G=g;
                            Log.d(TAG,"CompletionOfIntention   游戏金币="+g);
                            if (g>BS*10*x){
                                if (Connect.actionInterface!=null){
                                    This=new JSONObject();
                                    JSONArray math=new JSONArray();
                                    long currentTimeMillis;
                                    currentTimeMillis=System.currentTimeMillis();
                                    for (int i=0;i<4;i++){
                                        math.put( Math.random());
                                    }
                                    This.put("currentTimeMillis",currentTimeMillis);
                                    This.put("math",math);
                                    This.put("name","随机分配下注");
                                    System.out.println("发送随机分配下注"+This);
                                    Connect.actionInterface.createTxtSendMessage(This.toString(),Connect.toChatUsername);
                                    sendAddIntention(getApplicationContext(),packageName,Action.AllowTime());
                                }
                                sendAddIntention(getApplicationContext(),packageName,Action.continueBets());
                            }else {
                                JSONObject jsonObject=new JSONObject();
                                String name="金币不足";
                                jsonObject.put("name",name);
                                jsonObject.put("g",g);
                                if (Connect.toChatUsername!=null&&Connect.actionInterface!=null){
                                    Connect.actionInterface.createTxtSendMessage(jsonObject.toString(),Connect.toChatUsername);
                                }else {
                                    System.out.println("Connect.toChatUsername="+Connect.toChatUsername);
                                    System.out.println("Connect.actionInterface"+Connect.actionInterface);
                                }
                            }
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                            sendAddIntention(getApplicationContext(),packageName,Action.continueBets());
                        }

                    }
                    if (intention.getString("name").equals("关闭弹幕")){
                        try {
                            JSONArray jsonArray=data.getJSONArray("游戏金币");
                            String string=jsonArray.getString(0);
                            int g=Integer.parseInt(string);
                            G=g;
                            Log.d(TAG,"CompletionOfIntention   游戏金币="+g);
                            if (g>BS*10*x){
                                sendAddIntention(getApplicationContext(),packageName,Action.FreeTimeCheck());
                            }else {
                                JSONObject jsonObject=new JSONObject();
                                String name="金币不足";
                                jsonObject.put("name",name);
                                jsonObject.put("g",g);
                                if (Connect.toChatUsername!=null&&Connect.actionInterface!=null)Connect.actionInterface.createTxtSendMessage(jsonObject.toString(),Connect.toChatUsername);
                            }
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                            sendAddIntention(getApplicationContext(),packageName,Action.continueBets());
                        }

                    }
                }catch (JSONException e){
                }


            }

            @Override
            public void RemoveIntention(JSONArray intention,String intentionNmae) {
                logView.getMBaseAdapter().upData(intention);
            }

            @Override
            public void AddIntention() {

            }

            @Override
            public void sendAction() {
                Log.d(TAG,"sendAction");

                try {
                    new Input(getApplicationContext(), packageName,FileSaveAndLoad.FileLoad(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName), new Input.InputInterface() {
                        @Override
                        public void end() {
                            try {
                                sendMain(getApplicationContext(),packageName,FileSaveAndLoad.loadJSONArray(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/"+"test.txt").toString());
                                new InputOcrLibrary(getApplicationContext(), packageName, new File(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/Ocr"), new InputOcrLibrary.InputInterface() {
                                    @Override
                                    public void end() {
                                        try {
                                            sendAddIntention(getApplicationContext(),packageName,Action.FourMix0());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                           /* if (Connect.isConnect){
                                                anInterface.isConnect();
                                            }*/
                                    }
                                }).start();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void start() {
                button.startAnimation(gearAnim);
            }

            @Override
            public void stop() {
                button.clearAnimation();
            }

            @Override
            public void set() {
               final AlertDialog dialog = new AlertDialog.Builder(getApplicationContext())
                        .setIcon(R.mipmap.ic_launcher)//设置标题的图片
                        .setTitle("设置")//设置对话框的标题
                        .create();
                dialog.setView(ChildView.set(getBaseContext(), new SetInterface() {
                    @Override
                    public void save() {
                        dialog.dismiss();
                    }

                    @Override
                    public void cancel() {
                        dialog.dismiss();
                    }

                    @Override
                    public void setConfigure(int EyeThreadTime ,int pageTime) {
                        sendSetConfigure(getApplicationContext(),packageName,EyeThreadTime,pageTime);
                    }
                }));
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();
            }

            @Override
            public void close() {
                AppUtils.stopService(getApplicationContext(),"android.intent.action.START_PCM_PLAY_SERVICE");
            }
            @Override
            public void log() {
                if (logView==null){
                    logView=new LogView(getApplicationContext());
                }
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(120,200);
                layoutParams.setMargins(0,70,0,0);
                openChildView(logView,layoutParams);
            }

            @Override
            public void Configure(int EyeThreadTime, int pageTime) {
                if (Connect.sharedPreferences!=null){
                    SharedPreferences.Editor editor=Connect.sharedPreferences.edit();
                    editor.putInt("EyeThreadTime",EyeThreadTime);
                    editor.putInt("pageTime",pageTime);
                    editor.commit();
                    Toast.makeText(getBaseContext(),"保存成功",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void screenshot(Context context) {
                send(context,packageName,"screenshot");
            }
            @Override
            public void screenshot(final Context context, final Bitmap bitmap) {
               Log.d(TAG,"screenshot");
               if (imageEdit==null){
                   imageEdit=new ImageEdit(context, bitmap, new ImageEditInterface() {
                       @Override
                       public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                           imageEdit.getBaseAdapter().Checked(position);
                           Log.d(TAG,"onItemLongClick: view="+view.getClass().getName());
                       }

                       @Override
                       public void onItemClick(ListView listView, HashMap<String, Bitmap>hashMap, AdapterView<?> parent, View view, int position, long id) {
                           imageEdit.getBaseAdapter().clearChecked();
                           try {
                               Rect rect=getRootRect(view);
                               ChildListView childListView=new ChildListView(getApplicationContext(), (JSONObject) parent.getItemAtPosition(position),hashMap);
                               showChildView(childListView,ChildLayoutParams(rect.left-(200),rect.top,200,WindowManager.LayoutParams.WRAP_CONTENT),WindowManagerService.SHOW_DIALOG);
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }

                       @Override
                       public void onCropOverlayReleased(Rect rect) {

                       }

                       @Override
                       public void Cutting(Bitmap cropped, Rect rect) {

                       }

                       @Override
                       public void Back(ArrayList<Bitmap> arrayList, Bitmap bitmap, Rect rect) {

                       }

                       @Override
                       public void add(Bitmap cropped, Rect rect) {
                           Log.d(TAG,"add: rect="+rect+" "+cropped.getWidth()+" "+cropped.getHeight());
                       }

                       @Override
                       public void addOcrMaterial(final Bitmap cropped, Rect rect) {
                           Log.d(TAG,"addOcrMaterial: rect="+rect+" "+cropped.getWidth()+" "+cropped.getHeight());
                           if (dexClassLoader!=null){
                               new Thread(){
                                   Bitmap []bitmaps;
                                   LinearLayout linearLayout;
                                   @Override
                                   public void run() {

                                       bitmaps=LoadSO.drop(dexClassLoader,cropped);
                                       System.out.println("bitmaps="+bitmaps.length);
                                       linearLayout=new addOcrMaterial(getApplicationContext(),cropped,bitmaps);
                                       new Handler(context.getMainLooper()).post(new Runnable() {
                                           @Override
                                           public void run() {
                                             try {
                                                 AlertDialog alertDialog=new AlertDialog.Builder(getApplicationContext()).setTitle("").
                                                         setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                             @Override
                                                             public void onClick(DialogInterface dialog, int which) {
                                                             }
                                                         }).
                                                         setView(linearLayout).
                                                         create();
                                                 alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                                 alertDialog.show();
                                             }catch (Exception e){
                                                 e.printStackTrace();
                                             }
                                           }
                                       });
                                   }
                               }.start();

                           }
                       }

                       @Override
                       public void loadStart() {

                       }

                       @Override
                       public void loadEnd() {

                       }

                       @Override
                       public void saveStart() {

                       }

                       @Override
                       public void saveEnd() {

                       }
                   });
               }else {
                   imageEdit.setBitmap(bitmap);
               }
                openChildView(imageEdit);
                WindowManager.LayoutParams layoutParams=getLayoutParams();
                if ( layoutParams.width<bitmap.getWidth())
                    layoutParams.width=bitmap.getWidth();
                if ( layoutParams.height<bitmap.getHeight())
                    layoutParams.height=bitmap.getHeight();
                //刷新
                getWindowManager().updateViewLayout(getRootView(), layoutParams);
            }

            @Override
            public void findPage(int CurrentPage, JSONArray ints) {
                dismissChildViewAll();
               // Log.d(TAG,"findPage: CurrentPage:"+CurrentPage+" ints:"+ints);
            }

            @Override
            public void unfindPage(JSONArray ints) {
          /*      dismissChildViewAll();
                for (int i=0;i<ints.length();i++){
                    try {
                        JSONObject jsonObject=ints.getJSONObject(i);
                        int similar=jsonObject.getInt("similar");
                        String name=jsonObject.getString("name");
                        Log.d(TAG,"unfindPage name="+name+" similar="+similar);
                        Rect rect=JSONObjectToRect(jsonObject.getJSONObject("rect"));
                        percent(similar,rect);
                     } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }*/
              //  Log.d(TAG,"unfindPage: ints:"+ints);
            }

            @Override
            public void findCurrentPage(JSONArray ints) {
         /*       dismissChildViewAll();
                for (int i=0;i<ints.length();i++){
                    try {
                        JSONObject jsonObject=ints.getJSONObject(i);
                        int similar=jsonObject.getInt("similar");
                        Rect rect=JSONObjectToRect(jsonObject.getJSONObject("rect"));
                        if (Double.parseDouble(percent(similar,rect))<0.5d){
                            View view=new View(getApplicationContext());
                            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            view.setBackgroundResource(R.drawable.shape3);
                            showChildView(view,ChildLayoutParams(rect, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE),WindowManagerService.SHOW_DEFAULT);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }*/

               // Log.d(TAG,"findCurrentPage: ints:"+ints);
            }

            @Override
            public void unfindCurrentPage(JSONArray ints) {
              //  dismissChildViewAll();
            //    Log.d(TAG,"unfindCurrentPage :ints:"+ints);
            }


        };

        buttonbar=ChildView.buttonbarinit(getApplicationContext() ,cmdInterface);
    }


    String IntToString(int i){
        if(i>=10000){
            float price=i/10000f;
            float num=(float)(Math.round(price*100))/100;//如果要求精确4位就*10000然后/10000
            DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            return decimalFormat.format(num)+"万";//format 返回的是字符串
        }else {
            return ""+i;
        }
    }
    int StringToInt(String string){
        if (string.indexOf(".")!=-1&&string.indexOf("万")!=-1){
            return (int)( Float.parseFloat(string.substring(0,string.indexOf("万")))*10000);
        }else {
            return Integer.parseInt(string);
        }
    }
    String percent(int similar, Rect rect){
        int w=rect.right- rect.left;
        int h=rect.bottom-rect.top;
        int num1=similar;
        int num2=w*h*255*3;
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(10);
        String result = numberFormat.format((double) num1 / (double) num2 * 100);
        System.out.println("app-debug.apk="+num1+" num2="+num2+" 误差:" + result + "%");
        return result;
    }
    String percent(int num1,int num2){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(10);
        String result = numberFormat.format((double) num1 / (double) num2 * 100);
        System.out.println("num1和num2的百分比为:" + result + "%");
        return result;
    }
    static void sendMain(Context context, String Action,String Material){
        System.out.println("sendMain Material="+Material);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Main");
        intent.putExtra("Material",Material);
        intent.putExtra("PackageName",context.getPackageName());
        context.sendBroadcast(intent);
    }
    static void sendAddIntention(Context context, String Action,JSONArray intention) throws JSONException {
        logView.getMBaseAdapter().addData(intention);
        for (int i=0;i<intention.length();i++)
            sendAddIntention(context,Action,intention.getJSONObject(i).toString());
    }
    static void sendAddIntention(Context context, String Action,String intention){
        System.out.println("sendAddIntention intention="+intention);
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","addIntention");
        intent.putExtra("intention",intention);
        intent.putExtra("PackageName",context.getPackageName());
        context.sendBroadcast(intent);
    }
    static void send(Context context, String Action, String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name",name);
        intent.putExtra("PackageName",context.getPackageName());
        context.sendBroadcast(intent);
    }
    static void sendSetConfigure(Context context, String Action, int EyeThreadTime,int pageTime){
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","setConfigure");
        intent.putExtra("PackageName",context.getPackageName());
        intent.putExtra("EyeThreadTime",EyeThreadTime);
        intent.putExtra("pageTime",pageTime);
        context.sendBroadcast(intent);
    }
    static public JSONObject SynthesisMath(JSONArray thisJSONArray,JSONArray toJSONArray) throws JSONException {

        double[] This=new double[thisJSONArray.length()];
        for (int i=0;i<thisJSONArray.length();i++){
            This[i]=thisJSONArray.getDouble(i);
        }
        double[] toThis=new double[toJSONArray.length()];
        for (int i=0;i<toJSONArray.length();i++){
            toThis[i]=toJSONArray.getDouble(i);
        }
        double[][]doubleS=new double[2][];
        doubleS[0]=This;
        doubleS[1]=toThis;
        if (doubleS==null)return null;
        if (!(doubleS.length==2||doubleS.length==4))return null;

        double[][]doubles=new double[2][4];
        int dS1=0;
        int dS2=0;
        for(int d=0;d<doubleS.length;d++){
            double[]doubles1=doubleS[d];

            for (int dd=0;dd<doubles1.length;dd++){
                if (doubles1.length/2>dd){
                    doubles[1][dS2++]=doubles1[dd];
                }else {
                    doubles[0][dS1++]=doubles1[dd];
                }
            }
        }
        boolean b=true;
        for (int d=0;d<doubles.length;d++){
            double[]doubles1=doubles[d];
            for (int x=0;x<doubles1.length-1;x++){
                for (int y=x+1;y<doubles1.length;y++){
                    double d1=doubles1[x];
                    double d2=doubles1[y];
                    if (doubles1[x]==doubles1[y]){
                        System.out.println(doubles1[x]+"  "+doubles1[y]);
                        b=false;
                    }else if (d1>d2){
                        doubles[d][x]=d2;
                        doubles[d][y]=d1;
                    }
                }
            }
        }
       JSONObject jsonObject=new JSONObject();
        JSONArray best=new JSONArray();
        JSONArray bestbig=new JSONArray();
        if (b){
            System.out.println("数据没有重复。");
            for (int d=0;d<doubles.length;d++){
                double[]doubles1=doubles[d];
                for (int x=0;x<doubles1.length;x++){
                    for (double D:This){
                        if (doubles[d][x]==D){
                            if (d==0){
                                best.put(x);
                            }else if (d==1){
                                bestbig.put(x);
                            }
                        }
                    }
                }
            }
        }else {
            System.out.println("数据重复！");
            return null;

        }
        jsonObject.put("best",best);
        jsonObject.put("bestbig",bestbig);
        return jsonObject;
    }
}
