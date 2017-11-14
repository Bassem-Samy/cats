package com.bassem.catfacts;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bassem.catfacts.ui.factdetails.FactDetailsFragment;
import com.bassem.catfacts.ui.factslisting.models.CatFact;
import com.bassem.catfacts.ui.factslisting.view.FactsListingFragment;

public class MainActivity extends AppCompatActivity implements FactsListingFragment.OnFragmentInteractionListener {

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
        FactDetailsFragment fragment = FactDetailsFragment.newInstance(catFact);
        fragment.show(getSupportFragmentManager(), FactDetailsFragment.TAG);

    }

    @Override
    public void onShareFactClicked(CatFact catFact) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, catFact.getFact());
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_fact)));
        } else {
            showToast(R.string.no_app_to_share_to);
        }

    }

    private void showToast(@StringRes int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
