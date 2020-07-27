package com.akameko.testforindeed.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akameko.testforindeed.R
import com.akameko.testforindeed.repository.pojos.Jeans

class FavouriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        initFragment()
        return inflater.inflate(R.layout.favourite_fragment, container, false)
    }
    private fun initFragment() {
        viewModel.initViewModel()
        viewModel.likedJeansList.observe(viewLifecycleOwner, Observer {
            likedJeansList: List<Jeans> -> initRecycler(likedJeansList)
        })
    }

    private fun initRecycler(jeansList: List<Jeans>) {

        val recyclerView: RecyclerView = activity!!.findViewById(R.id.favourite_recycler_view)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager

            val mainAdapter = MainAdapter(jeansList, jeansList)
            adapter = mainAdapter
        }

        val textViewCounter = activity!!.findViewById<TextView>(R.id.text_view_main_count_favourite)
        textViewCounter.text = String.format(getString(R.string.text_counter), jeansList.size)
    }


}
