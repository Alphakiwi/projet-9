package com.openclassrooms.realestatemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView

import androidx.recyclerview.widget.RecyclerView
import extensions.toVideoUrl

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    internal lateinit var youtubeVideoList: List<YouTubeVideos>

    constructor() {}

    constructor(youtubeVideoList: List<YouTubeVideos>) {
        this.youtubeVideoList = youtubeVideoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_view, parent, false)

        return VideoViewHolder(view)

    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.videoWeb.loadData(youtubeVideoList[position].getVideoUrl().toVideoUrl(), "text/html", "utf-8")

    }


    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var videoWeb: WebView

        init {

            videoWeb = itemView.findViewById<View>(R.id.videoWebView) as WebView

            videoWeb.settings.javaScriptEnabled = true
            videoWeb.webChromeClient = object : WebChromeClient() {


            }
        }
    }
}