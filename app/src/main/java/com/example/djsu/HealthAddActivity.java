package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class HealthAddActivity extends AppCompatActivity {

    Fragment fragment1, fragment2, fragment3, fragment4, fragment5, fragment6, fragment7, fragment8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_add);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();
        fragment6 = new Fragment6();
        fragment7 = new Fragment7();
        fragment8 = new Fragment8();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment1).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){

                    selected = fragment1;

                }else if (position == 1){

                    selected = fragment2;

                }else if (position == 2){

                    selected = fragment3;

                }else if (position == 3){

                    selected = fragment4;
                }
                else if (position == 4){

                    selected = fragment5;
                }
                else if (position == 5){

                    selected = fragment6;
                }
                else if (position == 6){

                    selected = fragment7;
                }
                else if (position == 7){

                    selected = fragment8;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}