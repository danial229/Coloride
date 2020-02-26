package com.carvision;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carvision.data.vision.response.Color;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ViewHolder> {

    List<Color> colors;

    SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ColorListAdapter() {
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return colors == null ? 0 : colors.size();
    }

    public List<Color> getSelectedColors() {
        ArrayList<Color> selectedColors = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            boolean state = itemStateArray.get(i);
            if (state) {
                selectedColors.add(colors.get(i));
            }
        }

        return selectedColors;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View view;
        final LinearLayout colorPreviewLayout;
        final TextView colorTextView;
        final CheckBox colorCheckBox;

        ViewHolder(View view) {
            super(view);
            this.view = view;

            colorPreviewLayout = view.findViewById(R.id.colorPreviewLayout);
            colorTextView = view.findViewById(R.id.colorTextView);
            colorCheckBox = view.findViewById(R.id.colorCheckBox);
            colorCheckBox.setClickable(false);
            colorCheckBox.setFocusable(false);

            view.setOnClickListener(this);
        }

        void bind(int position) {
            Color color = colors.get(position);

            String hex = String.format("#%02X%02X%02X", color.getColor().getRed(), color.getColor().getGreen(), color.getColor().getBlue());

            colorTextView.setText(hex + " (" + color.getColor().getRed() + ", " + color.getColor().getGreen() + ", " + color.getColor().getBlue() + ") Score: " + color.getScore());
            colorPreviewLayout.setBackgroundColor(android.graphics.Color.parseColor(hex));

            if (!itemStateArray.get(position, false)) {
                colorCheckBox.setChecked(false);
            } else {
                colorCheckBox.setChecked(true);
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                colorCheckBox.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            } else {
                colorCheckBox.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }
        }
    }
}
