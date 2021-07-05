package ru.androidschool.intensiv.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import ru.androidschool.intensiv.data.MovieContent

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieContent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieContent): Completable

    @Delete
    fun deleteMovie(movie: MovieContent): Completable
}