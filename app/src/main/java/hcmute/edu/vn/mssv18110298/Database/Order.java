package hcmute.edu.vn.mssv18110298.Database;

public class Order {

    int CartId;
    int ProductId;
    int amount;
    int price;

    public Order(int cartId, int productId, int amount, int price) {
        super();
        this.CartId = cartId;
        this.ProductId = productId;
        this.amount = amount;
        this.price = price;
    }

    public Order(int productId, int amount, int price){
        super();
        this.ProductId = productId;
        this.amount = amount;
        this.price = price;
    }

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getamount() {
        return amount;
    }

    public void setamount(int amount) {
        this.amount = amount;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        this.price = price;
    }
}
