package com.carvision;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.carvision.model.Car;
import com.carvision.model.SQLiteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeColorActivity extends AppCompatActivity {

    private Car car;
    private Bitmap carBitmap;
    private int carDominantColor;
    private int carDominantColorR;
    private int carDominantColorG;
    private int carDominantColorB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView carPhotoImageView = findViewById(R.id.carPhotoImageView);

        SeekBar redSeekBar = findViewById(R.id.redSeekBar);
        SeekBar greenSeekBar = findViewById(R.id.greenSeekBar);
        SeekBar blueSeekBar = findViewById(R.id.blueSeekBar);
        SeekBar hardnessSeekBar = findViewById(R.id.hardnessSeekBar);

        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int red = redSeekBar.getProgress();
                int green = greenSeekBar.getProgress();
                int blue = blueSeekBar.getProgress();
                int hardness = hardnessSeekBar.getProgress();

                int width = carBitmap.getWidth();
                int height = carBitmap.getHeight();

                int[] pixels = new int[width * height];

                for(com.carvision.model.Color c: car.getColors()) {
                    carDominantColorR = c.getRed();
                    carDominantColorG = c.getGreen();
                    carDominantColorB = c.getBlue();

                    for (int y = 0; y < height; y++) {
                        int offset = y * width;
                        for (int x = 0; x < width; x++) {
                            int pixelColor = carBitmap.getPixel(x, y);

                            int colorR = Color.red(pixelColor);
                            int colorG = Color.green(pixelColor);
                            int colorB = Color.blue(pixelColor);

                            if(isColorInRange(colorR, carDominantColorR, hardness)
                                    && isColorInRange(colorG, carDominantColorG, hardness)
                                    && isColorInRange(colorB, carDominantColorB, hardness)) {
                                int bestR = findBestColor(colorR, carDominantColorR, red);
                                int bestG = findBestColor(colorG, carDominantColorG, green);
                                int bestB = findBestColor(colorB, carDominantColorB, blue);
                                pixels[offset + x] = Color.rgb(bestR, bestG, bestB);
                            } else {
                                pixels[offset + x] = pixelColor;
                            }
                        }
                    }
                }

                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                carPhotoImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };


        redSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        hardnessSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        Long carId = getIntent().getLongExtra("CAR_ID", 0);
        SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(this);
        car = sqLiteAdapter.load(carId);

        if (car != null) {
            // Car Image
            byte[] decodedString = Base64.decode(car.getImage(), Base64.DEFAULT);
            carBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            carPhotoImageView.setImageBitmap(carBitmap);
        }
    }

    private boolean isColorInRange(int firstColor, int secondColor, int hardness) {
        return firstColor - hardness < secondColor && secondColor < firstColor + hardness;
    }

    private int findBestColor(int firstColor, int secondColor, int matchColor) {
        int delta = secondColor - firstColor;
        int resultColor = matchColor - delta;
        if(resultColor < 0) {
            resultColor = 0;
        } else if(resultColor > 255) {
            resultColor = 255;
        }

        return resultColor;
    }

}
