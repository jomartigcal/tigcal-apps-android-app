package com.tigcal.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter internal constructor(private val context: Context, private val apps: List<App>, private val clickListener: OnClickListener) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    var shareListener: ((App) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]

        holder.nameText.text = app.name
        holder.urlText.text = app.link

        holder.itemView.setOnClickListener { clickListener.onClick(app) }

        if (app.icon != 0) {
            holder.appIcon.setImageResource(app.icon)
        }

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
        var shareImage: ImageView = itemView.findViewById(R.id.share_image)

    }

    interface OnClickListener {
        fun onClick(app: App)
    }

}
