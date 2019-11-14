package com.ayush.awesomefabexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ayush.awesomefab.AwesomeFab;

public class MainActivity extends AppCompatActivity {

    private AwesomeFab mAwesomeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAwesomeFab = findViewById(R.id.fab);
        Log.e("X", "" + mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Hi", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Hey", getDrawable(R.drawable.ic_location_on_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Bro", getDrawable(R.drawable.ic_remove_black_24dp), mAwesomeFab.getId());
    }
}
