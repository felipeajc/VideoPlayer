package com.felipeajc.videochallengetest.ui.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.felipeajc.videochallengetest.di.App
import com.felipeajc.videochallengetest.di.AppComponent

open class BaseViewFragment : Fragment(), IBaseView {

    fun getApplicationComponent(): AppComponent {
        return (activity?.application as App).applicationComponent
    }

    fun showLongSnackbar(message: String) {
        Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        ).show()
    }
}