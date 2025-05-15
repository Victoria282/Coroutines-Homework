package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import otus.homework.coroutines.data.Fact
import otus.homework.coroutines.data.Picture

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    var presenter: CatsPresenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<Button>(R.id.button).setOnClickListener {
            presenter?.onInitComplete()
        }
    }

    override fun populate(fact: Fact, pict: Picture) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
        Picasso.get()
            .load(pict.url)
            .into(findViewById<ImageView>(R.id.pic_imageView))
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}

interface ICatsView {
    fun populate(fact: Fact, pict: Picture)
    fun showMessage(message: String)
}