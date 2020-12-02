package com.ccps406.expensetracker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewAdpater.ViewHolder> {

    private static final String TAG = "RecyclerViewAdpater";

     public RecyclerViewAdpater(){

     }
    private ArrayList<Double> amountArray = new ArrayList<>();
    private ArrayList<String> descriptionArray = new ArrayList<>();
    private ArrayList<String> dateArray = new ArrayList<>();
    private ArrayList<String> typeArray = new ArrayList<>();

    public RecyclerViewAdpater(ArrayList<Double> amountArray, ArrayList<String> descriptionArray, ArrayList<String> dateArray, ArrayList<String> typeArray) {
        this.amountArray = amountArray;
        this.descriptionArray = descriptionArray;
        this.dateArray = dateArray;
        this.typeArray = typeArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder: called");

        if(typeArray.get(position).equals("Expense")){
            holder.amountD.setText(Double.toString(amountArray.get(position)));
            holder.amountD.setTextColor(Color.RED);
            holder.signD.setText("-");
            holder.signD.setTextColor(Color.RED);
            holder.signDD.setTextColor(Color.RED);
        }
        else{
            holder.amountD.setText(Double.toString(amountArray.get(position)));
            holder.amountD.setTextColor(Color.rgb(0,150,0));
            holder.signD.setText("+");
            holder.signD.setTextColor(Color.rgb(0,150,0));
            holder.signDD.setTextColor(Color.rgb(0,150,0));
        }
        holder.descriptionD.setText(descriptionArray.get(position));
        holder.dateD.setText(dateArray.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return amountArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView amountD;
        TextView descriptionD;
        TextView dateD;
        TextView signD;
        TextView signDD;
        RelativeLayout holder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountD = itemView.findViewById(R.id.amount_display);
            descriptionD = itemView.findViewById(R.id.description_display);
            dateD = itemView.findViewById(R.id.date_display);
            signDD = itemView.findViewById(R.id.sign_dollar_display);
            holder = itemView.findViewById(R.id.infoHolder);
            signD =itemView.findViewById(R.id.sign_amount_display);
        }
    }
}
