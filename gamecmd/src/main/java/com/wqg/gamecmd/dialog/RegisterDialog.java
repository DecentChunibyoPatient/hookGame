package com.wqg.gamecmd.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wqg.gamecmd.R;
import com.wqg.gamecmd.child_view.RegisterView;
import com.wqg.gamecmd.ui.RegisterActivity;

public class RegisterDialog extends AlertDialog {
    protected RegisterDialog(final Context context) {
        super(context);
        setView(new RegisterView(context, new RegisterView.RegisterViewInterface() {
            @Override
            public void register(EditText userNameEditText, EditText passwordEditText, EditText confirmPwdEditText) {
                final String username = userNameEditText.getText().toString().trim();
                final String pwd = passwordEditText.getText().toString().trim();
                String confirm_pwd = confirmPwdEditText.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, context.getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                        }
                    });

                    userNameEditText.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(pwd)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, context.getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                        }
                    });
                    passwordEditText.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(confirm_pwd)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, context.getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                        }
                    });
                    confirmPwdEditText.requestFocus();
                    return;
                } else if (!pwd.equals(confirm_pwd)) {
                    new Handler(getContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, context.getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage(context.getResources().getString(R.string.Is_the_registered));
                    pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    pd.show();

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                // call method in SDK
                                EMClient.getInstance().createAccount(username, pwd);
                               new Handler(getContext().getMainLooper()).post(new Runnable() {
                                   public void run() {
                                       pd.dismiss();
                                       // save current user
                                       /*	DemoHelper.getInstance().setCurrentUserName(username);*/
                                       Toast.makeText(context, context.getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                                       dismiss();
                                   }
                               });
                            } catch (final HyphenateException e) {
                              new Handler(getContext().getMainLooper()).post(new Runnable() {
                                  public void run() {
                                      pd.dismiss();
                                      int errorCode=e.getErrorCode();
                                      if(errorCode== EMError.NETWORK_ERROR){
                                          Toast.makeText(context, context.getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                                      }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                          Toast.makeText(context, context.getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                                      }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                          Toast.makeText(context, context.getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                                      }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                          Toast.makeText(context, context.getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                                      }else if(errorCode == EMError.EXCEED_SERVICE_LIMIT){
                                          Toast.makeText(context, context.getResources().getString(R.string.register_exceed_service_limit), Toast.LENGTH_SHORT).show();
                                      }else{
                                          Toast.makeText(context, context.getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              });
                            }
                        }
                    }).start();

                }
            }
        }));
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
