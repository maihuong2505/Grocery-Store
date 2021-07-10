package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class RegisterConfirmActivity extends AppCompatActivity {

    TextView emailsent;
    Button loginbtn;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerconfirmfrn);

        emailsent = findViewById(R.id.emailsent);
        loginbtn = findViewById(R.id.loginbtn);

        mail = getIntent().getStringExtra("mail");
        emailsent.setText(mail);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.openDataBase();

                startActivity(intent);
                finish();
            }
        });
    }
}
