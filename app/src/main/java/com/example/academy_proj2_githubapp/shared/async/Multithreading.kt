package com.example.academy_proj2_githubapp.shared.async

import android.content.Context
import android.os.Handler
import javax.inject.Inject

class Multithreading @Inject constructor(context: Context) {
    private val mainHandler = Handler(context.mainLooper)

    fun <T> async(operation: () -> T): AsyncOperation<T> {
        return AsyncOperation(
            operation = operation,
            mainHandler = mainHandler,
            threadCreation = ::createThread
        )
    }

    private fun createThread(runnable: Runnable): Thread {
        return Thread(runnable).apply(Thread::start)
    }
}