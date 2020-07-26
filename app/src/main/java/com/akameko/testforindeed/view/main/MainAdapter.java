package com.akameko.testforindeed.view.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.akameko.testforindeed.R;
import com.akameko.testforindeed.repository.pojos.Jeans;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Jeans> jeansList;
    private List<Jeans> likedJeansList;

    private ViewGroup parent; //для предоставления локальных ресурсов в onBingViewHolder

    private OnLikeClickListener likeClickListener;
    private OnItemClickListener itemClickListener;

    public interface OnLikeClickListener {
        void onLikeClick(Jeans likedJeans, int position, Boolean addToFavourite);
    }

    public void setOnLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Jeans jeansToShow, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this .itemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layoutItemMain;

        public ImageView imageViewMain;
        public ImageView imageViewLike;

        public TextView textViewNew;
        public TextView textViewTitle;
        public TextView textViewPrice;

        public Boolean liked;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNew = itemView.findViewById(R.id.text_view_new);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewPrice = itemView.findViewById(R.id.text_view_price);

            imageViewMain = itemView.findViewById(R.id.image_view_main);
            imageViewLike = itemView.findViewById(R.id.image_view_like);

            layoutItemMain = itemView.findViewById(R.id.main_item_layout);



        }
    }

    public MainAdapter(List<Jeans> jeansList, List<Jeans> likedJeansList) {
        this.jeansList = jeansList;
        this.likedJeansList = likedJeansList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.liked = likedJeansList.contains(jeansList.get(position));
        //Log.d("onBindViewHolder", holder.liked.toString());

        if ( holder.liked){
            holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_true));
        } else {
            holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_false));
        }

        holder.textViewTitle.setText(jeansList.get(position).getTitle());
        holder.textViewPrice.setText(String.format("%s %s", jeansList.get(position).getPrice().toString(), parent.getResources().getString(R.string.text_rubble_letter)));


        if (jeansList.get(position).getNew()) {
            holder.textViewNew.setVisibility(View.VISIBLE);
        }

        Picasso.get()
                .load(jeansList.get(position).getImage())
                .placeholder(parent.getResources().getDrawable(R.drawable.photo_placeholder))
                .into(holder.imageViewMain);

        holder.imageViewLike.setOnClickListener(v -> {
            if (likeClickListener != null ) {

                if ( holder.liked) {
                    holder.liked = false;
                    holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_false));
                } else {
                    holder.liked = true;
                    holder.imageViewLike.setImageDrawable(parent.getResources().getDrawable(R.drawable.like_true));
                }

                likeClickListener.onLikeClick(jeansList.get(position), position,  holder.liked);
            }
        });

        holder.layoutItemMain.setOnClickListener(v -> {
            if (itemClickListener != null ) {

                itemClickListener.onItemClick(jeansList.get(position), position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return jeansList.size();
    }
}