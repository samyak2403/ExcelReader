package com.arrowwould.excelreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.listener.LanguageAdapterListener;
import com.arrowwould.excelreader.model.Language;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private final ArrayList<Language> languageArrayList;
    private final LanguageAdapterListener mListener;
    int lastPost;
    public LanguageAdapter(Context mContext, LanguageAdapterListener listener) {
        this.languageArrayList = GlobalConstant.createArrayLanguage();
        this.mListener = listener;
        this.lastPost = SharedPreferenceUtils.getInstance(mContext).getInt(GlobalConstant.LANGUAGE_KEY_NUMBER, 0);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_activity, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language language = languageArrayList.get(position);
        holder.imgFlag.setImageResource(language.getImgResource());
        holder.tvLang.setText(language.getNameLanguage());
        if (lastPost == position) {
            holder.imgChoice.setImageResource(R.drawable.ic_choice);
        } else {
            holder.imgChoice.setImageResource(R.drawable.ic_not_choice);
        }
        holder.itemView.setOnClickListener(view -> {
            notifyItemChanged(lastPost);
            notifyItemChanged(holder.getAdapterPosition());
            lastPost = holder.getAdapterPosition();
            if (mListener != null) {
                mListener.onLangSelected(holder.getAdapterPosition());
            }
        });
    }
    @Override
    public int getItemCount() {
        if (languageArrayList == null) {
            return 0;
        } else {
            return languageArrayList.size();
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLang;
        ImageView imgChoice;
        ImageView imgFlag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.img_flag);
            tvLang = itemView.findViewById(R.id.tv_name);
            imgChoice = itemView.findViewById(R.id.iv_selected_state);

        }
    }
}
