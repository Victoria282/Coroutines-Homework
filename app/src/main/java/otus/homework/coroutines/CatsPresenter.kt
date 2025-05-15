package otus.homework.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import otus.homework.coroutines.service.CatsService
import otus.homework.coroutines.service.PicsService
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService,
    private val picsService: PicsService
) {

    private val job = Job()
    private val presenterScope = CoroutineScope(
        Dispatchers.Main + job + CoroutineName("CatsCoroutine")
    )
    private var _catsView: ICatsView? = null

    fun onInitComplete() = presenterScope.launch {

        try {
            val factDeferred = async { catsService.getCatFact() }
            val picDeferred = async { picsService.getPics() }

            val fact = factDeferred.await()
            val pic = picDeferred.await()

            if (fact.isSuccessful && fact.body() != null && pic.isSuccessful && pic.body() != null) {
                _catsView?.populate(fact.body()!!, pic.body()!!.first())
            }
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