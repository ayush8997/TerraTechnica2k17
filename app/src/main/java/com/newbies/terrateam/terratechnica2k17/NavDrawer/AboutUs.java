package com.newbies.terrateam.terratechnica2k17.NavDrawer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newbies.terrateam.terratechnica2k17.GlobalData;
import com.newbies.terrateam.terratechnica2k17.R;


/**
 * Created by Directioner on 2/5/2017.
 */

public class AboutUs extends Fragment {

    private TextView aboutUs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.about_us, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aboutUs = (TextView) getActivity().findViewById(R.id.about_us);

        getActivity().findViewById(R.id.containerCard)
                .setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.about_us_bg));

        getActivity().findViewById(R.id.youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/channel/UCwkrKduy5122UrOAPZIpRMQ"));

                Intent chooser = intent.createChooser(intent, "Complete Action Using ...");
                startActivity(chooser);
            }
        });

        getActivity().findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/terratechnica/"));

                Intent chooser = intent.createChooser(intent, "Complete Action Using ...");
                startActivity(chooser);
            }
        });

        getActivity().findViewById(R.id.insta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/terratechnica"));

                Intent chooser = intent.createChooser(intent, "Complete Action Using ...");
                startActivity(chooser);
            }
        });

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "BreeSerif-Regular.ttf");
        aboutUs.setTypeface(type);

        aboutUs.setText(GlobalData.ABOUT_US);

    }

}