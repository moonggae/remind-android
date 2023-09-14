package com.ccc.remind.presentation.util

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.Transformation
import okhttp3.OkHttpClient

fun initCoil(context: Context, okHttpClient: OkHttpClient) {
    Coil.setImageLoader(
        ImageLoader
            .Builder(context)
            .components { add(coil.decode.SvgDecoder.Factory()) }
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .okHttpClient(okHttpClient)
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

fun buildCoilRequest(context: Context, url: String, transformations: List<Transformation> = emptyList()) =
    ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .transformations(transformations)
        .build()