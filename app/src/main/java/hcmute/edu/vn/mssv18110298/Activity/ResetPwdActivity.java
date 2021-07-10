package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.DBManager.EmailAPI;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class ResetPwdActivity extends AppCompatActivity {

    ImageButton backbtn;
    EditText newpwd, cfnnewpwd;
    Button btnresetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetfrm_3);

        backbtn = findViewById(R.id.backbtn);
        newpwd = findViewById(R.id.newpwd);
        cfnnewpwd = findViewById(R.id.cfnewpwd);
        btnresetpwd = findViewById(R.id.btnresetpwd);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnresetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass = newpwd.getText().toString().trim();
                String confirm = cfnnewpwd.getText().toString().trim();


                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.openDataBase();

                if(!newpass.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Mật khẩu chưa trùng khớp", Toast.LENGTH_LONG).show();
                    cfnnewpwd.requestFocus();
                    return;
                }

                if(newpass.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!isPasswordValid(newpass)){
                    Toast.makeText(getApplicationContext(),"Mật khẩu phải có ít nhất 8 ký tự, trong đó có ít nhất 1 chữ in hoa và 1 số", Toast.LENGTH_LONG).show();
                    newpwd.requestFocus();
                    return;
                }
                String name = getIntent().getStringExtra("email");

                if(dbManager.emailExisted(name)==true){
                    dbManager.updatePass(name, newpass);

                    String Subject = "Thay đổi mật khẩu";
                    String mess = "Bạn đã thay đổi mật khẩu thành công.\n Mật khẩu mới của bạn là: " + newpass + "Mọi vấn đề thắc mắc xin vui lòng liên lạc với cửa hàng.\n Trân trọng.";
                    EmailAPI emailAPI = new EmailAPI(getApplicationContext(), name, Subject, mess);
                    emailAPI.execute();

                    Intent intent = new Intent(getApplicationContext(), ResetPwdCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
