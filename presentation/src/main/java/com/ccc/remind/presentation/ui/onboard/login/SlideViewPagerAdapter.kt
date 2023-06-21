package com.ccc.remind.presentation.ui.onboard.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ccc.remind.R

class SlideViewPagerAdapter(var images: ArrayList<Int>) : RecyclerView.Adapter<SlideViewPagerAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.layout_slide, parent, false)) {
        val slideImage: ImageView = itemView.findViewById(R.id.slide_imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(parent)

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.slideImage.setImageResource(images[position])
    }
}