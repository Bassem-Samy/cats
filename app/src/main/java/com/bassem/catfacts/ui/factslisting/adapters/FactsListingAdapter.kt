package com.bassem.catfacts.ui.factslisting.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bassem.catfacts.R
import com.bassem.catfacts.ui.factslisting.models.CatFact

/**
 * Created by mab on 11/13/17.
 */

class FactsListingAdapter(factItemOnClickListener: FactItemOnClickListener) : RecyclerView.Adapter<FactsListingAdapter.ViewHolder>() {
    private val onItemClickListener: FactItemOnClickListener = factItemOnClickListener


    var dataSet: ArrayList<CatFact>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_fact_item, parent, false);
        return ViewHolder(view);
    }

    public fun setDataset(items: List<CatFact>?) {
        this.dataSet = ArrayList(items)
    }

    public fun addItems(items: List<CatFact>) {
        if (dataSet != null) {
            val index = dataSet?.size ?: 0
            dataSet?.addAll(items)
            notifyItemRangeChanged(index, items.size - 1)
        } else {
            dataSet = ArrayList(items)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.factTextView.text = dataSet?.get(position)?.fact
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {init {
        ButterKnife.bind(this, itemView)
    }

        @BindView(R.id.txt_fact)
        lateinit var factTextView: TextView

        @OnClick(R.id.frm_share)
        fun onShareClicked(v: View) {
            Log.e("position", Integer.toString(adapterPosition))
            val item: CatFact? = dataSet?.get(adapterPosition)
            if (item != null && onItemClickListener != null) {
                onItemClickListener.onShareClicked(item)
            }

        }
    }

    interface FactItemOnClickListener {
        fun onItemClicked(item: CatFact)
        fun onShareClicked(item: CatFact)
    }

    fun clearDataset() {
        dataSet?.clear()
        notifyDataSetChanged()
    }
}

