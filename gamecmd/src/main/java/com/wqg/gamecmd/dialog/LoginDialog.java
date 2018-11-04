package com.wqg.gamecmd.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wqg.gamecmd.R;
import com.wqg.gamecmd.child_view.LoginView;
import com.wqg.gamecmd.ui.LoginActivity;
import com.wqg.gamecmd.ui.RegisterActivity;

public class LoginDialog extends AlertDialog {
    private static final String TAG = "LoginActivity";
    private boolean progressShow;
    private boolean autoLogin = false;
    public LoginDialog(final Context context) {
        super(context);
        setView(new LoginView(context, new LoginView.LoginViewInterface() {
            @Override
            public void login(EditText usernameEditText, EditText passwordEditText) {
                if (!EaseCommonUtils.isNetWorkConnected(context)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }
                String currentUsername = usernameEditText.getText().toString().trim();
                String currentPassword = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(currentUsername)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }
                if (TextUtils.isEmpty(currentPassword)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }

                progressShow = true;
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setCanceledOnTouchOutside(false);
                pd.setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d(TAG, "EMClient.getInstance().onCancel");
                        progressShow = false;
                    }
                });
                pd.setMessage(context.getString(R.string.Is_landing));
                pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                pd.show();
                final long start = System.currentTimeMillis();
                // call login method
                Log.d(TAG, "EMClient.getInstance().login");
                EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "login: onSuccess");

                        if ( pd.isShowing()) {
                            pd.dismiss();
                        }
                       new Handler(getContext().getMainLooper()).post(new Runnable() {
                           @Override
                           public void run() {
                               dismiss();
                           }
                       });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        Log.d(TAG, "login: onProgress");
                    }

                    @Override
                    public void onError(final int code, final String message) {
                        Log.d(TAG, "login: onError: " + code);
                        if (!progressShow) {
                            return;
                        }
                      new Handler(getContext().getMainLooper()).post(new Runnable() {
                          public void run() {
                              pd.dismiss();
                              Toast.makeText(context, context.getString(R.string.Login_failed) + message,
                                      Toast.LENGTH_SHORT).show();
                          }
                      });
                    }
                });
            }

            @Override
            public void register() {
               new RegisterDialog(context).show();
            }

            @Override
            public void serviceCheck() {

            }
        }));
        setCancelable(false);
        setButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
