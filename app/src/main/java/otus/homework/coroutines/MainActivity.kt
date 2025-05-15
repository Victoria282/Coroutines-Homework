package otus.homework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var catsViewModel: CatsViewModel
    private val diContainer = DiContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsViewModel = CatsViewModel(diContainer.factService, diContainer.picService)
        view.viewModel = catsViewModel
        catsViewModel.attachView(view)
        catsViewModel.onInitComplete()
    }

    override fun onStop() {
        if (isFinishing) {
            catsViewModel.detachView()
        }
        super.onStop()
    }
}