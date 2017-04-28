package com.example.miket.inclassassignment11_michaelt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTeam extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    FirebaseDatabase database;
    DatabaseReference teamRef;
    private ImageView teamLogo;
    private File photoFile;
    private StorageReference mStorageRef;
    private Uri fileToUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        teamLogo= (ImageView) findViewById(R.id.team_logo_add);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance();
        teamRef = database.getReference("teams");
    }

    public void addPlayer(View view) {
        TextView eTeamName=(TextView) findViewById(R.id.team_name_field);
        TextView eTeamRanking=(TextView) findViewById(R.id.team_ranking_field);

        String teamName= eTeamName.getText().toString();
        String teamRanking= eTeamRanking.getText().toString();

        final String fileName = fileToUpload.getLastPathSegment();
        StorageReference uploadRef = mStorageRef.child("images").child(fileName);
        uploadRef.putFile(fileToUpload)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                });

        NHLTeam team= new NHLTeam(teamName, teamRanking, fileName);
        teamRef.push().setValue(team);

        Toast.makeText(AddTeam.this, teamName + "'s Added Successfully!", Toast.LENGTH_SHORT).show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public void pickPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_TAKE_PHOTO) {
            fileToUpload = Uri.parse(photoFile.toURI().toString());
        } else if (requestCode == REQUEST_PICK_PHOTO) {
            fileToUpload = data.getData();
        }

        Picasso.with(this)
                .load(fileToUpload)
                .resize(teamLogo.getWidth(), teamLogo.getHeight())
                .centerCrop()
                .into(teamLogo);
    }

}
