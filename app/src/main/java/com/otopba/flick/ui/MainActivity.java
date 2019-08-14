package com.otopba.flick.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.otopba.flick.R;
import com.otopba.flick.ui.grid.GridFragment;
import com.otopba.flick.ui.image.ImageFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main__container, GridFragment.newInstance(), GridFragment.TAG)
                    .commit();
        }
    }

    public void openImage(@NonNull String image) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__container, ImageFragment.newInstance(image), GridFragment.TAG)
                .addToBackStack(GridFragment.TAG)
                .commit();
    }

}
