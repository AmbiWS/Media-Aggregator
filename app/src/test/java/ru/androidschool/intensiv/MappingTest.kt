package ru.androidschool.intensiv

import org.junit.Before
import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.data.dto.MovieResponse
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.vo.Movie
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class MappingTest {

    private lateinit var dtoMapper: MovieMapper
    private lateinit var dtoList: List<Movie>

    @Before
    @Throws(Exception::class)
    fun init() {
        dtoList = dtoMapper.toValueObject(
            MovieResponse(
                1,
                listOf(
                    MovieContent(
                        "Title",
                        3.5,
                        "uri",
                        500
                    ), MovieContent(
                        "Title 2",
                        5.0,
                        "uri2",
                        700
                    )
                ),
                1,
                1
            )
        )
    }
}
