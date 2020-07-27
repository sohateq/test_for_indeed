package com.akameko.testforindeed.view.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akameko.testforindeed.R
import com.akameko.testforindeed.repository.pojos.Jeans

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        initFragment()
        return root
    }

    private fun initFragment() {
        mainViewModel.loadJeans()
        mainViewModel.jeansList.observe(viewLifecycleOwner, Observer {
            jeansList: List<Jeans?> -> initRecycler(jeansList)
        })
    }

    private fun initRecycler(jeansList: List<Jeans?>) {
        val recyclerView: RecyclerView = activity!!.findViewById(R.id.main_recycler_view)
        recyclerView.apply{
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager
        }

        val liked = mainViewModel.jeansDatabase.jeansDao.allItems
        val mainAdapter = MainAdapter(jeansList, liked)
        mainAdapter.setOnLikeClickListener { likedJeans: Jeans?, _: Int, addToFavourite: Boolean ->
            if (addToFavourite) {
                mainViewModel.addToFavourite(likedJeans)
                Log.d("123", "Добавлено в избранное")
                showLikeNotification()
            } else {
                mainViewModel.removeFromFavourite(likedJeans)
                Log.d("123", "Убрано из избранного")
            }
        }

        recyclerView.adapter = mainAdapter

        val textViewCounter = activity!!.findViewById<TextView>(R.id.text_view_main_count)
        textViewCounter.text = String.format(getString(R.string.text_counter), jeansList.size)
    }

    private fun showLikeNotification() {
        val cardViewNotification: CardView = activity!!.findViewById(R.id.card_view_notification_main)
        cardViewNotification.visibility = View.VISIBLE
        val h = Handler()
        h.postDelayed({ cardViewNotification.visibility = View.GONE }, 3000)
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}