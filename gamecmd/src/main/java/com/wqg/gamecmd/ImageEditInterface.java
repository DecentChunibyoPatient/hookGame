package com.wqg.gamecmd;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface ImageEditInterface {
    void onItemLongClick(AdapterView<?> parent, View view, int position, long id);
    void onItemClick(ListView listView, HashMap<String,Bitmap>hashMap, AdapterView<?> parent, View view, int position, long id);
    void onCropOverlayReleased(Rect rect);
    void Cutting(Bitmap cropped,Rect rect);
    void Back(ArrayList<Bitmap> arrayList,Bitmap bitmap,Rect rect);
    void add(Bitmap cropped,Rect rect);
    void addOcrMaterial(Bitmap cropped,Rect rect);
    void loadStart();
    void loadEnd();
    void saveStart();
    void saveEnd();
}
