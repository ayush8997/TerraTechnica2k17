package com.newbies.terrateam.terratechnica2k17.EventCateg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.newbies.terrateam.terratechnica2k17.FontChangeCrawler;
import com.newbies.terrateam.terratechnica2k17.GlobalData;
import com.newbies.terrateam.terratechnica2k17.R;

/**
 * Created by Directioner on 2/12/2017.
 */

public class Botmania extends ListFragment {

    private Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setListAdapter(new EventsAdapter(getActivity(), R.layout.event_list_cards, EventsDataManager.dataFetch("bot")));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "Raleway-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.getView());

//        Toast.makeText(getActivity(), "O! Hello!", Toast.LENGTH_SHORT).show();

//        Toast.makeText(getActivity(), ((TextView) v.findViewById(R.id.event_name)).getText().toString(), Toast.LENGTH_SHORT).show();

        bundle.putString(GlobalData.EVENT_CATEG, "bot");
        bundle.putString(GlobalData.EVENT_NAME, ((TextView) v.findViewById(R.id.event_name)).getText().toString());

        EventDescPage descPage = new EventDescPage();
        descPage.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, descPage)
                .addToBackStack(null)
                .commit();

    }
}