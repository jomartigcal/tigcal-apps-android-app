package com.tigcal.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter internal constructor(private val context: Context, private val apps: List<App>, private val onClick: (App) -> Unit) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    var openOrDownloadListener: ((App) -> Unit)? = null
    var shareListener: ((App) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]

        holder.nameText.text = app.name
        holder.urlText.text = app.link

        holder.itemView.setOnClickListener { onClick(app) }

        if (app.icon != 0) {
            holder.appIcon.setImageResource(app.icon)
        }

        holder.openOrDownloadImage.visibility = if (app.isAndroid) View.VISIBLE else View.GONE
        holder.openOrDownloadImage.setOnClickListener { openOrDownloadListener?.invoke(app) }
        holder.openOrDownloadImage.setImageResource(if (app.isInstalled) {
            R.drawable.ic_open
        } else {
            R.drawable.ic_install
        })
        holder.openOrDownloadImage.contentDescription = if (app.isInstalled) {
            context.getString(R.string.action_open)
        } else {
            context.getString(R.string.action_install)
        }.plus(" ${app.name}")


        holder.shareImage.setOnClickListener { shareListener?.invoke(app) }
        holder.shareImage.contentDescription = "${context.getString(R.string.action_share)} ${app.name}"
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appIcon: ImageView = itemView.findViewById(R.id.app_icon)
        var nameText: TextView = itemView.findViewById(R.id.app_name_text)
        var urlText: TextView = itemView.findViewById(R.id.app_url_text)
        var openOrDownloadImage: ImageView = itemView.findViewById(R.id.open_install_image)
        var shareImage: ImageView = itemView.findViewById(R.id.share_image)
    }
}
