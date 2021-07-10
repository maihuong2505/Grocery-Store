package hcmute.edu.vn.mssv18110298.DBManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.mssv18110298.Activity.DetailProductActivity;
import hcmute.edu.vn.mssv18110298.Database.Product;
import hcmute.edu.vn.mssv18110298.R;

public class ProductSupport extends RecyclerView.Adapter<ProductSupport.ViewHolder> {
    private Context mContext;
    private List<Product> mproduct;

    public ProductSupport(Context context, List<Product> mproduct) {
        this.mContext = context;
        this.mproduct = mproduct;
    }

    @Override
    public int getItemCount() {
        return mproduct.size();
    }

    @Override
    public ProductSupport.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.presentproduct, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ProductSupport.ViewHolder holder, int position) {
        int picture = mproduct.get(position).getImage();
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), picture);
        holder.img.setImageBitmap(image);

        String name = mproduct.get(position).getProductName();
        holder.txtName.setText(name);

        int price = mproduct.get(position).getPrice();
        holder.txtPrice.setText(standardizeProductPrice(price));
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView img;
        public TextView txtName;
        public TextView txtPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) {
                String email = "maihuong180283@gmail.com";
                Product product = mproduct.get(position);
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("email", email);
                mContext.startActivity(intent);
            }
        }
    }

}