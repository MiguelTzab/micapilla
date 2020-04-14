package com.migueltzabtah.micapilla.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.migueltzabtah.micapilla.Fragments.Frg_Eventos;
import com.migueltzabtah.micapilla.Fragments.Frg_Itinerarios;

/**
 * Created by migueltzabtah on 15/08/18.
 */

public class ViewPagerAdapterIglesias extends FragmentPagerAdapter {

    private String IGLESIA_ID;

    public ViewPagerAdapterIglesias(FragmentManager fm, String Iglesia_ID) {
        super(fm);
        this.IGLESIA_ID = Iglesia_ID;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Frg_Itinerarios tmp1 = new Frg_Itinerarios();
                tmp1.setIGLESIA_ID(this.IGLESIA_ID);
                return tmp1;
            case 1:
                Frg_Eventos tmp2 = new Frg_Eventos();
                tmp2.setIGLESIA_ID(this.IGLESIA_ID);
                return tmp2;
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return 2;
    }
}
