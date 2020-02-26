
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullMatchingImage {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
