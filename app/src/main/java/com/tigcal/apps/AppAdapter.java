package com.tigcal.apps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private Context context;
    private List<App> apps;
    private OnClickListener clickListener;
    private OnButtonClickListener buttonClickListener;

    AppAdapter(Context context, List<App> apps, OnClickListener clickListener) {
        this.context = context;
        this.apps = apps;
        this.clickListener = clickListener;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        final App app = apps.get(position);

        holder.nameText.setText(app.getName());
        holder.urlText.setText(app.getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(app);
            }
        });

        if(app.getIcon() != 0) {
            holder.appIcon.setImageResource(app.getIcon());
        }

        if (!app.isAndroid()) {
            holder.actionButton.setVisibility(View.GONE);
            holder.actionButton.setOnClickListener(null);
            return;
        }

        holder.actionButton.setVisibility(View.VISIBLE);
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonClickListener != null) {
                    buttonClickListener.onButtonClick(app);
                }
            }
        });

        if (app.isInstalled()) {
            holder.actionButton.setText(context.getString(R.string.button_open));
        } else {
            holder.actionButton.setText(context.getString(R.string.button_download));
        }
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void setButtonClickListener(OnButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView nameText;
        TextView urlText;
        Button actionButton;

        AppViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.app_icon);
            nameText = itemView.findViewById(R.id.app_name_text);
            urlText = itemView.findViewById(R.id.app_url_text);
            actionButton = itemView.findViewById(R.id.app_button);
        }
    }

    public interface OnClickListener {
        void onClick(App app);
    }

    public interface OnButtonClickListener {
        void onButtonClick(App app);
    }
}
