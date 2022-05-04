package el.ka.filmsslider.common

import el.ka.filmsslider.retrofit.RetrofitClient
import el.ka.filmsslider.retrofit.RetrofitServices

object Common {
    private const val BASE_URL = "https://www.simplifiedcoding.net/demos/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}