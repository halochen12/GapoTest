package gapo.androidhn.quangt.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.model.entity.Feed
import gapo.androidhn.quangt.utils.TYPE_GALLERY
import gapo.androidhn.quangt.utils.TYPE_VIDEO
import gapo.androidhn.quangt.utils.mapFeedType
import kotlinx.android.synthetic.main.item_feed_gallery.view.*
import kotlinx.android.synthetic.main.item_feed_news.view.*
import kotlinx.android.synthetic.main.item_feed_news.view.tvDescription
import kotlinx.android.synthetic.main.item_feed_news.view.tvOrigin
import kotlinx.android.synthetic.main.item_feed_news.view.tvTime
import kotlinx.android.synthetic.main.item_feed_news.view.tvTitle
import kotlinx.android.synthetic.main.item_feed_video.view.*
import tcking.github.com.giraffeplayer2.VideoView


class FeedAdapter constructor(
    private val onItemsSelected:
        (Feed, View) -> Unit
) : RecyclerView.Adapter<FeedAdapter.FeedVHolder>() {

    private val feeds: MutableList<Feed> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVHolder {
        return when (viewType) {
            TYPE_GALLERY -> FeedGalleryVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_feed_gallery,
                    parent,
                    false
                )
            )
            TYPE_VIDEO -> FeedVideoVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_feed_video,
                    parent,
                    false
                )
            )
            else -> FeedNewsVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_feed_news,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mapFeedType[feeds[position].content_type]!!
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    fun add(list: List<Feed>) {
        this.feeds.addAll(list)
        notifyDataSetChanged()
    }

    fun update(list: List<Feed>) {
        this.feeds.clear()
        this.feeds.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FeedVHolder, position: Int) {
        holder.bind(feeds[position], onItemsSelected)
    }

    open class FeedVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(feed: Feed, listener: (Feed, View) -> Unit) = with(itemView) {}
    }

    class FeedNewsVHolder(itemView: View) : FeedVHolder(itemView) {
        override fun bind(feed: Feed, listener: (Feed, View) -> Unit) = with(itemView) {
            tvTitle.text = feed.title
            tvDescription.text = feed.description
            tvOrigin.text = feed.publisher.name
            tvTime.text = feed.published_date
            if (feed.images == null || feed.images.isEmpty())
                ivPhoto.visibility = View.GONE
            else {
                ivPhoto.visibility = View.VISIBLE
                Glide.with(this).load(feed.images[0].href).into(ivPhoto)
            }
            setOnClickListener { listener(feed, itemView) }
        }
    }

    class FeedGalleryVHolder(itemView: View) : FeedVHolder(itemView) {
        override fun bind(feed: Feed, listener: (Feed, View) -> Unit) = with(itemView) {
            tvTitle.text = feed.title
            tvDescription.text = feed.description
            tvOrigin.text = feed.publisher.name
            tvTime.text = feed.published_date
            if (feed.images == null || feed.images.isEmpty())
                layoutPhotos.visibility = View.GONE
            else {
                layoutPhotos.visibility = View.VISIBLE
                if (feed.images.size == 1) {
                    ivSingle.visibility = View.VISIBLE
                    ivDoubleFirst.visibility = View.GONE
                    ivDoubleLast.visibility = View.GONE
                    ivTripleFirst.visibility = View.GONE
                    ivTripleFirst.visibility = View.GONE
                    ivTripleLast.visibility = View.GONE
                    Glide.with(this).load(feed.images[0].href).into(ivSingle)
                } else if (feed.images.size == 2) {
                    ivSingle.visibility = View.GONE
                    ivDoubleFirst.visibility = View.VISIBLE
                    ivDoubleLast.visibility = View.VISIBLE
                    ivTripleFirst.visibility = View.GONE
                    ivTripleFirst.visibility = View.GONE
                    ivTripleLast.visibility = View.GONE
                    Glide.with(this).load(feed.images[0].href).into(ivDoubleFirst)
                    Glide.with(this).load(feed.images[1].href).into(ivDoubleLast)
                } else {
                    ivSingle.visibility = View.GONE
                    ivDoubleFirst.visibility = View.GONE
                    ivDoubleLast.visibility = View.GONE
                    ivTripleFirst.visibility = View.VISIBLE
                    ivTripleFirst.visibility = View.VISIBLE
                    ivTripleLast.visibility = View.VISIBLE
                    Glide.with(this).load(feed.images[0].href).into(ivTripleFirst)
                    Glide.with(this).load(feed.images[1].href).into(ivTripleSecond)
                    Glide.with(this).load(feed.images[2].href).into(ivTripleLast)
                }
            }
            setOnClickListener { listener(feed, itemView) }
        }
    }

    class FeedVideoVHolder(itemView: View) : FeedVHolder(itemView) {
        private var videoPlayer: VideoView = itemView.videoView

        override fun bind(feed: Feed, listener: (Feed, View) -> Unit) = with(itemView) {
            tvTitle.text = feed.title
            tvDescription.text = feed.description
            Glide.with(this).load(feed.content?.href).into(videoPlayer.coverView)
            videoPlayer.videoInfo.isPortraitWhenFullScreen = false
            videoPlayer.setVideoPath(feed.content?.href).setFingerprint(feed.hashCode())
            tvOrigin.text = feed.publisher.name
            tvTime.text = feed.published_date
        }
    }
}