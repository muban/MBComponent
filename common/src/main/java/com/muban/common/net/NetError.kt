package com.muban.common.net

import com.google.gson.JsonParseException
import com.muban.model.model.BaseEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * 网络error处理（顶级函数，方便在项目中任何地方使用）
 */

// 简单说明:密封类结合when让可能情况都是已知的,代码维护性更高。
sealed class HttpResponse

data class Success<out T>(val data: T) : HttpResponse()
data class Failure(val error: HttpError) : HttpResponse()

data class HttpError(val code: Int, val errorMsg: String?, val isFinish: Boolean = false)

/**
 * 处理响应层的错误
 */
fun handlingApiExceptions(e: HttpError) {
    //发送eventBus，在当前页面展示弹窗
    EventBus.getDefault().post(e)
}

//转换服务器响应
fun <T : Any> BaseEntity<T>.convertHttpRes(isFinish: Boolean = false): HttpResponse {
    return if (this.isSuccess()) {
        data()?.let {
            Success(it)
        } ?: Success(Any())
    } else {
        Failure(HttpError(code() ?: 0, msg(), isFinish))
    }
}

/**
 * 处理HttpResponse
 * @param res
 * @param successBlock 成功
 * @param failureBlock 失败
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> handlingHttpResponse(
    res: HttpResponse,
    successBlock: (data: T) -> Unit,
    failureBlock: (error: HttpError) -> Unit = { ex ->
        handlingApiExceptions(ex)
    }
) {
    //切换为主线程
    withContext(Dispatchers.Main) {
        when (res) {
            is Success<*> -> {
                successBlock.invoke(res.data as T)
            }
            is Failure -> {
                failureBlock.invoke(res.error)
            }
        }
    }
}

/**
 * 处理请求层的错误,对可能的已知的错误进行处理
 */
fun handlingExceptions(e: Throwable) {
    Timber.e(e.message ?: "error")
    when (e) {
        is CancellationException -> {
            Timber.e("CancellationException")
        }
        is SocketTimeoutException -> {
            Timber.e("SocketTimeoutException")
        }
        is JsonParseException -> {
            Timber.e("JsonParseException")
        }
        else -> {
        }
    }
}

