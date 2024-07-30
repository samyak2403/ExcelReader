package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.Utils;

public class RateAppDialog extends Dialog implements View.OnClickListener {
    ImageView ivStart1, ivStart2, ivStart3, ivStart4, ivStart5, ivOffer;
    boolean optionRate = true;
    Context mContext;

    public RateAppDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_rate);
        findViewById(R.id.btnRate).setOnClickListener(this);
        findViewById(R.id.ivCancel).setOnClickListener(this);
        this.mContext = context;
        ivStart1 = findViewById(R.id.ivStar1);
        ivStart2 = findViewById(R.id.ivStar2);
        ivStart3 = findViewById(R.id.ivStar3);
        ivStart4 = findViewById(R.id.ivStar4);
        ivStart5 = findViewById(R.id.ivStar5);
        ivOffer = findViewById(R.id.ivOffer);
        ivStart1.setOnClickListener(this);
        ivStart2.setOnClickListener(this);
        ivStart3.setOnClickListener(this);
        ivStart4.setOnClickListener(this);
        ivStart5.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int idView =v.getId();

        if (idView==R.id.ivCancel){
            dismiss();
            SharedPreferenceUtils.getInstance(mContext).setBoolean(GlobalConstant.RATE_APP, false);

        }else if (idView==R.id.btnRate){
            rateApp();
            SharedPreferenceUtils.getInstance(mContext).setBoolean(GlobalConstant.RATE_APP, false);

        }else if (idView==R.id.ivStar1){
            optionRate = false;
            ivStart1.setImageResource(R.drawable.ic_star_up);
            ivStart2.setImageResource(R.drawable.ic_un_star_up);
            ivStart3.setImageResource(R.drawable.ic_un_star_up);
            ivStart4.setImageResource(R.drawable.ic_un_star_up);
            ivStart5.setImageResource(R.drawable.ic_un_star_up);
            ivOffer.setVisibility(View.GONE);
        }else if (idView==R.id.ivStar2){
            optionRate = false;
            ivStart1.setImageResource(R.drawable.ic_star_up);
            ivStart2.setImageResource(R.drawable.ic_star_up);
            ivStart3.setImageResource(R.drawable.ic_un_star_up);
            ivStart4.setImageResource(R.drawable.ic_un_star_up);
            ivStart5.setImageResource(R.drawable.ic_un_star_up);
            ivOffer.setVisibility(View.GONE);
        }else if (idView==R.id.ivStar3){
            optionRate = false;
            ivStart1.setImageResource(R.drawable.ic_star_up);
            ivStart2.setImageResource(R.drawable.ic_star_up);
            ivStart3.setImageResource(R.drawable.ic_star_up);
            ivStart4.setImageResource(R.drawable.ic_un_star_up);
            ivStart5.setImageResource(R.drawable.ic_un_star_up);
            ivOffer.setVisibility(View.GONE);
        }else if (idView==R.id.ivStar4){
            optionRate = true;
            ivStart1.setImageResource(R.drawable.ic_star_up);
            ivStart2.setImageResource(R.drawable.ic_star_up);
            ivStart3.setImageResource(R.drawable.ic_star_up);
            ivStart4.setImageResource(R.drawable.ic_star_up);
            ivStart5.setImageResource(R.drawable.ic_un_star_up);
            ivOffer.setVisibility(View.GONE);
        }else if (idView==R.id.ivStar5){
            optionRate = true;
            ivStart1.setImageResource(R.drawable.ic_star_up);
            ivStart2.setImageResource(R.drawable.ic_star_up);
            ivStart3.setImageResource(R.drawable.ic_star_up);
            ivStart4.setImageResource(R.drawable.ic_star_up);
            ivStart5.setImageResource(R.drawable.ic_star_up);
            ivOffer.setVisibility(View.VISIBLE);
        }

    }

    private void rateApp() {
        if (optionRate) {
            Utils.RateApp(mContext);
            dismiss();
        } else {
            dismiss();
        }
    }
}
