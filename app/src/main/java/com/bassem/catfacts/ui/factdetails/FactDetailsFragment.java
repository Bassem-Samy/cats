package com.bassem.catfacts.ui.factdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bassem.catfacts.R;
import com.bassem.catfacts.ui.factslisting.models.CatFact;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass that display fact details
 * Use the {@link FactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FactDetailsFragment extends DialogFragment {


    private static final String ARG_FACT = "arg_fact";
    public static final String TAG = "fact_details_fragment";
    private CatFact mCatFact;
    @BindView(R.id.txt_fact)
    TextView factTextView;


    public FactDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fact the cat fact to be displayed.
     * @return A new instance of fragment FactDetailsFragment.
     */

    public static FactDetailsFragment newInstance(CatFact fact) {
        FactDetailsFragment fragment = new FactDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FACT, fact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCatFact = getArguments().getParcelable(ARG_FACT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fact_details, container, false);
        ButterKnife.bind(this, view);
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCatFact != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                factTextView.setText(Html.fromHtml(mCatFact.getFact(), Html.FROM_HTML_MODE_COMPACT).toString());
            } else {
                factTextView.setText(Html.fromHtml(mCatFact.getFact()));
            }
        }

    }
}
