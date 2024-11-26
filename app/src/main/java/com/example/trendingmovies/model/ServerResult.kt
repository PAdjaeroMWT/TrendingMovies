package com.example.trendingmovies.model

data class ServerResult<out T>(
    val status: Status,
    val data: T?,
    val error: ErrorResponse?,
    val message: String?
){
    enum class Status{
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object{
        fun <T> success(data: T?): ServerResult<T> {
            return ServerResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: ErrorResponse?): ServerResult<T> {
            return ServerResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): ServerResult<T> {
            return ServerResult(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "ServerResponse(status = $status, error = $error, data = $data, message = $message)"
    }
}
