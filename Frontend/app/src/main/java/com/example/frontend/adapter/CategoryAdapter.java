package com.example.frontend.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.CategoryRequest;
import com.example.frontend.model.ChatRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoryRequest> mCategoryList;
    private OnItemClickListener categoryListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        categoryListener = listener;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitulo;
        public TextView tvSubitulo;
        //private LinearLayout lyCategory;

        public CategoryViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubitulo = itemView.findViewById(R.id.tvSubtitulo);

            //lyCategory = itemView.findViewById(R.id.lyCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        //lyCategory.setBackgroundColor(Color.parseColor("#ffffff"));
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public CategoryAdapter(ArrayList<CategoryRequest> categoryList){
        mCategoryList = categoryList;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view, categoryListener);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
        CategoryRequest currentCategory = mCategoryList.get(position);
        holder.tvTitulo.setText(currentCategory.getNameCategory());
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

}