
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebDetection {

    @SerializedName("bestGuessLabels")
    @Expose
    private List<BestGuessLabel> bestGuessLabels = null;

    @SerializedName("fullMatchingImages")
    @Expose
    private List<FullMatchingImage> fullMatchingImages = null;

    @SerializedName("pagesWithMatchingImages")
    @Expose
    private List<PagesWithMatchingImage> pagesWithMatchingImages = null;

    @SerializedName("partialMatchingImages")
    @Expose
    private List<PartialMatchingImage> partialMatchingImages = null;

    @SerializedName("visuallySimilarImages")
    @Expose
    private List<VisuallySimilarImage> visuallySimilarImages = null;

    @SerializedName("webEntities")
    @Expose
    private List<WebEntity> webEntities = null;


    public List<BestGuessLabel> getBestGuessLabels() {
        return bestGuessLabels;
    }

    public void setBestGuessLabels(List<BestGuessLabel> bestGuessLabels) {
        this.bestGuessLabels = bestGuessLabels;
    }

    public List<FullMatchingImage> getFullMatchingImages() {
        return fullMatchingImages;
    }

    public void setFullMatchingImages(List<FullMatchingImage> fullMatchingImages) {
        this.fullMatchingImages = fullMatchingImages;
    }

    public List<PagesWithMatchingImage> getPagesWithMatchingImages() {
        return pagesWithMatchingImages;
    }

    public void setPagesWithMatchingImages(List<PagesWithMatchingImage> pagesWithMatchingImages) {
        this.pagesWithMatchingImages = pagesWithMatchingImages;
    }

    public List<PartialMatchingImage> getPartialMatchingImages() {
        return partialMatchingImages;
    }

    public void setPartialMatchingImages(List<PartialMatchingImage> partialMatchingImages) {
        this.partialMatchingImages = partialMatchingImages;
    }

    public List<VisuallySimilarImage> getVisuallySimilarImages() {
        return visuallySimilarImages;
    }

    public void setVisuallySimilarImages(List<VisuallySimilarImage> visuallySimilarImages) {
        this.visuallySimilarImages = visuallySimilarImages;
    }

    public List<WebEntity> getWebEntities() {
        return webEntities;
    }

    public void setWebEntities(List<WebEntity> webEntities) {
        this.webEntities = webEntities;
    }

}
