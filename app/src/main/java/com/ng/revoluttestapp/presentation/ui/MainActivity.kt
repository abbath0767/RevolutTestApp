package com.ng.revoluttestapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.ng.revoluttestapp.App
import com.ng.revoluttestapp.R
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.presentation.adapter.BaseItem
import com.ng.revoluttestapp.presentation.adapter.currency.CurrencyAdapter
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

    private lateinit var adapter: CurrencyAdapter

    private var flagNeedScrollToTop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intiRecycler()

        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java).apply {
            loadData()
            viewState.observe(this@MainActivity, Observer { receiveViewState(it) })
        }

        retry_button.setOnClickListener { viewModel.reload() }
    }

    private fun intiRecycler() {
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.addItemDecoration(CommonLinearItemDecorator(this))
        val delegateManager = AdapterDelegatesManager<List<BaseItem>>().apply {
            addDelegate(
                CurrencyDelegate(
                    this@MainActivity,
                    ::onCurrencyClick,
                    ::onRateChange
                )
            )
        }
        adapter = CurrencyAdapter(delegateManager)
        main_recycler.adapter = adapter
        main_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    showSoftKeyboard(false)
                }
            }
        })
    }

    private fun receiveViewState(viewState: MainViewState?) {
        viewState?.let { state ->
            if (state.error == null && state.exchangeEntity != null) {
                showErrorViews(false)
                setViewState(state.exchangeEntity)
            } else if (state.error != null) {
                showErrorViews(true)
                setErrorState(state.error)
            }
        }
    }

    private fun setErrorState(error: Throwable) {
        error_text_view.text = "error: ${error.message}"
    }

    private fun setViewState(exchangeData: ExchangeEntity) {
        val items = mutableListOf<CurrencyEntity>().apply {
            add(exchangeData.selectedCurrency)
            addAll(exchangeData.currencies)
        }.toList()

        setFlagScrollToTop(items)

        adapter.items = items
        if (flagNeedScrollToTop) {
            main_recycler.scrollToPosition(0)
        }
    }

    private fun setFlagScrollToTop(items: List<CurrencyEntity>) {
        flagNeedScrollToTop = items.isNotEmpty() && adapter.items.isNotEmpty() && items[0].currencyName !=
                (adapter.items[0] as CurrencyEntity).currencyName
    }

    private fun onCurrencyClick(currency: CurrencyEntity) = viewModel.onCurrencyClick(currency)

    private fun onRateChange(newRate: Double) = viewModel.onRateChange(newRate)

    private fun showErrorViews(errorViewsIsVisible: Boolean) {
        if (errorViewsIsVisible) {
            if (error_text_view.visibility == View.GONE) {
                error_text_view.visibility = View.VISIBLE
                retry_button.visibility = View.VISIBLE
            }
            if (main_recycler.visibility != View.GONE) {
                main_recycler.visibility = View.GONE
            }
        } else {
            if (error_text_view.visibility != View.GONE) {
                error_text_view.visibility = View.GONE
                retry_button.visibility = View.GONE
            }
            if (main_recycler.visibility != View.VISIBLE) {
                main_recycler.visibility = View.VISIBLE
            }
        }
    }

    private fun showSoftKeyboard(show: Boolean) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } else {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
