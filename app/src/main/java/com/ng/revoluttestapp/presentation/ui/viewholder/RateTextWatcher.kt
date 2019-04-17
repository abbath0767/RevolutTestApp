package com.ng.revoluttestapp.presentation.ui.viewholder

import android.text.Editable
import android.text.TextWatcher

class RateTextWatcher(
    private val onRateChange: (Double) -> Unit
) : TextWatcher {

    private var isSelfChange = false

    override fun afterTextChanged(s: Editable) {
        if (isSelfChange)
            return

        if (s.isEmpty())
            onRateChange.invoke(0.0)
        else
            onRateChange.invoke(s.toString().toDouble())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    fun selfChange(block: () -> Unit) {
        isSelfChange = true
        block.invoke()
        isSelfChange = false
    }
}