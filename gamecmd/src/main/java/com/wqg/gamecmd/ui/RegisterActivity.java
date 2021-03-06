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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wqg.gamecmd.AppUtils;
import com.wqg.gamecmd.R;
import com.wqg.gamecmd.child_view.RegisterView;

/**
 * register screen
 * 
 */
public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new RegisterView(this, new RegisterView.RegisterViewInterface() {
			@Override
			public void register(EditText userNameEditText, EditText passwordEditText, EditText confirmPwdEditText) {
				final String username = userNameEditText.getText().toString().trim();
				final String pwd = passwordEditText.getText().toString().trim();
				String confirm_pwd = confirmPwdEditText.getText().toString().trim();
				if (TextUtils.isEmpty(username)) {

					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
					userNameEditText.requestFocus();
					return;
				} else if (TextUtils.isEmpty(pwd)) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
					passwordEditText.requestFocus();
					return;
				} else if (TextUtils.isEmpty(confirm_pwd)) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
					confirmPwdEditText.requestFocus();
					return;
				} else if (!pwd.equals(confirm_pwd)) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
					return;
				}

				if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
					final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
					pd.setMessage(getResources().getString(R.string.Is_the_registered));
					pd.show();

					new Thread(new Runnable() {
						public void run() {
							try {
								// call method in SDK
								EMClient.getInstance().createAccount(username, pwd);
								runOnUiThread(new Runnable() {
									public void run() {
										if (!RegisterActivity.this.isFinishing())
											pd.dismiss();
										// save current user
										/*	DemoHelper.getInstance().setCurrentUserName(username);*/
										Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
										finish();
									}
								});
							} catch (final HyphenateException e) {
								runOnUiThread(new Runnable() {
									public void run() {
										if (!RegisterActivity.this.isFinishing())
											pd.dismiss();
										int errorCode=e.getErrorCode();
										if(errorCode== EMError.NETWORK_ERROR){
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.USER_ALREADY_EXIST){
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.EXCEED_SERVICE_LIMIT){
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_exceed_service_limit), Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
										}
									}
								});
							}
						}
					}).start();

				}
			}
		}));

	}
}
