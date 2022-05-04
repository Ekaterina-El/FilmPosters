package el.ka.filmsslider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import el.ka.filmsslider.R
import el.ka.filmsslider.models.Movie
import kotlinx.android.synthetic.main.slider_item.view.*

class SliderAdapter(
    private val context: Context,
    private val movies: MutableList<Movie>
) : PagerAdapter() {
    override fun getCount(): Int = movies.size
    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_item, null)


        val movie = movies[position]
        Picasso.get().load(movie.imageurl).into(view.sliderImage)
        view.filmName.text = "${movie.name} (${movie.firstappearance})"
        view.filmCreatedBy.text = movie.createdby
        view.filmBio.text = movie.bio

        (container as ViewPager).addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        (container as ViewPager).removeView(obj as View)
    }
}