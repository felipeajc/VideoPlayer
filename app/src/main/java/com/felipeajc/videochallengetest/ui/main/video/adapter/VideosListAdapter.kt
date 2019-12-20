package com.felipeajc.videochallengetest.ui.main.video.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.felipeajc.videochallengetest.R
import com.felipeajc.videochallengetest.constants.Constants
import com.felipeajc.videochallengetest.constants.URLs
import com.felipeajc.videochallengetest.api.apimodel.VideoDataModel
import com.felipeajc.videochallengetest.player.ExoPlayerViewManager
import com.felipeajc.videochallengetest.ui.fullscreen.FullscreenVideoActivity
import kotlinx.android.synthetic.main.row_item_video.view.*
import kotlinx.android.synthetic.main.exo_playback_control_view.view.*
import kotlinx.android.synthetic.main.exo_simple_player_view.view.*
import java.lang.Exception
import java.util.*


class VideosListAdapter(val context: Context, val presenter: VideosListPresenter) :
    RecyclerView.Adapter<VideosListAdapter.VideosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        return VideosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_video, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        presenter.onBindVideoRow(holder, position)
    }

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        VideosListMVP.View {

        init {
            itemView.imgPlay.setOnClickListener {

                itemView.imgPlay.visibility = View.GONE
                presenter.handleItemViewClick(this, adapterPosition)
            }

            val fullScreenButton = itemView.exoplayer.exo_controller.exo_fullscreen_button
            fullScreenButton.setOnClickListener {
                presenter.handlePlayerFullScreenClick(this, adapterPosition)
            }

        }

        override fun setVideoTitle(title: String) {
            itemView.txtTitle.text = title
        }

        override fun setThumbnailImage(url: String, title: String) {

            val imageUrl = URLs.BASE_URL_THUMBS + title + "/" + url


            Glide
                .with(context)
                .load(imageUrl)
                .apply(
                    RequestOptions()
                        .transform(MultiTransformation(CenterCrop()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.imgThumbnail)

        }

        override fun showThumbnailAndHideVideo() {
            itemView.imgThumbnail.visibility = View.VISIBLE
            itemView.imgPlay.visibility = View.VISIBLE
            itemView.exoplayer.visibility = View.GONE
        }

        override fun showVideoAndHideThumbnail() {
            itemView.imgThumbnail.visibility = View.GONE
            itemView.imgPlay.visibility = View.GONE
            itemView.exoplayer.visibility = View.VISIBLE
        }

        override fun showLoading() {
            itemView.imgPlay.visibility = View.GONE
            itemView.prg.visibility = View.VISIBLE
        }

        override fun hideLoading() {
            itemView.prg.visibility = View.GONE
        }

        override fun showFullScreenActivity(url: String, audio: String) {

            Constants.isNavigatingToFullScreen = true

            val intent = Intent(context, FullscreenVideoActivity::class.java)
            intent.putExtra(ExoPlayerViewManager.EXTRA_VIDEO_URI, url)
            intent.putExtra(ExoPlayerViewManager.EXTRA_AUDIO_URI, audio)
            context.startActivity(intent)
        }

        override fun makeVideoPlayerInstance(videoDataModel: VideoDataModel) {
            videoDataModel.audioSet = getAudioUrl(videoDataModel)
            ExoPlayerViewManager.getInstance(
                context,
                this.itemView.exoplayer,
                videoDataModel.video_url,
                videoDataModel.audioSet
            )
        }

        fun getAudioUrl(videoDataModel: VideoDataModel): String {

            var language = Locale.getDefault().getLanguage()

            var audioUrl: String = videoDataModel.audioUrlsList!!.en.toString();

            if (language.equals(Constants.DE)) {
                if (!videoDataModel.audioUrlsList!!.de.equals(null))
                    audioUrl = videoDataModel.audioUrlsList!!.de.toString();
            } else if (language.equals(Constants.CN)) {
                if (!videoDataModel.audioUrlsList!!.cn.equals(null))
                    audioUrl = videoDataModel.audioUrlsList!!.cn.toString();
            } else if (language.equals(Constants.ZH)) {
                if (!videoDataModel.audioUrlsList!!.zh.equals(null))
                    audioUrl = videoDataModel.audioUrlsList!!.zh.toString();
            }

            return audioUrl
        }
    }
}