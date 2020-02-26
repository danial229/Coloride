
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropHint {

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("confidence")
    @Expose
    private Double confidence;

    @SerializedName("importanceFraction")
    @Expose
    private Double importanceFraction;

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Double getImportanceFraction() {
        return importanceFraction;
    }

    public void setImportanceFraction(Double importanceFraction) {
        this.importanceFraction = importanceFraction;
    }

}
