package com.example.calculachurras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private float initialY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);

        linearLayout.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialY = event.getY();
                    return true;

                case MotionEvent.ACTION_UP:
                    float finalY = event.getY();
                    if (initialY > finalY) {
                        // O usuário arrastou para cim
                        Intent intent = new Intent(MainActivity.this, CadastroDePessoas.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
//                            A atividade será encerrada, então, quando tentar voltar, o app fecha já q ela não existe mais.
//                            finish();
                    }
                    return true;
            }
            return false;
        });

    }
}