package com.example.restaurant_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddOrderActivity extends AppCompatActivity {

    private OrderDatabaseHelper dbHelper;
    private EditText edtCustomerName, edtOrder, edtAmount;
    private CheckBox chkTakeAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        dbHelper = new OrderDatabaseHelper(this);

        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtOrder = findViewById(R.id.edtOrder);
        edtAmount = findViewById(R.id.edtAmount);
        chkTakeAway = findViewById(R.id.chkTakeAway);

        Button btnSaveOrder = findViewById(R.id.btnSaveOrder);
        btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerName = edtCustomerName.getText().toString();
                String order = edtOrder.getText().toString();
                double totalAmount = Double.parseDouble(edtAmount.getText().toString());
                boolean isTakeAway = chkTakeAway.isChecked();
                long orderTime = Calendar.getInstance().getTimeInMillis();

                Order newOrder = new Order(0, customerName, order, totalAmount, isTakeAway, orderTime, false);
                dbHelper.insertOrder(newOrder);

                edtCustomerName.getText().clear();
                edtOrder.getText().clear();
                edtAmount.getText().clear();
                chkTakeAway.setChecked(false);

                // Show a toast or feedback
            }
        });
    }
}
