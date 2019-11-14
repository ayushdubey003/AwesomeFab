package com.ayush.awesomefabexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ayush.awesomefab.AwesomeFab;

public class MainActivity extends AppCompatActivity {

    private AwesomeFab mAwesomeFab;
    private AwesomeFab mAwesomeFabMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAwesomeFab = findViewById(R.id.fab);
        mAwesomeFabMini = findViewById(R.id.fab_mini);
        mAwesomeFab.inflateMenu("Option 1", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 2", getDrawable(R.drawable.ic_location_on_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 3", getDrawable(R.drawable.ic_remove_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 4", getDrawable(R.drawable.ic_remove_black_24dp), mAwesomeFab.getId());

        mAwesomeFabMini.inflateMenu("Mini", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFabMini.getId());
        mAwesomeFabMini.inflateMenu("Mini", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFabMini.getId());
        mAwesomeFabMini.inflateMenu("Mini", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFabMini.getId());
        mAwesomeFabMini.inflateMenu("Mini", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFabMini.getId());
        mAwesomeFabMini.inflateMenu("Mini", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFabMini.getId());

    }
}
