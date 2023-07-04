package com.ccc.remind.presentation.util.glide

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import java.io.InputStream

@GlideModule
class SvgModule : AppGlideModule() {

    override fun registerComponents(
        context: Context, glide: Glide, registry: Registry
    ) {
        registry
            .register<SVG, PictureDrawable>(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}


fun getSvgRequestBuilder(context: Context) = GlideApp.with(context)
    .`as`(PictureDrawable::class.java)
    .transition(DrawableTransitionOptions.withCrossFade())
    .listener(SvgSoftwareLayerSetter())