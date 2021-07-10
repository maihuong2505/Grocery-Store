package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.Bill;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Temporder;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class CompleteOrderActivity extends AppCompatActivity {

    Button homebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completeorder);

        homebtn = findViewById(R.id.homebtn);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.openDataBase();
        String email = getIntent().getStringExtra("email");
        User user = dbManager.getUserByEmail(email);
        Cart cart = dbManager.getCart(user);

        String t = getIntent().getStringExtra("total");
        int totalFinal = Integer.parseInt(t);

        int id = dbManager.generateBillId();
        Bill bill = new Bill(id, cart.getCartId(), totalFinal);
        dbManager.newBill(bill);

        Cart newcart = new Cart(dbManager.generateCartId(), user.getUserID());
        dbManager.addCart(newcart);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}

