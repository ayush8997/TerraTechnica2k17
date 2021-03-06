package delhi.android.nit.com.terratechnica.InstaGram;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import delhi.android.nit.com.terratechnica.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LargePicture extends Fragment {

    String link ;

    public LargePicture() {

    }

    public LargePicture(String link) {
        this.link = link;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_large_picture, container, false);
    }

    ImageView imageView;
    Button button;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.largeIV);
        Picasso.with(getContext())
                .load(link)
                .into(imageView);
    }
}
