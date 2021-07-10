package hcmute.edu.vn.mssv18110298.Database;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Bill implements Serializable {
    int BillID;
    int CartId;
    int Total;

    public Bill(int BillID, int cartId, int total){
        super();
        this.BillID = BillID;
        this.CartId = cartId;
        this.Total = total;
    }

    public Bill(int cartId, int total)
    {
        super();
        this.CartId = cartId;
        this.Total = total;
    }


    public int getBillID() {
        return BillID;
    }

    public void setBillID(int billID) {
        BillID = billID;
    }

    public int getCartId()
    {
        return CartId;
    }

    public void setCartId(int CartId)
    {
        this.CartId = CartId;
    }

    public int getTotal(){
        return Total;
    }

    public void setTotal(int Total){
        this.Total = Total;
    }
}
