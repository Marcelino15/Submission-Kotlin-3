package com.marcel.submissionandroid2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from user ORDER BY date DESC")
    fun getAllUsers(): LiveData<List<User>>
}