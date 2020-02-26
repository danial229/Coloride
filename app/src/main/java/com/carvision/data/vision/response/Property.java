
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Property {

    @SerializedName("detectedBreak")
    @Expose
    private DetectedBreak detectedBreak;

    public DetectedBreak getDetectedBreak() {
        return detectedBreak;
    }

    public void setDetectedBreak(DetectedBreak detectedBreak) {
        this.detectedBreak = detectedBreak;
    }

}
