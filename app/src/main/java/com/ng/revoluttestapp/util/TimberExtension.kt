package com.ng.revoluttestapp.util

import timber.log.Timber

object TimberExtension {

    inline fun e(t: Throwable? = null, message: () -> String) = log { Timber.e(t, message()) }
    inline fun e(t: Throwable?) = Timber.e(t)

    inline fun d(t: Throwable? = null, message: () -> String) = log { Timber.d(t, message()) }
    inline fun d(t: Throwable?) = Timber.d(t)

    @PublishedApi
    internal inline fun log(block: () -> Unit) {
        if (Timber.treeCount() > 0) block()
    }
}