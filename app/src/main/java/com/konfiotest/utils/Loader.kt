package com.konfiotest.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import java.lang.Exception
import kotlin.concurrent.thread

class Loader(val context: Context?, val fm: FragmentManager){

    private val loaderDialog = LoaderDialog()

    fun show(){
        context?.let {
            thread {
                if (!loaderDialog.isAdded) {
                    loaderDialog.isCancelable = false
                    loaderDialog.show(fm,"Loader")
                }
            }
        }
    }

    fun dismiss(){
        try {
            loaderDialog.dismiss()
        } catch (e: Exception) {/*Do nothing*/}
    }

}
