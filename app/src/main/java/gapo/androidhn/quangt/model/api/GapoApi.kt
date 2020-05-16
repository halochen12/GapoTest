package gapo.androidhn.quangt.model.api

import gapo.androidhn.quangt.model.entity.FeedDetail
import gapo.androidhn.quangt.model.entity.FeedResults
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GapoApi {
    @GET("newsfeed.json")
    suspend fun getNewsFeedAsync(): FeedResults

    @GET("detail.json")
    fun getDetailAsync(): Deferred<FeedDetail>
}