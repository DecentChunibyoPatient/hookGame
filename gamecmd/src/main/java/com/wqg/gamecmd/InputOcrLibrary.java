package com.wqg.gamecmd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.wqg.gamecmd.child_view.addOcrMaterial;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class InputOcrLibrary extends Thread {
    String TAG="InputOcrLibrary";
    String PackageName;
    Context context;

    InputInterface inputInterface;
    Bitmap[][]NumberBitmaps;
    String[][]Names;
    public InputOcrLibrary(Context context, String PackageName,Bitmap[][]NumberBitmaps,String[][]Names,InputInterface inputInterface) throws JSONException {
        this.context=context;

        this.PackageName=PackageName;
        this.inputInterface=inputInterface;
        this.NumberBitmaps=NumberBitmaps;
        this.Names=Names;
    }
    public InputOcrLibrary(Context context, String PackageName,File Dir,InputInterface inputInterface){
        this.context=context;
        this.PackageName=PackageName;
        this.inputInterface=inputInterface;
        if (Dir.isDirectory()){
            ArrayList<HashMap<Bitmap,String>>arrayList=new ArrayList<>();
            for (File file1:Dir.listFiles()){
                if (file1.isDirectory()){
                    arrayList.add(addOcrMaterial.load(file1));
                }
            }
            if (arrayList.size()>0){
                this.NumberBitmaps=new Bitmap[arrayList.size()][];
                this.Names=new String[arrayList.size()][];
                for (int i=0;i<arrayList.size();i++){
                    HashMap<Bitmap,String>hashMap=arrayList.get(i);
                    Bitmap[]bitmaps=new Bitmap[hashMap.size()];
                    String[]names=new String[hashMap.size()];
                    int b=0;
                    for (Bitmap bitmap:hashMap.keySet()){
                        bitmaps[b]=bitmap;
                        names[b]=hashMap.get(bitmap);
                        b++;
                    }
                    NumberBitmaps[i]=bitmaps;
                    Names[i]=names;
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            if (NumberBitmaps==null||Names==null)return;
            for (int i=0;i<NumberBitmaps.length;i++){
                sendMakeImageLib(context,PackageName,NumberBitmaps[i],Names[i]);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        send(context,PackageName,"MakeImageLibInput");
        inputInterface.end();
    }
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public void sendMakeImageLib(Context context, String Action, Bitmap[]bitmaps, String[]names) throws InterruptedException {
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","names");
        intent.putExtra("namesData",names);
        context.sendBroadcast(intent);
        for (int i=0;i<bitmaps.length;i++){
            sendWrite(context,Action, Bitmap2Bytes(bitmaps[i]));
            sendBitmaps(context,Action,bitmaps.length,i);
        }

        System.out.println("send");
        Intent intentLast=new Intent();
        intentLast.setAction(Action);
        intentLast.putExtra("name","MakeImageLib");
        context.sendBroadcast(intentLast);
    }
    public void send(Context context, String packname, String name){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(packname);
        intent.putExtra("name",name);
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
    void sendBitmaps(Context context, String Action, int length, int destPos){
        System.out.println("send");
        Intent intent=new Intent();
        intent.setAction(Action);
        intent.putExtra("name","Bitmaps");
        intent.putExtra("length",length);
        intent.putExtra("destPos",destPos);
        context.sendBroadcast(intent);

    }
    interface InputInterface{
        void end();
    }
}
