package com.carvision.api;

import com.carvision.data.vision.request.VisionRequest;
import com.carvision.data.vision.response.VisionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoogleVisionServiceEndpoint {

    @POST("v1/images:annotate/")
    Call<VisionResponse> getPhotoLabels(@Body VisionRequest request, @Query("key") String path);

}
