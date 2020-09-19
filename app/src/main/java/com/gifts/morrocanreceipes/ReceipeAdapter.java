package com.gifts.morrocanreceipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.ViewHolder>{

    private List<ListItem> listItems;

    public ReceipeAdapter(List<ListItem> listItems, Context context) {

        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.title.setText(listItem.getTitle());
        holder.description.setText(listItem.getDescription());
        //holder.receipeImage.setImageURI(listItem.getReceipe_image());

    }

    @Override
    public int getItemCount() {

        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView receipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            receipeImage = itemView.findViewById(R.id.receipe_image);
        }
    }
}
