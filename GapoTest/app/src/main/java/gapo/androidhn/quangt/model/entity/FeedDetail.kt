package gapo.androidhn.quangt.model.entity

data class FeedDetail(
    val description: String,
    val document_id: String,
    val origin_url: String,
    val published_date: String,
    val publisher: Publisher,
    val sections: List<Section>,
    val template_type: String,
    val title: String
)