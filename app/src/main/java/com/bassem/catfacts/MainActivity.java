package com.bassem.catfacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.bassem.catfacts.ui.factdetails.FactDetailsFragment;
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
        FactDetailsFragment fragment=FactDetailsFragment.newInstance(catFact);
        fragment.show(getSupportFragmentManager(),FactDetailsFragment.TAG);


    }
    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
