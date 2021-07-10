package hcmute.edu.vn.mssv18110298.Database;

import java.io.Serializable;

import hcmute.edu.vn.mssv18110298.BuildConfig;

public class Cart implements Serializable {
    int CartId;
    int UserId;

    public Cart(int CartId, int userId)
    {
        super();
        this.CartId = CartId;
        this.UserId = userId;
    }

    public int getCartId(){
        return CartId;
    }

    public void setCartId(int CartId){
        this.CartId = CartId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }
}
