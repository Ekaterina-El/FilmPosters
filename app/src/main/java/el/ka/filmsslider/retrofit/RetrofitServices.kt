package el.ka.filmsslider.retrofit

import el.ka.filmsslider.models.Movie
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET("marvel")
    fun getMoviesList(): Call<MutableList<Movie>>
}