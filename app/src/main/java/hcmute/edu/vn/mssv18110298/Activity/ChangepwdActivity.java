package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
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

public class ChangepwdActivity extends AppCompatActivity {

    ImageButton backbtn1;
    EditText txtPwd, txtNewpwd, txtConfirmpwd;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);

        backbtn1 = findViewById(R.id.backbtn1);
        txtPwd = findViewById(R.id.txtPwd);
        txtNewpwd = findViewById(R.id.txtNewpwd);
        txtConfirmpwd = findViewById(R.id.txtConfirmpwd);
        btnSave = findViewById(R.id.btnSave);

        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = txtPwd.getText().toString().trim();
                String newpwd = txtNewpwd.getText().toString().trim();
                String confirmpwd = txtConfirmpwd.getText().toString().trim();

                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.openDataBase();

                if(!newpwd.equals(confirmpwd)){
                    Toast.makeText(getApplicationContext(), "Mật khẩu chưa trùng khớp", Toast.LENGTH_LONG).show();
                    txtConfirmpwd.requestFocus();
                    return;
                }

                if(pwd.isEmpty() || newpwd.isEmpty() || confirmpwd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!isPasswordValid(newpwd)){
                    Toast.makeText(getApplicationContext(),"Mật khẩu phải có ít nhất 8 ký tự, trong đó có ít nhất 1 chữ in hoa và 1 số", Toast.LENGTH_LONG).show();
                    txtNewpwd.requestFocus();
                    return;
                }

                String name = getIntent().getStringExtra("email");
                User user = dbManager.getUserByEmail(name);

                if (!pwd.equals(user.getPassword())) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu hiện tại chưa đúng", Toast.LENGTH_LONG).show();
                    txtPwd.setText("");
                    txtPwd.requestFocus();
                    txtNewpwd.setText("");
                    txtConfirmpwd.setText("");
                    return;
                }

                user.setPassword(newpwd);
                dbManager.updatePass(name, newpwd);
                Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công.", Toast.LENGTH_LONG).show();

                String Subject = "Thay đổi mật khẩu";
                String mess = "Bạn đã thay đổi mật khẩu thành công.\n Mọi vấn đề thắc mắc xin vui lòng liên lạc với cửa hàng.\n Trân trọng.";
                EmailAPI emailAPI = new EmailAPI(getApplicationContext(), name, Subject, mess);
                emailAPI.execute();

                finish();
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
