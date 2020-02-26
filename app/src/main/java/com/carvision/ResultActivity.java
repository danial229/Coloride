package com.carvision;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carvision.api.GoogleVisionServiceEndpoint;
import com.carvision.data.vision.request.CropHintsParams;
import com.carvision.data.vision.request.Feature;
import com.carvision.data.vision.request.Image;
import com.carvision.data.vision.request.ImageContext;
import com.carvision.data.vision.request.Request;
import com.carvision.data.vision.request.VisionRequest;
import com.carvision.data.vision.response.Color;
import com.carvision.data.vision.response.DominantColors;
import com.carvision.data.vision.response.ImagePropertiesAnnotation;
import com.carvision.data.vision.response.LabelAnnotation;
import com.carvision.data.vision.response.Response;
import com.carvision.data.vision.response.VisionResponse;
import com.carvision.model.Car;
import com.carvision.model.Label;
import com.carvision.model.SQLiteAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity {

    private File photoFile;

    private RecyclerView labelsRecyclerView;
    private RecyclerView colorsRecyclerView;

    GoogleVisionServiceEndpoint endpoint;
    private Response response;

    ProgressBar progressBar;

    private LabelListAdapter labelListAdapter;
    private ColorListAdapter colorListAdapter;

    private Button saveToDatabaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Get image from intent's extras
        String imagePath = getIntent().getExtras().getString("IMAGE");
        photoFile = new File(imagePath);

        ImageView carImageView = findViewById(R.id.carImageView);
        Glide.with(this).load(photoFile).into(carImageView);

        progressBar = findViewById(R.id.progressBar);

        labelsRecyclerView = findViewById(R.id.labelsRecyclerView);
        labelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        labelListAdapter = new LabelListAdapter();
        labelsRecyclerView.setAdapter(labelListAdapter);

        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        colorListAdapter = new ColorListAdapter();
        colorsRecyclerView.setAdapter(colorListAdapter);

        RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.labelsRadioButton:
                    labelsRecyclerView.setVisibility(View.VISIBLE);
                    colorsRecyclerView.setVisibility(View.INVISIBLE);
                    break;
                case R.id.colorsRadioButton:
                    labelsRecyclerView.setVisibility(View.INVISIBLE);
                    colorsRecyclerView.setVisibility(View.VISIBLE);
                    break;
            }
        });

        optionsRadioGroup.check(R.id.labelsRadioButton);

        saveToDatabaseButton = findViewById(R.id.saveToDatabaseButton);
        saveToDatabaseButton.setOnClickListener(v -> saveToDatabase());
        saveToDatabaseButton.setEnabled(false);

        //
        // Initializing Retrofit to access API endpoint
        Retrofit retrofit = createRetrofit();

        endpoint = retrofit.create(GoogleVisionServiceEndpoint.class);

        triggerGoogleVisionApi();
    }


    private Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vision.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(createOkHttpClient())
                .build();

        return retrofit;
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        return builder.build();
    }

    /**
     * Trigger query Google Vision API service
     */
    private void triggerGoogleVisionApi() {
        if (photoFile == null) {
            throw new IllegalStateException("Photo is null");
        }

        VisionRequest visionRequest = createVisionRequest();
        Call<VisionResponse> visionCall = endpoint.getPhotoLabels(visionRequest, "AIzaSyCy0Pl_zPG2HANIQlrjIlIcgo1lpTjRxhY");

        visionCall.enqueue(new Callback<VisionResponse>() {
            @Override
            public void onResponse(Call<VisionResponse> call, retrofit2.Response<VisionResponse> serviceResponse) {
                progressBar.setVisibility(View.GONE);

                if (serviceResponse.isSuccessful()) {

                    saveToDatabaseButton.setEnabled(true);

                    VisionResponse visionResponse = serviceResponse.body();

                    if (visionResponse != null && visionResponse.getResponses() != null && !visionResponse.getResponses().isEmpty()) {
                        response = visionResponse.getResponses().get(0);

                        if (response.getLabelAnnotations() != null && !response.getLabelAnnotations().isEmpty()) {
                            // Extract image's list of label annotation
                            List<LabelAnnotation> labelAnnotations = response.getLabelAnnotations();
                            labelListAdapter.setLabelAnnotations(labelAnnotations);
                        }

                        // Extract image's list of dominant colors
                        ImagePropertiesAnnotation imagePropertiesAnnotation = response.getImagePropertiesAnnotation();
                        if (imagePropertiesAnnotation != null) {
                            DominantColors dominantColors = imagePropertiesAnnotation.getDominantColors();
                            if (dominantColors != null) {
                                List<Color> colors = dominantColors.getColors();

                                // Sort colors by score
                                Collections.sort(colors);

                                colorListAdapter.setColors(colors);
                            }
                        }
                    } else {
                        saveToDatabaseButton.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<VisionResponse> call, Throwable t) {
            }
        });
    }

    private VisionRequest createVisionRequest() {
        // Create a LABEL_DETECTION and LOGO_DETECTION request
        Request request = new Request();

        // Create Base64 version of image
        Image image = new Image();
        String base64Image = Base64.encodeToString(convertFileToByteArray(photoFile), Base64.DEFAULT);
        image.setContent(base64Image);
        request.setImage(image);

        // Create ImageContext
        CropHintsParams cropHintsParams = new CropHintsParams();
        List<Double> aspectRations = new ArrayList<>(3);
        aspectRations.add(1.0);
        aspectRations.add(1.0);
        aspectRations.add(1.0);
        cropHintsParams.setAspectRatios(aspectRations);
        ImageContext imageContext = new ImageContext();
        imageContext.setCropHintsParams(cropHintsParams);
        request.setImageContext(imageContext);

        // Create Features
        Feature labelFeature = new Feature();
        labelFeature.setType("LABEL_DETECTION");
        labelFeature.setMaxResults(50);

        Feature imagePropertiesFeature = new Feature();
        imagePropertiesFeature.setType("IMAGE_PROPERTIES");
        imagePropertiesFeature.setMaxResults(50);

        List<Feature> featureList = new ArrayList<>(2);
        featureList.add(labelFeature);
        featureList.add(imagePropertiesFeature);
        request.setFeatures(featureList);

        List<Request> requestList = new ArrayList<>(1);
        requestList.add(request);

        // Create VisionRequest: List of Requests
        VisionRequest visionRequest = new VisionRequest();
        visionRequest.setRequests(requestList);

        return visionRequest;
    }

    private void saveToDatabase() {
        if (response == null) {
            throw new IllegalStateException("Response from server is null");
        }

        if (labelListAdapter.getSelectedLabels().isEmpty()) {
            Toast.makeText(this, "You have to select at least one label.", Toast.LENGTH_LONG).show();
            return;
        }

        if (colorListAdapter.getSelectedColors().isEmpty()) {
            Toast.makeText(this, "You have to select at least one color.", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(this);
        Car car = new Car();
        car.setImage(Base64.encodeToString(convertFileToByteArray(photoFile), Base64.DEFAULT));

        for (Color color : colorListAdapter.getSelectedColors()) {
            com.carvision.model.Color c = new com.carvision.model.Color();
            c.setScore(color.getScore());
            c.setRed(color.getColor().getRed());
            c.setGreen(color.getColor().getGreen());
            c.setBlue(color.getColor().getBlue());

            car.addColor(c);
        }

        for (LabelAnnotation label : labelListAdapter.getSelectedLabels()) {
            Label l = new Label();
            l.setScore(label.getScore());
            l.setLabel(label.getDescription());

            car.addLabel(l);
        }

        sqLiteAdapter.saveCar(car);
        Toast.makeText(this, "Saved in database", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, ListActivity.class));
    }

    private byte[] convertFileToByteArray(File file) {
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytesArray;
    }
}
