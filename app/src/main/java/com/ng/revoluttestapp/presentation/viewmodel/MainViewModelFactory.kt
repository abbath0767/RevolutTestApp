package com.ng.revoluttestapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ng.revoluttestapp.domain.usecase.GetExchangeRate

class MainViewModelFactory(private val getExchangeRate: GetExchangeRate) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(getExchangeRate)
            else -> throw IllegalArgumentException("Unsupported view model for this factory: ${modelClass.simpleName}")
        } as T
    }
}