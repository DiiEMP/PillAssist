package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;



public class ForgotPassword extends AppCompatActivity {

    String valorRecibido = "";
    FrameLayout frame1, frame2, frame3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("Valor_Frame")) {
                valorRecibido = extras.getString("Valor_Frame");
            }
        } else {
            Log.d("Encontrado", "No encontrado");
        }
        Log.d("TAG", valorRecibido);

        switch (valorRecibido) {
            case "one":
                frame1 = findViewById(R.id.fmOne);
                frame1.setVisibility(View.VISIBLE);
                break;
        }

        frame2 = findViewById(R.id.fmTwo);
        frame3 = findViewById(R.id.fmThree);


    }

    public void sendCode(View view) {
        frame1.setVisibility(View.INVISIBLE);
        frame2.setVisibility(View.VISIBLE);
    }

    public void verify(View view) {
        frame2.setVisibility(View.INVISIBLE);
        frame3.setVisibility(View.VISIBLE);
    }

    public void savePass(View view) {
        frame3.setVisibility(View.INVISIBLE);
        finish();
        //frame2.setVisibility(View.VISIBLE);
    }


}