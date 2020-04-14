package com.migueltzabtah.micapilla.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.migueltzabtah.micapilla.Fragments.Frg_Content;
import com.migueltzabtah.micapilla.Fragments.Frg_Iglesias;
import com.migueltzabtah.micapilla.Fragments.Frg_MisEventos;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Frg_Iglesias.getInstancia();
            case 1:
                Frg_MisEventos tmp2 = new Frg_MisEventos();
                return tmp2;
            case 2:
                Frg_Content tmp3 = new Frg_Content();
                return tmp3;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}