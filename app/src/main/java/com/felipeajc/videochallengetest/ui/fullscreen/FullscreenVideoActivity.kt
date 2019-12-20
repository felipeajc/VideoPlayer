package com.felipeajc.videochallengetest.ui.fullscreen

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.felipeajc.videochallengetest.R
import com.felipeajc.videochallengetest.player.ExoPlayerViewManager
import com.felipeajc.videochallengetest.ui.base.BaseViewActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import kotlinx.android.synthetic.main.exo_simple_player_view.*
import javax.inject.Inject

// Fullscreen related code taken from Android Studio blueprint
class FullscreenVideoActivity : BaseViewActivity(), FullscreenMVP.View {


    @Inject
    lateinit var presenter: FullscreenMVP.Presenter

    private lateinit var mVideoUri: String
    private lateinit var mAudioUri: String

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        getApplicationComponent().inject(this)
        presenter.setView(this)

        setupPlayer()
        changeFullscreenButtonToExit()

        exo_controller.findViewById<View>(R.id.exo_fullscreen_button)
            .setOnClickListener { presenter.handleFullscreenButtonClick() }
    }

    override fun setupPlayer() {

        mVideoUri = intent.getStringExtra(ExoPlayerViewManager.EXTRA_VIDEO_URI)
        mAudioUri = intent.getStringExtra(ExoPlayerViewManager.EXTRA_AUDIO_URI)

        ExoPlayerViewManager.getInstance(this, player_view, mVideoUri, mAudioUri).playVideo(null)
    }

    override fun changeFullscreenButtonToExit() {
        exo_fullscreen_icon.setImageResource(R.drawable.exo_controls_fullscreen_exit)
    }

    override fun closeFullscreenActivity() {
        finish()
    }

    override fun makeActivityFullscreen() {
        enclosing_layout!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    public override fun onPause() {
        super.onPause()
        presenter.setNavigatiingToFullscreenFalse()
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide()
    }

    override fun hide() {
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, animationDelay.toLong())
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    override fun delayedHide() {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, 100)
    }

    private val animationDelay = 300

    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        makeActivityFullscreen()
    }


    private val mHideRunnable = Runnable { hide() }
}