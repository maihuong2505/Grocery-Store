package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.CartSupport;
import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.DBManager.EmailAPI;
import hcmute.edu.vn.mssv18110298.Database.Bill;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Order;
import hcmute.edu.vn.mssv18110298.Database.Temporder;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class CartActivity extends AppCompatActivity {

    ImageButton backbtn;
    TextView txtThongbao, txtGiatri;
    RecyclerView recyclerView;
    Button btnThanhtoan, btnTieptuc;
    ImageView img;
    CartSupport cartSupport;
    List<Temporder> temporderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartviewer);

        backbtn = findViewById(R.id.backbtn);
        txtGiatri = findViewById(R.id.txtGiatri);
        txtThongbao = findViewById(R.id.txtThongbao);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        btnTieptuc = findViewById(R.id.btnTieptuc);
        img = findViewById(R.id.img);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DBManager dbManager = new DBManager(getApplicationContext());
        String email = getIntent().getStringExtra("email");
        User user = dbManager.getUserByEmail(email);
        Cart cart = dbManager.getCart(user);
        int id = cart.getCartId();


        recyclerView = findViewById(R.id.recyclerView);
        if(dbManager.isCartEmpty(id)==true){
            txtThongbao.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            btnThanhtoan.setVisibility(View.INVISIBLE);
            txtGiatri.setText("0 Đồng");
        } else {
            txtThongbao.setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
            btnThanhtoan.setVisibility(View.VISIBLE);

            temporderList = dbManager.displayProducts(cart);
            cartSupport = new CartSupport(this, temporderList, cart, dbManager, img, txtThongbao, txtGiatri);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setAdapter(cartSupport);
            recyclerView.setLayoutManager(linearLayoutManager);

        }

        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer t = totalCost();
                String total = t.toString();

                String Subject = "Thông tin đơn hàng";
                String mess = "Đơn hàng từ khách hàng " + email + " đã được tạo thành công.\n Chúc quý khách có thời gian mua sắm vui vẻ!";
                EmailAPI emailAPI = new EmailAPI(getApplicationContext(), email, Subject, mess);
                emailAPI.execute();

                Intent intent = new Intent(getApplicationContext(), CompleteOrderActivity.class);
                intent.putExtra("cart", cart);
                intent.putExtra("email", email);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }

    private int totalCost() {
        int result = 0;
        for (int i = 0; i < temporderList.size(); i++) {
            result += temporderList.get(i).getprice() * temporderList.get(i).getamount();
        }
        return result;
    }
}