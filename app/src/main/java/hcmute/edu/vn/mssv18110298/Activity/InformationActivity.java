package hcmute.edu.vn.mssv18110298.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class InformationActivity extends AppCompatActivity {

    ImageButton backbtn;
    Button signoutbtn, Infobtn, Changepwdbtn;
    TextView txtName, txtSex, txtPhone, txtAddr, txtMail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationfrm);

        signoutbtn = findViewById(R.id.signoutbtn);
        backbtn = findViewById(R.id.backbtn);
        txtName = findViewById(R.id.txtName);
        txtSex = findViewById(R.id.txtSex);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddr = findViewById(R.id.txtAddr);
        txtMail = findViewById(R.id.txtMail);
        Infobtn = findViewById(R.id.Infobtn);
        Changepwdbtn = findViewById(R.id.Changepwdbtn);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.createDataBase();
        dbManager.openDataBase();

        String email = getIntent().getStringExtra("email");
        User user1 = dbManager.getUserByEmail(email);

        if(user1!=null) {
            txtName.setText(user1.getFullname());
            txtPhone.setText(user1.getPhonenumber());
            txtSex.setText(user1.getGender());
            txtAddr.setText(user1.getAddress());
            txtMail.setText(user1.getEmail());
        } else {
            txtName.setText("");
            txtPhone.setText("");
            txtSex.setText("");
            txtAddr.setText("");
            txtMail.setText("");
        }

        signoutbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog =	new AlertDialog.Builder(InformationActivity.this);
                dialog.setTitle("Đăng xuất?");
                dialog.setMessage("Bạn thật sự muốn đăng xuất?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.putString("msg", "Thoát thành công");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    }
                }).setNegativeButton("Hủy", null).show();

             }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BillActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Changepwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangepwdActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}
