package com.example.farm;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;
public class Balance extends Activity {
    EditText editText;
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<Double> quantity=new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        gridView=findViewById(R.id.das);
        db.collection("Medicines")
                .whereGreaterThan("Quantity",-100)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name.add(document.getString("Name"));
                                quantity.add(document.getDouble("Quantity"));
                                type.add(document.getString("Type"));
                            }
                            Balance_Adapter adapter=new Balance_Adapter(getApplicationContext(),name,quantity,type);
                            gridView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }                 }                });

    } }
