package com.carvision;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //startActivity(new Intent(this, ListActivity.class));
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        ImageView takePictureImageView = findViewById(R.id.takePictureImageView);
        takePictureImageView.setOnClickListener(v -> {
            if (isCameraPermissionGranted()) {
                takePicture();
            } else {
                requestForCameraPermission();
            }
        });

        ImageView openGalleryImageView = findViewById(R.id.openGalleryImageView);
        openGalleryImageView.setOnClickListener(v -> {
            if (isExternalStorageReadWritePermissionGranted()) {
                openGallery();
            } else {
                requestForReadWriteExternalStoragePermissions();
            }
        });

        ImageView existingModelImageView = findViewById(R.id.existingModelImageView);
        existingModelImageView.setOnClickListener(v -> {
            Toast.makeText(this, "Opening existing models...", Toast.LENGTH_LONG).show();
        });
    }



    //Closes the Navigation Drawer by pressing the back button
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }

    //Items in the Navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.camera:
                if (isCameraPermissionGranted()) {
                    takePicture();
                } else {
                    requestForCameraPermission();
                }
                break;
            case R.id.gallery:
                if (isExternalStorageReadWritePermissionGranted()) {
                    openGallery();
                } else {
                    requestForReadWriteExternalStoragePermissions();
                }
                break;
            case R.id.ExModel:
                Toast.makeText(HomeActivity.this, "Existing Model Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.garage:
                Toast.makeText(HomeActivity.this, "Garage Selected", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps"));
                startActivity(intent3);
                break;
            case R.id.myCar:
                Toast.makeText(HomeActivity.this, "My Car Selected", Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(HomeActivity.this, ListActivity.class);
                startActivity(intent5);
                break;
            case R.id.about:
                Toast.makeText(HomeActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent4);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Checks if Camera permission is granted?
     *
     * @return true if Camera permission is granted, false otherwise
     */
    private boolean isCameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks if READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions are granted?
     *
     * @return true if permissions are granted, false otherwise
     */
    private boolean isExternalStorageReadWritePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request Camera permission from user
     */
    private void requestForCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    /**
     * Request Read/Write External storage permissions
     */
    private void requestForReadWriteExternalStoragePermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(this, "This function needs camera permission", Toast.LENGTH_LONG).show();
                }
                break;
            case GALLERY_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "This function needs read/write external storage permissions", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * After image capture or selection
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //
            // Taking photo from camera was successful
            if (requestCode == REQUEST_TAKE_PHOTO) {
                // Photo is saved in mPhotoFile object.
                // We do not need to do anything here.
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                //
                // Selecting photo from gallery was successful
                Uri selectedImage = data.getData();
                try {
                    photoFile = new File(getRealPathFromUri(selectedImage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // resize image file to lower size for camera images
            photoFile = resizeImageFile(photoFile, 300);

            // Convert image to Base64 and pass it to results activity
//            String base64Image = Base64.encodeToString(convertFileToByteArray(photoFile), Base64.DEFAULT);
            Intent resultActivityIntent = new Intent(this, ResultActivity.class);
            resultActivityIntent.putExtra("IMAGE", photoFile.getAbsolutePath());
            startActivity(resultActivityIntent);
        }
    }

    /**
     * Capture image from camera
     */
    private void takePicture() {
        Toast.makeText(this, "Taking Picture...", Toast.LENGTH_LONG).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (file != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
                photoFile = file;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Select image from gallery
     */
    private void openGallery() {
        Toast.makeText(this, "Opening gallery...", Toast.LENGTH_LONG).show();
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    /**
     * Create file with current timestamp name
     *
     * @return the generated file
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(fileName, ".jpg", storageDir);
        return file;
    }

    private String extractFileName(File file) {
        String filePath = file.getAbsolutePath();
        String fileNameWithSuffix = filePath.substring(filePath.lastIndexOf("/") + 1);
        String fileName = fileNameWithSuffix.substring(0, fileNameWithSuffix.lastIndexOf("."));
        return fileName;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private File resizeImageFile(File source, int destWidth) {
        File resized = source;
        Bitmap bitmap = BitmapFactory.decodeFile(source.getAbsolutePath());
        int origWidth = bitmap.getWidth();
        int origHeight = bitmap.getHeight();
        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            int destHeight = (destWidth * origHeight) / origWidth;
            // we create an scaled bitmap so it reduces the image, not just trim it
            Bitmap b2 = Bitmap.createScaledBitmap(bitmap, destWidth, destHeight, false);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            b2.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            String fileName = "/" + extractFileName(photoFile);
            resized = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + fileName + "-resized");

            try {
                resized.createNewFile();
                FileOutputStream fo = new FileOutputStream(resized);
                fo.write(outStream.toByteArray());
                // remember close de FileOutput
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resized;
    }
}
