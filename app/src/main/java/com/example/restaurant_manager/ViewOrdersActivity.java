package com.example.restaurant_manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class ViewOrdersActivity extends AppCompatActivity {

    private OrderDatabaseHelper dbHelper;
    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private TextView totalEarnedTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        dbHelper = new OrderDatabaseHelper(this);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Order> ordersList = dbHelper.getAllOrders();

        totalEarnedTextView = findViewById(R.id.totalEarnedTextView); // Replace with your TextView ID
        updateTotalEarnedUI(ordersList); // Initial update

        orderAdapter = new OrderAdapter(this, ordersList, dbHelper, totalEarnedTextView); // Pass TextView
        recyclerViewOrders.setAdapter(orderAdapter);

        // Find the toolbar and set it as the support ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back arrow
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Handle back arrow click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateTotalEarnedUI(ArrayList<Order> ordersList) {
        double totalEarned = calculateTotalEarned(ordersList);
        totalEarnedTextView.setText("Total Earned: $" + totalEarned);
    }

    private double calculateTotalEarned(ArrayList<Order> ordersList) {
        double totalEarned = 0;
        for (Order order : ordersList) {
            if (order.isDone()) {
                totalEarned += order.getTotalAmount();
            }
        }
        return totalEarned;
    }
}
