package ru.androidschool.intensiv

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
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
    private lateinit var rawDtoList: List<MovieContent>

    @Before
    @Throws(Exception::class)
    fun init() {

        rawDtoList = listOf(
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
        )

        dtoList = dtoMapper.toValueObject(
            MovieResponse(
                1,
                rawDtoList,
                1,
                1
            )
        )
    }

    @Test
    fun checkMapperDtoToVoIsCorrect() {
        for (i in dtoList.indices) {
            compareDtoToVo(dtoList[i], )
        }
    }

    private fun compareDtoToVo(movie: Movie, movieDto: MovieContent) {
        MatcherAssert.assertThat(movie.id, CoreMatchers.equalTo(movieDto.id))
        MatcherAssert.assertThat(movie.posterPath, CoreMatchers.equalTo(movieDto.posterPath))
        MatcherAssert.assertThat(movie.rating, CoreMatchers.equalTo(movieDto.rating))
        MatcherAssert.assertThat(movie.title, CoreMatchers.equalTo(movieDto.title))
        MatcherAssert.assertThat(movie.voteAverage, CoreMatchers.equalTo(movieDto.voteAverage))
    }
}
