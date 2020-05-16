package gapo.androidhn.quangt.model.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gapo.androidhn.quangt.model.entity.Avatar
import gapo.androidhn.quangt.model.entity.Content
import gapo.androidhn.quangt.model.entity.Image
import gapo.androidhn.quangt.model.entity.Publisher
import java.lang.reflect.Type
import java.util.*

class DataConverter {
    @TypeConverter
    fun publisherToJson(publisher: Publisher): String = Gson().toJson(publisher)

    @TypeConverter
    fun publisherFromJson(data: String): Publisher {
        val type: Type = object : TypeToken<Publisher?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun avatarToJson(avatar: Avatar?): String {
        if (avatar == null) return ""
        return Gson().toJson(avatar)
    }

    @TypeConverter
    fun avatarFromJson(data: String): Avatar? {
        val type: Type = object : TypeToken<Avatar?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun contentToJson(content: Content?): String {
        if (content == null) return ""
        return Gson().toJson(content)
    }

    @TypeConverter
    fun contentFromJson(data: String): Content? {
        val type: Type = object : TypeToken<Content?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun imagesToJson(list: List<Image>?): String {
        if (list.isNullOrEmpty()) return ""
        return Gson().toJson(list)
    }

    @TypeConverter
    fun imagesFromJson(data: String): List<Image> {
        if (data.isEmpty())
            return Collections.emptyList()
        val listType: Type = object : TypeToken<List<Image?>?>() {}.type
        return Gson().fromJson(data, listType)
    }
}