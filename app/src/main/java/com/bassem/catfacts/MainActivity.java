package com.bassem.catfacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bassem.catfacts.ui.factslisting.models.CatFact;
import com.bassem.catfacts.ui.factslisting.view.FactsListingFragment;

public class MainActivity extends AppCompatActivity implements FactsListingFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFactsListingFragment();
    }

    private void initFactsListingFragment() {

        if (getSupportFragmentManager().findFragmentByTag(FactsListingFragment.Companion.getTAG()) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frm_container,
                            FactsListingFragment.Companion.newInstance(),
                            FactsListingFragment.Companion.getTAG())
                    .commit();
        }
    }

    @Override
    public void onFactClicked(CatFact catFact) {

    }
}
