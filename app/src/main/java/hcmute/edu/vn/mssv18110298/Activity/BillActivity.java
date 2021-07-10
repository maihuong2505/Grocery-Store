package hcmute.edu.vn.mssv18110298.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.BillSupport;
import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.Bill;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class BillActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton backbtn;
    TextView txtThongbao;
    ImageView img;
    List<Bill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billviewer);

        recyclerView = findViewById(R.id.recyclerView);
        backbtn = findViewById(R.id.backbtn);
        txtThongbao = findViewById(R.id.txtThongbao);
        img = findViewById(R.id.img);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.openDataBase();

        String email = getIntent().getStringExtra("email");
        User user = dbManager.getUserByEmail(email);
        bills = dbManager.getBill(user.getUserID());

        if(bills.size()==0){
            img.setVisibility(View.VISIBLE);
            txtThongbao.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {

            img.setVisibility(View.INVISIBLE);
            txtThongbao.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            BillSupport billSupport = new BillSupport(bills);
            recyclerView.setAdapter(billSupport);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }
}
