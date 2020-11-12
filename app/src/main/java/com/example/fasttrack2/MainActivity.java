package com.example.fasttrack2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loginmenu(View view) {
        Intent intent = new Intent(this, LoginWin.class);
        startActivity(intent);
    }

    public void regmenu(View view) {
        Intent intent = new Intent(this, registerWin.class);
        startActivity(intent);
    }


}