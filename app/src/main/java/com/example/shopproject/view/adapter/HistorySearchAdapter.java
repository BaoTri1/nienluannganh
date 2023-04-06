package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.sqlite.Entity.SearchHistory;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.HistorySearchViewHolder>{

    private List<SearchHistory> List_LSTK;
    private Context mContext;
    private clickListener listener;

    public HistorySearchAdapter(List<SearchHistory> list_LSTK, Context mContext, clickListener listener) {
        List_LSTK = list_LSTK;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<SearchHistory> list_LSTK){
        this.List_LSTK = list_LSTK;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistorySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_search_layout, parent, false);

        return new HistorySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySearchViewHolder holder, int position) {
        SearchHistory LSTK = List_LSTK.get(position);

        if(LSTK == null)
            return;
        holder.txtLichSu.setText(LSTK.getKeywork());
        holder.layoutMain.setOnClickListener(view -> {
            listener.onClickHistorySearch(LSTK.getKeywork());
        });
    }

    @Override
    public int getItemCount() {
        if (List_LSTK != null)
            return List_LSTK.size();
        return 0;
    }

    public static class HistorySearchViewHolder extends RecyclerView.ViewHolder {

        private TextView txtLichSu;
        private LinearLayout layoutMain;

        public HistorySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLichSu = itemView.findViewById(R.id.txtLichSuTimKiem);
            layoutMain = itemView.findViewById(R.id.layoutContain_search);
        }

    }
}
