package gapo.androidhn.quangt.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.model.entity.FeedDetail
import gapo.androidhn.quangt.model.entity.Section
import gapo.androidhn.quangt.utils.SECTION_TYPE_HEADER
import gapo.androidhn.quangt.utils.SECTION_TYPE_VIDEO
import kotlinx.android.synthetic.main.item_detail_header.view.*
import kotlinx.android.synthetic.main.item_detail_header.view.tvDescription
import kotlinx.android.synthetic.main.item_detail_header.view.tvTime
import kotlinx.android.synthetic.main.item_detail_header.view.tvTitle
import kotlinx.android.synthetic.main.item_feed_news.view.*
import kotlinx.android.synthetic.main.item_feed_news.view.tvOrigin
import kotlinx.android.synthetic.main.item_feed_video.view.*
import tcking.github.com.giraffeplayer2.VideoView

class DetailAdapter(
    private val onItemsSelected:
        (Section, View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var feedDetail: FeedDetail
    private var sections: MutableList<Section> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SECTION_TYPE_HEADER -> DetailHeaderVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_detail_header,
                    parent,
                    false
                )
            )
            SECTION_TYPE_VIDEO -> SectionVideoVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_feed_video,
                    parent,
                    false
                )
            )
            else -> SectionNewsVHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_feed_news,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun getItemViewType(position: Int): Int {
        return sections[position].section_type
    }

    fun update(detail: FeedDetail) {
        sections.clear()
        sections.addAll(detail.sections)
        sections.add(0, Section(null, SECTION_TYPE_HEADER))
        feedDetail = detail
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionVHolder)
            holder.bind(sections[position], onItemsSelected)
        else if (holder is DetailHeaderVHolder)
            holder.bind(feedDetail)
    }

    class DetailHeaderVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(feedDetail: FeedDetail) = with(itemView) {
            tvTitle.text = feedDetail.title
            tvDescription.text = feedDetail.description
            Glide.with(this).load(feedDetail.publisher.icon).into(ivAvatar)
            tvPublisher.text = feedDetail.publisher.name
            tvTime.text = feedDetail.published_date
        }
    }

    open class SectionVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(section: Section, listener: (Section, View) -> Unit) = with(itemView) {}
    }

    class SectionNewsVHolder(itemView: View) : SectionVHolder(itemView) {
        override fun bind(section: Section, listener: (Section, View) -> Unit) = with(itemView) {
            if (section.section_type == 1) {
                tvTitle.text = section.content?.text
                ivPhoto.visibility = View.GONE
            } else {
                tvTitle.text = section.content?.caption
                ivPhoto.visibility = View.VISIBLE
                Glide.with(this).load(section.content?.href).into(ivPhoto)
            }
            tvDescription.visibility = View.GONE
            tvOrigin.visibility = View.GONE
            tvTime.visibility = View.GONE
            setOnClickListener { listener(section, itemView) }
        }
    }

    class SectionVideoVHolder(itemView: View) : SectionVHolder(itemView) {
        private var videoPlayer: VideoView = itemView.videoView

        override fun bind(section: Section, listener: (Section, View) -> Unit) = with(itemView) {
            tvTitle.text = section.content?.caption
            Glide.with(this).load(section.content?.preview_image?.href).into(videoPlayer.coverView)
            videoPlayer.videoInfo.isPortraitWhenFullScreen = false
            videoPlayer.setVideoPath(section.content?.href).setFingerprint(section.hashCode())
            tvDescription.visibility = View.GONE
            tvOrigin.visibility = View.GONE
            tvTime.visibility = View.GONE
            setOnClickListener { listener(section, itemView) }
        }
    }
}