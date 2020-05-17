package gapo.androidhn.quangt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gapo.androidhn.quangt.model.entity.FeedDetail
import gapo.androidhn.quangt.model.repository.FeedRepository
import gapo.androidhn.quangt.utils.LoadingState
import kotlinx.coroutines.launch

class DetailViewModel(private val feedRepository: FeedRepository) : ViewModel() {
    //LiveData state
    val loadingState = MutableLiveData<LoadingState>()

    //LiveData products
    var data = MutableLiveData<FeedDetail>()

    //DocumentId
    var documentId: String? = ""

    fun getFeedDetail() {
        viewModelScope.launch {
            try {
                loadingState.value = LoadingState.LOADING
                data.value = feedRepository.getDetail(documentId)
                loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                println(e)
                loadingState.value = LoadingState.error(e.message)
            }
        }
    }

}