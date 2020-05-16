package gapo.androidhn.quangt.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gapo.androidhn.quangt.model.entity.Feed

@Database(
    entities = [Feed::class], version = 1, exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val feedDao: FeedDao
}