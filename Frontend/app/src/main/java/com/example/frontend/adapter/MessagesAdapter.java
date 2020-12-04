package com.example.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.MessagesRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private ArrayList<MessagesRequest> mMessageList;
    private MessagesAdapter.OnItemClickListener messageListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MessagesAdapter.OnItemClickListener listener){
        messageListener = listener;
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder{
        public TextView chatNameView;
        public TextView chatContentView;
        public TextView chatDateView;

        public MessagesViewHolder(@NonNull @NotNull View itemView, final MessagesAdapter.OnItemClickListener listener) {
            super(itemView);
            chatNameView = itemView.findViewById(R.id.tvName);
            chatContentView = itemView.findViewById(R.id.tvContent);
            chatDateView = itemView.findViewById(R.id.tvDate);

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

    public MessagesAdapter(ArrayList<MessagesRequest> messageList){
        mMessageList = messageList;
    }

    @NonNull
    @NotNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_caardview, parent, false);
        MessagesAdapter.MessagesViewHolder messagesViewHolder = new MessagesAdapter.MessagesViewHolder(view, messageListener);
        return messagesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessagesAdapter.MessagesViewHolder holder, int position) {
        MessagesRequest currentMessage = mMessageList.get(position);
        holder.chatNameView.setText(currentMessage.getUserName());
        holder.chatContentView.setText(currentMessage.getContent());
        holder.chatDateView.setText(currentMessage.getDateMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
