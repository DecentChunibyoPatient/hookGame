package com.wqg.gamecmd;

import android.graphics.Rect;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IntentionJSONObject extends JSONObject{
    final static public String ACTION_HAAR="HAAR";
    final static public String ACTION_OCR="OCR";
    final static public String ACTION_BAIDU_OCR="baiduOCR";
    final static public String ACTION_INPUT="Input";
    final static public String ACTION_TOUCH_EVENT="TouchEvent";
    final static public String ACTION_INTENTION="Intention";

    final static public String INPUT_DEFAULT="DEFAULT";
    final static public String INPUT_OCR="OCR";

    final static public String TOUCH_EVENT_PLAY ="PLAY";
    final static public String TOUCH_EVENT_DEFAULT="DEFAULT";
    final static public String TOUCH_EVENT_HAAR="HAAR";

    final static public String HAAR_DEFAULT="DEFAULT";
    final static public String HAAR_CHECK="HAAR";

    final static public String INTENTION_ADD="addIntention";
    final static public String INTENTION_REMOVE="removeIntention";
    public IntentionJSONObject(String name,long intentionTime , String intentionPageNmae , JSONArray intentionAction ,JSONObject intentionState) throws JSONException {
        this.put("name",name);//名字
        this.put("intentionTime",intentionTime);//时间
        this.put("intentionPageNmae",intentionPageNmae);//页面
        this.put("intentionAction",intentionAction);//动作
        this.put("intentionState",intentionState);//状态
    }
    public IntentionJSONObject(String name,long intentionTime , String intentionPageNmae , JSONArray intentionAction) throws JSONException {
        this.put("name",name);//名字
        this.put("intentionTime",intentionTime);//时间
        this.put("intentionPageNmae",intentionPageNmae);//页面
        this.put("intentionAction",intentionAction);//动作
    }
    static public JSONArray IntentionAction(JSONObject...Action){
        JSONArray intentionAction=new JSONArray();
        for (JSONObject action:Action){
            intentionAction.put(action);
        }
        return intentionAction;
    }
    static public JSONArray IntentionAction(JSONObject[] jsonObjects,JSONObject...Action){
        JSONArray intentionAction=new JSONArray();
        for (JSONObject jsonObject:jsonObjects){
            intentionAction.put(jsonObject);
        }
        for (JSONObject action:Action){
            intentionAction.put(action);
        }
        return intentionAction;
    }
    static public JSONObject IntentionAction_ACTION_HAAR( String HaarMode,String...Material) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_HAAR);
        jsonObject.put("HaarMode",HaarMode);
        JSONArray MaterialName=new JSONArray();
        for (String name:Material){
            MaterialName.put(name);
        }
        jsonObject.put("MaterialName",MaterialName);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_OCR(String OcrName,int Materiallibrary, Rect... rects) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_OCR);
        jsonObject.put("OcrName",OcrName);
        jsonObject.put("Materiallibrary",Materiallibrary);
        JSONArray jsonArrayRect=new JSONArray();
        for (int i=0;i<rects.length;i++){
            jsonArrayRect.put(rects[i].left);
            jsonArrayRect.put(rects[i].top);
            jsonArrayRect.put(rects[i].right);
            jsonArrayRect.put(rects[i].bottom);
        }
        jsonObject.put("Rect",jsonArrayRect);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_BAIDU_OCR(String OcrName, Rect... rects) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_BAIDU_OCR);
        jsonObject.put("OcrName",OcrName);
        JSONArray jsonArrayRect=new JSONArray();
        for (int i=0;i<rects.length;i++){
            jsonArrayRect.put(rects[i].left);
            jsonArrayRect.put(rects[i].top);
            jsonArrayRect.put(rects[i].right);
            jsonArrayRect.put(rects[i].bottom);
        }
        jsonObject.put("Rect",jsonArrayRect);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_INPUT(  String InputType,String string) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_INPUT);
        jsonObject.put("InputType",InputType);
        switch (InputType){
            case INPUT_DEFAULT:
            {
                jsonObject.put("text",string);
            }break;
            case INPUT_OCR:
            {
                jsonObject.put("OcrName",string);
            }break;
        }
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_TOUCH_EVENT( JSONObject TOUCH_EVENT) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_TOUCH_EVENT);
        jsonObject.put("TouchEventType",TOUCH_EVENT_DEFAULT);
        jsonObject.put("TOUCH_EVENT",TOUCH_EVENT);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_TOUCH_EVENT( JSONArray TouchEventRecording) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_TOUCH_EVENT);
        jsonObject.put("TouchEventType",TOUCH_EVENT_PLAY);
        jsonObject.put("TouchEventRecording",TouchEventRecording);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_TOUCH_EVENT( String[] materialNames,int[] clicks, int time,double bar) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_TOUCH_EVENT);
        jsonObject.put("TouchEventType",TOUCH_EVENT_HAAR);
        JSONArray MaterialNames=new JSONArray();
        for (int i=0;i<materialNames.length;i++){
            JSONObject jsonObject1=new JSONObject();
            String MaterialName=materialNames[i];
            jsonObject1.put("MaterialName",MaterialName);
            jsonObject1.put("click",clicks[i]);
            MaterialNames.put(jsonObject1);
        }
        jsonObject.put("MaterialNames",MaterialNames);
        jsonObject.put("time",time);
        jsonObject.put("bar",bar);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_INTENTION_REMOVE( String name) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_INTENTION);
        jsonObject.put("operation",INTENTION_REMOVE);
        jsonObject.put("name",name);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_INTENTION_ADD( JSONObject ...intentions) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_INTENTION);
        jsonObject.put("operation",INTENTION_ADD);
        JSONArray jsonArray=new JSONArray();
        for (JSONObject intention:intentions){
            jsonArray.put(intention);
        }
        jsonObject.put("intention",jsonArray);
        return jsonObject;
    }
    static public JSONObject IntentionAction_ACTION_INTENTION_ADD( JSONArray jsonArray) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",ACTION_INTENTION);
        jsonObject.put("operation",INTENTION_ADD);
        jsonObject.put("intention",jsonArray);
        return jsonObject;
    }
    static public JSONObject TOUCH_EVENT(final int action, final float x, final float y) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("action",action);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        return jsonObject;
    }
    static public JSONArray TouchEventRecording(JSONObject... jsonObjects) throws JSONException {
        JSONArray TouchEventRecording=new JSONArray();
        for (JSONObject jsonObject:jsonObjects){
            TouchEventRecording.put(jsonObject);
        }
        return TouchEventRecording;
    }
    static public JSONArray TouchEventRecording(MotionEvent[] events) throws JSONException {
        JSONArray TouchEventRecording=new JSONArray();
        for (MotionEvent event:events){
            TouchEventRecording.put(TouchEventRecordingItme(event));
        }
        return TouchEventRecording;
    }
    static public JSONObject TouchEventRecordingItme(final long downTime, final long eventTime, final int action, final float x, final float y, final int metaState) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("downTime",downTime);
        jsonObject.put("eventTime",eventTime);
        jsonObject.put("action",action);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("metaState",metaState);
        return jsonObject;
    }
    static public JSONObject TouchEventRecordingItme(MotionEvent event) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("downTime",event.getDownTime());
        jsonObject.put("eventTime", event.getEventTime());
        jsonObject.put("action",event.getAction());
        jsonObject.put("x",event.getX());
        jsonObject.put("y",event.getY());
        jsonObject.put("metaState",event.getMetaState());
        return jsonObject;
    }
    static public JSONObject IntentionState(JSONArray intentionStateChildPage, JSONArray intentionStateChildData) throws JSONException {
        JSONObject intentionState=new JSONObject();
        intentionState.put("intentionStateChildPage",intentionStateChildPage);
        intentionState.put("intentionStateChildData",intentionStateChildData);
        return intentionState;
    }
    static public JSONArray intentionStateChildPage(String...Page){
        JSONArray intentionStateChildPage=new JSONArray();
        for (String page:Page){
            intentionStateChildPage.put(page);
        }
        return intentionStateChildPage;
    }
    static public JSONArray intentionStateChildData(JSONObject... jsonObject){
        JSONArray intentionStateChildData= new JSONArray();
        for (JSONObject jsonObject1:jsonObject){
            intentionStateChildData.put(jsonObject1);
        }
        return intentionStateChildData;
    }
    static public JSONObject intentionStateChildDataItme(  String name, String MathSymbol, Object operationData, Object Result) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("MathSymbol",MathSymbol);
        jsonObject.put("operationData",operationData);
        jsonObject.put("Result",Result);
        return jsonObject;
    }
}
