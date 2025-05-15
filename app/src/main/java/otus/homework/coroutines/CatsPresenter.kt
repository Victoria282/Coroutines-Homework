package otus.homework.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService
) {

    private val job = Job()
    private val presenterScope = CoroutineScope(
        Dispatchers.Main + job + CoroutineName("CatsCoroutine")
    )
    private var _catsView: ICatsView? = null

    fun onInitComplete() = presenterScope.launch {
        try {
            val response = catsService.getCatFact()
            if (response.isSuccessful && response.body() != null)
                _catsView?.populate(response.body()!!)
        } catch (e: SocketTimeoutException) {
            _catsView?.showMessage("Не удалось получить ответ от сервером")
        } catch (e: Exception) {
            CrashMonitor.trackWarning()
            _catsView?.showMessage(e.message ?: "Ошибка")
        }
    }

    fun onInitStop() {
        job.cancel()
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}