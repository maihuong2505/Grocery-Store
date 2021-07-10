package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class ForgetPwdAuthActivity extends AppCompatActivity {

    ImageButton backbtn;
    TextView maxacthuctxt, message;
    Button btnforgotpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetfrm_2);

        backbtn = findViewById(R.id.backbtn);
        maxacthuctxt = findViewById(R.id.maxacthuctxt);
        btnforgotpwd = findViewById(R.id.btnforgotpwd);
        message = findViewById(R.id.message);

        message.setVisibility(View.INVISIBLE);

        String random = getIntent().getStringExtra("random");
        String email = getIntent().getStringExtra("mail");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = maxacthuctxt.getText().toString().trim();

                if(code.equals(random)){

                    Intent intent = new Intent(getApplicationContext(), ResetPwdActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();

                }else {
                    message.setVisibility(View.VISIBLE);
                    maxacthuctxt.requestFocus();
                    return;
                }
            }
        });
    }
}
