package gapo.androidhn.quangt.utils

const val BASE_URL = "https://raw.githubusercontent.com/Akaizz/static/master/"

const val TYPE_NEWS = 0
const val TYPE_GALLERY = 1
const val TYPE_VIDEO = 2

val mapFeedType: HashMap<String, Int> = hashMapOf(
    "overview" to TYPE_NEWS,
    "story" to TYPE_GALLERY,
    "gallery" to TYPE_GALLERY,
    "video" to TYPE_VIDEO,
    "article" to TYPE_NEWS,
    "long_form" to TYPE_GALLERY
)