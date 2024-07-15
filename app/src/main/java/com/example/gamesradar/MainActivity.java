package com.example.gamesradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
       // toolbar=findViewById(R.id.topAppBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

             if(item.getItemId()==bottomNavigationView.getSelectedItemId()){
                 return true;
             }
                switch (item.getItemId()){

                    case R.id.news:
                        //toolbar.setTitle("Explore");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, NewsFragment.newInstance()).commit();
                        break;

//                    case R.id.videos:
//                        //toolbar.setTitle("Trailers");
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, VideosFragment.newInstance()).commit();
//                        break;
                    case R.id.games:
                        //toolbar.setTitle("Games");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,GamesFragment.newInstance()).commit();
                        break;
                    case R.id.radar:
                        //toolbar.setTitle("Trailers");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, RadarFragment.newInstance()).commit();
                        break;
//                    case R.id.esports:
//                        //toolbar.setTitle("Esports");
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,EsportsFragment.newInstance()).commit();
//                        break;

                }


                return true;
            }
        });
       bottomNavigationView.setSelectedItemId(R.id.games);
    }
}