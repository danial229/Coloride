package com.carvision.model;

import java.util.ArrayList;
import java.util.List;

public class Car {

    private Long id;
    private String image;
    private List<Label> labels;
    private List<Color> colors;

    public String getImage() {
        return image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public void addLabel(Label label) {
        if(labels == null) {
            labels = new ArrayList<>();
        }

        labels.add(label);
    }

    public void addColor(Color color) {
        if(colors == null) {
            colors = new ArrayList<>();
        }

        colors.add(color);
    }
}
