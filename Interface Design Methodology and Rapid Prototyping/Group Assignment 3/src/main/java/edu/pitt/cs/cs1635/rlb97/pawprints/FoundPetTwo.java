package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;


public class FoundPetTwo extends AppCompatActivity {

    private Button uploadButton;
    private Button takeButton;
    private ImageView imageView;
    private Button nextButton;

    private final int GALLERY_REQUEST = 0;
    private final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_pet_two);

        imageView = (ImageView) findViewById(R.id.uploadImage);
        nextButton = (Button) findViewById(R.id.petTwoNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean submitPet = getIntent().getBooleanExtra("lost", false);
                Intent intent = new Intent(FoundPetTwo.this, FoundPetThree.class);
                intent.putExtra("lost", submitPet);
                FoundPetTwo.this.startActivity(intent);
            }
        });
    }

    public void onPickImage(View view) {
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoPickerIntent, Intent.ACTION_GET_CONTENT), GALLERY_REQUEST);
    }

    public void onTakeImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
}
