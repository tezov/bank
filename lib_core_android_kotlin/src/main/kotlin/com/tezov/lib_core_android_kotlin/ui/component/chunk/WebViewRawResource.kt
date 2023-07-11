/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:55
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.tezov.lib_core_kotlin.type.collection.ListEntry

//TODO scheme constant from URL class (cf. java, peut etre deja fait.)

@Suppress("CAST_NEVER_SUCCEEDS")
@SuppressLint("ViewConstructor")
class WebViewRawResource private constructor(context: Context, placeholders:ListEntry<String, String>? = null, rawHtmlResourceId: Int) :
    WebView(context) {
    private val domain = context.packageName
    private val maxHeight: Int? = null

    private var listener: ((authority: String?) -> Boolean)? = null

    init {
        webViewClient = object : WebViewClientCompat() {
            val assetLoader = WebViewAssetLoader.Builder().apply {
                setDomain(domain)
                addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
                addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(context))
            }.build()

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ) = request?.let {
                assetLoader.shouldInterceptRequest(it.url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //local handler
                request.url.scheme?.takeIf { scheme ->
                    scheme == "local"
                }?.let {
                    listener?.takeIf { it.invoke(request.url.authority) }?.let {
                        return true
                    }
                    return false
                }
                //remote external browser
                request.url.scheme?.takeIf { scheme ->
                    scheme == "http" || scheme == "https"
                }?.let {
                    view.context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                val parent = parent as? View
                invalidate()
                parent?.apply {
                    invalidate()
                    requestLayout()
                } ?: run {
                    requestLayout()
                }
            }
        }
        setBackgroundColor(0x00000000)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        val baseUrl = "https://$domain/res/"
        val name = context.resources.getResourceEntryName(rawHtmlResourceId)
        placeholders?.let {
            val content = resources.openRawResource(rawHtmlResourceId).bufferedReader().use { it.readText() }
            val buffer = StringBuilder(content)
            placeholders.forEach { placeholders ->
                val key = "\${${placeholders.key}}"
                var index = 0
                while (buffer.indexOf(key,index).also { index = it } > 0){
                    buffer.replace(index, index+key.length, placeholders.value)
                    index+= placeholders.value.length
                }
            }
            loadDataWithBaseURL(baseUrl, buffer.toString(), "text/html", "UTF-8", null)
        } ?: kotlin.run {
            loadUrl("${baseUrl}/raw/$name.html")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onMeasure(widthMeasureSpec: Int, _heightMeasureSpec: Int) {
        var heightMeasureSpec = _heightMeasureSpec
        if (maxHeight != null) {
            val hSize = MeasureSpec.getSize(heightMeasureSpec)
            val hMode = MeasureSpec.getMode(heightMeasureSpec)
            when (hMode) {
                MeasureSpec.AT_MOST -> heightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.AT_MOST)
                MeasureSpec.UNSPECIFIED -> heightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
                MeasureSpec.EXACTLY -> heightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.EXACTLY)
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    companion object {
        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            rawHtmlResourceId: Int,
            placeholders:ListEntry<String, String>? = null,
            listener: ((authority: String?) -> Boolean)? = null
        ) {
            AndroidView(
                modifier = modifier,
                factory = {
                    WebViewRawResource(it, placeholders, rawHtmlResourceId).apply {
                        this.listener = listener
                    }
                },
                update = {
                    it.listener = listener
                }
            )
        }
    }

}