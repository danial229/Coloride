
package com.carvision.data.vision.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageContext {

    @SerializedName("cropHintsParams")
    @Expose
    private CropHintsParams cropHintsParams;

    public CropHintsParams getCropHintsParams() {
        return cropHintsParams;
    }

    public void setCropHintsParams(CropHintsParams cropHintsParams) {
        this.cropHintsParams = cropHintsParams;
    }

}
