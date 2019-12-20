package com.felipeajc.videochallengetest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.felipeajc.videochallengetest.R
import com.felipeajc.videochallengetest.ui.base.BaseViewActivity
import com.felipeajc.videochallengetest.ui.main.video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseViewActivity(), MainMVP.View {

    @Inject
    lateinit var presenter: MainMVP.Presenter

    private var videoFragment: VideoFragment? = null
    private var currentFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getApplicationComponent().inject(this)

        presenter.setView(this)

        showVideoFragment()
    }



    override fun setupViews() {


    }

    override fun showVideoFragment() {

        if (isDestroyed)
            return
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (videoFragment == null) {
            videoFragment = VideoFragment.getInstance()
            fragmentTransaction.add(R.id.containerFragment, videoFragment!!)
            if (currentFragment != null)
                fragmentTransaction.detach(currentFragment!!)
        } else {
            currentFragment?.let { fragmentTransaction.detach(it) }
            fragmentTransaction.attach(videoFragment!!)
        }

        fragmentTransaction.commit()
        currentFragment = videoFragment

    }

}