package com.example.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.ReportRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private ArrayList<ReportRequest> mReportList;
    private ReportAdapter.OnItemClickListener reportListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ReportAdapter.OnItemClickListener listener){
        reportListener = listener;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder{
        public TextView reportedNameView;
        public TextView reportContentView;

        public ReportViewHolder(@NonNull @NotNull View itemView, final ReportAdapter.OnItemClickListener listener) {
            super(itemView);
            reportedNameView = itemView.findViewById(R.id.userNameView);
            reportContentView = itemView.findViewById(R.id.reportContentView);

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

    public ReportAdapter(ArrayList<ReportRequest> reportList){
        mReportList = reportList;
    }

    @NonNull
    @NotNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        ReportAdapter.ReportViewHolder reportViewHolder = new ReportAdapter.ReportViewHolder(view, reportListener);
        return reportViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReportAdapter.ReportViewHolder holder, int position) {
        ReportRequest currentReport = mReportList.get(position);
        holder.reportedNameView.setText(currentReport.getReportedUser());
        holder.reportContentView.setText(currentReport.getReportOp());
    }

    @Override
    public int getItemCount() {
        return mReportList.size();
    }

}
