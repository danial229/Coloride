package com.carvision;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.carvision.data.vision.response.LabelAnnotation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabelListAdapter extends RecyclerView.Adapter<LabelListAdapter.ViewHolder> {

    List<LabelAnnotation> labelAnnotations;

    SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public LabelListAdapter() {
    }

    public void setLabelAnnotations(List<LabelAnnotation> labelAnnotations) {
        this.labelAnnotations = labelAnnotations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LabelListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return labelAnnotations == null ? 0 : labelAnnotations.size();
    }

    public List<LabelAnnotation> getSelectedLabels() {
        ArrayList<LabelAnnotation> selectedLabels = new ArrayList<>();
        for (int i = 0; i < labelAnnotations.size(); i++) {
            boolean state = itemStateArray.get(i);
            if (state) {
                selectedLabels.add(labelAnnotations.get(i));
            }
        }

        return selectedLabels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View view;
        final CheckBox labelCheckBox;
        final TextView labelTextView;

        ViewHolder(View view) {
            super(view);
            this.view = view;

            labelCheckBox = view.findViewById(R.id.labelCheckBox);
            labelCheckBox.setClickable(false);
            labelCheckBox.setFocusable(false);
            labelTextView = view.findViewById(R.id.labelTextView);

            view.setOnClickListener(this);
        }

        void bind(int position) {
            LabelAnnotation label = labelAnnotations.get(position);
            labelTextView.setText(label.getDescription() + " (" + label.getScore() + ")");

            if (!itemStateArray.get(position, false)) {
                labelCheckBox.setChecked(false);
            } else {
                labelCheckBox.setChecked(true);
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                labelCheckBox.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            } else {
                labelCheckBox.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }
        }
    }
}
