
package com.carvision.data.vision.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropHintsParams {

    @SerializedName("aspectRatios")
    @Expose
    private List<Double> aspectRatios = null;

    public List<Double> getAspectRatios() {
        return aspectRatios;
    }

    public void setAspectRatios(List<Double> aspectRatios) {
        this.aspectRatios = aspectRatios;
    }

}
