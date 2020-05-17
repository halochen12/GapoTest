package gapo.androidhn.quangt.model.api

import gapo.androidhn.quangt.model.entity.FeedDetail
import gapo.androidhn.quangt.model.entity.FeedResults
import retrofit2.http.GET
import retrofit2.http.Query

interface GapoApi {
    @GET("newsfeed.json")
    suspend fun getNewsFeed(): FeedResults

    @GET("detail.json")
    suspend fun getDetail(@Query("documentId") documentId: String?): FeedDetail
}