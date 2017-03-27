package delhi.android.nit.com.terratechnica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Events extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public Events() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager.setAdapter(new MyCustomTabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyCustomTabAdapter extends FragmentPagerAdapter {

        public MyCustomTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = Event_Main.newInstance(1);
            }
            if (position == 1) {
                fragment = Robotics_Fragment.newInstance(1);
            }
            if (position == 2) {
                fragment = Online_Fragment.newInstance(1);
            }
            if (position == 3) {
                fragment = Misc_Fragment.newInstance(1);
            }
            if (position == 4) {
                fragment = CyberSecurity_Fragment.newInstance(1);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = new String();
            if (position == 0) {
                title = "Coding";
            }
            if (position == 1) {
                title = "Robotics";
            }
            if (position == 2) {
                title = "Online";
            }
            if (position == 3) {
                title = "Misc";
            }
            if (position == 4) {
                title = "Cyber Security";
            }
            return title;
        }
    }
}
