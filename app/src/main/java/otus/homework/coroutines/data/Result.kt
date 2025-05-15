package otus.homework.coroutines.data

sealed class Result {
    data class Error(val message: String) : Result()
    data class Success(val fact: Fact, val picture: Picture) : Result()
    object Loading : Result()
}