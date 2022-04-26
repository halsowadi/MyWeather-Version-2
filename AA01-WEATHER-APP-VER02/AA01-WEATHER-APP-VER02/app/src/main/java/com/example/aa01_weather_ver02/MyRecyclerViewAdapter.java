package com.example.aa01_weather_ver02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aa01_weather_ver02.databinding.RecyclerviewRowBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// https://developer.android.com/guide/topics/ui/layout/recyclerview#java
// Terrible design issue
// Confusing naming -- RecyclerViewAdapter, ViewHolder, data, textView, viewGroup, etc.

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<MyRecyclerViewData> mv_data;
    Activity cv_activity;
RecyclerviewRowBinding binding;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView cv_tvZip, cv_tvCity, cv_tvTemp;

        public MyViewHolder(View view) {
            super(view);

            // Define click listener for the ViewHolder's View
            cv_tvZip = (TextView) view.findViewById(R.id.vv_tvTime);
            cv_tvCity = (TextView) view.findViewById(R.id.vv_tvCity);
            cv_tvTemp = (TextView) view.findViewById(R.id.vv_tvTemp);
        }

        public TextView getCv_tvZip() {
            return cv_tvZip;
        }
    }

    public MyRecyclerViewAdapter(ArrayList data,Activity activity){

        mv_data = data;
        cv_activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View lv_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(lv_view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        holder.cv_tvZip.setText("zip: "+mv_data.get(position).getZip());
        holder.cv_tvCity.setText(mv_data.get(position).getCity());
        holder.cv_tvTemp.setText(mv_data.get(position).getTemp()+ "°");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "position : " + holder.getLayoutPosition()
                        + " text : " + holder.cv_tvZip.getText(), Toast.LENGTH_SHORT).show();
                Intent lv_it = new Intent(cv_activity, MyListDetailsActivity.class);
                //lv_it.putExtra("OSName",
                   //     (String) mv_data.get(position).getCity());
                lv_it.putExtra("City",
                        (String) mv_data.get(position).getCity().toString());
                lv_it.putExtra("Temp",
                        (String) String.valueOf(mv_data.get(position).getTemp()));

                lv_it.putExtra("TempHigh",
                        (String) String.valueOf(mv_data.get(position).getTempH()));
                lv_it.putExtra("TempLow",
                        (String) String.valueOf(mv_data.get(position).getTempL()));

                lv_it.putExtra("Con",
                        (String) mv_data.get(position).getCon().toString());

                cv_activity.startActivity(lv_it);
                cv_activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "City: " + holder.cv_tvCity.getText()
                        + " Deleted " , Toast.LENGTH_SHORT).show();
              mv_data.remove(0);
                notifyItemRemoved(0);

                return true;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mv_data.size();
    }
}