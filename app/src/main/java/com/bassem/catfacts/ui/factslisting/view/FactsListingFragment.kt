package com.bassem.catfacts.ui.factslisting.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import com.bassem.catfacts.R
import com.bassem.catfacts.application.CatFactsApplication
import com.bassem.catfacts.ui.controls.LoadMoreRecyclerView
import com.bassem.catfacts.ui.factslisting.adapters.FactsListingAdapter
import com.bassem.catfacts.ui.factslisting.di.FactsListingModule
import com.bassem.catfacts.ui.factslisting.models.CatFact
import com.bassem.catfacts.ui.factslisting.presenter.FactsListingPresenter
import com.bassem.catfacts.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
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
    @BindView(R.id.rltv_loading)
    lateinit var loadingRelativeLayout: RelativeLayout
    @BindView(R.id.prgrs_loading_more)
    lateinit var loadMoreProgressBar: ProgressBar
    @BindView(R.id.rltv_no_data)
    lateinit var noDataRelativeLayout: RelativeLayout
    @BindView(R.id.rltv_no_internet_connection)
    lateinit var noInternetRelativeLayout: RelativeLayout

    @BindView(R.id.sb_facts_length)
    lateinit var factsLengthSeekBar: SeekBar

    @BindView(R.id.txt_selected_length)
    lateinit var selectedContentLengthTextView: TextView
    @BindView(R.id.rclr_facts)
    lateinit var factsRecyclerView: LoadMoreRecyclerView

    var adapter: FactsListingAdapter? = null
    @Inject
    lateinit var presenter: FactsListingPresenter
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_facts_listing, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // inject dependencies
        CatFactsApplication.get(activity.application)
                .applicationComponent
                .plus(FactsListingModule(this, context))
                .inject(this)
        initializeFactsContentSeekBar()
    }

    /**
     * setup the seek bar and create the observable to listen to value changes
     */
    private fun initializeFactsContentSeekBar() {
        factsLengthSeekBar.max = FACTS_MAX_CONTENT
        presenter.loadMoreItems()
        createSeekBarObservable().debounce(Constants.SEEK_BAR_WAIT_TIME_IN_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t1 ->
                    Log.e("t1", t1.toString())
                    adapter?.clearDataset()
                    presenter?.updateFactsLengthValues(t1, PAGE_SIZE)
                    presenter?.loadMoreItems()
                })
        factsLengthSeekBar.progress = FACTS_MAX_CONTENT / 2

    }

    override fun showLoading() {
        loadingRelativeLayout.visibility = View.VISIBLE
        noDataRelativeLayout.visibility = View.GONE
        noInternetRelativeLayout.visibility = View.GONE
    }

    override fun showNoFacts() {
        noDataRelativeLayout.visibility = View.VISIBLE
    }

    override fun hideLoading() {

        loadingRelativeLayout.visibility = View.GONE
        loadMoreProgressBar.visibility = View.GONE


    }

    override fun showLoadingMore() {
        loadMoreProgressBar.visibility = View.VISIBLE
    }

    override fun showError() {
        makeToast(R.string.general_error)
    }

    override fun showNoInternetConnection() {
        noInternetRelativeLayout.visibility = View.VISIBLE
    }

    override fun showCantLoadMoreNoInternet() {

        makeToast(R.string.cant_load_more_no_internet)
    }

    private fun makeToast(@StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * Update UI with items to display
     */
    override fun updateFacts(items: List<CatFact>) {
        if (adapter == null) {
            adapter = FactsListingAdapter(onFactItemClickListener)
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            factsRecyclerView.layoutManager = linearLayoutManager
            factsRecyclerView.adapter = adapter
            adapter?.setDataset(items)
            factsRecyclerView.setOnScrolledToEndListener { presenter.loadMoreItems() }
        } else {
            adapter?.addItems(items)
        }

    }

    /**
     * Click listener for the listing of facts
     */
    private val onFactItemClickListener = object : FactsListingAdapter.FactItemOnClickListener {
        override fun onShareClicked(item: CatFact) {
            mListener?.onShareFactClicked(item)
        }

        override fun onItemClicked(item: CatFact) {
            mListener?.onFactClicked(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    /**
     * create an observable for the seek bar setOnSeekBarChangeListener
     * The point is not call the api hundreds of times whenever the value is changed
     * but to use debounce on that observable to wait for some milliseconds (user stopped changing the seek bar value)
     * and then call the api
     */
    private fun createSeekBarObservable(): Observable<Int> {
        return io.reactivex.Observable.create { emmitter ->
            factsLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    selectedContentLengthTextView.text = p1.toString()
                    emmitter.onNext(p1)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })
            emmitter.setCancellable { factsLengthSeekBar.setOnSeekBarChangeListener(null) }
        }
    }

    @OnClick(R.id.img_no_internet, R.id.tv_no_internet)
    fun tryAgain() {
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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()

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
        fun onShareFactClicked(catFact: CatFact)
    }

    companion object {

        val TAG: String = "facts_listing_fragment"

        val PAGE_SIZE: Int = 20
        val FACTS_MAX_CONTENT: Int = 1000
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         ** @return A new instance of fragment FactsListingFragment.
         */
        fun newInstance(): FactsListingFragment = FactsListingFragment()


    }
}// Required empty public constructor
