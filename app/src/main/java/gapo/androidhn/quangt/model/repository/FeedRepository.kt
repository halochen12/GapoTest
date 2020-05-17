package gapo.androidhn.quangt.model.repository

import gapo.androidhn.quangt.model.api.GapoApi
import gapo.androidhn.quangt.model.db.FeedDao
import gapo.androidhn.quangt.model.entity.Feed
import gapo.androidhn.quangt.model.entity.FeedDetail

class FeedRepository(private val api: GapoApi, private val dao: FeedDao) {

    var feedData = dao.findAll()

    suspend fun getNewsFeed(): List<Feed> {
        val response = api.getNewsFeed()
        return response.items
    }

    fun saveFeeds(list: List<Feed>) {
        dao.add(list)
    }

    suspend fun getDetail(documentId: String?): FeedDetail {
        return api.getDetail(documentId)
    }
}