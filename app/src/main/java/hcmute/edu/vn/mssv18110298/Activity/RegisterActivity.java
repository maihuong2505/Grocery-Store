package hcmute.edu.vn.mssv18110298.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.edu.vn.mssv18110298.DBManager.DBManager;
import hcmute.edu.vn.mssv18110298.DBManager.EmailAPI;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.User;
import hcmute.edu.vn.mssv18110298.R;

public class RegisterActivity extends AppCompatActivity{

    ImageButton backbtn1;
    Button registerbtn;
    EditText name, lastname, email, pwd, confirmpwd, gender, phone, address;
    CheckBox checkBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registefrm);

        backbtn1 = findViewById(R.id.backbtn1);
        registerbtn = findViewById(R.id.registerbtn);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        confirmpwd = findViewById(R.id.confirmpwd);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        checkBox2 = findViewById(R.id.checkBox2);

        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = name.getText().toString().trim();
                String lname = lastname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = pwd.getText().toString().trim();
                String passconfrm = confirmpwd.getText().toString().trim();
                String sex = gender.getText().toString().trim();
                String phoneno = phone.getText().toString().trim();
                String addr = address.getText().toString().trim();

                if(fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || pass.isEmpty() || passconfrm.isEmpty() || sex.isEmpty() || phoneno.isEmpty() || addr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!isEmailValid(mail)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại email hợp lệ", Toast.LENGTH_LONG).show();
                    email.requestFocus();
                    return;
                }

                DBManager dbManager = new DBManager(getApplicationContext());
                dbManager.openDataBase();

                if(dbManager.emailExisted(mail)){
                    Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_LONG).show();
                    email.requestFocus();
                    return;
                }

                if(!pass.equals(passconfrm)){
                    Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();
                    pwd.requestFocus();
                    return;
                }

                if(!isPasswordValid(pass)){
                    Toast.makeText(getApplicationContext(),"Mật khẩu phải có ít nhất 8 ký tự, trong đó có ít nhất 1 chữ in hoa và 1 số", Toast.LENGTH_LONG).show();
                    pwd.requestFocus();
                    return;
                }

                if(!checkBox2.isChecked()){
                    Toast.makeText(getApplicationContext(), "Bạn phải đồng ý với điều khoản", Toast.LENGTH_LONG).show();
                    return;
                }


                int id = dbManager.generateId();
                int cartID = dbManager.generateCartId();

                String fullname = fname + lname;
                dbManager.addUser(id, fullname, sex, phoneno, addr, mail, pass);
                Cart cart = new Cart(cartID, id);
                dbManager.addCart(cart);

                Intent intent = new Intent(getApplicationContext(), RegisterConfirmActivity.class);

                intent.putExtra("mail", mail);

                String Subject = "WELCOME TO GROCERY STORE";
                String mess = "Chào mừng bạn đến với Grocery Store. Chúng tôi rất mong bạn có những phút giây mua sắm thoải mái nhất.\n Trân trọng kính chào!";
                EmailAPI emailAPI = new EmailAPI(getApplicationContext(), mail, Subject, mess);
                emailAPI.execute();

                startActivity(intent);
            }
        });
    }
    private boolean isEmailValid(String mail) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    private boolean isPasswordValid(String pass) {
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
