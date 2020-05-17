package gapo.androidhn.quangt.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

const val BASE_URL = "https://raw.githubusercontent.com/Akaizz/static/master/"
const val ARG_DOCUMENT_ID = "arg_document_id"

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

const val SECTION_TYPE_HEADER = 0
const val SECTION_TYPE_VIDEO = 2


fun convertCreatedDateToAgoString(strCreatedDate: String) :String{
    val timeNow = Calendar.getInstance().timeInMillis
    val sdf = SimpleDateFormat("yyyy-MM-dd E hh:mm:ss z")
    val createdDate = sdf.parse(strCreatedDate).time
    val timeElapsed = timeNow - createdDate
    // For logging in Android for testing purposes
    /*
        Date dateCreatedFriendly = new Date(createdDate);
        Log.d("MicroR", "dateCreatedFriendly: " + dateCreatedFriendly.toString());
        Log.d("MicroR", "timeNow: " + timeNow.toString());
        Log.d("MicroR", "timeElapsed: " + timeElapsed.toString());*/

    // Lengths of respective time durations in Long format.
    val oneMin = 60000L
    val oneHour = 3600000L
    val oneDay = 86400000L
    val oneWeek = 604800000L
    var finalString = "0sec"
    val unit: String
    when {
        timeElapsed < oneMin -> {
            // Convert milliseconds to seconds.
            var seconds = (timeElapsed / 1000).toDouble()
            // Round up
            seconds = Math.round(seconds).toDouble()
            // Generate the friendly unit of the ago time
            unit = if (seconds == 1.0) {
                "sec"
            } else {
                "secs"
            }
            finalString = String.format("%.0f", seconds) + unit
        }
        timeElapsed < oneHour -> {
            var minutes = (timeElapsed / 1000 / 60).toDouble()
            minutes = Math.round(minutes).toDouble()
            unit = if (minutes == 1.0) {
                "min"
            } else {
                "mins"
            }
            finalString = String.format("%.0f", minutes) + unit
        }
        timeElapsed < oneDay -> {
            var hours = (timeElapsed / 1000 / 60 / 60).toDouble()
            hours = Math.round(hours).toDouble()
            unit = if (hours == 1.0) {
                "hr"
            } else {
                "hrs"
            }
            finalString = String.format("%.0f", hours) + unit
        }
        timeElapsed < oneWeek -> {
            var days = (timeElapsed / 1000 / 60 / 60 / 24).toDouble()
            days = Math.round(days).toDouble()
            unit = if (days == 1.0) {
                "day"
            } else {
                "days"
            }
            finalString = String.format("%.0f", days) + unit
        }
        timeElapsed > oneWeek -> {
            var weeks = (timeElapsed / 1000 / 60 / 60 / 24 / 7).toDouble()
            weeks = Math.round(weeks).toDouble()
            unit = if (weeks == 1.0) {
                "week"
            } else {
                "weeks"
            }
            finalString = String.format("%.0f", weeks) + unit
        }
    }
    return finalString
}
