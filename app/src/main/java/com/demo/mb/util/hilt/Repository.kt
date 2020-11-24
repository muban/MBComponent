package com.demo.mb.util.hilt

import retrofit2.Retrofit
import javax.inject.Inject

class Repository @Inject constructor() {
    @Inject
    lateinit var retrofit: Retrofit
}