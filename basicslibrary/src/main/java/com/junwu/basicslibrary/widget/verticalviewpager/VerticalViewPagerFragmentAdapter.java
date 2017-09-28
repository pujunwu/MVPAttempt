package junwu.com.basicslibrary.widget.verticalviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VerticalViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public VerticalViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public static class Holder {
        private final List<Fragment> fragments = new ArrayList<>();
        private FragmentManager manager;
        public Holder(FragmentManager manager) {
            this.manager = manager;
        }

        public Holder add(Fragment f) {
            fragments.add(f);
            return this;
        }

        public VerticalViewPagerFragmentAdapter set() {
            return new VerticalViewPagerFragmentAdapter(manager, fragments);
        }
    }
}
