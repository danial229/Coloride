
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DominantColors {

    @SerializedName("colors")
    @Expose
    private List<Color> colors = null;

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

}
