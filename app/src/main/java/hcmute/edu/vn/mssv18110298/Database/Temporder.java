package hcmute.edu.vn.mssv18110298.Database;

public class Temporder {
    int CartId;
    int ProductId;
    String ProductName;
    int Image;
    int amount;
    int price;

    public Temporder(int cartId, int productId, int image, String productName, int amount, int price){
        super();
        this.CartId = cartId;
        this.ProductId = productId;
        this.ProductName = productName;
        this.Image = image;
        this.amount = amount;
        this.price = price;
    }

    public Temporder(int productId, String productName, int image, int amount, int price){
        super();
        this.ProductId = productId;
        this.ProductName = productName;
        this.Image = image;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getamount() {
        return amount;
    }

    public void setamount(int amount) {
        amount = amount;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        price = price;
    }
}
