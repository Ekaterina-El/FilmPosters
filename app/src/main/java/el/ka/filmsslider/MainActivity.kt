package el.ka.filmsslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import el.ka.filmsslider.adapter.SliderAdapter
import el.ka.filmsslider.common.Common
import el.ka.filmsslider.models.Movie
import el.ka.filmsslider.retrofit.RetrofitServices
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mService: RetrofitServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
        getMoviesList()

    }

    private fun getMoviesList() {
        mService.getMoviesList().enqueue(object: Callback<MutableList<Movie>> {
            override fun onResponse(
                call: Call<MutableList<Movie>>,
                response: Response<MutableList<Movie>>
            ) {
                setMoviesToSlider(response.body()!!)
            }

            override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {}
        })
    }

    private fun setMoviesToSlider(movies: MutableList<Movie>) {
        this.viewPager.adapter = SliderAdapter(this, movies)
        this.indicators.setupWithViewPager(this.viewPager)
    }
}