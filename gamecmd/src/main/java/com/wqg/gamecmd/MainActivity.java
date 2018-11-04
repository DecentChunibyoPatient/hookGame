package com.wqg.gamecmd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.wqg.gamecmd.child_view.ImageEdit;
import com.wqg.gamecmd.dialog.LoginDialog;

import org.json.JSONException;

import java.util.List;

import cn.modificator.waterwave_progress.WaterWaveProgress;


public class MainActivity extends Activity {
    String TAG="MainActivity";
    static public WaterWaveProgress waveProgress;
    ImageEdit imageEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
/*
        try {
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name","133");
            jsonArray.put(jsonObject);
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            //jsonObject1.put("name","xxx");
            System.out.println(jsonArray.getJSONObject(0).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
      /*  setContentView(imageEdit=new ImageEdit(this, BitmapFactory.decodeResource(getResources(), R.drawable.test), new ImageEditInterface() {
            @Override
            public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                imageEdit.getBaseAdapter().Checked(position);
                Log.d(TAG,"onItemLongClick: view="+view.getClass().getName());
            }

            @Override
            public void onItemClick(ListView listView,HashMap<String, Bitmap> hashMap, AdapterView<?> parent, View view, int position, long id) {
                imageEdit.getBaseAdapter().clearChecked();
                Log.d(TAG,"onItemClick: view="+view.getClass().getName());
            }

            @Override
            public void onCropOverlayReleased(Rect rect) {

            }

            @Override
            public void Cutting(Bitmap cropped, Rect rect) {

            }

            @Override
            public void Back(ArrayList<Bitmap> arrayList, Bitmap bitmap, Rect rect) {

            }

            @Override
            public void add(Bitmap cropped, Rect rect) {
                Log.d(TAG,"add: rect="+rect);
            }
        }));*/
      //{"rect":{"bottom":42,"right":189,"left":89,"top":344},"name":"页面0","similar":160}
      /*  try {
            System.out.println("cccccccccccccccccc"+  new IntentionJSONObject(System.currentTimeMillis(),"页面2",
                    IntentionJSONObject.IntentionAction(IntentionJSONObject.IntentionAction_ACTION_TOUCH_EVENT(new String[]{"页面2_child1"},new int[]{1},200,0.5d))) );
        } catch (JSONException e) {
            e.printStackTrace();
        };*/
        init();
        new LoginDialog(this).show();
    }
    void init(){
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(false);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(false);
//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);

    }
}
