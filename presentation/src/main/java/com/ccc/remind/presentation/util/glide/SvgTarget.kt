package com.ccc.remind.presentation.util.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


fun GlideRequest<PictureDrawable>.svgInto(view: ImageView) =
    this.into(object : CustomTarget<PictureDrawable>() {
        override fun onResourceReady(resource: PictureDrawable, transition: Transition<in PictureDrawable>?) {
            val bitmapTransformation = this@svgInto.transformations[Bitmap::class.java] as BitmapTransformation

            view.post {
                view.setImageDrawable(resource)
                view.scaleType = when(bitmapTransformation) {
                    is CenterCrop ->  ImageView.ScaleType.CENTER_CROP
                    is FitCenter ->  ImageView.ScaleType.FIT_CENTER
                    is CenterInside ->  ImageView.ScaleType.CENTER_INSIDE
                    else -> view.scaleType
                }
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })