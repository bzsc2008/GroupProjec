package com.example.activitymonitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Calender page, allows coach to pick dates that activity's were
 * assigned to
 */
public class CoachCalendar extends AppCompatActivity {

    static String date;
    CalendarView calendarView;
    Button button;
    private FirebaseFirestore db;
    static ArrayList<String> exerciseNameArray = new ArrayList<>();
    static ArrayList<String> athleteNameArray = new ArrayList<>();
    static ArrayList<String> noteArray = new ArrayList<>();
    static ArrayList<String> athleteSpinnerArray = new ArrayList<>();
    public String selectedAthlete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_calendar);
        button = findViewById(R.id.button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner completedSpinner = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[] {"Completed Exercises", "Not Completed Exercises"};
        ArrayAdapter<String> completedAdaptor = new ArrayAdapter<String>(CoachCalendar.this, android.R.layout.simple_spinner_dropdown_item, items);
        completedAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        completedSpinner.setAdapter(completedAdaptor);

        Spinner athleteSpinner = (Spinner) findViewById(R.id.spinnerAthlete);
        athleteSpinnerArray.add("Choose an Athlete");
        getNamesFirebase();
        ArrayAdapter<String> atheleteAdaptor = new ArrayAdapter<String>(CoachCalendar.this, android.R.layout.simple_spinner_dropdown_item, athleteSpinnerArray);
        atheleteAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        athleteSpinner.setAdapter(atheleteAdaptor);
        athleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAthlete = athleteSpinner.getSelectedItem().toString();
                getExercise();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Intent intent = new Intent (this, PastActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteArray.isEmpty()) showDialog();
               else startActivity(intent);

            }
        });
    }

    private void getNamesFirebase(){
        db = FirebaseFirestore.getInstance();
        db.collection("Assigned Exercise")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                athleteSpinnerArray.add(document.get("Athlete name").toString());
                            }
                        }
                    }
                });
    }

    /**
     * retrieves assignedExercise activity from firebase and saves to to arrayList
     */
    private void getExercise(){
        db = FirebaseFirestore.getInstance();
        db.collection("Assigned Exercise")
                .whereEqualTo("Athlete name", selectedAthlete)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                exerciseNameArray.add(document.get("Exercise name").toString());
                                athleteNameArray.add(document.get("Athlete name").toString());
                                noteArray.add(document.get("Coach notes").toString());
                            }
                        }
                    }
                });
    }

    /**
     * Shows dialog box if day selected has no activity assigned
     */
    public void showDialog() {
        AlertDialog.Builder window = new AlertDialog.Builder(CoachCalendar.this);
        window.setTitle("")
                .setMessage("There are NO activities assigned on this day")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alert = window.create();
        alert.show();
    }
}
