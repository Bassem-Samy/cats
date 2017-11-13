package com.bassem.catfacts.ui.factslisting.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

import com.bassem.catfacts.R
import com.bassem.catfacts.application.CatFactsApplication
import com.bassem.catfacts.ui.controls.LoadMoreRecyclerView
import com.bassem.catfacts.ui.factslisting.adapters.FactsListingAdapter
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


    val pageSize: Int = 20
    val factsContentMax: Int = 1000
    @BindView(R.id.sb_facts_length)
    lateinit var factsLengthSeekBar: SeekBar
    @BindView(R.id.txt_selected_length)
    lateinit var selectedContentLengthTextView: TextView
    @BindView(R.id.rclr_facts)
    lateinit var factsRecyclerView: LoadMoreRecyclerView
    var adapter: FactsListingAdapter? = null
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

    override fun updateFacts(items: List<CatFact>) {
        if (adapter == null) {
            adapter = FactsListingAdapter(onFactItemClickListener)
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            factsRecyclerView.layoutManager = linearLayoutManager
            factsRecyclerView.adapter = adapter
            adapter?.setDataset(items)
        } else {
            adapter?.addItems(items)
        }

    }

    private val onFactItemClickListener = object : FactsListingAdapter.FactItemOnClickListener {
        override fun onShareClicked(item: CatFact) {
        }

        override fun onItemClicked(item: CatFact) {
        }
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
        factsLengthSeekBar.max = factsContentMax
        factsLengthSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        presenter.updateFactsLengthValues(100, pageSize)
        presenter.loadMoreItems()
    }

    var onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            selectedContentLengthTextView.text = Integer.toString(i)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
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
