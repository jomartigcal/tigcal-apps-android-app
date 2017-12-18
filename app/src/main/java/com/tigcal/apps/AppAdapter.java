package com.tigcal.apps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private Context context;
    private List<App> apps;
    private OnClickListener clickListener;

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
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final App app = apps.get(position);

        holder.nameText.setText(app.getName());
        holder.urlText.setText(app.getUrl());

        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(app);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView urlText;
        Button actionButton;

        AppViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.app_name_text);
            urlText = itemView.findViewById(R.id.app_url_text);
            actionButton = itemView.findViewById(R.id.app_button);
        }
    }

    public interface OnClickListener {
        void onClick(App app);
    }
}
