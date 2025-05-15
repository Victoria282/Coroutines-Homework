package otus.homework.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import otus.homework.coroutines.data.Result
import otus.homework.coroutines.service.CatsService
import otus.homework.coroutines.service.PicsService
import java.net.SocketTimeoutException

class CatsViewModel(
    private val catsService: CatsService,
    private val picsService: PicsService
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        CrashMonitor.trackWarning()
    }

    private var _catsView: ICatsView? = null

    fun onInitComplete() = viewModelScope.launch(coroutineExceptionHandler) {
        _catsView?.populate(Result.Loading)
        try {
            val factDeferred = async { catsService.getCatFact() }
            val picDeferred = async { picsService.getPics() }

            val fact = factDeferred.await()
            val pic = picDeferred.await()

            if (fact.isSuccessful && fact.body() != null && pic.isSuccessful && pic.body() != null) {
                _catsView?.populate(Result.Success(fact.body()!!, pic.body()!!.first()))
            }
        } catch (e: SocketTimeoutException) {
            _catsView?.populate(Result.Error("Не удалось получить ответ от сервером"))
        } catch (e: Exception) {
            CrashMonitor.trackWarning()
            _catsView?.populate(Result.Error("Ошибка"))
        }
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}