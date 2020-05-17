package gapo.androidhn.quangt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gapo.androidhn.quangt.model.entity.Feed
import gapo.androidhn.quangt.model.repository.FeedRepository
import gapo.androidhn.quangt.utils.LoadingState
import kotlinx.coroutines.launch

class PagerViewModel(private val feedRepository: FeedRepository) : ViewModel() {

    private val categoryData = MutableLiveData<Int>()

    //LiveData state
    val loadingState = MutableLiveData<LoadingState>()

    //LiveData products
    var data = MutableLiveData<List<Feed>>()

    fun setIndex(index: Int) {
        categoryData.value = index
    }

    init {
        data.value = feedRepository.feedData
        getNewsFeed(true)
    }

    fun getNewsFeed(isFirst: Boolean) {
        viewModelScope.launch() {
            try {
                loadingState.value = LoadingState.LOADING
                data.value = feedRepository.getNewsFeed()
                if (isFirst)
                    feedRepository.saveFeeds(data.value!!)
                loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                loadingState.value = LoadingState.error(e.message)
            }
        }
    }

}