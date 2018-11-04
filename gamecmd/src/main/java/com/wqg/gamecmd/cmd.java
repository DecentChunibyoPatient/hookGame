package com.wqg.gamecmd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class cmd extends SendBroadcast {
    static public byte[]data;
    static public String Dir="/mnt/shared/Other/hookGame";
    static public String PackageName;
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY")) {
            /* 服务开机自启动 */
            Intent service = new Intent(context, Connect.class);
            context.startService(service);
        }
        if (intent.getAction().equals(context.getPackageName())) {
            //System.out.println(intent.getStringExtra("name"));
            switch (intent.getStringExtra("name")) {
                case "init":
                    PackageName= intent.getStringExtra("PackageName");
                    if (PackageName==null)PackageName=AppUtils.getCurrentAppPackage(context);
                    new InputViruses(context,PackageName).start();
             /*   new Input(context,PackageName,
                        SampleRes,
                        bID,
                        bets,
                        betsbig,
                        gethongbao,
                        home,
                        hongbao,
                        intoroom,
                        intoroom2,
                        isOptional,
                        isSelect,
                        sendhongbao).start();*/
                    break;
                case "screenshot":
                    System.out.println("screenshot");
                    if (data!=null){
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        CmdWindow.cmdInterface.screenshot(context,bitmap);
                   /*     try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String str = formatter.format(curDate);
                            File file=new File("/mnt/shared/Image",str+".png");
                            FileOutputStream outputStream= new FileOutputStream(file);
                            outputStream.write(data);
                            outputStream.close();
                            System.out.println("保存成功"+file.getPath());
                            // Toast.makeText(context,"保存成功"+file.getPath(),Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                    break;
                case "Write":
                    int length=intent.getIntExtra("length",0);
                    int destPos=intent.getIntExtra("destPos",0);
                    if (data==null){
                        data=new byte[length];
                        System.out.println("new byte");
                    }else if (length!=data.length){
                        data=new byte[length];
                        System.out.println("new byte");
                    }
                    byte[] bytes=intent.getByteArrayExtra("data");
                    System.arraycopy(bytes,0,data,destPos,bytes.length);
                    System.out.println("length="+length+"  destPos="+destPos+"  bytes.length="+bytes.length);
                    break;
                case "Start":
                {
                    CmdWindow.cmdInterface.start();
                }break;
                case "Stop":
                {
                    CmdWindow.cmdInterface.stop();
                }break;
                case "CompletionOfIntention":
                {
                    try {
                        CmdWindow.cmdInterface.CompletionOfIntention(new JSONObject(intent.getStringExtra("intention")),new JSONArray(intent.getStringExtra("jsonArrayAction")),new JSONObject(intent.getStringExtra("data")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }break;
                case "RemoveIntention":
                {
                    String intentionNmae=intent.getStringExtra("intentionNmae");
                    String intention=intent.getStringExtra("intention");
                    try {
                        CmdWindow.cmdInterface.RemoveIntention(new JSONArray(intention),intentionNmae);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }break;
                case "AddIntention":
                {
                    CmdWindow.cmdInterface.AddIntention();
                }break;
                case "findPage":
                {
                    try {
                        int CurrentPage=intent.getIntExtra("CurrentPage",0);
                        JSONArray ints = new JSONArray(intent.getStringExtra("ints"));
                        CmdWindow.cmdInterface.findPage(CurrentPage,ints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }break;
                case "unfindPage":
                {
                    try {
                        JSONArray ints = new JSONArray(intent.getStringExtra("ints"));
                        CmdWindow.cmdInterface.unfindPage(ints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }break;
                case "findCurrentPage":
                {
                    try {
                        JSONArray ints = new JSONArray(intent.getStringExtra("ints"));
                        CmdWindow.cmdInterface.findCurrentPage(ints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }break;
                case "unfindCurrentPage":
                {
                    try {
                        JSONArray ints = new JSONArray(intent.getStringExtra("ints"));
                        CmdWindow.cmdInterface.unfindCurrentPage(ints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }break;
                case "Configure":
                {
                    CmdWindow.cmdInterface.Configure(intent.getIntExtra("EyeThreadTime",0),intent.getIntExtra("pageTime",0));
                }break;
            }
        }
    }
    public void sendData(Context context, String packname){
        System.out.println("send");
        try {
            InputStream inputStream = context.getAssets().open("app-debug.apk");
            byte[]bytes=toByteArray(inputStream);
            inputStream.close();
            int length=500000;
            for (int i=0;i<bytes.length/length;i++){
                byte[]bytes1=new byte[length];
                System.arraycopy(bytes,i*length,bytes1,0,bytes1.length);
                Intent intent=new Intent();
                intent.setAction(packname);
                intent.putExtra("name","Write");
                intent.putExtra("length",bytes.length);
                intent.putExtra("destPos",i*length);
                intent.putExtra("buffer",bytes1);
                context.sendBroadcast(intent);
            }
            if (bytes.length%length>0){
                byte[]bytes1=new byte[bytes.length%length];
                System.arraycopy(bytes,bytes.length-bytes1.length,bytes1,0,bytes1.length);
                Intent intent=new Intent();
                intent.setAction(packname);
                intent.putExtra("name","Write");
                intent.putExtra("length",bytes.length);
                intent.putExtra("destPos",bytes.length-bytes1.length);
                intent.putExtra("buffer",bytes1);
                context.sendBroadcast(intent);
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}