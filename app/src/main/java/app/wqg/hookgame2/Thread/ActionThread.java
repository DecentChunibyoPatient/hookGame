package app.wqg.hookgame2.Thread;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.wqg.hookgame2.Log;
import dalvik.system.DexClassLoader;

public class ActionThread extends HookThread {
    final static private String TAG="ActionThread";
    boolean run=false;
    boolean isRun=false;
    JSONObject  data=new JSONObject();
    JSONArray jsonArrayAction;
    JSONObject intention;
    JSONArray thisStatePage;
    ActionState actionState;

    public ActionThread(DexClassLoader dexClassLoader, HashMap<String, Bitmap> ResDrawadle, View view, JSONArray thisStatePage, JSONObject intention, String action,ActionState actionState) throws JSONException {
        super(dexClassLoader, ResDrawadle, view);
        jsonArrayAction=new JSONArray(action);
        this.actionState=actionState;
        this.intention=intention;
        this.thisStatePage=thisStatePage;
    }
    public ActionThread(DexClassLoader dexClassLoader, HashMap<String, Bitmap> ResDrawadle, View view,JSONArray thisStatePage,  JSONObject intention, JSONArray jsonArrayAction,ActionState actionState){
        super(dexClassLoader, ResDrawadle, view);
        this.jsonArrayAction=jsonArrayAction;
        this.actionState=actionState;
        this.intention=intention;
        this.thisStatePage=thisStatePage;
    }
    @Override
    public void run() {
        run=true;
        isRun=true;
        try {
            Log(TAG+" jsonArrayAction="+jsonArrayAction);
            Log(TAG+" intention="+intention);
            Log(TAG+" thisStatePage="+thisStatePage);
            for (int a=0;a<jsonArrayAction.length()&&run;a++){
                JSONObject Action=jsonArrayAction.getJSONObject(a);
                Log(TAG+Action.getString("Action")+" start ");
                selector(thisStatePage,Action,actionState);
                Log(TAG+Action.getString("Action")+" end ");
            }
            actionState.Completion(intention,jsonArrayAction,data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        run=false;
        isRun=false;
    }
    public void Stop(){
        run=false;
    }
    void selector(JSONArray thisStatePage,JSONObject Action,ActionState actionState) throws JSONException, InterruptedException {
        Log.d(TAG,Action.getString("Action"));
        switch (Action.getString("Action")){
            case ACTION_HAAR:
            {
                String HaarMode=Action.getString("HaarMode");
                switch (HaarMode){
                    case HAAR_DEFAULT:
                    {
                        JSONArray MaterialName=Action.getJSONArray("MaterialName");
                        int lenth=MaterialName.length();
                        Bitmap []bitmaps=new Bitmap[lenth];
                        for (int i=0;i<bitmaps.length;i++){if (!run)return;
                            bitmaps[i]=getResBitmap(MaterialName.getString(i));
                        }
                        int[][]ints=haar(getBitmap(),bitmaps);
                        JSONObject haar=null;
                        try {
                            haar=data.getJSONObject("haar");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (haar==null)haar=new JSONObject();
                        for (int i=0;i<ints.length;i++){if (!run)return;
                            int []ints1=ints[i];
                            JSONObject describe=new JSONObject();
                            describe.put("similarity",ints1[0]);
                            describe.put("x",ints1[1]%bitmaps[i].getWidth());
                            describe.put("y",ints1[1]/bitmaps[i].getWidth());
                            describe.put("Width",bitmaps[i].getWidth());
                            describe.put("Height",bitmaps[i].getHeight());
                            haar.put(MaterialName.getString(i),describe);
                        }
                        data.put("haar",haar);

                    }break;
                    case HAAR_CHECK:
                    {
                        JSONArray MaterialName=Action.getJSONArray("MaterialName");
                        int lenth=MaterialName.length();
                        Bitmap []bitmaps=new Bitmap[lenth];
                        for (int i=0;i<bitmaps.length;i++){if (!run)return;
                            bitmaps[i]=getResBitmap(MaterialName.getString(i));
                        }
                        int[][]ints=haar(getBitmap(),bitmaps);
                        JSONObject haar=data.getJSONObject("haar");
                        if (haar==null)haar=new JSONObject();
                        for (int i=0;i<ints.length;i++){if (!run)return;
                            int []ints1=ints[i];
                            JSONObject describe=new JSONObject();
                            describe.put("similarity",ints1[0]);
                            describe.put("x",ints1[1]%bitmaps[i].getWidth());
                            describe.put("y",ints1[1]/bitmaps[i].getWidth());
                            describe.put("Width",bitmaps[i].getWidth());
                            describe.put("Height",bitmaps[i].getHeight());
                            haar.put(MaterialName.getString(i),describe);
                        }
                        data.put("haar",haar);
                    }break;
                }

            } break;
            case ACTION_OCR:
            {
                int Materiallibrary=Action.getInt("Materiallibrary");
                JSONArray jsonArrayRect=Action.getJSONArray("Rect");
                String OcrName=Action.getString("OcrName");
                Log.d(TAG,"Materiallibrary="+Materiallibrary+" jsonArrayRect="+jsonArrayRect+" OcrName="+OcrName);
                int []Rect=new int[jsonArrayRect.length()];
                for (int i=0;i<Rect.length;i++){if (!run)return;
                    Rect[i]=jsonArrayRect.getInt(i);
                }
                String []strings=OCR(getBitmap(),Rect,Materiallibrary);
                JSONArray jsonArray=new JSONArray();
                for (String string:strings){if (!run)return;
                    jsonArray.put(string);
                    Log.d(TAG,"strings="+string);
                }
                data.put(OcrName,jsonArray);
            }break;
            case ACTION_BAIDU_OCR:
            {
                JSONArray jsonArrayRect=Action.getJSONArray("Rect");
                String OcrName=Action.getString("OcrName");
                int []Rect=new int[jsonArrayRect.length()];
                for (int i=0;i<Rect.length;i++){if (!run)return;
                    Rect[i]=jsonArrayRect.getInt(i);
                }
                String []strings=baiduOCR(getBitmap(),Rect);
                JSONArray jsonArray=new JSONArray();
                for (String string:strings){if (!run)return;
                    jsonArray.put(string);
                    Log.d(TAG,"strings="+string);
                }
                data.put(OcrName,jsonArray);
            }break;
            case ACTION_INPUT:
            {

                String InputType=Action.getString("InputType");
                switch (InputType){
                    case INPUT_DEFAULT:
                    {
                        Input(Action.getString("text"));
                    }break;
                    case INPUT_OCR:
                    {
                        StringBuffer stringBuffer=new StringBuffer();
                        String OcrName=Action.getString("OcrName");
                        try {
                            JSONArray jsonArray=data.getJSONObject("ocr").getJSONArray(OcrName);
                            for (int i=0;i<jsonArray.length();i++){if (!run)return;
                                stringBuffer.append(jsonArray.get(i));
                            }
                            Input(stringBuffer.toString());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }break;
                }


            }break;
            case ACTION_TOUCH_EVENT:
            {
                String TouchEventType=Action.getString("TouchEventType");

                switch (TouchEventType){
                    case TOUCH_EVENT_DEFAULT:
                    {
                        JSONObject TOUCH_EVENT=Action.getJSONObject("TOUCH_EVENT");
                        final int action=TOUCH_EVENT.getInt("action");
                        final float x= (float) TOUCH_EVENT.getDouble("x");
                        final float y= (float) TOUCH_EVENT.getDouble("y");
                        onTouchEvent(action,x,y);
                    }break;
                    case TOUCH_EVENT_PLAY:
                    {
                        JSONArray TouchEventRecording=Action.getJSONArray("TouchEventRecording");
                        for (int i=0;i<TouchEventRecording.length();i++){if (!run)return;
                            JSONObject touchEvent=TouchEventRecording.getJSONObject(i);
                            final long downTime=touchEvent.getLong("downTime");
                            final long eventTime=touchEvent.getLong("eventTime");
                            final int action=touchEvent.getInt("action");
                            final float x=(float) touchEvent.getDouble("x");
                            final float y=(float) touchEvent.getDouble("y");
                            final int metaState=touchEvent.getInt("metaState");
                           // final int time=touchEvent.getInt("time");
                            onTouchEvent(downTime,eventTime, action, x, y, metaState);
                            Thread.sleep(eventTime-downTime);
                        }
                    }break;
                    case TOUCH_EVENT_HAAR:
                    {
                        JSONArray jsonArray=Action.getJSONArray("MaterialNames");
                        String[] MaterialNames=new String[jsonArray.length()];
                        int[] clicks=new int[jsonArray.length()];
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            MaterialNames[i]=jsonObject.getString("MaterialName");
                            clicks[i]=jsonObject.getInt("click");
                        }
                        int time=Action.getInt("time");
                        double bar=Action.getDouble("bar");
                        //ints:[{"rect":{"bottom":44,"right":62,"left":2,"top":1},"name":"页面2","similar":0},{"rect":{"bottom":210,"right":549,"left":457,"top":192},"name":"页面2_child0","similar":0},{"rect":{"bottom":64,"right":667,"left":622,"top":7},"name":"页面2_child1","similar":5},{"rect":{"bottom":61,"right":728,"left":683,"top":5},"name":"页面2_child2","similar":120702},{"rect":{"bottom":476,"right":243,"left":177,"top":444},"name":"页面2_child3","similar":21416}]
                        for (int s=0;s<MaterialNames.length;s++){
                            String materialNmae=MaterialNames[s];
                            for (int i=0;i<thisStatePage.length();i++){
                                JSONObject Material=thisStatePage.getJSONObject(i);
                                String name=Material.getString("name");
                                int similar=Material.getInt("similar");
                                Rect rect=JSONObjectToRect(Material.getJSONObject("rect"));
                                if (name.equals(materialNmae)&&Double.parseDouble(percent(similar,rect))<bar){
                                    final float x= (float)rect.left;
                                    final float y= (float) rect.top;
                                    final int Width=rect.right-rect.left;
                                    final int Height=rect.bottom-rect.top;
                                    for (int c=0;c<clicks[s];c++){
                                        onTouchEvent(MotionEvent.ACTION_DOWN,x+Width/2,y+Height/2);
                                        Thread.sleep(time);
                                        onTouchEvent(MotionEvent.ACTION_UP,x+Width/2,y+Height/2);
                                        Thread.sleep(time);
                                    }
                                }
                            }
                        }

                    }break;
                }
            }break;
            case ACTION_INTENTION:
            {
                String operation=Action.getString("operation");
                switch (operation){
                    case INTENTION_ADD:
                    {
                        JSONArray jsonArray=Action.getJSONArray("intention");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject intention=jsonArray.getJSONObject(i);
                            actionState.AddIntention(intention);
                        }
                    }break;
                    case INTENTION_REMOVE:
                    {
                        String name =Action.getString("name");
                        actionState.RemoveIntention(name);

                    }break;
                }
            }break;
        }
    }
}
