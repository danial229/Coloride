
package com.carvision.data.vision.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SafeSearchAnnotation {

    @SerializedName("adult")
    @Expose
    private String adult;

    @SerializedName("medical")
    @Expose
    private String medical;

    @SerializedName("racy")
    @Expose
    private String racy;

    @SerializedName("spoof")
    @Expose
    private String spoof;

    @SerializedName("violence")
    @Expose
    private String violence;

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getRacy() {
        return racy;
    }

    public void setRacy(String racy) {
        this.racy = racy;
    }

    public String getSpoof() {
        return spoof;
    }

    public void setSpoof(String spoof) {
        this.spoof = spoof;
    }

    public String getViolence() {
        return violence;
    }

    public void setViolence(String violence) {
        this.violence = violence;
    }

}
