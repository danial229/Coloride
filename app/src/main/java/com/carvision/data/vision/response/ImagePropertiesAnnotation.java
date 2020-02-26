
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagePropertiesAnnotation {

    @SerializedName("cropHints")
    @Expose
    private List<CropHint> cropHints = null;

    @SerializedName("dominantColors")
    @Expose
    private DominantColors dominantColors;

    public List<CropHint> getCropHints() {
        return cropHints;
    }

    public void setCropHints(List<CropHint> cropHints) {
        this.cropHints = cropHints;
    }

    public DominantColors getDominantColors() {
        return dominantColors;
    }

    public void setDominantColors(DominantColors dominantColors) {
        this.dominantColors = dominantColors;
    }

}
