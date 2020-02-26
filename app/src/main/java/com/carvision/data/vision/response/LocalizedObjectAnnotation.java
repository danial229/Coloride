
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalizedObjectAnnotation {

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("mid")
    @Expose
    private String mid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("score")
    @Expose
    private Double score;

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
