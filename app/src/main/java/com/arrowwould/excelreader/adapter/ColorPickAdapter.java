package com.arrowwould.excelreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;


import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.model.ColorModel;

import java.util.ArrayList;

public class ColorPickAdapter extends RecyclerView.Adapter<ColorPickAdapter.ViewHolder> {


    private final ArrayList<ColorModel> colorModelArrayList;

    private final ColorChangedListener listener;

    int lastPost;


    public ColorPickAdapter(ArrayList<ColorModel> colorList, int lstPosition, ColorChangedListener listener) {
        this.colorModelArrayList = colorList;
        this.listener = listener;
        this.lastPost = lstPosition;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorModel colorModel = colorModelArrayList.get(position);
        if (colorModel.isWhite()) {
            holder.imgChoose.setImageResource(R.drawable.ic_color_choosed_main);
        } else {
            holder.imgChoose.setImageResource(R.drawable.ic_color_choosed);
        }
        holder.viewBg.setBackgroundResource(colorModel.getIdSourceBg());
        if (lastPost == position) {
            holder.imgChoose.setVisibility(View.VISIBLE);
        } else {
            holder.imgChoose.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(lastPost);
                notifyItemChanged(holder.getAdapterPosition());
                lastPost = holder.getAdapterPosition();
                if (listener != null) {
                    listener.onColorChanged(colorModel.getCodeColor());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (colorModelArrayList == null) {
            return 0;
        } else {
            return colorModelArrayList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        View viewBg;

        AppCompatImageView imgChoose;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBg = itemView.findViewById(R.id.imgColor);
            imgChoose = itemView.findViewById(R.id.choose_color_iv1);
        }
    }

    public interface ColorChangedListener {
        void onColorChanged(String var1);
    }
}
