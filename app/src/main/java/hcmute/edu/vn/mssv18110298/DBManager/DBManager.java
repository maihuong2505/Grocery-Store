package hcmute.edu.vn.mssv18110298.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110298.Database.Bill;
import hcmute.edu.vn.mssv18110298.Database.Cart;
import hcmute.edu.vn.mssv18110298.Database.Order;
import hcmute.edu.vn.mssv18110298.Database.Product;
import hcmute.edu.vn.mssv18110298.Database.Temporder;
import hcmute.edu.vn.mssv18110298.Database.User;

public class DBManager extends SQLiteOpenHelper {

    private static String TAG = "DBManager";
    public static String DB_NAME = "Grocerystore.db";
    public static String DB_PATH;
    private SQLiteDatabase mDataBase;
    public  Context mContext;

    //Tables
     String TABLE_USER = "User";
     String TABLE_PRODUCTGROUP = "ProductGroup";
     String TABLE_PRODUCT = "Product";
     String TABLE_CART = "Cart";
     String TABLE_BILL = "Bill";
     String TABLE_ORDER = "`Order`";

     //Common key
    String KEY_ID = "UserID";
    String KEY_PRODUCTID = "ProductID";
    String KEY_PRODUCTNAME = "ProductName";
    String KEY_PRICE = "Price";
    String KEY_IMAGE = "Image";
    String KEY_AMOUNT = "Amount";
    String KEY_PRODUCTNEWID = "ProductId";
    String KEY_CARTNEWID = "CartId";
    String KEY_USERID = "UserId";

    //TBL User
     String KEY_FULLNAME = "Fullname";
     String KEY_GENDER = "Gender";
     String KEY_PHONENUMBER = "Phonenumber";
     String KEY_ADDRESS = "Address";
     String KEY_EMAIL = "Email";
     String KEY_PASSWORD = "Password";

     //TBL Product
     String KEY_UNIT = "Unit";
     String KEY_PRODUCTGROUP = "GroupID";

     //TBL BILL
    String KEY_BILLID = "BillID";
    String KEY_TOTAL = "Total";
    String ORDER = "Temorder";

    public  DBManager(Context context){
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;

    }

    private boolean checkDataBase(){
        File dbfile = new File(DB_PATH + DB_NAME);
        return dbfile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public void createDataBase() {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    //Mở kết nối và đóng kết nối
    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(int ID, String fullname, String gender, String phoneno, String address, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, ID);
        values.put(KEY_FULLNAME, fullname);
        values.put(KEY_GENDER, gender);
        values.put(KEY_PHONENUMBER, phoneno);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, pass);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void updatePass(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, pass);
        db.update(TABLE_USER, values, KEY_EMAIL + " = ?", new String[] {email});
        db.close();
    }

    public boolean checklogin(String email, String password) {
        mDataBase = this.getReadableDatabase();
        String query = "Select * from " + TABLE_USER + " where Email ='"
                + email + "'and Password='" + password + "'";
        Cursor cursor = mDataBase.rawQuery(query, null);
        if (cursor.getCount() >= 1) {
            mDataBase.close();
            return true;
        }


        mDataBase.close();
        return false;

    }

     public boolean emailExisted(String email) {
         mDataBase = this.getReadableDatabase();
         String query = "Select * from " + TABLE_USER + " where Email ='" + email + "'";
         Cursor cursor = mDataBase.rawQuery(query, null);
         if (cursor.getCount() >= 1) {
             mDataBase.close();
             return true;
         }


         mDataBase.close();
         return false;

     }
    public int generateId() {
        String script = "SELECT * FROM " + TABLE_USER + " ORDER BY " + KEY_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if (cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex(KEY_ID)) + 1;
        return 1;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String script = " SELECT * FROM " + TABLE_USER + " WHERE Email = '" + email + "'";
        Cursor cursor = db.rawQuery(script, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setUserID(cursor.getInt(0));
            user.setFullname(cursor.getString(1));
            user.setGender(cursor.getString(2));
            user.setPhonenumber(cursor.getString(3));
            user.setAddress(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setPassword(cursor.getString(6));
        }
        cursor.close();
        db.close();
        return user;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String script = "SELECT * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6));
            productList.add(product);
            cursor.moveToNext();
        }
        return productList;
    }

    public List<Product> getProducts(int GroupID) {
        List<Product> productList = new ArrayList<>();
        String script = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + KEY_PRODUCTGROUP + " = "+  GroupID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6));
            productList.add(product);
            cursor.moveToNext();
        }
        return productList;
    }

    public int generateCartId() {
        String script = "SELECT * FROM " + TABLE_CART + " ORDER BY " + KEY_CARTNEWID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if (cursor.moveToFirst())
            return cursor.getInt(0) + 1;
        return 1;
    }

    public Cart getCart(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String script = "SELECT * FROM " + TABLE_CART + " WHERE " + KEY_USERID + " = " + user.getUserID() + " ORDER BY " + KEY_CARTNEWID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            Cart cart = new Cart(cursor.getInt(0), cursor.getInt(1));
            db.close();
            return cart;
        }
        db.close();
        return null;
    }

    public void addCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CARTNEWID, cart.getCartId());
        values.put(KEY_USERID, cart.getUserId());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CARTNEWID, order.getCartId());
        values.put(KEY_PRODUCTNEWID, order.getProductId());
        values.put(KEY_AMOUNT, order.getamount());
        values.put(KEY_PRICE, order.getprice());

        db.insert(TABLE_ORDER, null, values);
        db.close();
    }

    public boolean isCartEmpty(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String script = "SELECT * FROM " + TABLE_ORDER + " WHERE CartId = " + id;
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.getCount()>0){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public List<Temporder> displayProducts(Cart cart) {
        List<Temporder> temporderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String script = "SELECT * FROM " + ORDER + " WHERE CartId = " + cart.getCartId();
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Temporder temporder = new Temporder(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
            temporderList.add(temporder);
            cursor.moveToNext();
        }

        return temporderList;
    }

    public void deleteProductinCart(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, KEY_CARTNEWID + " = ? AND " + KEY_PRODUCTNEWID + " = ?", new String[] {String.valueOf(order.getCartId()), String.valueOf(order.getProductId())});
        db.close();
    }


    public int generateBillId() {
        String script = "SELECT * FROM " + TABLE_BILL + " ORDER BY " + KEY_BILLID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if (cursor.moveToFirst())
            return cursor.getInt(0) + 1;
        return 1;
    }

    public void newBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_BILLID, bill.getBillID());
        values.put(KEY_CARTNEWID, bill.getCartId());
        values.put(KEY_TOTAL, bill.getTotal());

        db.insert(TABLE_BILL, null, values);
        db.close();
    }

    public void updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, order.getamount());
        db.update(TABLE_ORDER, values, KEY_CARTNEWID + " = ? AND " + KEY_PRODUCTNEWID + " = ?", new String[] {String.valueOf(order.getCartId()), String.valueOf(order.getProductId())});
        db.close();
    }

    public List<Bill> getBill(int userId) {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String script = "SELECT * FROM " + TABLE_BILL + " INNER JOIN " + TABLE_CART + " ON " + TABLE_BILL + "." + KEY_CARTNEWID + " = " + TABLE_CART + "." + KEY_CARTNEWID + " WHERE " + KEY_USERID + " = " + userId;
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Bill bill = new Bill(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
            bills.add(bill);
            cursor.moveToNext();
        }

        return bills;
    }
}
