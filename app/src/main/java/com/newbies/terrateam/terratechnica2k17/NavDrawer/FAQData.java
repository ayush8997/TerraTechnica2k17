package com.newbies.terrateam.terratechnica2k17.NavDrawer;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newbies.terrateam.terratechnica2k17.FontChangeCrawler;
import com.newbies.terrateam.terratechnica2k17.GlobalData;
import com.newbies.terrateam.terratechnica2k17.R;


/**
 * Created by Directioner on 2/8/2017.
 */

public class FAQData extends ListFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GlobalData.setFAQData();

        setListAdapter(new FAQAdapter(getActivity(), R.layout.faq_cardview, GlobalData.FAQ_DATA));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "Raleway-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.getView());
    }
}