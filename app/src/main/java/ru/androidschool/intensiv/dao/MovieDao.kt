package ru.androidschool.intensiv.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import ru.androidschool.intensiv.data.MovieContent

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): Flowable<List<MovieContent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieContent): Completable

    @Delete
    fun deleteMovie(movie: MovieContent): Completable
}
