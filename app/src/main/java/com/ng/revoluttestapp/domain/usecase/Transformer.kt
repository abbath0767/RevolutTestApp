package com.ng.revoluttestapp.domain.usecase

import io.reactivex.ObservableTransformer

abstract class Transformer<T> : ObservableTransformer<T, T>