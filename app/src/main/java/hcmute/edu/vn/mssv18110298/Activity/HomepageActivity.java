package hcmute.edu.vn.mssv18110298.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class HomepageActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Animation in,out;
    EditText searchtxt;
    ImageButton cartbtn, loginbtn1, btnNuoc, btnBanhkeo, btnDokho, btnCanhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        ActionViewFlipper();

        searchtxt = findViewById(R.id.searchtxt);
        cartbtn = findViewById(R.id.cartbtn);
        btnNuoc = findViewById(R.id.btnNuoc);
        btnBanhkeo = findViewById(R.id.btnBanhkeo);
        btnDokho = findViewById(R.id.btnDokho);
        btnCanhan = findViewById(R.id.btnCanhan);
        loginbtn1 = findViewById(R.id.loginbtn1);

        cartbtn.setOnClickListener(this::onClick);
        btnNuoc.setOnClickListener(this::onClick);
        btnBanhkeo.setOnClickListener(this::onClick);
        btnDokho.setOnClickListener(this::onClick);
        btnCanhan.setOnClickListener(this::onClick);


        String email = getIntent().getStringExtra("email");
        DBManager dbManager = new DBManager(getApplicationContext());
        User user = dbManager.getUserByEmail(email);
        Cart cart = dbManager.getCart(user);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), CartActivity.class);
                it.putExtra("email", email);
                it.putExtra("cart", cart);
                startActivity(it);
            }
        });

        loginbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = getIntent().getStringExtra("email");
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    private void onClick(View view) {
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        intent.putExtra("email", email);
        switch (view.getId()) {
            case R.id.btnNuoc:
                intent.putExtra("GroupID", 1);
                break;
            case R.id.btnBanhkeo:
                intent.putExtra("GroupID", 2);
                break;
            case R.id.btnDokho:
                intent.putExtra("GroupID", 3);
                break;
            case R.id.btnCanhan:
                intent.putExtra("GroupID", 4);
                break;
        }
        startActivity(intent);

    }

    private void ActionViewFlipper() {
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        in = AnimationUtils.loadAnimation(this,R.anim.slideinright);
        out = AnimationUtils.loadAnimation(this,R.anim.slideoutright);
        viewFlipper.setAnimation(in);
        viewFlipper.setAnimation(out);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
    }
}