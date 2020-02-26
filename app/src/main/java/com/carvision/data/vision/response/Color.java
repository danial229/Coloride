
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color implements Comparable<Color> {

    @SerializedName("color")
    @Expose
    private ColorRGB color;

    @SerializedName("hex")
    @Expose
    private String hex;

    @SerializedName("percent")
    @Expose
    private Double percent;

    @SerializedName("percentRounded")
    @Expose
    private Integer percentRounded;

    @SerializedName("pixelFraction")
    @Expose
    private Double pixelFraction;

    @SerializedName("rgb")
    @Expose
    private String rgb;

    @SerializedName("score")
    @Expose
    private Double score;

    public ColorRGB getColor() {
        return color;
    }

    public void setColor(ColorRGB color) {
        this.color = color;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getPercentRounded() {
        return percentRounded;
    }

    public void setPercentRounded(Integer percentRounded) {
        this.percentRounded = percentRounded;
    }

    public Double getPixelFraction() {
        return pixelFraction;
    }

    public void setPixelFraction(Double pixelFraction) {
        this.pixelFraction = pixelFraction;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Color o) {
        return o.getScore().compareTo(this.getScore());
    }
}
