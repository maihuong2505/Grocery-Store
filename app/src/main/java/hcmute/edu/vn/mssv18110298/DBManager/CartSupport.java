package hcmute.edu.vn.mssv18110298.DBManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import hcmute.edu.vn.mssv18110298.Activity.CartActivity;
import hcmute.edu.vn.mssv18110298.Activity.InformationActivity;
import hcmute.edu.vn.mssv18110298.Activity.LoginActivity;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Order;
import hcmute.edu.vn.mssv18110298.Database.Temporder;
import hcmute.edu.vn.mssv18110298.R;

public class CartSupport extends RecyclerView.Adapter<CartSupport.ViewHolder> {

    Context mContext;
    List<Temporder> temporderList;
    Cart mCart;
    DBManager dbManager;
    ImageView imageView;
    TextView textView;
    TextView mtxtPrice;


    public CartSupport(Context context, List<Temporder> temporders, Cart cart, DBManager dbManager, ImageView imageView, TextView textView, TextView mtxtPrice){
        this.mContext = context;
        this.temporderList = temporders;
        this.mCart = cart;
        this.dbManager = dbManager;
        this.imageView = imageView;
        this.textView = textView;
        this.mtxtPrice = mtxtPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int picture = temporderList.get(position).getImage();
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), picture);
        holder.img1.setImageBitmap(image);

        String name = temporderList.get(position).getProductName();
        holder.txtProductName.setText(name);

        Integer price = temporderList.get(position).getprice();
        holder.txtPrice.setText(standardizeProductPrice(price));

        Integer a = temporderList.get(position).getamount();
        holder.txtAmount.setText(a.toString());

        mtxtPrice.setText(standardizeProductPrice(total()));

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

    @Override
    public int getItemCount() {
        return temporderList.size();
    }

    public int total(){
        int total = 0;
        for (int i = 0; i < temporderList.size(); i++){
            total += temporderList.get(i).getprice() * temporderList.get(i).getamount();
        }
        return total;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        TextView txtProductName, txtPrice, txtAmount;
        ImageView img1;
        ImageButton btnMinus, btnPlus;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtProductName= itemView.findViewById(R.id.txtProductName);
            img1 = itemView.findViewById(R.id.img1);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            txtAmount = itemView.findViewById(R.id.txtAmount);

            itemView.setOnLongClickListener(this::onLongClick);
            btnMinus.setOnClickListener(this::onClick);
            btnPlus.setOnClickListener(this::onClick);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Temporder temporder = temporderList.get(position);
                Order order = new Order(temporder.getCartId(), temporder.getProductId(), temporder.getamount(), temporder.getprice());
                AlertDialog.Builder dialog =	new AlertDialog.Builder(mContext);
                dialog.setTitle("Xóa sản phầm");
                dialog.setMessage("Bạn thật sự muốn xóa sản phẩm?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dbManager.deleteProductinCart(order);
                        temporderList = dbManager.displayProducts(mCart);
                        mtxtPrice.setText(standardizeProductPrice(total()));
                        notifyDataSetChanged();
                        if(temporderList.size()==0){
                            img1.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                }).setNegativeButton("Hủy", null).show();
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Temporder temporder = temporderList.get(position);
                Order order = new Order(temporder.getCartId(), temporder.getProductId(), temporder.getamount(), temporder.getprice());
                switch (v.getId()) {
                    case R.id.btnMinus:
                        if (order.getamount() == 1)
                            return;
                        order.setamount(order.getamount() - 1);
                        dbManager.updateOrder(order);
                        temporderList = dbManager.displayProducts(mCart);
                        notifyDataSetChanged();

                        int total = 0;
                        for (int i = 0; i < temporderList.size(); i++){
                            total += temporderList.get(i).getprice() * temporderList.get(i).getamount();
                        }
                        txtPrice.setText(standardizeProductPrice(total));
                        break;
                    case R.id.btnPlus:
                        order.setamount(order.getamount() + 1);
                        dbManager.updateOrder(order);
                        temporderList = dbManager.displayProducts(mCart);
                        notifyDataSetChanged();
                        txtPrice.setText(standardizeProductPrice(total()));
                        break;
                }
            }
        }
    }
}
