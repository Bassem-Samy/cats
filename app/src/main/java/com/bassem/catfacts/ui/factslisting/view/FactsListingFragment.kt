package com.bassem.catfacts.ui.factslisting.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

import com.bassem.catfacts.R
import com.bassem.catfacts.application.CatFactsApplication
import com.bassem.catfacts.ui.factslisting.di.FactsListingModule
import com.bassem.catfacts.ui.factslisting.models.CatFact
import com.bassem.catfacts.ui.factslisting.presenter.FactsListingPresenter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FactsListingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FactsListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FactsListingFragment : Fragment(), FactsListingView {
    @Inject
    lateinit var presenter: FactsListingPresenter

    override fun showLoading() {
    }

    override fun showNoFacts() {
    }

    override fun hideLoading() {
    }

    override fun showError() {
    }

    override fun showNoInternetConnection() {
    }

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_facts_listing, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CatFactsApplication.get(activity.application)
                .applicationComponent
                .plus(FactsListingModule(this, context))
                .inject(this)
        presenter.updateFactsLengthValues(100, 20)
        presenter.loadMoreItems()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun onFactClicked(catFact: CatFact)
    }

    companion object {

        val TAG: String = "facts_listing_fragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         ** @return A new instance of fragment FactsListingFragment.
         */
        fun newInstance(): FactsListingFragment = FactsListingFragment()


    }
}// Required empty public constructor
