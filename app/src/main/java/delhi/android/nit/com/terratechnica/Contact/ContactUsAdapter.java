package delhi.android.nit.com.terratechnica.Contact;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import delhi.android.nit.com.terratechnica.R;

/**
 * Created by Directioner on 3/16/2017.
 */

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.MyViewHolder> {

    private static String URL = "http://insigniathefest.com/manojit/Shivam/";
    private List<ContactsData> contactList;
    private ViewGroup parent;

    public ContactUsAdapter(List<ContactsData> contactList) {
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.parent = parent;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
            return new MyViewHolder(view);
        } else {
            this.parent = parent;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_itme2, parent, false);
            return new MyViewHolder(view);
        }
    }

    public boolean isOnline() {

        ConnectivityManager manager = (ConnectivityManager) parent.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactsData contactUs = contactList.get(position);
        holder.contactName.setText(contactUs.getPersonName());
        holder.supportLevel.setText(contactUs.getPersonSupportLevel());
        holder.contactName1.setText(contactUs.getPersonName());
        holder.supportLevel1.setText(contactUs.getPersonSupportLevel());
        if (position % 2 == 0) {
            holder.down.setVisibility(View.GONE);
            holder.up.setVisibility(View.VISIBLE);

            if (isOnline()) {

                Picasso.with(parent.getContext())
                        .load(URL + contactUs.getPersonImage() + ".jpg")
                        .into(holder.person);
            }
        } else {
            holder.up.setVisibility(View.GONE);
            holder.down.setVisibility(View.VISIBLE);

            if (isOnline()) {
                Picasso.with(parent.getContext())
                        .load(URL + contactUs.getPersonImage() + ".jpg")
                        .into(holder.person1);
            }
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout up, down;
        private TextView contactName, supportLevel, contactName1, supportLevel1;
        private ImageView person, person1;

        public MyViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            supportLevel = (TextView) itemView.findViewById(R.id.support_level);
            contactName1 = (TextView) itemView.findViewById(R.id.contact_name1);
            supportLevel1 = (TextView) itemView.findViewById(R.id.support_level1);
            up = (RelativeLayout) itemView.findViewById(R.id.up);
            down = (RelativeLayout) itemView.findViewById(R.id.down);
            person = (ImageView) itemView.findViewById(R.id.person_image);
            person1 = (ImageView) itemView.findViewById(R.id.person_image1);
        }
    }
}
