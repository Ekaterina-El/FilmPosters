package el.ka.filmsslider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import el.ka.filmsslider.adapter.SliderAdapter
import el.ka.filmsslider.common.Common
import el.ka.filmsslider.models.Movie
import el.ka.filmsslider.retrofit.RetrofitServices
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private var filmsCount = 0
    private var autoScrollingOn = false
    private lateinit var mService: RetrofitServices
    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
        getMoviesList()

    }

    private fun getMoviesList() {
        mService.getMoviesList().enqueue(object : Callback<MutableList<Movie>> {
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
        filmsCount = movies.size
        this.viewPager.adapter = SliderAdapter(this, movies)
        this.indicators.setupWithViewPager(this.viewPager)

        startAutoScrolling()

        this.viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING && autoScrollingOn) {
                    timer.cancel()
                    autoScrollingOn = false
                } else if (!autoScrollingOn) {
                    startAutoScrolling()
                }
            }

        })
    }

    private fun startAutoScrolling() {
        timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 3000, 3000)
        autoScrollingOn = true
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            this@MainActivity.runOnUiThread {
                this@MainActivity.viewPager.currentItem =
                    if (this@MainActivity.viewPager.currentItem < filmsCount - 1) {
                        this@MainActivity.viewPager.currentItem + 1
                    } else {
                        0
                    }

            }
        }

    }
}