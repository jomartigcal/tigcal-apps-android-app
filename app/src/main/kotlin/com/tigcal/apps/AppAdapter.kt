package com.tigcal.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter internal constructor(private val context: Context, private val apps: List<App>, private val clickListener: OnClickListener) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    private var buttonClickListener: OnButtonClickListener? = null

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

        if (!app.isAndroid) {
            holder.actionButton.visibility = View.GONE
            holder.actionButton.setOnClickListener(null)
            return
        }

        holder.actionButton.visibility = View.VISIBLE
        holder.actionButton.setOnClickListener {
            if (buttonClickListener != null) {
                buttonClickListener!!.onButtonClick(app)
            }
        }

        val action = if (app.isInstalled) {
            context.getString(R.string.button_open)
        } else {
            context.getString(R.string.button_download)
        }
        holder.actionButton.text = action
        holder.actionButton.contentDescription = action + " " + app.name
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appIcon: ImageView = itemView.findViewById(R.id.app_icon)
        var nameText: TextView = itemView.findViewById(R.id.app_name_text)
        var urlText: TextView = itemView.findViewById(R.id.app_url_text)
        var actionButton: Button = itemView.findViewById(R.id.app_button)

    }

    interface OnClickListener {
        fun onClick(app: App)
    }

    interface OnButtonClickListener {
        fun onButtonClick(app: App)
    }
}
