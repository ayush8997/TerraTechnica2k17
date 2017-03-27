package delhi.android.nit.com.terratechnica.FAQSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import delhi.android.nit.com.terratechnica.Data;
import delhi.android.nit.com.terratechnica.R;


/**
 * Created by Directioner on 2/8/2017.
 */

public class FAQData extends ListFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Data.setFAQData();

        setListAdapter(new FAQAdapter(getActivity(), R.layout.faq_cardview, Data.FAQ_DATA));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}