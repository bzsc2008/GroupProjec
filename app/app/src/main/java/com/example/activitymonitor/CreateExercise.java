package com.example.activitymonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class CreateExercise extends AppCompatActivity {

    String exerciseNameString, repNumString, setNumString, noteString;
    EditText exerciseName, repNum, setNum, note;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);

        exerciseName = findViewById(R.id.ExerciseNameBox);
        repNum = findViewById(R.id.RepNumBox);
        setNum = findViewById(R.id.SetNumBox);
        note = findViewById(R.id.Notes);

        db = FirebaseFirestore.getInstance();



        Button confirmExerciseButton = findViewById(R.id.button);
        confirmExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseNameString = exerciseName.getText().toString();
                repNumString = repNum.getText().toString();
                setNumString = setNum.getText().toString();
                noteString = note.getText().toString();
                showDialog();
            }
        });
    }

    public void showDialog() {

        AlertDialog.Builder window = new AlertDialog.Builder(CreateExercise.this);
        window.setTitle("Please Confirm Info Below")
                .setMessage("Exercise name: " + exerciseNameString + '\n' + "Reps: " + repNumString+
                        '\n' + "Sets: " + setNumString + '\n' + "Coach notes: " + noteString)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadData(exerciseNameString, repNumString, setNumString, noteString);
                        goBack();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog alert = window.create();
        alert.show();

    }

    private void uploadData(String name, String rep, String set, String note){
        Map<String, Object> activity = new HashMap<>();
        activity.put("ActivityName", name);
        activity.put("Creator", "Zach");
        activity.put("Instructional Notes", note);
        activity.put("Reps", rep);
        activity.put("Sets", set);

        db.collection("Activities").add(activity)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(CreateExercise.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void goBack(){
        Intent intent = new Intent (String.valueOf(CreateExercise.class));
        startActivity(intent);
    }
}
