package hcmute.edu.vn.mssv18110298.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Order;
import hcmute.edu.vn.mssv18110298.Database.Product;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class DetailProductActivity extends AppCompatActivity {

    ImageButton backbtn;
    TextView txtName, txtPrice;
    ImageView img;
    Button btnAdd;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailproduct);

        backbtn = findViewById(R.id.backbtn);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        img = findViewById(R.id.img);
        btnAdd = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.spinner);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.openDataBase();
        String email = getIntent().getStringExtra("email");

        Product product = (Product) getIntent().getSerializableExtra("product");
        Bitmap image = BitmapFactory.decodeResource(getResources(), product.getImage());

        img.setImageBitmap(image);
        txtName.setText(product.getProductName());
        txtPrice.setText(standardizeProductPrice(product.getPrice()));

        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);

        int myspinner = Integer.parseInt(spinner.getSelectedItem().toString());

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        User user = dbManager.getUserByEmail(email);
        Cart cart = dbManager.getCart(user);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order(cart.getCartId(), product.getProductID(), myspinner, product.getPrice());
                dbManager.addToCart(order);

                Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private String standardizeProductPrice(int Price) {
        String price = String.valueOf(Price);
        String result = "";
        int i = price.length() - 1, count = 0;
        while (i >= 0) {
            result += price.substring(i, i + 1);
            count++;
            if (count == 3 && i != 0) {
                result += ".";
                count = 0;
            }
            i--;
        }
        return new StringBuilder(result).reverse().toString() + " Đồng";
    }
}
