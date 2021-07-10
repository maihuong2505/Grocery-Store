package hcmute.edu.vn.mssv18110298.Database;

import java.io.Serializable;

public class Product implements Serializable {
    int ProductID;
    String ProductName;
    int Price;
    int Image;
    int Amount;
    String Unit;
    int GroupID;


    public Product(int ProductID, String ProductName, int Price, int Image, int Amount, String Unit, int GroupID) {
        super();
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Image = Image;
        this.Amount = Amount;
        this.Unit = Unit;
        this.GroupID = GroupID;
    }

    public Product(String ProductName, int Price, int image, int Amount, String Unit, int GroupID) {
        super();
        this.ProductName = ProductName;
        this.Price = Price;
        this.Image = Image;
        this.Amount = Amount;
        this.Unit = Unit;
        this.GroupID = GroupID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }
}
