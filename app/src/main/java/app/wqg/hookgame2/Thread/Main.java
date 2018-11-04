package app.wqg.hookgame2.Thread;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import app.wqg.hookgame2.Api;
import app.wqg.hookgame2.GameState;
import dalvik.system.DexClassLoader;

public class Main extends Handler {

    final int findPage=1;
    final int unfindPage=-1;
    final int findCurrentPage=2;
    final int unfindCurrentPage=-2;

    HashMap<String, Bitmap> ResDrawadle;
    View view;
    String Material;
    int time =200;
    DexClassLoader dexClassLoader;
    JSONObject data=new JSONObject();
    JSONArray intention=new JSONArray();
    ActionThread actionThread;
    EyeThread eyeThread;
    GameState gameState=new GameState() {
        @Override
        public void upDate(GL10 gl, int width, int height) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
            gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, buffer);
            // 因为上下颠倒，所以上下反转
            byte[] tmp1 = new byte[width * 4];
            byte[] tmp2 = new byte[width * 4];
            int h = (int) height / 2;
            for (int y = 0; y < h; y++) {
                buffer.position(y * width * 4);
                buffer.get(tmp1, 0, tmp1.length);
                buffer.position((height - 1 - y) * width * 4);
                buffer.get(tmp2, 0, tmp2.length);
                buffer.position((height - 1 - y) * width * 4);
                buffer.put(tmp1);
                buffer.position(y * width * 4);
                buffer.put(tmp2);
            }
            buffer.position(0);
            tmp1 = tmp2 = null;
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes, 0, bytes.length);
            Api.gameBitmap=bytes;
        }
    };
    ActionState actionState=new ActionState() {

        @Override
        public void Completion(JSONObject intention,JSONArray jsonArrayAction,JSONObject data) {
            Api.CompletionOfIntention(intention.toString(),jsonArrayAction.toString(),data.toString());
            actionThread=null;
        }

        @Override
        public void RemoveIntention(String name) {
            try {
                removIntention(name);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Api.RemoveIntention(intention.toString(),name);
        }

        @Override
        public void AddIntention(JSONObject jsonObject) {
           // Api.AddIntention(intention.toString(),jsonArrayAction.toString(),data.toString());
            addIntention(jsonObject);
        }
    };
    HaarState haarState=new HaarState() {
        @Override
        public void start() {
            Api.Start();
        }

        @Override
        public void stop() {
            Api.Stop();
        }

        @Override
        public void findPage(int CurrentPage, JSONArray jsonArray) {
            Api.findPageMessage(CurrentPage,jsonArray);
            sendEmptyMessage(findPage);
        }

        @Override
        public void unfindPage(JSONArray jsonArray) {
            Api.unfindPageMessage(jsonArray);
            sendEmptyMessage(unfindPage);
        }

        @Override
        public void findCurrentPage(JSONArray jsonArray) {
            Api.findCurrentPageMessage(jsonArray);
            Message message=new Message();
            message.what=findCurrentPage;
            message.obj=jsonArray;
            sendMessage(message);
        }

        @Override
        public void unfindCurrentPage(JSONArray jsonArray) {
          Api.unfindCurrentPageMessage(jsonArray);
            sendEmptyMessage(unfindCurrentPage);
        }
    };
    public Main(DexClassLoader dexClassLoader, HashMap<String, Bitmap> ResDrawadle, View view,String Material){
        this.dexClassLoader=dexClassLoader;
        this.ResDrawadle=ResDrawadle;
        this.view=view;
        this.Material=Material;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case findPage:break;
            case unfindPage:break;
            case findCurrentPage:
                {
                    if (intention!=null)
                        if(intention.length()!=0)
                            try {
                                JSONArray thisStatePage= (JSONArray) msg.obj;
                                for (int I=0;I<intention.length();I++){
                                    JSONObject jsonObject=intention.getJSONObject(I);
                                    long intentionTime=jsonObject.getLong("intentionTime");
                                    String intentionPageNmae=jsonObject.getString("intentionPageNmae");
                                    JSONArray intentionAction=jsonObject.getJSONArray("intentionAction");
                                    if (thisStatePage.getJSONObject(0).getString("name").equals(intentionPageNmae)){
                                        boolean isState=false;
                                        try {
                                            JSONObject intentionState=jsonObject.getJSONObject("intentionState");
                                            JSONArray intentionStateChildPage=intentionState.getJSONArray("intentionStateChildPage");
                                            JSONArray intentionStateChildData=intentionState.getJSONArray("intentionStateChildData");
                                            boolean chileb=false;
                                            for (int i=0;i<intentionStateChildPage.length();i++){
                                                for (int t=1;t<thisStatePage.length();t++){
                                                    JSONObject page=thisStatePage.getJSONObject(t);
                                                    int similar=page.getInt("similar");
                                                    Rect rect=HookThread.JSONObjectToRect(page.getJSONObject("rect"));
                                                    String name=page.getString("name");
                                                    if (name.equals(intentionStateChildPage.getString(i))){
                                                        chileb=Double.parseDouble(HookThread.percent(similar,rect))<0.5d;
                                                        if (chileb==false)break;
                                                    }
                                                }
                                                if (chileb==false)break;

                                            }
                                            isState=chileb;
                                            for (int i=0;i<intentionStateChildData.length();i++){
                                                boolean datanb=false;
                                                JSONObject data=intentionStateChildData.getJSONObject(i);
                                                String name=data.getString("name");
                                                String MathSymbol=data.getString("MathSymbol");
                                                Object operationData=data.get("operationData");
                                                Object Result=data.get("Result");
                                                switch (MathSymbol){
                                                    case "*":datanb = this.data.getInt(name)*((int)operationData)==((int)Result);continue;
                                                    case "/":datanb = this.data.getInt(name)/((int)operationData)==((int)Result);continue;
                                                    case "+":datanb = this.data.getInt(name)+((int)operationData)==((int)Result);continue;
                                                    case "-":datanb = this.data.getInt(name)-((int)operationData)==((int)Result);continue;
                                                    case ">":datanb = this.data.getInt(name)>((int)operationData)==((boolean)Result);continue;
                                                    case "<":datanb = this.data.getInt(name)<((int)operationData)==((boolean)Result);continue;
                                                    case "==":datanb = this.data.getInt(name)==((int)operationData)==((boolean)Result);continue;
                                                    case "equals":datanb =this.data.get(name).equals(operationData)==((boolean)Result);continue;
                                                }
                                                if (!(isState=datanb))break;
                                            }
                                            if (isState&&System.currentTimeMillis()>intentionTime){
                                                useActionThread(thisStatePage,jsonObject,intentionAction);
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                            useActionThread(thisStatePage,jsonObject,intentionAction);
                                        }

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                }break;
            case unfindCurrentPage:break;
        }
    }
    public GameState getGameState(){
      return gameState;
    }
    public void addIntention(JSONObject jsonObject){
        if (intention!=null){
            intention.put(jsonObject);
            useEyeThread();
        }
    }
    public void removIntention(int i) throws InterruptedException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intention.remove(i);
        }
        if (intention==null){
            closeEyeThread();
        }else if (intention.length()<=0){
            closeEyeThread();
        }
    }
    public void removIntention(String name) throws JSONException, InterruptedException {
      for (int i=0;i<intention.length();i++){
          JSONObject jsonObject=intention.getJSONObject(i);
          String intentionNmae=jsonObject.getString("name");
          if (name.equals(intentionNmae)){
              removIntention(i);
          }
      }
    }
    public  void closeActionThread() throws InterruptedException {
        if (actionThread!=null)
            while (actionThread.isRun){
                actionThread.Stop();
                Thread.sleep(100);
            }
        actionThread=null;
    }
    public  void useActionThread(JSONArray thisStatePage,JSONObject intention,String jsonArrayAction){
        try {
            if (actionThread==null){
                actionThread=new ActionThread(dexClassLoader,ResDrawadle, view,thisStatePage,intention,jsonArrayAction,actionState);
                actionThread.start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void useActionThread(JSONArray thisStatePage,JSONObject intention,JSONArray jsonArrayAction){
        try {
            if (actionThread==null){
                actionThread=new ActionThread(dexClassLoader,ResDrawadle, view,thisStatePage,intention,jsonArrayAction,actionState);
                actionThread.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void closeEyeThread() throws InterruptedException {
        if (eyeThread!=null)
            while (eyeThread.isRun){
                eyeThread.Stop();
                Thread.sleep(100);
            }
        eyeThread=null;
    }
    public void useEyeThread(){
        try {
            if (eyeThread==null){
                eyeThread=new EyeThread(dexClassLoader,ResDrawadle, view,Material,time,haarState);
                eyeThread.start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
