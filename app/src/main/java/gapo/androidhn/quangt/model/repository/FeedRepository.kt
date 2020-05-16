package gapo.androidhn.quangt.model.repository

import gapo.androidhn.quangt.model.api.GapoApi
import gapo.androidhn.quangt.model.db.FeedDao
import gapo.androidhn.quangt.model.entity.Feed
import gapo.androidhn.quangt.model.entity.FeedDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepository(private val api: GapoApi, private val dao: FeedDao) {

    var feedData = dao.findAll()

    suspend fun getNewsFeed(): List<Feed> {
        val response = api.getNewsFeedAsync()
        return response.items
    }

    suspend fun saveFeeds(list: List<Feed>) {
        dao.add(list)
    }

    suspend fun getDetail(): FeedDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getDetailAsync().await()
            response
        }
    }
}