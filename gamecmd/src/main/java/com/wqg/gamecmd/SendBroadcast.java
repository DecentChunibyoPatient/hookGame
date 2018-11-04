package com.wqg.gamecmd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendBroadcast extends BroadcastReceiver {
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    void sendhongbao(Context context, String Action, int []ids){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","hongbao");
        intent.putExtra("id",ids);
        context.sendBroadcast(intent);
    }
    void sendhongbao(Context context, String Action, int []ids, String action, String action1, String action2){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","hongbao");
        intent.putExtra("id",ids);
        intent.putExtra("action",action);
        context.sendBroadcast(intent);
    }
    public void sendhongbao(Context context, String Action, int[] ids, String action){
        System.out.println("hongbao");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","hongbao");
        intent.putExtra("id",ids);
        intent.putExtra("action",action);
        context.sendBroadcast(intent);
    }
    public void sendIntoRoom(Context context, String Action, int[] ids){
        System.out.println("sendIntoRoom");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","IntoRoom");
        intent.putExtra("id",ids);
        context.sendBroadcast(intent);
    }
    public void sendhome(Context context, String Action, int[] ids, String action){
        System.out.println("sendhome");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","home");
        intent.putExtra("id",ids);
        intent.putExtra("action",action);
        context.sendBroadcast(intent);
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
    public void sendHashMap(Context context, String packname, String name, int sampleName){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name",name);
        intent.putExtra("sampleName",sampleName);
        context.sendBroadcast(intent);
    }
   public void sendMakeImageLib(Context context, String Action, Bitmap[]bitmaps, String[]names) throws InterruptedException {
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","names");
        intent.putExtra("namesData",names);
        context.sendBroadcast(intent);
        Thread.sleep(100);
        for (int i=0;i<bitmaps.length;i++){
            sendData(context,Action, Bitmap2Bytes(bitmaps[i]));
            Thread.sleep(100);
            sendBitmaps(context,Action,bitmaps.length,i);
            Thread.sleep(100);
        }
        System.out.println("send");
        Intent intentLast=new Intent();
        intentLast.setAction(Action);
        intentLast.putExtra("name","MakeImageLib");
        context.sendBroadcast(intentLast);
    }
    void sendBitmaps(Context context, String Action, int length, int destPos){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Bitmaps");
        intent.putExtra("length",length);
        intent.putExtra("destPos",destPos);
        context.sendBroadcast(intent);

    }
    public void sendAllow(Context context, String packname, String name, int bets, int[]bestID, int[]bestbigID, int[]isOptional, int[]isSelect, int[]SampleRes){
        System.out.println("send Allow");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name","Allow");
        intent.putExtra("data",name);
        intent.putExtra("best",bets);
        intent.putExtra("bestID",bestID);
        intent.putExtra("bestbigID",bestbigID);
        intent.putExtra("isOptional",isOptional);
        intent.putExtra("isSelect",isSelect);
        intent.putExtra("SampleRes",SampleRes);
        context.sendBroadcast(intent);
    }
    public void sendsendhongbao(Context context, String Action, int []ids, String number, String fileName){
        System.out.println("sendsendhongbao");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","sendhongbao");
        intent.putExtra("id",ids);
        intent.putExtra("number",number);
        intent.putExtra("fileName",fileName);
        context.sendBroadcast(intent);
    }

    public void sendgethongbao(Context context, String Action, int []ids, String ID, String Number){
        System.out.println("sendgethongbao");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","gethongbao");
        intent.putExtra("id",ids);
        intent.putExtra("ID",ID);
        intent.putExtra("Number",Number);
        context.sendBroadcast(intent);
    }
    public void sendTest(Context context, String Action, int[] ids){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","test");
        intent.putExtra("id",ids);
        context.sendBroadcast(intent);
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
                intent.putExtra("name","send");
                intent.putExtra("length",bytes.length);
                intent.putExtra("destPos",i*length);
                intent.putExtra("data",bytes1);
                context.sendBroadcast(intent);
            }
            if (bytes.length%length>0){
                byte[]bytes1=new byte[bytes.length%length];
                System.arraycopy(bytes,bytes.length-bytes1.length,bytes1,0,bytes1.length);
                Intent intent=new Intent();
                intent.setAction(packname);
                intent.putExtra("name","send");
                intent.putExtra("length",bytes.length);
                intent.putExtra("destPos",bytes.length-bytes1.length);
                intent.putExtra("data",bytes1);
                context.sendBroadcast(intent);
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
    public void sendData(Context context, String packname, byte[] bytes){
        System.out.println("send");
        int length=500000;
        for (int i=0;i<bytes.length/length;i++){
            byte[]bytes1=new byte[length];
            System.arraycopy(bytes,i*length,bytes1,0,bytes1.length);
            Intent intent=new Intent();
            intent.setAction(packname);
            intent.putExtra("name","send");
            intent.putExtra("length",bytes.length);
            intent.putExtra("destPos",i*length);
            intent.putExtra("data",bytes1);
            context.sendBroadcast(intent);
        }
        if (bytes.length%length>0){
            byte[]bytes1=new byte[bytes.length%length];
            System.arraycopy(bytes,bytes.length-bytes1.length,bytes1,0,bytes1.length);
            Intent intent=new Intent();
            intent.setAction(packname);
            intent.putExtra("name","send");
            intent.putExtra("length",bytes.length);
            intent.putExtra("destPos",bytes.length-bytes1.length);
            intent.putExtra("data",bytes1);
            context.sendBroadcast(intent);
        }
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

    @Override
    public void onReceive(Context context, Intent intent) {

    }

}
