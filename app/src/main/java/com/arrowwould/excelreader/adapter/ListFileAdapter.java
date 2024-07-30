package com.arrowwould.excelreader.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.db.DbHelper;
import com.arrowwould.excelreader.listener.MoreDialogListener;
import com.arrowwould.excelreader.listener.OfficeClickListener;
import com.arrowwould.excelreader.model.OfficeModel;
import com.arrowwould.excelreader.ui.dialog.BottomDialogFragment;

import java.io.File;
import java.util.ArrayList;

public class ListFileAdapter extends RecyclerView.Adapter<ListFileAdapter.ViewHolder> {
    private final Activity mContext;
    private ArrayList<OfficeModel> originalData;
    private final OfficeClickListener mListener;

    public ListFileAdapter(Activity mContext, ArrayList<OfficeModel> originalData, OfficeClickListener mListener) {
        this.mContext = mContext;
        this.originalData = originalData;
        this.mListener = mListener;
    }

    @Override
    public ListFileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_list, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (originalData == null) {
            return 0;
        } else {
            return originalData.size();
        }
    }

    @Override
    public void onBindViewHolder(ListFileAdapter.ViewHolder holder, int position) {
        OfficeModel documentModel = originalData.get(position);
        holder.tvName.setText(documentModel.getName());
        holder.tvDate.setText(Utils.formatDateToHumanReadable(documentModel.getLastModified()));
        holder.tvFileSize.setText(Formatter.formatFileSize(mContext, documentModel.getLength()));
        DbHelper dbHelper = DbHelper.getInstance(mContext);
        if (dbHelper.isStared(documentModel.getFileUri())) {
            holder.imgFavorite.setFrame(300);
        } else {
            holder.imgFavorite.setFrame(0);
        }
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.isStared(documentModel.getFileUri())) {
                    holder.imgFavorite.setFrame(0);
                    dbHelper.removeStaredPDF(documentModel.getFileUri());
                } else {
                    dbHelper.addStaredPDF(documentModel.getFileUri());

                    holder.imgFavorite.playAnimation();
                    holder.imgFavorite.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            holder.btnFavorite.setClickable(true);
                            holder.btnFavorite.setFocusable(true);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            holder.btnFavorite.setClickable(false);
                            holder.btnFavorite.setFocusable(false);
                        }
                    });
                }
                Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT);
                mContext.sendBroadcast(intent);

                if (dbHelper.isRecent(documentModel.getAbsolutePath())) {
                    Intent intent1 = new Intent(GlobalConstant.ACTION_UPDATE_RECENT_FRAGMENT);
                    mContext.sendBroadcast(intent1);
                }
            }
        });
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogFragment dialog = new BottomDialogFragment(mContext, documentModel, new MoreDialogListener() {
                    @Override
                    public void onRename(String newName) {
                        final File fileOldPdfName = new File(documentModel.getFileUri());
                        final String replaceName = documentModel.getFileUri().replace(Utils.removeExtension(documentModel.getName()), newName);
                        if (fileOldPdfName.renameTo(new File(replaceName))) {
                            if (dbHelper.isStared(fileOldPdfName.getAbsolutePath())) {
                                dbHelper.updateStaredPDF(documentModel.getFileUri(), replaceName);
                                Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT);
                                mContext.sendBroadcast(intent);
                            }
                            if (dbHelper.isRecent(fileOldPdfName.getAbsolutePath())) {
                                dbHelper.updateHistory(documentModel.getFileUri(), replaceName);
                                Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_RECENT_FRAGMENT);
                                mContext.sendBroadcast(intent);
                            }
                            File newFile = new File(replaceName);
                            OfficeModel fileHolderNew = new OfficeModel();
                            fileHolderNew.setName(newFile.getName());
                            fileHolderNew.setAbsolutePath(newFile.getAbsolutePath());
                            fileHolderNew.setFileUri(newFile.getAbsolutePath());
                            fileHolderNew.setLength(newFile.length());
                            fileHolderNew.setLastModified(newFile.lastModified());
                            fileHolderNew.setDirectory(newFile.isDirectory());
                            originalData.set(holder.getAdapterPosition(), fileHolderNew);
                            notifyItemChanged(holder.getAdapterPosition());


                        } else {
                            Toast.makeText(mContext, R.string.dialog_rename_fail, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDelete() {
                        File file = new File(documentModel.getFileUri());
                        file.delete();
                        MediaScannerConnection.scanFile(mContext, new String[]{documentModel.getFileUri()}, null, null);
                        if (dbHelper.isStared(documentModel.getFileUri())) {
                            dbHelper.removeStaredPDF(documentModel.getFileUri());
                            Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT);
                            mContext.sendBroadcast(intent);
                        }
                        if (dbHelper.isRecent(documentModel.getFileUri())) {
                            dbHelper.removeRecentPDF(documentModel.getFileUri());
                            Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_RECENT_FRAGMENT);
                            mContext.sendBroadcast(intent);
                        }
                        try {
                            originalData.remove(holder.getAdapterPosition());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), originalData.size());


                        if (originalData.size() == 0) {
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onRemove() {

                    }

                    @Override
                    public void onFav() {
                        notifyDataSetChanged();
                        Intent intent = new Intent(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT);
                        mContext.sendBroadcast(intent);
                        if (dbHelper.isRecent(documentModel.getAbsolutePath())) {
                            Intent intent1 = new Intent(GlobalConstant.ACTION_UPDATE_RECENT_FRAGMENT);
                            mContext.sendBroadcast(intent1);
                        }
                    }


                });
                Window window = dialog.getWindow();
                assert window != null;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onOfficeClick(documentModel);
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDate;
        private final TextView tvFileSize;
        private final LottieAnimationView imgFavorite;
        private final AppCompatImageView imgMore;
        private final LinearLayout btnFavorite;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tvFileDate);
            tvFileSize = itemView.findViewById(R.id.tvFileSize);
            imgFavorite = itemView.findViewById(R.id.lt_favorite);
            imgMore = itemView.findViewById(R.id.iv_more);
            btnFavorite = itemView.findViewById(R.id.ll_favorite);
        }
    }

    public void filter(ArrayList<OfficeModel> list) {
        originalData = list;
        notifyDataSetChanged();
    }
}
