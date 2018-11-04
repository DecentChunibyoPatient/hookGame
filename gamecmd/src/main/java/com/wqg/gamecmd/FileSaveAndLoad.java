package com.wqg.gamecmd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FileSaveAndLoad {
    static public String Dir="/mnt/shared/Image";
    static public void FileSave(String dir, HashMap<String,Bitmap>hashMap){
        for (String name:hashMap.keySet()){
            saveBitmap(dir,name+".png",hashMap.get(name));
        }
    }
    static public HashMap<String,Bitmap> FileLoad(String dir) throws IOException {
        HashMap<String,Bitmap>hashMap=new HashMap<>();
        File file=new File(dir);
        if (file.isDirectory()){
            for (File file1:file.listFiles()){
                if (file1.isFile()){
                    String []fileName=file1.getName().split("[.]");
                    if (fileName[fileName.length-1].equalsIgnoreCase("png")){
                        String name=file1.getName().replace("."+fileName[fileName.length-1],"");
                        Bitmap bitmap=loadBitmap(file1.getPath());
                        hashMap.put(name,bitmap);
                    }
                }
            }

        }
        return hashMap;
    }
    static public JSONArray loadJSONArray(String file) throws IOException, JSONException {
        FileInputStream fileInputStream=new FileInputStream(new File(file));
        byte[]bytes=toByteArray(fileInputStream);
        return new JSONArray(new String(bytes));
    }
    static public void saveJSONArray(String dir, String fileName, JSONArray jsonArray){
        try {
                File file=new File(dir,fileName);
            // 如果文件夹不存在，创建文件夹
            if (!createDir(dir)) {
                System.out.println("创建文件夹失败!");
            }
            FileOutputStream outputStream= new FileOutputStream(file);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
            System.out.println("保存成功"+file.getPath());
            // Toast.makeText(context,"保存成功"+file.getPath(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public Bitmap loadBitmap(String file) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(file));
        byte[]bytes=toByteArray(fileInputStream);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    static public void saveBitmap(String dir, String fileName, Bitmap cropped){
               ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                try {
                    File file=new File(dir,fileName);
                    // 如果文件夹不存在，创建文件夹
                    if (!createDir(dir)) {
                        System.out.println("创建文件夹失败!");
                    }
                    FileOutputStream outputStream= new FileOutputStream(file);
                    outputStream.write(baos1.toByteArray());
                    outputStream.close();
                    System.out.println("保存成功"+file.getPath());
                    // Toast.makeText(context,"保存成功"+file.getPath(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    /**
     * 创建指定路径的文件夹，并返回执行情况 ture or false
     * @param filePath
     * @return
     */
    public static boolean createDir(String filePath) {
        File fileDir = new File(filePath); // 生成文件流对象
        boolean bRet = true;
        // 如果文件不存在，创建文件
        if (!fileDir.exists()) {
            // 获得文件或文件夹名称
            String[] aDirs = filePath.split("/");
            StringBuffer strDir = new StringBuffer();
            for (int i = 0; i < aDirs.length; i++) {
                // 获得文件上一级文件夹
                fileDir = new File(strDir.append("/").append(aDirs[i]).toString());
                // 是否存在
                if (!fileDir.exists()) {
                    // 不存在创建文件失败返回FALSE
                    if (!fileDir.mkdir()) {
                        bRet = false;
                        break;
                    }
                }
            }
        }

        return bRet;
    }
    public static byte[] toByteArray(InputStream input)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    public static int copy(InputStream input, OutputStream output)
            throws IOException
    {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    public static long copyLarge(InputStream input, OutputStream output)
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
}
