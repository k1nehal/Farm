package com.example.farm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add extends AppCompatActivity {
Spinner category, type;
EditText name,quantity;
Button button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
String s_name ="",s_type="",s_category="";
//String i_quantity="";
    int i_quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        category=findViewById(R.id.cat);
        type=findViewById(R.id.typ);
        name=findViewById(R.id.nam);
        quantity=findViewById(R.id.qua);
        button=findViewById(R.id.add1);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cat_array, R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.typ_array, R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter1);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_category=category.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_type=type.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name=name.getText().toString();
//        s_type=type.getSelectedItem().toString();
                s_category=category.getSelectedItem().toString();
//        String q=quantity.getText().toString();
                i_quantity= Integer.parseInt(quantity.getText().toString());
                Log.d("aaaaa:::",""+s_name+s_category+s_type+i_quantity);

                Map<String , Object> medicine = new HashMap<>();
                medicine.put("Category",s_category);
                medicine.put("Name",s_name);
                medicine.put("Type",s_type);
                medicine.put("Quantity",i_quantity);

                db.collection("Medicines")
                        .add(medicine)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                /*Spinner category, type;
                EditText name,quantity;
                Button button;*/

                category.setSelection(0);
                type.setSelection(0);
                name.setText("");
                quantity.setText("");
            }
        });









    }
}
