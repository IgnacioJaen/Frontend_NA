package com.example.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.UserSubcategory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    private ArrayList<UserSubcategory> mSubcategoryList;
    private MatchAdapter.OnItemClickListener subcategoryListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MatchAdapter.OnItemClickListener listener){
        subcategoryListener = listener;
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvSub;

        public MatchViewHolder(@NonNull @NotNull View itemView, final MatchAdapter.OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.userNameMatch);
            tvSub = itemView.findViewById(R.id.subMatch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MatchAdapter(ArrayList<UserSubcategory> subcategoryList){
        mSubcategoryList = subcategoryList;
    }

    @NonNull
    @NotNull
    @Override
    public MatchAdapter.MatchViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        MatchAdapter.MatchViewHolder subcategoryViewHolder = new MatchAdapter.MatchViewHolder(view, subcategoryListener);
        return subcategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MatchAdapter.MatchViewHolder holder, int position) {
        UserSubcategory currentSubcategory = mSubcategoryList.get(position);
        holder.tvName.setText(currentSubcategory.getUserName());
        holder.tvSub.setText(currentSubcategory.getSubName());
    }

    @Override
    public int getItemCount() {
        return mSubcategoryList.size();
    }

}
