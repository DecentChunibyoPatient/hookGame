package com.wqg.gamecmd.child_view;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wqg.gamecmd.AppUtils;
import com.wqg.gamecmd.R;

public class LoginView extends LinearLayout {
    private static final String TAG = "LoginView";
    public EditText usernameEditText;
    public EditText passwordEditText;
    public LoginViewInterface loginViewInterface;
    public LoginView(Context context , final LoginViewInterface loginViewInterface) {
        super(context);
        this.loginViewInterface=loginViewInterface;
        inflate(context,R.layout.em_activity_login,this);
        usernameEditText = (EditText) findViewById(R.id.username);
        usernameEditText.setText(AppUtils.getIMEI(context));
        passwordEditText = (EditText) findViewById(R.id.password);

        // if user changed, clear the password
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {

                    return true;
                }
                else{
                    return false;
                }
            }
        });
        TextView serviceCheckTV = (TextView) findViewById(R.id.txt_service_ckeck);
        serviceCheckTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        findViewById(R.id.login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewInterface.login( usernameEditText, passwordEditText);
            }
        });
        findViewById(R.id.register).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewInterface.register();
            }
        });
        findViewById(R.id.txt_service_ckeck).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewInterface.serviceCheck();
            }
        });
    }
	public interface LoginViewInterface{
	    void login( EditText usernameEditText,EditText passwordEditText);
        void register();
        void serviceCheck();
    }

}
