package com.example.farm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Use extends AppCompatActivity {
    Spinner name;
    EditText quantity;
    Button button;


    String s_name,s_type,id;
    Double i_quantity,old,new_q;
    private ArrayList<String> name_list=new ArrayList<>();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use);
        button=findViewById(R.id.use);
        name=findViewById(R.id.nam);
        quantity=findViewById(R.id.qua);

        Intent intent=getIntent();
        s_type=intent.getStringExtra("Category");
        Log.d("aaaa",s_type);


        name_list.clear();
        db.collection("Medicines")
                .whereEqualTo("Category", s_type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name_list.add(document.getString("Name"));
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(Use.this, R.layout.support_simple_spinner_dropdown_item, name_list);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                name.setAdapter(adapter);
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_name=name.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i_quantity=Double.parseDouble(quantity.getText().toString());
                db.collection("Medicines")
                        .whereEqualTo("Category", s_type)
                        .whereEqualTo("Name", s_name)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        old = documentSnapshot.getDouble("Quantity");
                                        id = documentSnapshot.getId();
                                        new_q = old - i_quantity;
                                    }
                                    Toast.makeText(getApplicationContext(),"Balance Updated",Toast.LENGTH_SHORT);
                                    name.setSelection(0);
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




    }
}
