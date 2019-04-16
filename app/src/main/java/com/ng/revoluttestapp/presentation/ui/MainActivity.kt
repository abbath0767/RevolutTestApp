package com.ng.revoluttestapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.ng.revoluttestapp.App
import com.ng.revoluttestapp.R
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.presentation.adapter.BaseAdapter
import com.ng.revoluttestapp.presentation.adapter.BaseItem
import com.ng.revoluttestapp.presentation.adapter.currency.CurrencyDelegate
import com.ng.revoluttestapp.presentation.viewmodel.MainViewModel
import com.ng.revoluttestapp.presentation.viewmodel.MainViewModelFactory
import com.ng.revoluttestapp.presentation.viewmodel.MainViewState
import com.ng.revoluttestapp.util.CommonLinearItemDecorator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: BaseAdapter<BaseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intiRecycler()

        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java).apply {
            loadData()
            viewState.observe(this@MainActivity, Observer { receiveViewState(it) })
        }
    }

    private fun intiRecycler() {
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.addItemDecoration(CommonLinearItemDecorator(this))
        val delegateManager = AdapterDelegatesManager<List<BaseItem>>().apply {
            addDelegate(
                CurrencyDelegate(
                    this@MainActivity,
                    ::onCurrencyClick
                )
            )
        }
        adapter = BaseAdapter(delegateManager)
        main_recycler.adapter = adapter
    }

    private fun receiveViewState(viewState: MainViewState?) {
        viewState?.let { state -> state.exchangeEntity?.let { exchangeData -> setViewState(exchangeData) } }
    }

    private fun setViewState(exchangeData: ExchangeEntity) {
        val items = mutableListOf<BaseItem>().apply {
            add(exchangeData.selectedCurrency)
            addAll(exchangeData.currencies)
        }

        adapter.items = items
    }

    private fun onCurrencyClick(currency: CurrencyEntity) = viewModel.onCurrencyClick(currency)
}
