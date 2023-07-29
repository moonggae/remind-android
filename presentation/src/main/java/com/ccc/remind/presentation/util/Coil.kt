package com.ccc.remind.presentation.util

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy

fun initCoil(context: Context) {
    Coil.setImageLoader(
        ImageLoader
            .Builder(context)
            .components { add(coil.decode.SvgDecoder.Factory()) }
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context).build()
            }
            .diskCache {
                DiskCache
                    .Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .build()
            }
            .build()
    )
}