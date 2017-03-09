package com.newbies.terrateam.terratechnica2k17;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.newbies.terrateam.terratechnica2k17.EventCateg.Events;
import com.newbies.terrateam.terratechnica2k17.Maps.Maps;
import com.newbies.terrateam.terratechnica2k17.Organizers.Contacts;
import com.newbies.terrateam.terratechnica2k17.Sponsors.Sponsors;

/**
 * Created by Directioner on 1/28/2017.
 */

public class CategoryItemDisplay extends AppCompatActivity {

    public static final String CATEGORY_NAME = "Category";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "Raleway-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        Intent intent = getIntent();

        String frag_name = intent.getStringExtra(CATEGORY_NAME);

//        Toast.makeText(this, frag_name, Toast.LENGTH_SHORT).show();

        Fragment fragment = null;
        android.support.v4.app.Fragment frag = null;
        boolean l = false;

        switch (frag_name) {

            case "Events":
                intent = new Intent(CategoryItemDisplay.this, Events.class);
                startActivity(intent);
                finish();
                return;
            case "InstaFeed":
                frag = new insta();

                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content,frag)
                        .commit();
                break;
            case "Sponsors":
                l = true;
                fragment = new Sponsors();
                break;
            case "Maps":
                intent = new Intent(CategoryItemDisplay.this, MapsActivity.class);
                startActivity(intent);
                finish();
                return;
            case "Contact":
                l = true;
                fragment = new Contacts();
                //findViewById(R.id.iv_main).setBackgroundDrawable(getResources().getDrawable(R.drawable.trial));
                break;
//            case "Gallery":
//                l = true;
//                fragment = new Gallery();
//                break;
        }
//        if(l) {

            getSupportActionBar().setTitle(frag_name);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        if(l) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .commit();
        }
    }
}