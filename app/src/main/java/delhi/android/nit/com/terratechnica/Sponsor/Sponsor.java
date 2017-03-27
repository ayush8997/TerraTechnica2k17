package delhi.android.nit.com.terratechnica.Sponsor;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import delhi.android.nit.com.terratechnica.DataDownloader.HttpManager;
import delhi.android.nit.com.terratechnica.R;


/**
 * Created by Manojit Paul on 3/18/2017.
 */

public class Sponsor extends Fragment {
    ImageView contactBG;
    String URL = "http://insigniathefest.com/manojit/Shivam/Sponsors/sponsor.json";
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private List<SponsorModel> sponsorModelList = new ArrayList<>();

    private View view;
    private int spCount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.contact_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        if (isOnline()) {
            new Background().execute();
//            setUpData(view);
        } else {
            Toast.makeText(getContext(), "Network not available!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpData(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SponsorUsAdapter());
        contactBG = (ImageView) view.findViewById(R.id.contactBG);
        Glide.with(getActivity())
                .load(R.drawable.bg2)
                .crossFade()
                .centerCrop()
                .into(contactBG);
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    void loadSponsorData() {

        String sponsorData = HttpManager.getData(URL);

        // Parsing the sponsor data obtained ...

        try {
            JSONObject jsonObj = new JSONObject(sponsorData);

            spCount = jsonObj.getInt("count");

            JSONArray sponsorList = jsonObj.getJSONArray("name");
            JSONArray sponsorImg = jsonObj.getJSONArray("img");
            JSONArray sponsorSupport = jsonObj.getJSONArray("support");

            for (int i = 0; i < sponsorList.length(); i++) {
                sponsorModelList.add(new SponsorModel(sponsorList.get(i).toString(),
                        sponsorImg.get(i).toString(),
                        sponsorSupport.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class SponsorUsAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_sponsor, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (!sponsorModelList.isEmpty()) {
                SponsorModel model = sponsorModelList.get(position);

                holder.sponsor_name.setText(model.getName());
                holder.sponsor_support_level.setText(model.getSupportLevel());
                Picasso.with(getContext())
                        .load(model.getImg())
                        .into(holder.sponsor_image);

                if(pDialog.isShowing())
                    pDialog.dismiss();

            }
        }

        @Override
        public int getItemCount() {
            return spCount;
        }
    }

    private class Background extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            loadSponsorData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            Toast.makeText(getContext(), sponsorModelList.toString(), Toast.LENGTH_SHORT).show();

            setUpData(view);
        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView sponsor_image;
        TextView sponsor_name, sponsor_support_level;

        public ViewHolder(View itemView) {
            super(itemView);
            sponsor_image = (ImageView) itemView.findViewById(R.id.sponsor_image);
            sponsor_name = (TextView) itemView.findViewById(R.id.sponsor_name);
            sponsor_support_level = (TextView) itemView.findViewById(R.id.sponsor_support_level);

        }
    }
}
