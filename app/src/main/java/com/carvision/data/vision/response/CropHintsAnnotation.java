
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropHintsAnnotation {

    @SerializedName("cropHints")
    @Expose
    private List<CropHint> cropHints = null;

    public List<CropHint> getCropHints() {
        return cropHints;
    }

    public void setCropHints(List<CropHint> cropHints) {
        this.cropHints = cropHints;
    }

}
