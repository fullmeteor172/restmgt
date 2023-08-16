package com.example.restaurant_manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private ArrayList<Order> ordersList;
    private OrderDatabaseHelper dbHelper;

    private TextView totalEarnedTextView; // Add this line

    public OrderAdapter(Context context, ArrayList<Order> ordersList, OrderDatabaseHelper dbHelper, TextView totalEarnedTextView) {
        this.context = context;
        this.ordersList = ordersList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order = ordersList.get(position);
        holder.txtCustomerName.setText(order.getCustomerName());
        holder.txtOrder.setText(order.getOrder());
        holder.txtAmount.setText("$" + order.getTotalAmount());
        holder.txtOrderType.setText(order.isTakeAway() ? "Take Away" : "Dining");

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.markOrderAsDone(order.getId());
                order.setDone(true);
                notifyDataSetChanged();
            }
        });

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteOrder(order.getId());
                ordersList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.btnDone.setVisibility(order.isDone() ? View.GONE : View.VISIBLE);
        holder.btnCancel.setVisibility(order.isDone() ? View.GONE : View.VISIBLE);
    }

    public void markOrderAsDone(int orderId) {
        dbHelper.markOrderAsDone(orderId);
        for (Order order : ordersList) {
            if (order.getId() == orderId) {
                order.setDone(true);
                break;
            }
        }
        notifyDataSetChanged();

        double totalEarned = calculateTotalEarned();
        updateTotalEarnedUI(totalEarned);
    }

    private double calculateTotalEarned() {
        double totalEarned = 0;
        for (Order order : ordersList) {
            if (order.isDone()) {
                totalEarned += order.getTotalAmount();
            }
        }
        return totalEarned;
    }

    private void updateTotalEarnedUI(double totalEarned) {
        totalEarnedTextView.setText("Total Earned: $" + totalEarned);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomerName, txtOrder, txtAmount, txtOrderType;
        Button btnDone, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtOrder = itemView.findViewById(R.id.txtOrder);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtOrderType = itemView.findViewById(R.id.txtOrderType);
            btnDone = itemView.findViewById(R.id.btnDone);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
