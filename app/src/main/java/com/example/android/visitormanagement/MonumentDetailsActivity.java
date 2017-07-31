package com.example.android.visitormanagement;

import android.support.v4.app.Fragment;

/**
 * Created by Shrung on 31-Jul-17.
 */

public class MonumentDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MonumentDetailsFragment()
        ;
    }


}
