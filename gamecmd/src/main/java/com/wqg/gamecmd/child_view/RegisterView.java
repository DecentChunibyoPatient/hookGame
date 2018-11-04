package com.wqg.gamecmd.child_view;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wqg.gamecmd.AppUtils;
import com.wqg.gamecmd.R;

public class RegisterView extends LinearLayout{
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText confirmPwdEditText;
    public RegisterView(Context context, final RegisterViewInterface registerViewInterface) {
        super(context);
        inflate(context,R.layout.em_activity_register,this);
        userNameEditText = (EditText) findViewById(R.id.username);
        userNameEditText.setText(AppUtils.getIMEI(context));
        passwordEditText = (EditText) findViewById(R.id.password);
        confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
        findViewById(R.id.register).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewInterface.register(userNameEditText,passwordEditText,confirmPwdEditText);
            }
        });
    }
    public interface RegisterViewInterface{
        void register(EditText userNameEditText, EditText passwordEditText, EditText confirmPwdEditText);
    }
}
