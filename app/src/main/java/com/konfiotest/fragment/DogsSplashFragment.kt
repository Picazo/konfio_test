package com.konfiotest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.konfiotest.R
import com.konfiotest.databinding.DogsSplashBinding

class DogsSplashFragment : BaseFragment() {

    private lateinit var binding: DogsSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DogsSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableOnBackPressed()
        Handler().postDelayed({
            findNavController().navigate(R.id.dogsListFragment)
        }, 3000)
    }

}
