package delhi.android.nit.com.terratechnica.About_Us;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import delhi.android.nit.com.terratechnica.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class About_Us extends Fragment implements View.OnClickListener {

    ImageView image_logo;
    TextView about_text;
    ImageView abFB,abIN,abYO;
    public About_Us() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about__us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/JosefinSans-Regular.ttf");
        about_text = (TextView) view.findViewById(R.id.about_text);
        about_text.setTypeface(custom_font);
        image_logo = (ImageView) view.findViewById(R.id.image_logo);
        abFB = (ImageView) view.findViewById(R.id.abFB);
        abIN = (ImageView) view.findViewById(R.id.abIN);
        abYO = (ImageView) view.findViewById(R.id.abYO);
        abFB.setOnClickListener(this);
        abIN.setOnClickListener(this);
        abYO.setOnClickListener(this);
        Uri uri = Uri.parse("android.resource://delhi.android.nit.com.terratechnica/drawable/logo");
        Picasso.with(getContext())
                .load(uri)
                .into(image_logo);
    }

    @Override
    public void onClick(View view) {

        String url = "";
        if(view.getId() == R.id.abFB)
            url = "https://www.facebook.com/terratechnica/";
        if(view.getId() == R.id.abIN)
            url = "https://www.instagram.com/terratechnica/";
        if(view.getId() == R.id.abYO)
            url = "https://www.youtube.com/channel/UCwkrKduy5122UrOAPZIpRMQ";


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
