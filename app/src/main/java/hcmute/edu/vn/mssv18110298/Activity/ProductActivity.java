package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.DBManager.ProductSupport;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Product;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class ProductActivity extends AppCompatActivity {

    ImageButton cartbtn, backbtn1;
    EditText searchtxt;
    RecyclerView recyclerView;
    List<Product> product;
    ProductSupport Ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productviewer);

        searchtxt = findViewById(R.id.searchtxt);
        recyclerView = findViewById(R.id.recyclerView);
        cartbtn = findViewById(R.id.cartbtn);
        backbtn1 = findViewById(R.id.backbtn1);

        int GroupID = getIntent().getIntExtra("GroupID", 1);

        DBManager db = new DBManager(getApplicationContext());
        db.createDataBase();
        db.openDataBase();

        product = db.getProducts(GroupID);

        Ps = new ProductSupport(this, product);
        recyclerView.setAdapter(Ps);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        String email = getIntent().getStringExtra("email");
        User user = db.getUserByEmail(email);
        Cart cart = db.getCart(user);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchtxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Intent intent = new Intent(v.getContext(), ProductActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("type", "from text");
                    intent.putExtra("text", searchtxt.getText().toString().trim());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

}
