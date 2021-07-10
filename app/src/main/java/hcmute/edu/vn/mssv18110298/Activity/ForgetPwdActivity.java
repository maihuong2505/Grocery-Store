package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.DBManager.EmailAPI;
import hcmute.edu.vn.mssv18110298.R;

public class ForgetPwdActivity extends AppCompatActivity {

    ImageButton backbtn;
    EditText emailforgetpwd;
    Button btnforgotpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwdfrm_1);

        backbtn = findViewById(R.id.backbtn);
        emailforgetpwd = findViewById(R.id.emailforgetpwd);
        btnforgotpwd = findViewById(R.id.btnforgotpwd);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = emailforgetpwd.getText().toString().trim();
                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.openDataBase();

                if(dbManager.emailExisted(mail)==true){
                    Intent intent = new Intent(getApplicationContext(), ForgetPwdAuthActivity.class);

                    Random r = new Random();
                    int randomnum = r.nextInt(9999 - 1000 + 1) + 1000;

                    String random = String.valueOf(randomnum);
                    intent.putExtra("random", random);
                    intent.putExtra("mail", mail);

                    String Subject = "Xác thực tài khoản";
                    String mess = "Mã xác thực của bạn là: " + String.valueOf(randomnum);
                    EmailAPI emailAPI = new EmailAPI(getApplicationContext(), mail, Subject, mess);
                    emailAPI.execute();

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Email chưa đúng, hãy thử lại!", Toast.LENGTH_LONG).show();
                    emailforgetpwd.requestFocus();
                    return;
                }

            }
        });
    }
}
