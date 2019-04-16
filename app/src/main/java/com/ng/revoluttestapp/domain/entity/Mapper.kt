package com.ng.revoluttestapp.domain.entity

interface Mapper<F, T> {
    fun map(from: F): T
}