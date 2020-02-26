package com.carvision;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carvision.model.Car;
import com.carvision.model.Color;
import com.carvision.model.Label;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> cars;
    private Context context;

    public CarListAdapter(Context context, List<Car> cars) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = cars.get(position);

        // Car Image
        byte[] decodedString = Base64.decode(car.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.carPhotoImageView.setImageBitmap(decodedByte);

        // Car labels
        String labelsStr = "LABELS:\n";
        for (Label label : car.getLabels()) {
            labelsStr = labelsStr.concat(label.getLabel()).concat(" (").concat(String.valueOf(label.getScore())).concat(") \n");
        }
        holder.labelsTextView.setText(labelsStr);

        // Car colors
        String colorsStr = "\nColors:\n";
        for (Color color : car.getColors()) {
            String c = color.getHexColor() + " (Red:" + color.getRed() + ", Green:" + color.getGreen() + ", Blue:" + color.getBlue() + ", Score: " + color.getScore() + ")\n";
            colorsStr = colorsStr.concat(c);
        }
        holder.colorsTextView.setText(colorsStr);

        holder.view.setOnClickListener(v-> {
            Intent i = new Intent(context, ChangeColorActivity.class);
            i.putExtra("CAR_ID", car.getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final ImageView carPhotoImageView;
        public final TextView labelsTextView;
        public final TextView colorsTextView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            carPhotoImageView = view.findViewById(R.id.carPhotoImageView);
            labelsTextView = view.findViewById(R.id.labelsTextView);
            colorsTextView = view.findViewById(R.id.colorsTextView);
        }

    }

}
