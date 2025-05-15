package otus.homework.coroutines

import otus.homework.coroutines.service.CatsService
import otus.homework.coroutines.service.PicsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofitFact by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitPic by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val factService by lazy { retrofitFact.create(CatsService::class.java) }
    val picService by lazy { retrofitPic.create(PicsService::class.java) }
}