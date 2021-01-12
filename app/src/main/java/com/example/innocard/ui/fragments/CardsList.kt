package com.example.innocard.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innocard.R
import com.example.innocard.data.models.Card
import com.example.innocard.ui.fragments.adapters.CardsListAdapters
import com.example.innocard.ui.viewmodel.CardViewModel
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.fragment_cards.*

class CardsList : Fragment(R.layout.fragment_cards) {

    private lateinit var cardList: List<Card>
    private lateinit var cardViewModel: CardViewModel
    private lateinit var adapters: CardsListAdapters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        rv_cards.layoutManager = layoutManager

        adapters = CardsListAdapters()
        rv_cards.adapter = adapters
//        rv_cards.layoutManager = LinearLayoutManager(context)

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        cardViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            adapters.setData(it)
            cardList = it

            if(it.isEmpty()) {
                rv_cards.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_cards.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }
        })

        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapters.setData(cardList)
            swipeToRefresh.isRefreshing = false
        }

        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_cards_to_createCard)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_del -> cardViewModel.deleteAllCards()
        }
        return super.onOptionsItemSelected(item)
    }
}