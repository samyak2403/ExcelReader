package com.arrowwould.excelreader.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.listener.MergeChooseListener;
import com.arrowwould.excelreader.model.OfficeModel;

import java.util.ArrayList;

public class ChooseFileAdapter extends RecyclerView.Adapter<ChooseFileAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<OfficeModel> originalData;
    private final MergeChooseListener mListener;
    public boolean isSelectedAll = false;

    public ChooseFileAdapter(Context mContext, ArrayList<OfficeModel> originalData, MergeChooseListener listener) {
        this.mContext = mContext;
        this.originalData = originalData;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfficeModel pdfModel = originalData.get(position);
        holder.tvName.setText(pdfModel.getName());

        holder.btnFavorite.setVisibility(View.INVISIBLE);
        holder.imgMore.setVisibility(View.GONE);
        holder.llCheckBox.setVisibility(View.VISIBLE);
        holder.tvDate.setText(Utils.formatDateToHumanReadable(pdfModel.getLastModified()));
        holder.tvFileSize.setText(Formatter.formatFileSize(mContext, pdfModel.getLength()));
        holder.cbSelect.setChecked(pdfModel.isChecked());
        holder.rootView.setBackground(pdfModel.isChecked()
                ? ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.bg_round_selected, null)
                : ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.bg_round_view_white_r5, null));


        holder.cbSelect.setClickable(false);
        holder.cbSelect.setFocusable(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfModel.setChecked(!pdfModel.isChecked());
                holder.cbSelect.setChecked(pdfModel.isChecked());
                holder.rootView.setBackground(pdfModel.isChecked()
                        ? ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.bg_round_selected, null)
                        : ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.bg_round_view_white_r5, null));

                if (mListener != null) {
                    mListener.onMergeChoose(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (originalData == null) {
            return 0;
        } else {
            return originalData.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout rootView;
        private final TextView tvName;
        private final TextView tvDate;
        private final TextView tvFileSize;
        private final AppCompatCheckBox cbSelect;
        private final LinearLayout llCheckBox;

        private final AppCompatImageView imgMore;
        private final LinearLayout btnFavorite;
        private final AppCompatImageView ivPdf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tvFileDate);
            tvFileSize = itemView.findViewById(R.id.tvFileSize);
            llCheckBox = itemView.findViewById(R.id.llSelection);
            cbSelect = itemView.findViewById(R.id.cb_selection);
            imgMore = itemView.findViewById(R.id.iv_more);
            btnFavorite = itemView.findViewById(R.id.ll_favorite);
            ivPdf = itemView.findViewById(R.id.iv_icon);
        }
    }


    public void setSelectedAll() {
        for (int i = 0; i < originalData.size(); i++) {
            originalData.get(i).setChecked(true);
        }
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    public void setUnSelectedAll() {
        for (int i = 0; i < originalData.size(); i++) {
            originalData.get(i).setChecked(false);
        }
        isSelectedAll = false;
        notifyDataSetChanged();
    }

    public ArrayList<OfficeModel> getSelected() {
        ArrayList<OfficeModel> selected = new ArrayList<>();
        for (int i = 0; i < originalData.size(); i++) {
            if (originalData.get(i).isChecked()) {
                selected.add(originalData.get(i));
            }
        }
        return selected;
    }
}
