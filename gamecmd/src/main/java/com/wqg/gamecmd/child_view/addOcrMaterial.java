package com.wqg.gamecmd.child_view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wqg.gamecmd.CmdWindow;
import com.wqg.gamecmd.FileSaveAndLoad;
import com.wqg.gamecmd.LoadSO;
import com.wqg.gamecmd.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class addOcrMaterial extends LinearLayout {
    ListView listView;
    MBaseAdapter mBaseAdapter=new MBaseAdapter();
    public addOcrMaterial(Context context, final Bitmap bitmap, Bitmap[]bitmaps) {
        super(context);
        View.inflate(context, R.layout.add_ocr_material,this);
        final ImageView imageView=findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        listView=findViewById(R.id.listView);

        listView.setAdapter(mBaseAdapter);
        LinearLayout linearLayout=findViewById(R.id.linearlayout);

        for (Bitmap bitmap1:bitmaps){
            final ImageView imageView1=new ImageView(getContext());
            imageView1.setImageBitmap(bitmap1);
            imageView1.setOnClickListener(new OnClickListener() {
                boolean aBoolean=true;
                @Override
                public void onClick(final View v) {
                    System.out.println("imageView1 onClick");
                    if (aBoolean){
                        aBoolean=false;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    if (v instanceof ImageView){
                                        final Bitmap bitmap=((BitmapDrawable) ((ImageView)v).getDrawable()).getBitmap();
                                        if (mBaseAdapter.getIsEnabled()>=0){
                                            mBaseAdapter.addData(mBaseAdapter.getIsEnabled(),mBaseAdapter.createName(mBaseAdapter.getIsEnabled(),"number"),bitmap);
                                        }else {
                                            mBaseAdapter.addData(new String[]{"number0"},new Bitmap[]{bitmap});
                                        }
                                        new Handler(getContext().getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mBaseAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                aBoolean=true;
                            }
                        }.start();
                    }
                }
            });
            linearLayout.addView(imageView1);
        }
        findViewById(R.id.load).setOnClickListener(new OnClickListener() {
            boolean aBoolean=true;
            @Override
            public void onClick(View v) {
                mBaseAdapter.removeAll();
                mBaseAdapter.notifyDataSetChanged();
                if (aBoolean){
                    aBoolean=false;
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                File file=new File(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/Ocr");
                                if (file.isDirectory()){
                                    for (File file1:file.listFiles()){
                                        if (file1.isDirectory()){
                                            mBaseAdapter.addData(load(file1));
                                        }
                                        new Handler(getContext().getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mBaseAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            aBoolean=true;
                        }
                    }.start();
                }
            }
        });
        findViewById(R.id.save).setOnClickListener(new OnClickListener() {
            boolean aBoolean=true;
            @Override
            public void onClick(View v) {
                if (aBoolean){
                    aBoolean=false;
                    new Thread(){
                        @Override
                        public void run() {

                            try {
                                ArrayList<HashMap<Bitmap,String>>arrayLists=mBaseAdapter.getArrayLists();
                                for (int i=0;i<arrayLists.size();i++){
                                    String DirName="library"+i;
                                    HashMap<Bitmap,String>hashMap=arrayLists.get(i);
                                    for (Bitmap bitmap1:hashMap.keySet()){
                                        FileSaveAndLoad.saveBitmap(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName+"/Ocr/"+DirName,hashMap.get(bitmap1)+".png",bitmap1);
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            aBoolean=true;
                        }
                    }.start();
                }
            }
        });
        findViewById(R.id.ocr).setOnClickListener(new OnClickListener() {
            boolean aBoolean=true;
            @Override
            public void onClick(View v) {
                if (aBoolean){
                    aBoolean=false;
                    if (mBaseAdapter.getIsEnabled()>=0)
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    ArrayList<HashMap<Bitmap,String>>arrayList=mBaseAdapter.getArrayLists();
                                    Bitmap[][]NumberBitmaps;
                                    String[][]Names;
                                    if (arrayList.size()>0){
                                        NumberBitmaps=new Bitmap[arrayList.size()][];
                                        Names=new String[arrayList.size()][];
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
                                        LoadSO.MakeImageLibS(CmdWindow.dexClassLoader,NumberBitmaps, Names);
                                    }
                                    String []strings=LoadSO.OcrData(CmdWindow.dexClassLoader,bitmap,new int[][]{new int[]{0,0,bitmap.getWidth(),bitmap.getHeight()}},mBaseAdapter.getIsEnabled());
                                    final StringBuffer stringBuffer=new StringBuffer();
                                    for (String string:strings){
                                        stringBuffer.append(string);
                                    }
                                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),stringBuffer.toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                aBoolean=true;
                            }
                        }.start();
                }
            }
        });

    }
   static public HashMap<Bitmap,String> load(File file){
       Log.d("load","file= "+file);
        File[]files=file.listFiles();
        if (files.length>0){
            HashMap<Bitmap,String>hashMap=new HashMap<>();
            for (File file1:files){
                if (file1.isFile()){
                    String[] fileName=file1.getName().split("[.]");
                    if (fileName[fileName.length-1].equalsIgnoreCase("png")){
                        String name=file1.getName().replace("."+fileName[fileName.length-1],"");
                        try {
                            hashMap.put(FileSaveAndLoad.loadBitmap(file1.getPath()),name );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return hashMap;
        }
      return null;
    }
    class MBaseAdapter extends BaseAdapter{
        int isEnabled=-1;
        ArrayList<HashMap<Bitmap,String>>arrayLists=new ArrayList<>();

        @Override
        public int getCount() {
            return arrayLists.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public int getIsEnabled() {
            return isEnabled;
        }

        public void setIsEnabled(int isEnabled) {
            this.isEnabled = isEnabled;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HashMap<Bitmap,String>hashMap=arrayLists.get(position);
            View view=View.inflate(getContext(),R.layout.add_ocr_material_item,null);
            if (position==isEnabled){
                view.setBackgroundResource(R.drawable.shape3);
            }
            TextView num=view.findViewById(R.id.itme_number);
            num.setText(position+"");
            num.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("onItemClick");
                    if (position==mBaseAdapter.getIsEnabled()){
                        mBaseAdapter.setIsEnabled(-1);
                    }else {
                        mBaseAdapter.setIsEnabled(position);
                    }
                    mBaseAdapter.notifyDataSetChanged();
                }
            });
            LinearLayout linearLayout=view.findViewById(R.id.linearLayout);
            for (Bitmap key:hashMap.keySet()){
                View child=View.inflate(getContext(),R.layout.add_ocr_material_item_child,null);
                ImageView imageView=child.findViewById(R.id.imageView);
                imageView.setImageBitmap(key);
                TextView textView=child.findViewById(R.id.name);
                textView.setText(hashMap.get(key));
                imageView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final Bitmap bitmap=((BitmapDrawable) ((ImageView)v).getDrawable()).getBitmap();
                        remove(position,bitmap);
                        if (arrayLists.get(position).size()==0){
                            isEnabled=-1;
                            arrayLists.remove(position);
                        }
                        notifyDataSetChanged();
                        return true;
                    }
                });
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bitmap bitmap=((BitmapDrawable) ((ImageView)v).getDrawable()).getBitmap();
                        final EditText editText=new EditText(getContext());
                        editText.setText(arrayLists.get(position).get(bitmap));
                        editText.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.hasFocus()){
                                    v.setFocusable(false);
                                    requestFocus();
                                }else {
                                    v.setFocusable(true);
                                    requestFocus();
                                }
                            }
                        });
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("").
                                setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.out.println(editText.getText().toString());
                                        addPut(position,editText.getText().toString(),bitmap);
                                        System.out.println(arrayLists.get(position).get(bitmap));
                                        notifyDataSetChanged();
                                    }
                                }).
                                setView(editText).
                                create();
                        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        alertDialog.show();

                    }
                });
                linearLayout.addView(child);
            }
            return view;
        }

        public ArrayList<HashMap<Bitmap,String>> getArrayLists() {
            return arrayLists;
        }

        public void addData(String[]names, Bitmap[]bitmaps){
            if (names.length==bitmaps.length){
                HashMap<Bitmap,String>hashMap=new HashMap<>();
                for (int i=0;i<names.length;i++){
                    hashMap.put(bitmaps[i],names[i]);
                }
                 arrayLists.add(hashMap);
                new Handler(getContext().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

        }
        public void addData(HashMap<Bitmap,String>hashMap){
            if (hashMap!=null){
                arrayLists.add(hashMap);
            }
        }
        
        public void addData(int position,String name, Bitmap bitmap){
            if (!arrayLists.get(position).containsKey(bitmap)){
                HashMap<Bitmap,String>hashMap=arrayLists.remove(position);
                hashMap.put(bitmap,name);
                arrayLists.add(position,hashMap);
            }
        }
        public void addPut(int position,String name, Bitmap bitmap){
            HashMap<Bitmap,String>hashMap=arrayLists.remove(position);
            hashMap.put(bitmap,name);
            arrayLists.add(position,hashMap);
        }
        public String createName(String string){
            for (int i=0;true;i++){
                String name=string+i;
                for (HashMap<Bitmap,String>hashMap:arrayLists){
                    for (String key:hashMap.values()){
                        if (!name.equals(key))return name;
                    }
                }
            }
        }
        public String createName(int position,String string){
            HashMap<Bitmap,String>hashMap=arrayLists.get(position);
            for (int i=0;true;i++){
                String name=string+i;
                boolean b=true;
                for (String key:hashMap.values()){
                    System.out.println("name="+name+" key="+key+" "+name.equals(key));
                    if (b=name.equals(key))break;
                }
                if (!b){
                    return name;
                }
            }
        }
        public void remove(int position,Bitmap key){
            arrayLists.get(position).remove(key);
        }
        public void removeAll(){
            arrayLists=new ArrayList<>();
        }
    }
}
