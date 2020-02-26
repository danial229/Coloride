
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagesWithMatchingImage {

    @SerializedName("fullMatchingImages")
    @Expose
    private List<FullMatchingImage> fullMatchingImages = null;

    @SerializedName("pageTitle")
    @Expose
    private String pageTitle;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("partialMatchingImages")
    @Expose
    private List<PartialMatchingImage> partialMatchingImages = null;

    public List<FullMatchingImage> getFullMatchingImages() {
        return fullMatchingImages;
    }

    public void setFullMatchingImages(List<FullMatchingImage> fullMatchingImages) {
        this.fullMatchingImages = fullMatchingImages;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<PartialMatchingImage> getPartialMatchingImages() {
        return partialMatchingImages;
    }

    public void setPartialMatchingImages(List<PartialMatchingImage> partialMatchingImages) {
        this.partialMatchingImages = partialMatchingImages;
    }

}
