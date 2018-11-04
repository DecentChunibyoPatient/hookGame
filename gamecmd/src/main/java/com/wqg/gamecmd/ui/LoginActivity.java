/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wqg.gamecmd.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wqg.gamecmd.AppUtils;
import com.wqg.gamecmd.R;
import com.wqg.gamecmd.child_view.LoginView;

/**
 * Login screen
 * 
 */
public class LoginActivity extends Activity {
	private static final String TAG = "LoginActivity";


	private boolean progressShow;
	private boolean autoLogin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new LoginView(this, new LoginView.LoginViewInterface() {
			@Override
			public void login(EditText usernameEditText,EditText passwordEditText) {
				if (!EaseCommonUtils.isNetWorkConnected(LoginActivity.this)) {
					Toast.makeText(LoginActivity.this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
					return;
				}
				String currentUsername = usernameEditText.getText().toString().trim();
				String currentPassword = passwordEditText.getText().toString().trim();

				if (TextUtils.isEmpty(currentUsername)) {
					Toast.makeText(LoginActivity.this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(currentPassword)) {
					Toast.makeText(LoginActivity.this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
					return;
				}

				progressShow = true;
				final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
				pd.setCanceledOnTouchOutside(false);
				pd.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						Log.d(TAG, "EMClient.getInstance().onCancel");
						progressShow = false;
					}
				});
				pd.setMessage(getString(R.string.Is_landing));
				pd.show();
				final long start = System.currentTimeMillis();
				// call login method
				Log.d(TAG, "EMClient.getInstance().login");
				EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

					@Override
					public void onSuccess() {
						Log.d(TAG, "login: onSuccess");


						// ** manually load all local groups and conversation
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager().loadAllConversations();

						// update current user's display name for APNs
						boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname("");
						if (!updatenick) {
							Log.e("LoginActivity", "update current user nick fail");
						}

						if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
							pd.dismiss();
						}

				/*// get user's info (this should be get from App's server or 3rd party service)
				DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);*/

						finish();
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
						runOnUiThread(new Runnable() {
							public void run() {
								pd.dismiss();
								Toast.makeText(LoginActivity.this, getString(R.string.Login_failed) + message,
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
			}

			@Override
			public void register() {
				startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 0);
			}

			@Override
			public void serviceCheck() {

			}
		}));
		init();
	}




	@Override
	protected void onResume() {
		super.onResume();
		if (autoLogin) {
			return;
		}
	}
	void init() {
		EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
		options.setAutoTransferMessageAttachments(false);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
		options.setAutoDownloadThumbnail(false);
//初始化
		EMClient.getInstance().init(LoginActivity.this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
		EMClient.getInstance().setDebugMode(false);
	}

}
