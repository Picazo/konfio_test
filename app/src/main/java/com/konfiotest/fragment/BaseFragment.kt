package com.konfiotest.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    private lateinit var mContext: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
    }

    fun disableOnBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(this){}
    }

    fun appHasInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}