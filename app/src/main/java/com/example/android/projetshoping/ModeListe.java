package com.example.android.projetshoping;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModeListe extends AppCompatActivity {

    ArrayList<ModelListesCourses> mArrayListCourses = new ArrayList<ModelListesCourses>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_liste);

        populateList();

        Button addToList = findViewById(R.id.button_send);

        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView objetAAcheter = findViewById(R.id.edit_text_projet_achat);
                if ((objetAAcheter.getText().toString()).isEmpty()){

                    Toast.makeText(ModeListe.this, "Entrez un article avant de valider.", Toast.LENGTH_SHORT).show();

                } else {

                    // Cacher le clavier une fois que l'on clic
                    InputMethodManager inputMethodManager = (InputMethodManager) ModeListe.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(ModeListe.this.getCurrentFocus().getWindowToken(), 0);

                    // On recupere ce qu'on veut acheter
                    ModelListesCourses objetListe = new ModelListesCourses(objetAAcheter.getText().toString());

                    //on l'envois sur firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("aAcheter");
                    myRef.push().setValue(objetListe);

                    //On reset le texte et on informe que c'est parti
                    objetAAcheter.setText("");
                    Toast.makeText(ModeListe.this, "Ajouté à la liste", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void populateList() {

        final ListView liste = findViewById(R.id.list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("aAcheter");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArrayListCourses.clear();
                for (DataSnapshot datasnap: dataSnapshot.getChildren() ){
                    ModelListesCourses objetPourListe = datasnap.getValue(ModelListesCourses.class);
                    mArrayListCourses.add(objetPourListe);
                }
                Adapter listeCourses = new Adapter(ModeListe.this, mArrayListCourses);
                liste.setAdapter(listeCourses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ModeListe.this, "Error in database", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
