package com.samsung.itschool.firereconize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    int cameraRequestCode = 001;
    Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classifier = new Classifier(Utils.assetFilePath(this,"fire_net.pt"));
        Button capture = findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,cameraRequestCode);
            }
        });
    }

     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
             Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
             String pred = classifier.predict(imageBitmap);
             Toast.makeText(this, pred, Toast.LENGTH_LONG).show();
         }

     }

}