package com.example.academy_proj2_githubapp

import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val dateString = "2020-12-24T06:38:42Z"

        val date = LocalDate.parse(dateString, DateTimeFormatter.ISO_INSTANT)
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")

        println(date.format(formatter))
    }
}
