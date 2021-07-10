package hcmute.edu.vn.mssv18110298.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class LoginActivity extends AppCompatActivity{

    EditText username, password;
    Button login;
    TextView forgotpwd, register;
    CheckBox checkBox;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginfrm);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgotpwd = findViewById(R.id.forgotpwd);
        register = findViewById(R.id.register);
        checkBox = findViewById(R.id.checkBox);

        loginPreferences=getSharedPreferences("loginPref", MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if(saveLogin ==true){
            username.setText(loginPreferences.getString("username",""));
            password.setText(loginPreferences.getString("password",""));
            checkBox.setChecked(true);
        }else {
            username.setText("");
            password.setText("");
            checkBox.setChecked(false);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();

                if(email.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.createDataBase();
                dbManager.openDataBase();

                User user = dbManager.getUserByEmail(email);

                if (dbManager.checklogin(email, pwd)==true) {
                    if (checkBox.isChecked()) {
                        loginPrefEditor.putBoolean("saveLogin", true);
                        loginPrefEditor.putString("email", email);
                        loginPrefEditor.putString("password", pwd);
                        loginPrefEditor.apply();
                    } else {
                        loginPrefEditor.clear();
                        loginPrefEditor.commit();
                    }

                    Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Thông tin đăng nhập sai", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgetPwdActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
