package com.felipeajc.videochallengetest.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.felipeajc.videochallengetest.di.App
import com.felipeajc.videochallengetest.di.AppComponent


open class BaseViewActivity : AppCompatActivity(), IBaseView {
    fun showShortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun getApplicationComponent(): AppComponent {
        return (application as App).applicationComponent
    }

    fun showShortSnackbar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        ).show()
    }

    fun showLongSnackbar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        ).show()
    }

    fun showIndefiniteSnackbar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_INDEFINITE
        ).show()
    }

}