
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorRGB {

    @SerializedName("blue")
    @Expose
    private Integer blue = 0;

    @SerializedName("green")
    @Expose
    private Integer green = 0;

    @SerializedName("red")
    @Expose
    private Integer red = 0;

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

}
