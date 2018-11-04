package com.wqg.gamecmd.child_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.wqg.gamecmd.BA;
import com.wqg.gamecmd.CmdWindow;
import com.wqg.gamecmd.FileSaveAndLoad;
import com.wqg.gamecmd.ImageEditInterface;
import com.wqg.gamecmd.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageEdit extends RelativeLayout {
    static public String TAG="ImageEdit";
    ArrayList<Bitmap> arrayList=new ArrayList<>();
    ArrayList<Rect>arrayListRect=new ArrayList<>();
    ListView listView;
    BA ba;
    ImageEditInterface imageEditInterface;
    Bitmap bitmap;
    CropImageView cropImageView;
    public ImageEdit(Context context, final Bitmap bitmap, final ImageEditInterface imageEditInterface) {
        super(context);
        this.bitmap=bitmap;
        this.imageEditInterface=imageEditInterface;
        inflate(context, R.layout.activity_main,this);
        ba=new BA(context);
        listView=findViewById(R.id.listview);
        listView.setLayoutParams(new LinearLayout.LayoutParams(35, LinearLayout.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(5);
        listView.setDivider(null);
        listView.setAdapter(ba);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                imageEditInterface.onItemLongClick( parent,  view,  position,  id);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageEditInterface.onItemClick(listView,ba.getHashMap(), parent,  view,  position,  id);
                Log.d(TAG,"position="+position);
            }
        });
        CheckBox checkBox=findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.MATCH_PARENT);
                    listView.setLayoutParams(layoutParams);
                }else {
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(35, LinearLayout.LayoutParams.MATCH_PARENT);
                    listView.setLayoutParams(layoutParams);
                }
            }
        });
        setLayoutParams(new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
        cropImageView=findViewById(R.id.cropImageView);
        // start picker to get image for cropping and then use the image in cropping activity
        cropImageView.setImageBitmap(bitmap);
        System.out.println(cropImageView.getMaxZoom());
        cropImageView.setMaxZoom(10);
        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

            }
        });

        cropImageView.setOnSetCropOverlayReleasedListener(new CropImageView.OnSetCropOverlayReleasedListener() {
            @Override
            public void onCropOverlayReleased(Rect rect) {
                imageEditInterface.onCropOverlayReleased( rect);
                System.out.println("onCropOverlayReleased"+rect.left+" "+rect.top+" "+rect.right+" "+rect.bottom+"  cropImageView.getCropWindowRect()"+ cropImageView.getCropWindowRect());
            }
        });
        findViewById(R.id.Cutting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // subscribe to async event using cropImageView.setOnCropImageCompleteListener(listener)
                cropImageView.getCroppedImageAsync();
                Bitmap cropped = cropImageView.getCroppedImage();
                Rect rect=cropImageView.getCropRect();
                arrayList.add(cropImageView.getImageBitmap());
                arrayListRect.add(rect);
                Log.d(TAG,"arrayListRect.add(rect);rect="+rect);
                cropImageView.setImageBitmap(cropped);
                imageEditInterface.Cutting(cropped, rect);
             /*   ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    File file=new File("/mnt/shared/Image",str+".png");
                    FileOutputStream outputStream= new FileOutputStream(file);
                    outputStream.write(baos1.toByteArray());
                    outputStream.close();
                    System.out.println("保存成功"+file.getPath());
                    // Toast.makeText(context,"保存成功"+file.getPath(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        findViewById(R.id.Back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size()!=0&&arrayListRect.size()!=0){
                    Rect rect=arrayListRect.remove(arrayListRect.size()-1);
                    Bitmap bitmap1=arrayList.remove(arrayList.size()-1);
                    cropImageView.setImageBitmap(bitmap1);
                    imageEditInterface.Back(arrayList,bitmap1, rect);
                }

            }
        });

        findViewById(R.id.load).setOnClickListener(new OnClickListener() {
            boolean aBoolean=true;
            @Override
            public void onClick(View v) {
                imageEditInterface.loadStart();
                if (aBoolean){
                    aBoolean=false;
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                ba.load(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName,"test.txt");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            imageEditInterface.loadEnd();
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
                imageEditInterface.saveStart();
                if (aBoolean){
                    aBoolean=false;
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                ba.save(FileSaveAndLoad.Dir+"/"+ CmdWindow.packageName,"test.txt");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            imageEditInterface.saveEnd();
                            aBoolean=true;
                        }
                    }.start();
                }

            }
        });
        Log.d(TAG,"onClick: v.isFocusable()="+cropImageView.isFocusable());
        Log.d(TAG,"onClick: v.hasFocus()="+cropImageView.hasFocus());
        findViewById(R.id.add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = cropImageView.getCroppedImage();
                Rect rect;
                if (arrayListRect.size()!=0){
                    int left=0;
                    int top=0;
                    int right=0;
                    int bottom=0;
                    for (int i=0;i<arrayListRect.size();i++){
                        Rect rect1=arrayListRect.get(i);
                        left+=rect1.left;
                        top+=rect1.top;
                    }
                    Rect rect1=arrayListRect.get(arrayListRect.size()-1);
                    right=left+rect1.right-rect1.left;
                    bottom=top+rect1.bottom-rect1.top;
                    rect=new Rect( left,  top,  right,  bottom);
                }else {
                    rect=cropImageView.getCropRect();
                }

                imageEditInterface.add(cropped,rect);
                try {
                    Log.d(TAG,""+ba.isChecked());
                    if (ba.isChecked()){
                        ba.addChild(ba.getChecked(),cropped,rect);
                    }else {
                        ba.add(cropped,rect);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.addOcrMaterial).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = cropImageView.getCroppedImage();
                Rect rect;
                if (arrayListRect.size()!=0){
                    int left=0;
                    int top=0;
                    int right=0;
                    int bottom=0;
                    for (int i=0;i<arrayListRect.size();i++){
                        Rect rect1=arrayListRect.get(i);
                        left+=rect1.left;
                        top+=rect1.top;
                    }
                    Rect rect1=arrayListRect.get(arrayListRect.size()-1);
                    right=left+rect1.right-rect1.left;
                    bottom=top+rect1.bottom-rect1.top;
                    rect=new Rect( left,  top,  right,  bottom);
                }else {
                    rect=cropImageView.getCropRect();
                }
                imageEditInterface.addOcrMaterial(cropped,rect);
            }
        });
    }
    public void setBitmap(Bitmap bitmap){
        arrayList=new ArrayList<>();
        arrayListRect=new ArrayList<>();
        this.bitmap=bitmap;
        cropImageView.setImageBitmap(bitmap);
    }
    public BA getBaseAdapter(){
        return ba;
    }
}
