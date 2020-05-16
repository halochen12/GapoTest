package gapo.androidhn.quangt.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gapo.androidhn.quangt.model.entity.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feeds")
    fun findAll(): List<Feed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(products: List<Feed>)
}