package com.konfiotest.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.konfiotest.MainActivity
import com.konfiotest.R
import com.konfiotest.adapter.DogAdapter
import com.konfiotest.databinding.DogsViewFragmentBinding
import com.konfiotest.model.Dog
import com.konfiotest.viewmodel.DogsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DogsViewFragment : BaseFragment(), DogAdapter.OnItemClicked {

    private lateinit var binding: DogsViewFragmentBinding
    private val dogsViewModel : DogsViewModel by viewModel()
    private lateinit var dogsAdapter: DogAdapter
    private var dogsList : ArrayList<Dog>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DogsViewFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableOnBackPressed()
        initDefaultContent()
        initObservers()
        getListDogs()
        initAdapter()
        initRecyclerView()
    }

    private fun initDefaultContent(){
        binding.apply {
            header.headerTitle.text = requireActivity().getString(R.string.dogs_main_view_title)
        }
    }

    private fun getListDogs(){
        if(appHasInternet(requireContext())){
            (activity as MainActivity).loader.show()
            dogsViewModel.getListDogs()
        }else{
            Toast.makeText(requireContext(), requireActivity().resources.getString(R.string.dogs_internet_error_message), Toast.LENGTH_LONG).show()
        }

    }

    private fun initAdapter(){
        dogsAdapter = DogAdapter(requireContext(), dogsList , this)
    }

    private fun initRecyclerView(){
        binding.recyclerView?.run {
            onFlingListener = null
            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            layoutAnimation = controller
            adapter = dogsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        }
    }

    private fun initObservers(){
        val successGetDogsList = Observer<ArrayList<Dog>>{
            (activity as MainActivity).loader.dismiss()
            dogsList = it
            dogsAdapter?.apply {
                if (!dogsList.isNullOrEmpty()) {
                    setItems(dogsList)
                } else {
                    setItems(null)
                }
                notifyDataSetChanged()
                binding.recyclerView?.scheduleLayoutAnimation()
            }
        }
        dogsViewModel.dataListDogMd.observe(viewLifecycleOwner, successGetDogsList)

        val errorObserver = Observer<String>{
            (activity as MainActivity).loader.dismiss()
            if (it != null){
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        dogsViewModel.errorMessageMd.observe(viewLifecycleOwner,errorObserver)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    private fun removeObservers(){
        dogsViewModel.dataListDogMd.value = null
        dogsViewModel.errorMessageMd.value = null
    }

    override fun onItemClicked(itemClick: Dog?, position: Int) {
        Log.d("DogsView", Gson().toJson(itemClick))
    }

}