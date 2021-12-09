package com.konfiotest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konfiotest.R
import com.konfiotest.model.Dog

class DogAdapter(
    private val context : Context,
    private var items : ArrayList<Dog>?,
    private var listener : OnItemClicked
) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isSkeleton : Boolean = true
    private val SKELETON_TYPE = -1
    interface OnItemClicked {
        fun onItemClicked(itemClick: Dog?, position: Int)
    }

    fun setItems(items : ArrayList<Dog>?){
        isSkeleton = items==null
        this.items = items
    }

    override fun getItemViewType(position: Int): Int {
        return if(isSkeleton){
            -1
        }else{
            1
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == SKELETON_TYPE) {
            ViewHolderSeleton(
                (LayoutInflater.from(context).inflate(
                    R.layout.dogs_main_skeleton,
                    parent, false
                ))
            )
        } else {
            ViewHolder(
                (LayoutInflater.from(context).inflate(
                    R.layout.dogs_item,
                    parent, false
                ))
            )
        }
    }

    override fun getItemCount(): Int {
        if(items!=null){
            items?.let {
                return it.size
            }
        }else{
            return 1
        }
        return 0
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(items==null){
            onBindViewSkeletonHolder(holder as ViewHolderSeleton)
        }else{
            onBindViewUserHolder(holder as ViewHolder, position)
        }

    }

    private fun onBindViewSkeletonHolder(
        holder: ViewHolderSeleton) {
        holder.title.text = "Error"
    }

    private fun onBindViewUserHolder(holder: ViewHolder, position: Int) {
        items?.let{ _items ->
            val item = _items[position]
            item?.let{
                holder.tvTitle.text = it.dogName
                holder.tvDesc.text = it.description
                holder.tvYears.text = "Almost ${it.age} years"

                Glide.with(context).load(it.image)
                    .into(holder.ivDog)

                handleClick(holder.itemView, item,position)
            }
        }
    }

    private fun handleClick(maimClickedView: View, item: Dog, position: Int) {
        maimClickedView.setOnClickListener {
            item?.let {
                listener.onItemClicked(it, position)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvYears: TextView = view.findViewById(R.id.tvYears)
        val ivDog: ImageView = view.findViewById(R.id.ivDog)
    }

    class ViewHolderSeleton(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
    }

}