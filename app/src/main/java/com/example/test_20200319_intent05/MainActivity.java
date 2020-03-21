package com.example.test_20200319_intent05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextShareData, editTextMyIntent;
    private Button buttonSend, buttonMyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextShareData = (EditText) findViewById(R.id.editText_sharedata);

        buttonSend = (Button) findViewById(R.id.button_sendto);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sharedata = editTextShareData.getText().toString();
                String action = Intent.ACTION_SEND;
                Intent intent = new Intent(action);
                intent.putExtra(Intent.EXTRA_TEXT, sharedata);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


        editTextMyIntent = (EditText) findViewById(R.id.editText_mydata);

        buttonMyIntent = (Button) findViewById(R.id.button_myintent);

        buttonMyIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myData = editTextMyIntent.getText().toString();
                Intent intent = new Intent();
                intent.setAction("MyIntent");
                intent.putExtra("data", myData);
                intent.putExtra("number", 1);

                startActivity(intent);
            }
        });






    }// onCreate()

}// class MainActivity
