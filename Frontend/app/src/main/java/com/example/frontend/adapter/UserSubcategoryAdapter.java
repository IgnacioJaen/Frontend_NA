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
import com.example.frontend.model.Subcategory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserSubcategoryAdapter extends RecyclerView.Adapter<UserSubcategoryAdapter.SubcategoryViewHolder> {
    private ArrayList<Subcategory> mSubcategoryList;
    private OnItemClickListener subcategoryListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        subcategoryListener = listener;
    }

    public static class SubcategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvSubitulo;
        public LinearLayout lySubcategory;

        public SubcategoryViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSubitulo = itemView.findViewById(R.id.tvSubtitulo);
            lySubcategory = itemView.findViewById(R.id.lySubcategory);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lySubcategory.setBackgroundColor(Color.parseColor("#E36E6E"));
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

    public UserSubcategoryAdapter(ArrayList<Subcategory> subcategoryList){
        mSubcategoryList = subcategoryList;
    }

    @NonNull
    @NotNull
    @Override
    public SubcategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_subcategory_item, parent, false);
        SubcategoryViewHolder subcategoryViewHolder = new SubcategoryViewHolder(view, subcategoryListener);
        return subcategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubcategoryViewHolder holder, int position) {
        Subcategory currentSubcategory = mSubcategoryList.get(position);
        holder.tvName.setText(currentSubcategory.getName());
    }

    @Override
    public int getItemCount() {
        return mSubcategoryList.size();
    }

}