package com.example.farm;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Purchase extends Activity {
    Spinner category, name;
    EditText quantity;
    Button button;
    String id;

    String s_category, s_name;
    Double i_quantity,new_q,old;
    private ArrayList<String> name_list=new ArrayList<>();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        category = findViewById(R.id.cat);
        name = findViewById(R.id.nam);
        quantity = findViewById(R.id.qua);
        button = findViewById(R.id.add1);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cat_array, R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_category = category.getSelectedItem().toString();
                name_list.clear();
                db.collection("Medicines")
                        .whereEqualTo("Category", s_category)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        name_list.add(document.getString("Name"));
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Purchase.this, R.layout.support_simple_spinner_dropdown_item, name_list);
                                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        name.setAdapter(adapter);
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_name = name.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), s_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i_quantity = Double.parseDouble(quantity.getText().toString());
                Log.d("Purchase", s_category + s_name + i_quantity);
                db.collection("Medicines")
                        .whereEqualTo("Category", s_category)
                        .whereEqualTo("Name", s_name)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        old = documentSnapshot.getDouble("Quantity");
                                        id = documentSnapshot.getId();
                                        new_q = old + i_quantity;
                                        Log.d("Old Quantity", old+ "");
                                        Log.d("new Quantity", i_quantity+ "");
                                        Log.d("Idddddd", id);
                                        Log.d("New_Quantityttttt",new_q+"");

                                    }
                                    /*Spinner category, name;
                                    EditText quantity;*/
                                    Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                                    category.setSelection(0);
                                    category.setSelection(0);
                                    quantity.setText("");
                                    updateData();


                                }
                            }
                        });





            }

            private void updateData() {
                DocumentReference washingtonRef = db.collection("Medicines").document(id);
                washingtonRef
                        .update("Quantity", new_q)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });

    }}