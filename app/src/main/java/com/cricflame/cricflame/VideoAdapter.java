package com.cricflame.cricflame;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.List;

/**
 * Created by Tofiq Quadri on 27-03-2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YoutubeVideo> youtubeVideoList;

    public VideoAdapter() {
    }

    public VideoAdapter(List<YoutubeVideo> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(com.cricflame.cricflame.R.layout.card_video, parent, false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData( youtubeVideoList.get(position).getVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(com.cricflame.cricflame.R.id.webVideoView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.getSettings().setPluginState(WebSettings.PluginState.ON);
            videoWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            videoWeb.getSettings().setSupportMultipleWindows(true);
            videoWeb.getSettings().setSupportZoom(true);
            videoWeb.getSettings().setBuiltInZoomControls(true);
            videoWeb.getSettings().setAllowFileAccess(true);

            videoWeb.setWebChromeClient(new WebChromeClient() {


            } );
        }
    }

}
