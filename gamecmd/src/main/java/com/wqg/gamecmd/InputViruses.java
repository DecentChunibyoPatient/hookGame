package com.wqg.gamecmd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static com.wqg.gamecmd.CmdWindow.Progress;
import static com.wqg.gamecmd.CmdWindow.waveProgress;

public class InputViruses extends Thread {
    String TAG="InputViruses";
    String PackageName;
    Context context;
    public InputViruses(Context context, String PackageName){
        this.context=context;
        this.PackageName=PackageName;
    }
    public void run() {
     try {
         InputStream inputStream = context.getAssets().open("app-debug.apk");
         byte[]bytes=toByteArray(inputStream);
         inputStream.close();
         sendWrite(context,PackageName,bytes);
         sendInit(context,PackageName);
         send(context,PackageName,"DexClassLoader");
     }catch (Exception e){}

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
