package otus.homework.coroutines.service

import otus.homework.coroutines.data.Picture
import retrofit2.Response
import retrofit2.http.GET

interface PicsService {

    @GET("search")
    suspend fun getPics(): Response<List<Picture>>
}