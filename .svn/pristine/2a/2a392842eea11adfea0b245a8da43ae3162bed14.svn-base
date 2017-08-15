package com.midea.fridgesettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.midea.fridge.utils.update.FridgeUpdateUtil;
import com.midea.fridgesettings.model.OtaUpdateInfo;

/**
 * Created by Administrator on 2016/9/27.
 */
public class OtaActivity extends Activity implements View.OnClickListener {

    private TextView mVersionCode, mSize, mOtaNotic, mVersiondescribe, mUpdatedescribe, mPhone, mLink;
    private ImageView mCheckIcon;
    private Button mOkBtn;
    private boolean mIsChecked = false;
    private OtaUpdateInfo mOtaUpdateInfo;
    private String mOtaNoticStr = "<p>1、升级时长约10分钟。</p>\r\n<p>2、升级过程中请保证冰箱不断电。</p>\r\n<p>3、在升级过程中，如果设备断电有可能造成设备黑屏，美的公司不承担相关责任。升级过程遇到任何问题，可通过电话或网站联系我们获取帮助。</p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota);
        Intent intent = getIntent();
        String json = intent.getExtras().getString("json");
        mOtaUpdateInfo = JSON.parseObject(json, OtaUpdateInfo.class);
        initView();
        setListener();
    }


    private void initView() {
        mVersionCode = (TextView) findViewById(R.id.version_code);
        mOtaNotic = (TextView) findViewById(R.id.ota_notic);
        mSize = (TextView) findViewById(R.id.size);
        mCheckIcon = (ImageView) findViewById(R.id.check);
        mOkBtn = (Button) findViewById(R.id.ok_btn);
        mVersiondescribe = (TextView) findViewById(R.id.versiondescribe);
        mUpdatedescribe = (TextView) findViewById(R.id.updatedescribe);
        mPhone = (TextView) findViewById(R.id.phone);
        mLink = (TextView) findViewById(R.id.link);
        mSize.setText(789 + "M");
        mOtaNotic.setText(Html.fromHtml(mOtaNoticStr));
        mVersionCode.setText(mOtaUpdateInfo.versioncode);
        mVersiondescribe.setText(mOtaUpdateInfo.versiondescribe);
        mUpdatedescribe.setText(Html.fromHtml(mOtaUpdateInfo.updatedescribe));
        mPhone.setText(mOtaUpdateInfo.phoneNum);
        mLink.setText(mOtaUpdateInfo.upUrl);
    }

    private void setListener() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.check_layout).setOnClickListener(this);
        mOkBtn.setOnClickListener(this);
        mOkBtn.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.check_layout:
                mIsChecked = !mIsChecked;
                mCheckIcon.setBackgroundResource(mIsChecked ? R.mipmap.fuxuan02 : R.mipmap.fuxuan01);
                mOkBtn.setBackgroundResource(mIsChecked ? R.drawable.ota_blue_btn : R.drawable.ota_grey_btn);
                mOkBtn.setClickable(mIsChecked);
                break;
            case R.id.ok_btn:
                FridgeUpdateUtil.getInstance().startUpdate();
                break;
        }
    }
}
