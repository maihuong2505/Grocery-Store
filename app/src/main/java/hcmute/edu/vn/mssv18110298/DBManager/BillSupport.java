package hcmute.edu.vn.mssv18110298.DBManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.mssv18110298.Activity.BillActivity;
import hcmute.edu.vn.mssv18110298.Database.Bill;
import hcmute.edu.vn.mssv18110298.R;

public class BillSupport extends RecyclerView.Adapter<BillSupport.ViewHolder> {
    private List<Bill> billList;

    public BillSupport(List<Bill> bills) {
        billList = bills;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bill bill = billList.get(position);
        String id = "Đơn hàng " + bill.getBillID();
        holder.txtOrderId.setText(id);
        holder.txtTotal.setText(standardizeProductPrice(bill.getTotal()));
    }

    @Override
    public int getItemCount() {
        return billList.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOrderId, txtTotal;

        public ViewHolder(View view) {
            super(view);
            txtOrderId = view.findViewById(R.id.txtOrderId);
            txtTotal = view.findViewById(R.id.txtTotal);
        }
    }

}
