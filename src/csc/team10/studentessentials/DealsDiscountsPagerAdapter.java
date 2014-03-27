package csc.team10.studentessentials;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DealsDiscountsPagerAdapter extends FragmentPagerAdapter{
	
    public DealsDiscountsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Deals Fragment
            return new DealsFragment();
            
        case 1:
            // Discounts Fragment
            return new DiscountsFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
}
