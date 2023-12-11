package br.com.alexf.minhastarefas

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val mutex = Mutex()
    @Test
    fun addition_isCorrect() = runBlocking {
        var counter = 0
        // Launch multiple coroutines
        repeat(100) {
            launch {
                // Use Mutex to protect the counter
                mutex.withLock {
                    counter++
                }
            }
        }
    }
}