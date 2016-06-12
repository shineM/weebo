package com.danlvse.weebo.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.presenter.AddWeiboPresenter;
import com.danlvse.weebo.presenter.imp.AddWeiboPresenterImp;
import com.danlvse.weebo.ui.view.AddWeiboView;
import com.danlvse.weebo.utils.FileUtil;
import com.danlvse.weebo.utils.PermissionUtils;
import com.danlvse.weebo.utils.ToastUtil;
import com.danlvse.weebo.utils.ViewUtils;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.File;

public class AddWeiboActivity extends AppCompatActivity implements AddWeiboView {
    private static final int REQUEST_WRITE_PERMISSION_CODE = 0;
    private static final int REQUEST_CAMERA_CODE = 0;
    private static final int REQUEST_LIBRARY_CODE = 2;
    public static final String ORIGIN_WEIBO = "originWeibo";
    public static final String IS_REPOST = "isRepost";
    public static final String REPOST_CONTENT = "repostContent";
    private View container;
    private TextView repostText;
    private FrameLayout imageLayout;
    private LinearLayout bottomMenu;
    private LinearLayout addWindow;
    private EditText mInputText;
    private TextView mUserName;
    private ImageView mAvatar;
    private Button mPostButton;
    private TextView mLocationInfo;
    private TextView mAddImageText;
    private ImageView mSelectedImage;
    private ImageView mCancelButton;
    private String content;
    private File imageUrl;
    private String imagePath;
    private AddWeiboPresenter presenter;
    private PermissionUtils.PermissionOperation operation;
    private ProgressWheel progressWheel;
    private Boolean isRepost;
    private String repostContent;
    private Weibo originWeibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weibo);
        isRepost = getIntent().getBooleanExtra(IS_REPOST,false);
        originWeibo = getIntent().getParcelableExtra(ORIGIN_WEIBO);
        repostContent = getIntent().getStringExtra(REPOST_CONTENT);
        presenter = new AddWeiboPresenterImp(this);
        operation = new PermissionUtils.PermissionOperation() {
            @Override
            public void doIfGranted(int requestCode) {
                if (requestCode == REQUEST_WRITE_PERMISSION_CODE) {
                    startChooseDialog();
                }
            }
        };

        bindView();
        setAnim();
        setUpView();
        initPostType();
    }

    private void initPostType() {
        if (isRepost){
            mPostButton.setText("转发");
            bottomMenu.setVisibility(View.GONE);
            mInputText.setHint("转发微博");
            repostText.setText(repostContent);
            imageLayout.setVisibility(View.GONE);
//            mInputText.requestFocus();
//            InputMethodManager inputMethodManager = (InputMethodManager) mInputText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
//            mInputText.setSelection(0);
        }
    }

    private void startChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择添加方式");
        final String[] items = {"拍照", "图库"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    startCamera();
                } else {
                    startLibrary();
                }
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startLibrary() {
        Intent pickLibrary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickLibrary.resolveActivity(getPackageManager()) != null) {
            pickLibrary.setType("image/*");
            startActivityForResult(pickLibrary, REQUEST_LIBRARY_CODE);
        }
    }

    private void startCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            imageUrl = FileUtil.createFileByDate();
            if (imageUrl != null) {
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageUrl));
                takePicture.putExtra("picture path", imageUrl.getAbsolutePath());
                startActivityForResult(takePicture, REQUEST_CAMERA_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_CODE) {
                imagePath = imageUrl.getAbsolutePath();
            } else if (requestCode == REQUEST_LIBRARY_CODE) {
                imagePath = FileUtil.getPath(data.getData(), this);
            }
            Glide.with(this).load(imagePath).centerCrop().into(mSelectedImage);
            if (!TextUtils.isEmpty(imagePath)){

                mCancelButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUpView() {
        mAddImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkPermission(AddWeiboActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_WRITE_PERMISSION_CODE, operation);
            }
        });
        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content = mInputText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    mPostButton.setTextColor(getResources().getColor(R.color.button_clickable_text));
                    mPostButton.setBackground(getResources().getDrawable(R.drawable.post_clickable_button_background));
                }else{
                    mPostButton.setTextColor(getResources().getColor(R.color.button_unclickable_text));
                    mPostButton.setBackground(getResources().getDrawable(R.drawable.post_unclickable_button_background));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepost){
                    content = mInputText.getText().toString();
                    if (TextUtils.isEmpty(content)){
                        content = "转发微博";
                    }
                    repostWeibo(content);
                }else{
                    content = mInputText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showShort(getApplicationContext(),"还是说点什么吧");
                    } else {
                        sendWeibo(content);
                    }
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedImage.setImageDrawable(null);
                mCancelButton.setVisibility(View.GONE);
            }
        });
    }

    private void repostWeibo(String content) {
        presenter.repostWeibo(this,content,originWeibo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestResultAction(this, requestCode, permissions, grantResults, PermissionUtils.REQUEST_CODE_LIST, operation);
    }

    private void sendWeibo(String content) {
        if (null != imagePath) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            presenter.postPicWeibo(this, content, bitmap, null, null);
            mInputText.setText("");
            mSelectedImage.setImageDrawable(null);
        }else{
            presenter.postWeibo(this,content,null,null);
            mInputText.setText("");
            mSelectedImage.setImageDrawable(null);
        }
    }

    private void bindView() {
        repostText = (TextView) findViewById(R.id.repost_content);
        imageLayout = (FrameLayout) findViewById(R.id.image_layout);
        bottomMenu = (LinearLayout) findViewById(R.id.botton_menu);
        container = findViewById(R.id.container);
        addWindow = (LinearLayout) findViewById(R.id.add_window);
        mInputText = (EditText) findViewById(R.id.add_content);
        mUserName = (TextView) findViewById(R.id.user_name);
        mAvatar = (ImageView) findViewById(R.id.user_avatar);
        mPostButton = (Button) findViewById(R.id.post_button);
        mLocationInfo = (TextView) findViewById(R.id.add_location);
        mAddImageText = (TextView) findViewById(R.id.add_picture);
        mSelectedImage = (ImageView) findViewById(R.id.selected_picture);
        mCancelButton = (ImageView) findViewById(R.id.cancel_button);
        progressWheel = (ProgressWheel) findViewById(R.id.loading_progress);
    }

    @TargetApi(21)
    private void setAnim() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent_background_color));

        container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                container.getViewTreeObserver().removeOnPreDrawListener(this);
                AnimatorSet showContainer = new AnimatorSet();
                showContainer.playTogether(
                        ViewAnimationUtils.createCircularReveal(
                                container,
                                1300,
                                0,
                                0,
                                2500).setDuration(700L),
                        ObjectAnimator.ofFloat(addWindow, "alpha", 0f, 1f).setDuration(400L)
                );
//                showContainer.setDuration(500L);
//                showContainer.setInterpolator(AnimationUtils.loadInterpolator(AddWeiboActivity.this, R.anim.linear_out_slow_in));
                showContainer.start();
                return false;
            }
        });
    }

    @Override
    public void showErrorInfo() {
        ToastUtil.showShort(this, "Sorry,发送失败,请重试");
    }

    @Override
    public void showSuccessInfo(Weibo weibo) {
        ToastUtil.showShort(this, "发送成功");

    }

    @Override
    public void showLoadingIcon() {
        addWindow.setClickable(false);
        container.setBackground(getResources().getDrawable(R.drawable.unclickable_background));
        if (progressWheel.getVisibility()!=View.VISIBLE){
            progressWheel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingIcon() {
        addWindow.setClickable(true);
        container.setBackground(getResources().getDrawable(R.drawable.add_weibo_background));
        if (progressWheel.getVisibility()==View.VISIBLE){
            progressWheel.setVisibility(View.INVISIBLE);
        }
    }
}
