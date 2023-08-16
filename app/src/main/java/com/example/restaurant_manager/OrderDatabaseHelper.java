package com.example.restaurant_manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class OrderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "order_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_ORDER = "order_item";
    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    private static final String COLUMN_IS_TAKE_AWAY = "is_take_away";
    private static final String COLUMN_ORDER_TIME = "order_time";
    private static final String COLUMN_IS_DONE = "is_done";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CUSTOMER_NAME + " TEXT, " +
                COLUMN_ORDER + " TEXT, " +
                COLUMN_TOTAL_AMOUNT + " REAL, " +
                COLUMN_IS_TAKE_AWAY + " INTEGER, " +
                COLUMN_ORDER_TIME + " INTEGER, " +
                COLUMN_IS_DONE + " INTEGER DEFAULT 0)"; // Add the DEFAULT 0 for the is_done column
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, order.getCustomerName());
        values.put(COLUMN_ORDER, order.getOrder());
        values.put(COLUMN_TOTAL_AMOUNT, order.getTotalAmount());
        values.put(COLUMN_IS_TAKE_AWAY, order.isTakeAway() ? 1 : 0);
        values.put(COLUMN_ORDER_TIME, order.getOrderTime());
        values.put(COLUMN_IS_DONE, order.isDone() ? 1 : 0);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String customerName = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME));
                @SuppressLint("Range") String order = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER));
                @SuppressLint("Range") double totalAmount = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_AMOUNT));
                @SuppressLint("Range") boolean isTakeAway = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_TAKE_AWAY)) == 1;
                @SuppressLint("Range") long orderTime = cursor.getLong(cursor.getColumnIndex(COLUMN_ORDER_TIME));
                @SuppressLint("Range") boolean isDone = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_DONE)) == 1;

                Order orderObj = new Order(id, customerName, order, totalAmount, isTakeAway, orderTime, isDone);
                ordersList.add(orderObj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ordersList;
    }

    public void markOrderAsDone(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_DONE, 1);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});
        db.close();
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});
        db.close();
    }
}