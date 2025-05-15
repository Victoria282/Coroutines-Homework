package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import otus.homework.coroutines.data.Result

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    var viewModel: CatsViewModel? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel?.onInitComplete()
        }
    }

    override fun populate(result: Result) {
        findViewById<TextView>(R.id.progressBar).isVisible = result is Result.Loading
        when (result) {
            is Result.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
            }

            is Result.Success -> {
                findViewById<TextView>(R.id.fact_textView).text = result.fact.fact
                Picasso.get()
                    .load(result.picture.url)
                    .into(findViewById<ImageView>(R.id.pic_imageView))
            }

            else -> {}
        }
    }
}

interface ICatsView {
    fun populate(result: Result)
}