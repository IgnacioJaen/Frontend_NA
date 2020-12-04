package com.example.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.Subcategory;
import com.example.frontend.model.UserSubcategory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProfileSubcategoryAdapter extends RecyclerView.Adapter<ProfileSubcategoryAdapter.ProfileSubcategoryViewHolder> {
    private ArrayList<UserSubcategory> mSubcategoryList;
    private ProfileSubcategoryAdapter.OnItemClickListener subcategoryListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ProfileSubcategoryAdapter.OnItemClickListener listener){
        subcategoryListener = listener;
    }

    public static class ProfileSubcategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;

        public ProfileSubcategoryViewHolder(@NonNull @NotNull View itemView, final ProfileSubcategoryAdapter.OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);

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

    public ProfileSubcategoryAdapter(ArrayList<UserSubcategory> subcategoryList){
        mSubcategoryList = subcategoryList;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileSubcategoryAdapter.ProfileSubcategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_profile_item, parent, false);
        ProfileSubcategoryAdapter.ProfileSubcategoryViewHolder subcategoryViewHolder = new ProfileSubcategoryAdapter.ProfileSubcategoryViewHolder(view, subcategoryListener);
        return subcategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileSubcategoryAdapter.ProfileSubcategoryViewHolder holder, int position) {
        UserSubcategory currentSubcategory = mSubcategoryList.get(position);
        holder.tvName.setText(currentSubcategory.getSubName());
    }

    @Override
    public int getItemCount() {
        return mSubcategoryList.size();
    }

}