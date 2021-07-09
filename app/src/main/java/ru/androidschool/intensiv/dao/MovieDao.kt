package ru.androidschool.intensiv.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import ru.androidschool.intensiv.room.MovieDBEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): Flowable<List<MovieDBEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieDBEntity): Completable

    @Delete
    fun deleteMovie(movie: MovieDBEntity): Completable
}
