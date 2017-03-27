package delhi.android.nit.com.terratechnica;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Event_Description extends AppCompatActivity {

    ImageView image;
    TextView despTitle, despTitleHint, despDesp, despContact;
    int position;
    String type;
    String eventName;
    //String IMAGE_LINK = "http://saptrangnitd.com/beta1/ap/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transition);
            getWindow().setSharedElementEnterTransition(transition);

            Slide fade1 = new Slide(Gravity.LEFT);
            fade1.excludeTarget(android.R.id.navigationBarBackground, true);
            fade1.excludeTarget(android.R.id.statusBarBackground, true);
            fade1.setStartDelay(300);
            fade1.setDuration(300);
            getWindow().setEnterTransition(fade1);

            Fade fade = new Fade();
            fade.setDuration(100);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setReturnTransition(fade);

        }

        setContentView(R.layout.activity_event__description);

        position = getIntent().getIntExtra("position", 0);
        type = getIntent().getStringExtra("type");
        eventName = getIntent().getStringExtra("eventName");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/JosefinSans-Regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/JosefinSans-Bold.ttf");
        Typeface semibold = Typeface.createFromAsset(getAssets(), "fonts/JosefinSans-SemiBold.ttf");
        image = (ImageView) findViewById(R.id.image);
        despTitle = (TextView) findViewById(R.id.despTitle);
        despTitleHint = (TextView) findViewById(R.id.despTitleHint);
        despDesp = (TextView) findViewById(R.id.despDesp);
        despContact = (TextView) findViewById(R.id.despContact);
        despTitle.setTypeface(bold);
        despTitleHint.setTypeface(semibold);
        despDesp.setTypeface(custom_font);
        despContact.setTypeface(custom_font);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setTypeface(semibold);

        if (type.equals("coding")) {
            despTitle.setText(Data.codingEvents[position]);
            despTitleHint.setText("(" + "Coding Event" + ")");
            despDesp.setText(Data.codingDesp[position]);
            despContact.setText(Data.codingContact[position]);
            Picasso.with(this)
                    .load(Data.codingPics[position])
                    .error(R.drawable.asd)
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        } else if (type.equals("robotics")) {
            despTitle.setText(Data.roboEvent[position]);
            despTitleHint.setText("(" + "Robo Event" + ")");
            despDesp.setText(Data.roboDesp[position]);
            despContact.setText(Data.roboContact[position]);
            Picasso.with(this)
                    .load(Data.RoboPics[position])
                    .error(R.drawable.asd)
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        } else if (type.equals("online")) {
            despTitle.setText(Data.onlineEvent[position]);
            despTitleHint.setText("(" + "Online Event" + ")");
            despDesp.setText(Data.onlineDesp[position]);
            despContact.setText(Data.onlineContact[position]);

            Picasso.with(this)
                    .load(Data.onlinePics[position])
                    .error(R.drawable.asd)
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        } else if (type.equals("misc")) {
            despTitle.setText(Data.MiscEvent[position]);
            despTitleHint.setText("(" + "Misc Event" + ")");
            despDesp.setText(Data.MiscDesp[position]);
            despContact.setText(Data.miscContact[position]);

            Picasso.with(this)
                    .load(Data.MiscPics[position])
                    .error(R.drawable.asd)
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        } else if(type.equals("cyber_security")) {
            despTitle.setText(Data.cyberEvent[position]);
            despTitleHint.setText("(" + "Cyber Event" + ")");
            despDesp.setText(Data.cyberDesc[position]);
            despContact.setText(Data.cyberContact[position]);

            Picasso.with(this)
                    .load(Data.cyberPics[position])
                    .error(R.drawable.asd)
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://thecollegefever.com/events/terratechnica17";
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(Event_Description.this, Uri.parse(url));
                    }
                }
        );
    }

    public void back(View v)
    {

        onBackPressed();
    }

}