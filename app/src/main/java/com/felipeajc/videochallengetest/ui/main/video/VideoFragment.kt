package com.felipeajc.videochallengetest.ui.main.video


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.felipeajc.videochallengetest.R
import com.felipeajc.videochallengetest.constants.Constants
import com.felipeajc.videochallengetest.api.apimodel.Video
import com.felipeajc.videochallengetest.ui.base.BaseViewFragment
import com.felipeajc.videochallengetest.ui.main.video.adapter.VideosListAdapter
import com.felipeajc.videochallengetest.ui.main.video.adapter.VideosListPresenter
import kotlinx.android.synthetic.main.fragment_video.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoFragment : BaseViewFragment(), VideoMVP.View {

    @Inject
    lateinit var presenter: VideoMVP.Presenter

    private var adapterVideos: VideosListAdapter? = null
    private var videosListPresenter: VideosListPresenter? = null
    private var pageData: Video? = null

    private var mView: View? = null

    companion object {
        fun getInstance(): VideoFragment {
            return VideoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.fragment_video, container, false)

        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent().inject(this)

        presenter.setView(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pageData == null)
            presenter.getPageData()

        Constants.isNavigatingBetweenFragments = false
    }

    override fun onResume() {
        super.onResume()
        videosListPresenter?.resumeCurrentVideoOnBackFromFullScreen()
    }

    override fun onStop() {
        super.onStop()
        if (!Constants.isNavigatingToFullScreen && !Constants.isNavigatingBetweenFragments) {
            videosListPresenter?.releaseAllPlayers()
        }

        if (Constants.isNavigatingBetweenFragments) {
            videosListPresenter?.pauseAllPlayers()
        }
    }

    override fun showNetworkError() {
        showLongSnackbar(getString(R.string.try_again))
    }

    override fun showData(videos: Video) {
        pageData = videos

        val layoutManager = LinearLayoutManager(context)
        if (recyclerViewVideos != null) {
            if (adapterVideos == null && videos != null) {
                videosListPresenter =
                    VideosListPresenter(videos)
                adapterVideos = context?.let {
                    VideosListAdapter(
                        it,
                        videosListPresenter!!
                    )
                }
            } else if (adapterVideos != null && videos != null) {
                videosListPresenter!!.setAdapterData(videos)
            }
            recyclerViewVideos.layoutManager = layoutManager
            recyclerViewVideos.adapter = adapterVideos

        }
    }

    override fun showProgressBar() {
        prg.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        prg.visibility = View.GONE
    }
}
