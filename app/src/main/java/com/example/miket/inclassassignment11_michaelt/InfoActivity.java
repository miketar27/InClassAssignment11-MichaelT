package com.example.miket.inclassassignment11_michaelt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class InfoActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView infoTeamName = (TextView) findViewById(R.id.info_team_name);
        TextView infoTeamRanking = (TextView) findViewById(R.id.info_team_ranking);
        final ImageView infoTeamLogo = (ImageView) findViewById(R.id.default_pic);


        Intent intent = getIntent();
        NHLTeam team = (NHLTeam) intent.getSerializableExtra("A Team");

        infoTeamName.setText(team.getTeamName());
        infoTeamRanking.setText(team.getTeamRanking());

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference uploadRef = mStorageRef.child("images/" + team.getFileName());
        try {
            final File localFile = File.createTempFile("images", "jpg");
            uploadRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            Picasso.with(InfoActivity.this)
                                    .load(localFile)
                                    .resize(infoTeamLogo.getWidth(), infoTeamLogo.getHeight())
                                    .centerInside()
                                    .into(infoTeamLogo);
                            // ...
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
