package com.wqg.gamecmd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static com.wqg.gamecmd.CmdWindow.Progress;
import static com.wqg.gamecmd.CmdWindow.waveProgress;

public class Input extends Thread {
    String TAG="Input";
    String PackageName;
    Context context;
    HashMap<String,Bitmap>hashMap;
    InputInterface inputInterface;
    public Input(Context context, String PackageName,HashMap<String,Bitmap>hashMap,InputInterface inputInterface) throws JSONException {
        this.context=context;
        this.hashMap=hashMap;
        this.PackageName=PackageName;
        this.inputInterface=inputInterface;
    }
    public void run() {
     try {
         Log.d(TAG,"hashMap.size():"+hashMap.size());
         int i=0;
         for (String name:hashMap.keySet()){
             Progress.sendEmptyMessage(i++*100/hashMap.size());
             Bitmap bitmap=hashMap.get(name);
             if (bitmap!=null){
                 sendWrite(context,PackageName,Bitmap2Bytes(bitmap));
                 sendResDrawadle(context,PackageName,"ResDrawadle",name);
             }
         }
         Progress.sendEmptyMessage(100);
         inputInterface.end();
     }catch (Exception e){}

    }
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public void send(Context context, String packname, String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name",name);
        context.sendBroadcast(intent);
    }
    public void sendInit(Context context, String packname){
        System.out.println("sendInit");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name","init");
        context.sendBroadcast(intent);
    }
    public void sendResDrawadle(Context context, String packname, String name, String sampleName){
        System.out.println("sendResDrawadle");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name",name);
        intent.putExtra("sampleName",sampleName);
        context.sendBroadcast(intent);
    }
    public void sendMakeImageLib(Context context, String Action, Bitmap[]bitmaps, String[]names) throws InterruptedException {
        System.out.println("sendMakeImageLib");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","names");
        intent.putExtra("namesData",names);
        context.sendBroadcast(intent);
        for (int i=0;i<bitmaps.length;i++){
            sendWrite(context,Action, Bitmap2Bytes(bitmaps[i]));
            sendBitmaps(context,Action,bitmaps.length,i);
        }
        Intent intentLast=new Intent();
        intentLast.setAction(Action);
        intentLast.putExtra("name","MakeImageLib");
        context.sendBroadcast(intentLast);
    }
    public void sendWrite(Context context, String packname, byte[] bytes){
        System.out.println("Write");
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
    void sendBitmaps(Context context, String Action, int length, int destPos){
        System.out.println("sendBitmaps");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Bitmaps");
        intent.putExtra("length",length);
        intent.putExtra("destPos",destPos);
        context.sendBroadcast(intent);

    }
    byte[] toByteArray(InputStream input)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    int copy(InputStream input, OutputStream output)
            throws IOException
    {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    long copyLarge(InputStream input, OutputStream output)
            throws IOException
    {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    interface InputInterface{
        void end();
    }
}
