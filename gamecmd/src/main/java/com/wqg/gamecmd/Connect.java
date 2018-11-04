package com.wqg.gamecmd;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EasyUtils;
import com.wqg.gamecmd.dialog.LoginDialog;

import java.util.List;

public class Connect extends Service {
   static public boolean isConnect=false;
   static public String toChatUsername;
   static public ActionInterface actionInterface;
   static public SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("set.txt", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("toChatUsername","").equals("")){
            toChatUsername=sharedPreferences.getString("toChatUsername","");
        }
        init();
    }
    private static final int sleepTime = 2000;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        new Thread(new Runnable() {
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    Log.d("main", "登录聊天服务器成功！");

                    EMClientInit();


                }else {
                    try {

                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    new Handler(getApplicationContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            LoginDialog loginDialog=new LoginDialog(Connect.this);
                            loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    if (EMClient.getInstance().isLoggedInBefore()) {
                                        // auto login mode, make sure all group and conversation is loaed before enter the main screen
                                        long start = System.currentTimeMillis();
                                        EMClient.getInstance().chatManager().loadAllConversations();
                                        EMClient.getInstance().groupManager().loadAllGroups();
                                        long costTime = System.currentTimeMillis() - start;
                                        //wait
                                        if (sleepTime - costTime > 0) {
                                            try {
                                                Thread.sleep(sleepTime - costTime);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        Log.d("main", "登录聊天服务器成功！");
                                        EMClientInit();

                                    }
                                }
                            });
                            loginDialog.show();
                        }
                    });
                }
            }
        }).start();
    }
     void EMClientInit(){
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
        actionInterface=new ActionInterface() {
            @Override
            public void createTxtSendMessage(String var0, String var1) {
                EMClient.getInstance().chatManager().sendMessage(EMMessage.createTxtSendMessage(var0, var1));

            }
        };
    }
    static void getConnect(){
        System.out.println("getConnect");
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        if (toChatUsername!=null)
            actionInterface.createTxtSendMessage("0", toChatUsername);
        System.out.println("发送消息");
    }
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            for (EMMessage emMessage:messages){

                String string=((EMTextMessageBody) emMessage.getBody()).getMessage();
                Log.d("onMessageReceived", "收到消息！="+string);
                String toChatUsername=emMessage.getFrom();
                Log.d("onMessageReceived", "toChatUsername="+toChatUsername);
                if(AppUtils.isServiceRunning(  getBaseContext(),CmdWindow.class.getName()))
                    switch (string){
                        case "0":{
                            //发送消息
                            EMClient.getInstance().chatManager().sendMessage(EMMessage.createTxtSendMessage("1", toChatUsername));
                        }continue;
                        case "1":{
                            isConnect=true;
                            if (CmdWindow.anInterface!=null){
                                CmdWindow.anInterface.isConnect();
                            }
                            //发送消息
                            EMClient.getInstance().chatManager().sendMessage(EMMessage.createTxtSendMessage("2", toChatUsername));
                            //cmdInterface.sendAction();
                        }continue;
                        case "2":{
                            isConnect=true;
                            if (CmdWindow.anInterface!=null){
                                CmdWindow.anInterface.isConnect();
                            }
                            //cmdInterface.sendAction();
                            //发送消息
                            //EMClient.getInstance().chatManager().sendMessage(EMMessage.createTxtSendMessage("2", toChatUsername));
                        }continue;
                        default: {
                            if (CmdWindow.anInterface!=null){
                                CmdWindow.anInterface.onMessageReceived(string,toChatUsername);
                            }
                        }
                    }
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    static EMContactListener emContactListener=new EMContactListener() {


        @Override
        public void onContactAdded(String s) {

        }

        @Override
        public void onContactDeleted(String s) {

        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请

        }

        @Override
        public void onFriendRequestAccepted(String s) {

        }

        @Override
        public void onFriendRequestDeclined(String s) {

        }

    };

    void init() {
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
    static public interface Interface{
        public void isConnect();
        public void onMessageReceived(String var0, String var1);

    }
    static public interface ActionInterface{
        public void createTxtSendMessage(String var0, String var1);

    }
}
