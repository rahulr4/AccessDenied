package com.kairos.android.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kairos.Kairos;
import com.kairos.KairosListener;
import com.rahul.media.main.MediaFactory;
import com.rahul.media.model.Define;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by bkhera on 2/22/2018.
 */

public class EnrollmentActivity extends Activity implements View.OnClickListener, KairosListener {

    private EditText mNameEd;
    private com.kairos.Kairos kairos = new Kairos();
    private ArrayList<String> imageList = new ArrayList<>();
    private MediaFactory.MediaBuilder mediaBuilder;
    private MediaFactory mediaFactory;
    private ProgressBar mProgressBar;
    private ImageView mImageView1, mImageView2, mImageView3, mImageView4, mImageView5, mImageView6;
    private int pos = -1, currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        initializeSdk();
        initView();
    }

    /*initialized sdk*/
    private void initializeSdk() {
        Define.MEDIA_PROVIDER = getString(R.string.image_provider);
        String app_id = "c7d15241";
        String api_key = "fd3287889f836397be1857dd4d0adb11";
        kairos.setAuthentication(this, app_id, api_key);
    }

    private void initView() {
        mNameEd = findViewById(R.id.name_et);
        mProgressBar = findViewById(R.id.progressBar);
        mImageView1 = findViewById(R.id.image_view_1);
        mImageView2 = findViewById(R.id.image_view_2);
        mImageView3 = findViewById(R.id.image_view_3);
        mImageView4 = findViewById(R.id.image_view_4);
        mImageView5 = findViewById(R.id.image_view_5);
        mImageView6 = findViewById(R.id.image_view_6);

        findViewById(R.id.enroll_tv).setOnClickListener(this);
        mImageView1.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
        mImageView4.setOnClickListener(this);
        mImageView5.setOnClickListener(this);
        mImageView6.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enroll_tv:
                if (validArguments()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (currentIndex != imageList.size()) {
                        enrollImage(imageList.get(currentIndex));
                    }
                }
                break;
            case R.id.image_view_1:
                pos = 1;
                openCamera();
                break;
            case R.id.image_view_2:
                pos = 2;
                openCamera();
                break;
            case R.id.image_view_3:
                pos = 3;
                openCamera();
                break;
            case R.id.image_view_4:
                pos = 4;
                openCamera();
                break;
            case R.id.image_view_5:
                pos = 5;
                openCamera();
                break;
            case R.id.image_view_6:
                pos = 6;
                openCamera();
                break;
        }
    }

    private boolean validArguments() {
        if (TextUtils.isEmpty(mNameEd.getText().toString())) {
            Toast.makeText(this, R.string.enter_user_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void enrollImage(String imageUrl) {
        String subjectId = mNameEd.getText().toString();
        String galleryId = getString(R.string.gallery_name);
        try {
            kairos.enroll(getBitmap(imageUrl), subjectId, galleryId, null, null, null, this);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmap(String enrolledImage) {
        File image = new File(enrolledImage);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
    }


    @Override
    public void onSuccess(String response) {
        mProgressBar.setVisibility(View.GONE);
        currentIndex = currentIndex + 1;
        if (currentIndex != imageList.size()) {
            mProgressBar.setVisibility(View.VISIBLE);
            enrollImage(imageList.get(currentIndex));
        }
        Log.d("KAIROS DEMO", response);
        Gson gson = new Gson();
        ResultBean resultBean = gson.fromJson(response, ResultBean.class);
        if (resultBean.images.size() != 0) {
            String userName = resultBean.images.get(0).transaction.subject_id;
            Toast.makeText(this, userName + " enrolled successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFail(String response) {
        mProgressBar.setVisibility(View.GONE);
        Log.d("KAIROS DEMO", response);
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    }

    private void openCamera() {
        mediaBuilder = new MediaFactory.MediaBuilder(EnrollmentActivity.this)
                .isSquareCrop(false)
                .fromCamera();
        mediaFactory = MediaFactory.create().start(mediaBuilder);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> pathArrayList = mediaFactory.onActivityResult(requestCode, resultCode, data);
        if (pathArrayList.size() != 0) {
            imageList.add(pathArrayList.get(0));
            if (pos == 1) {
                loadImageToView(pathArrayList.get(0), mImageView1);
            } else if (pos == 2) {
                loadImageToView(pathArrayList.get(0), mImageView2);
            } else if (pos == 3) {
                loadImageToView(pathArrayList.get(0), mImageView3);
            } else if (pos == 4) {
                loadImageToView(pathArrayList.get(0), mImageView4);
            } else if (pos == 5) {
                loadImageToView(pathArrayList.get(0), mImageView5);
            } else if (pos == 6) {
                loadImageToView(pathArrayList.get(0), mImageView6);
            }
        }

    }

    private void loadImageToView(String imageUrl, ImageView imageView) {
        File f = new File(imageUrl);
        Picasso.with(this).load(f).into(imageView);
    }

}
