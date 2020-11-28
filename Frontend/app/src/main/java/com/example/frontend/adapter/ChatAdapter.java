package com.example.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.ChatRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private ArrayList<ChatRequest> mChatList;
    private OnItemClickListener chatListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        chatListener = listener;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        public TextView chatNameView;
        public TextView chatContentView;

        public ChatViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            chatNameView = itemView.findViewById(R.id.chatNameView);
            chatContentView = itemView.findViewById(R.id.chatContentView);

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

    public ChatAdapter(ArrayList<ChatRequest> chatList){
        mChatList = chatList;
    }

    @NonNull
    @NotNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view, chatListener);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatViewHolder holder, int position) {
        ChatRequest currentChat = mChatList.get(position);
        holder.chatNameView.setText(currentChat.getUser2UserName());
        holder.chatContentView.setText(currentChat.getContentChat());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

}
