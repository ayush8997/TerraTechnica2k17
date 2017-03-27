package delhi.android.nit.com.terratechnica.InstaGram;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import delhi.android.nit.com.terratechnica.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class insta extends Fragment {
    RecyclerView galleryRV;
    List<Images> setofFlowers;
    ProgressBar progressBar;
    Adapter adapter;
    String URL = "https://www.instagram.com/terratechnica/media/";

    public insta() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insta, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryRV = (RecyclerView) view.findViewById(R.id.galleryRV);
        galleryRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        progressBar = (ProgressBar) view.findViewById(R.id.prog);
        if (isOnline()) {
            new Background().execute();
        } else {
            Toast.makeText(getContext(), "Network not available!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    void loadImages() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();

            setofFlowers = new ArrayList<>();
            JSONObject jo1 = new JSONObject(result);
            JSONArray ja1 = jo1.getJSONArray("items");
            JSONObject jo2, jo3, jo4, jo5, jo6;
            JSONObject low = new JSONObject(), thumb = new JSONObject(), standard = new JSONObject();
            for (int i = 0; i < ja1.length(); i++) {
                jo2 = ja1.getJSONObject(i);
                jo3 = jo2.getJSONObject("images");
                jo4 = jo3.getJSONObject("low_resolution");
                jo5 = jo3.getJSONObject("thumbnail");
                jo6 = jo3.getJSONObject("standard_resolution");
                low.put(String.valueOf(i), jo4.getString("url"));
                thumb.put(String.valueOf(i), jo5.getString("url"));
                standard.put(String.valueOf(i), jo6.getString("url"));
                setofFlowers.add(new Images(jo4.getString("url"), jo6.getString("url")));
            }

        } catch (Exception e) {

        }
    }

    private class Background extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            loadImages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (setofFlowers != null) {
                adapter = new Adapter();
                galleryRV.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "setofFlower is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_instagram, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Images image1 = setofFlowers.get(position);

            try {
                Picasso.with(getContext())
                        .load(image1.getThumnailLink())
                        .into(holder.image1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            ViewCompat.setTransitionName(holder.image1, String.valueOf(position) + "_albumart");
        }

        @Override
        public int getItemCount() {
            if (setofFlowers == null) {
                return 0;
            } else {
                return (setofFlowers.size());
            }
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        ImageView image1;

        public Holder(final View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.image1);
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LargePicture largePicture = new LargePicture(setofFlowers.get(galleryRV.getChildAdapterPosition(itemView)).getFullImageLink());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                largePicture.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.transition));
                                largePicture.setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.transition));

                            }
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, largePicture, "Hello")
                                    .addSharedElement(image1, "largePic")
                                    .addToBackStack("")
                                    .commit();

                        }
                    }
            );

        }
    }
}

