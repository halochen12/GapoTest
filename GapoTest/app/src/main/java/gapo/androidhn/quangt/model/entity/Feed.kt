package gapo.androidhn.quangt.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "feeds")
data class Feed(
    @PrimaryKey
    val title: String,
    val document_id: String,
    val description: String,
    val content_type: String,
    val origin_url: String,
    val published_date: String,
    val publisher: Publisher,
    val avatar: Avatar?,
    val content: Content?,
    val images: List<Image>?
):Serializable