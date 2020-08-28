package com.example.farm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Index extends Activity {
    Button organic, fertilizer, insecticides, add,purchase,balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        balance=findViewById(R.id.bal1);
        organic=findViewById(R.id.bio);
        fertilizer=findViewById(R.id.fert);
        insecticides=findViewById(R.id.ins_fun);
        add=findViewById(R.id.add);
        purchase=findViewById(R.id.prod);

        Intent use = new Intent();
        organic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent use = new Intent(Index.this,Use.class);
                use.putExtra("Category","Bio-Organic");
                startActivity(use);
            }
        });

        fertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent use = new Intent(Index.this,Use.class);
                use.putExtra("Category","Feritilizers");
                startActivity(use);
            }
        });

        insecticides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent use = new Intent(Index.this,Use.class);
                use.putExtra("Category","Fungicide and Insecticides");
                startActivity(use);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Index.this, Add.class);
                startActivity(intent);
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Index.this,Purchase.class);
                startActivity(intent);
            }
        });

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Index.this,Balance.class);
                startActivity(intent);
            }
        });

    }
}
