
package com.carvision.data.vision.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("cropHintsAnnotation")
    @Expose
    private CropHintsAnnotation cropHintsAnnotation;

    @SerializedName("fullTextAnnotation")
    @Expose
    private FullTextAnnotation fullTextAnnotation;

    @SerializedName("imagePropertiesAnnotation")
    @Expose
    private ImagePropertiesAnnotation imagePropertiesAnnotation;

    @SerializedName("labelAnnotations")
    @Expose
    private List<LabelAnnotation> labelAnnotations = null;

    @SerializedName("localizedObjectAnnotations")
    @Expose
    private List<LocalizedObjectAnnotation> localizedObjectAnnotations = null;

    @SerializedName("safeSearchAnnotation")
    @Expose
    private SafeSearchAnnotation safeSearchAnnotation;

    @SerializedName("textAnnotations")
    @Expose
    private List<TextAnnotation> textAnnotations = null;

    @SerializedName("webDetection")
    @Expose
    private WebDetection webDetection;

    public CropHintsAnnotation getCropHintsAnnotation() {
        return cropHintsAnnotation;
    }

    public void setCropHintsAnnotation(CropHintsAnnotation cropHintsAnnotation) {
        this.cropHintsAnnotation = cropHintsAnnotation;
    }

    public FullTextAnnotation getFullTextAnnotation() {
        return fullTextAnnotation;
    }

    public void setFullTextAnnotation(FullTextAnnotation fullTextAnnotation) {
        this.fullTextAnnotation = fullTextAnnotation;
    }

    public ImagePropertiesAnnotation getImagePropertiesAnnotation() {
        return imagePropertiesAnnotation;
    }

    public void setImagePropertiesAnnotation(ImagePropertiesAnnotation imagePropertiesAnnotation) {
        this.imagePropertiesAnnotation = imagePropertiesAnnotation;
    }

    public List<LabelAnnotation> getLabelAnnotations() {
        return labelAnnotations;
    }

    public void setLabelAnnotations(List<LabelAnnotation> labelAnnotations) {
        this.labelAnnotations = labelAnnotations;
    }

    public List<LocalizedObjectAnnotation> getLocalizedObjectAnnotations() {
        return localizedObjectAnnotations;
    }

    public void setLocalizedObjectAnnotations(List<LocalizedObjectAnnotation> localizedObjectAnnotations) {
        this.localizedObjectAnnotations = localizedObjectAnnotations;
    }

    public SafeSearchAnnotation getSafeSearchAnnotation() {
        return safeSearchAnnotation;
    }

    public void setSafeSearchAnnotation(SafeSearchAnnotation safeSearchAnnotation) {
        this.safeSearchAnnotation = safeSearchAnnotation;
    }

    public List<TextAnnotation> getTextAnnotations() {
        return textAnnotations;
    }

    public void setTextAnnotations(List<TextAnnotation> textAnnotations) {
        this.textAnnotations = textAnnotations;
    }

    public WebDetection getWebDetection() {
        return webDetection;
    }

    public void setWebDetection(WebDetection webDetection) {
        this.webDetection = webDetection;
    }

}
