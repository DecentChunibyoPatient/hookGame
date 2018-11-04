package app.wqg.hookgame2.Thread;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import app.wqg.hookgame2.Api;
import app.wqg.hookgame2.Log;
import dalvik.system.DexClassLoader;

public class EyeThread extends HookThread {
    String TAG="EyeThread";
    boolean run=false;
    boolean isRun=false;
    int time;
    double bar=0.5d;
    JSONArray material;
    String CurrentPage;
    HaarState state;
    int err=0;
    public EyeThread(DexClassLoader dexClassLoader, HashMap<String, Bitmap> ResDrawadle, View view, String Material, HaarState state) throws JSONException {
        super(dexClassLoader, ResDrawadle, view);
        material=new JSONArray(Material);
        this.state=state;
    }
    public EyeThread(DexClassLoader dexClassLoader, HashMap<String, Bitmap> hashMap, View view,String Material,int time,HaarState state) throws JSONException {
        super(dexClassLoader, hashMap, view);
        material=new JSONArray(Material);
        this.time=time;
        this.state=state;
    }
    public EyeThread(DexClassLoader dexClassLoader, HashMap<String, Bitmap> hashMap, View view,JSONArray material,int time,HaarState state) throws JSONException {
        super(dexClassLoader, hashMap, view);
        this.material=material;
        this.time=time;
        this.state=state;
    }
    @Override
    public void run() {
        Log.d(TAG,"material="+material);
        try {
            run=true;
            state.start();
            while (run){
                time= Api.EyeThreadTime;
                isRun=true;
                String[]strings=null;
                Rect[]Rects=null;
                if (CurrentPage==null){
                    strings=new String[material.length()];
                    Rects=new Rect[material.length()];
                    for(int i=0;i<material.length();i++){
                        JSONObject jsonObject=material.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        strings[i]=name;
                        Rects[i]=JSONObjectToRect(jsonObject.getJSONObject("rect"));
                    }
                }else {
                    for(int i=0;i<material.length();i++){
                        JSONObject jsonObject=material.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        if (name.equals(CurrentPage)){
                            try {
                                JSONArray jsonArray=jsonObject.getJSONArray("child");
                                strings=new String[jsonArray.length()+1];
                                Rects=new Rect[jsonArray.length()+1];
                                strings[0]=CurrentPage;
                                Rects[0]=JSONObjectToRect(jsonObject.getJSONObject("rect"));
                                for (int s=0;s<jsonArray.length();s++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(s);
                                    String childNmae=jsonObject1.getString("name");
                                    strings[s+1]=childNmae;
                                    Rects[s+1]=JSONObjectToRect(jsonObject1.getJSONObject("rect"));
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                strings=new String[]{CurrentPage};
                                Rects=new Rect[]{JSONObjectToRect(jsonObject.getJSONObject("rect"))};
                            }

                            break;
                        }
                    }
                }
                Bitmap bitmap=getBitmap();
                if (bitmap==null)continue;
                int [][]ints=haarRect(bitmap,getResBitmaps(strings),Rects);
                Rect[] rects =intArrayToRect(bitmap,ints,strings,ResDrawadle);
                int []similar= getSimilar(ints) ;

                if (CurrentPage==null){
                    int best=Integer.MAX_VALUE;
                    int bestI=0;
                    for (int i=0;i<similar.length;i++){
                      try {
                          if(Double.parseDouble(percent(similar[i],rects[i]))<bar&&similar[i]<best){
                              best=similar[i];
                              bestI=i;
                          }
                      }catch (Exception e){
                          e.printStackTrace();
                      }
                    }
                    if (best!=Integer.MAX_VALUE){
                        CurrentPage=strings[bestI];
                        state.findPage(bestI,ToJSONArray(strings,rects,similar));
                    }else {
                        state.unfindPage(ToJSONArray(strings,rects,similar));
                        //ERR
                    }

                }else {
                    if (Double.parseDouble(percent(similar[0],rects[0]))<bar){
                        state.findCurrentPage(ToJSONArray(strings,rects,similar));
                    }else {
                        CurrentPage=null;
                        state.unfindCurrentPage(ToJSONArray(strings,rects,similar));
                        //ERR
                    }
                }
                Thread.sleep(time);
            }
        }catch (Exception e){
            e.printStackTrace();
            state.stop();
            isRun=false;
            Log.d(TAG,"end");
            new Thread(this).start();
        }
        state.stop();
        isRun=false;
        Log.d(TAG,"end");
    }
    public void setMaterial(JSONArray material){
        this.material=material;
    }
    public void Stop(){
        run=false;
    }
    public void setTime(int time){
        this.time=time;
    }
    JSONArray ToJSONArray(String[]names,Rect[] rects,int []similar) throws JSONException {
        JSONArray jsonArray=new JSONArray();
        for (int i=0;i<names.length;i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",names[i]);
            jsonObject.put("similar",similar[i]);
            jsonObject.put("rect",RectToJSONObject(rects[i]));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    JSONObject RectToJSONObject(Rect rect) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("left",rect.left);
        jsonObject.put("top",rect.top);
        jsonObject.put("right",rect.right);
        jsonObject.put("bottom",rect.bottom);
        return jsonObject;
    }
    Rect[] intArrayToRect(Bitmap bitmap,int [][]ints,String[] strings,HashMap<String,Bitmap>hashMap){
        Rect[] rects=new Rect[ints.length];
        for (int i=0;i<ints.length;i++){
            int []ints1=ints[i];
            String name=strings[i];
            Bitmap bitmap1=hashMap.get(name);
            int pix=ints1[1];
            int left=pix%bitmap.getWidth();
            int top=pix/bitmap.getWidth();
            int right=left+bitmap1.getWidth();
            int bottom=top+bitmap1.getHeight();

            rects[i]=new Rect( left,  top,  right,  bottom);
        }
        return rects;
    }
    int[] getSimilar(int [][]ints){
        int []ints1=new int[ints.length];
        for (int i=0;i<ints1.length;i++){
            ints1[i]=ints[i][0];
        }
        return ints1;
    }
}
