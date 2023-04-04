package com.example.shopproject.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Review;
import com.example.shopproject.orther_handle.Publics;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private List<Review> mList;

    public ReviewAdapter(List<Review> mList) {
        this.mList = mList;
    }

    public void setData(List<Review> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_layout, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = mList.get(position);
        if(review == null)
            return;

        holder.txtNameKH.setText(review.getName());
        if(review.getRating() != 0){
            holder.imgRate.setImageResource(Publics.getImgRateforId(review.getRating()));
        }
        holder.txtComment.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNameKH;
        private ImageView imgRate;
        private TextView txtComment;
        private TextView txtTime;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameKH = itemView.findViewById(R.id.txtNameKH);
            imgRate = itemView.findViewById(R.id.img_rate);
            txtComment = itemView.findViewById(R.id.txtBinhLuan);
            txtTime = itemView.findViewById(R.id.txtTG);
        }
    }
}
