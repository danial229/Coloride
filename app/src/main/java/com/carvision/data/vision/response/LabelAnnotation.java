
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabelAnnotation {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("mid")
    @Expose
    private String mid;

    @SerializedName("score")
    @Expose
    private Double score;

    @SerializedName("topicality")
    @Expose
    private Double topicality;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTopicality() {
        return topicality;
    }

    public void setTopicality(Double topicality) {
        this.topicality = topicality;
    }

}
