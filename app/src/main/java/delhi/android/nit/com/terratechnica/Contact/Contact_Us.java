package delhi.android.nit.com.terratechnica.Contact;

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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import delhi.android.nit.com.terratechnica.R;

public class Contact_Us extends Fragment {

    private RecyclerView recyclerView;
    private ContactUsAdapter contactUsAdapter;
    private List<ContactsData> contactsData = new ArrayList<>();
    ImageView contactBG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        contactUsAdapter = new ContactUsAdapter(contactsData);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactUsAdapter);
        contactBG = (ImageView) view.findViewById(R.id.contactBG);
        Glide.with(getActivity())
                .load(R.drawable.bg2)
                .crossFade()
                .centerCrop()
                .into(contactBG);

    }


    private void setData() {

        contactsData.add(new ContactsData("Mohit Yadav","8802370556","mohit","Convener"));
        contactsData.add(new ContactsData("Jasleen Kaur","7838980689","jasleen","Co-Convener"));
        contactsData.add(new ContactsData("Bhupesh Kumar","9716848553","bhupesh","Co-Convener"));
        contactsData.add(new ContactsData("Shantanu Patil","9158121986","shantanu","Co-Convener"));
        contactsData.add(new ContactsData("Lakshya","8527546574","lakshya","Web Developer"));
        contactsData.add(new ContactsData("Manojit Paul", "9650419350", "manojit_paul", "Android App Developer"));
        contactsData.add(new ContactsData("Shivam Bathla", "8860032051", "shivam", "Android App Developer"));
        contactsData.add(new ContactsData("Ayush Pranav", "7289017595", "ayush", "UI Developer"));
        contactsData.add(new ContactsData("Sai Manohar","8130129479","sai_manohar","UI Developer"));
        contactsData.add(new ContactsData("Arpan Singh", "9058105581", "arpan", "Graphic Designer"));
        contactsData.add(new ContactsData("Vishal Simon","9654474100","vishal","Design & Content"));
        contactsData.add(new ContactsData("Anubhav Nautiyal","8826849926","anubhav","Event Management(head)"));
        contactsData.add(new ContactsData("Anurag Giri","88002220624","anurag","Event Management(sub-head)"));
        contactsData.add(new ContactsData("Pradip Kathrotiya","9426495019","pradip","Infrastructure(head)"));
        contactsData.add(new ContactsData("Rajnish Prajapati","9560297049","rajnish","Hostel Accomodation"));
        contactsData.add(new ContactsData("Nishant Garg","9654962514","nishant","Hostel Accomodation"));
        contactsData.add(new ContactsData("Sunil","9716209524","sunil","Discipline & Security(head)"));
        contactsData.add(new ContactsData("Amit jangid","8233285595","amit","Discipline & Security (sub head)"));
        contactsData.add(new ContactsData("Sanjana Jain","9910696284","sanjana","Hospitality (head)"));
        contactsData.add(new ContactsData("Ajay Meena","9313431684","ajay_meena","Hospitality (sub head)"));
        contactsData.add(new ContactsData("Deepak","8285115778","deepak","Logistics(Head)"));
        contactsData.add(new ContactsData("Naseeb","8287575539","naseeb","Transport and Parking"));
        contactsData.add(new ContactsData("Mayank Sharma","8802293138","mayank","Transport and Parking"));
    }

}
