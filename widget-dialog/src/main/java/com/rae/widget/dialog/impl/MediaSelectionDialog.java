package com.rae.widget.dialog.impl;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.rae.widget.dialog.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择照片、相册对话框。
 * <p>注意：这个方法要在{@link Activity#onActivityResult(int, int, Intent)} 里面调用{@link MediaSelectionDialog#handleIntent(int, int, Intent)} 才能获取回调值。</p>
 * Created by ChenRui on 21:39.
 */
public class MediaSelectionDialog extends SelectionDialog {


    public void setMediaSelectionListener(MediaSelectionListener mediaSelectionListener) {
        mMediaSelectionListener = mediaSelectionListener;
    }

    public interface MediaSelectionListener {
        /**
         * 媒体文件回调方法
         *
         * @param file
         */
        void onMediaSelected(File file);
    }

    /**
     * 拍照回调
     */
    private static final int REQUEST_TAKE_PHOTO = 100;

    private static final int REQUEST_SELECT_PHOTO = 101;

    private static final int REQUEST_TAKE_VIDEO = 102;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_VIDEO = 2;

    private final View mContentView;
    private final Activity mContext;
    private final int mType;
    private MediaSelectionListener mMediaSelectionListener;

    private Uri mFileUri; // 选择的路径

    public MediaSelectionDialog(Activity context) {
        this(context, TYPE_ALL);
    }

    public MediaSelectionDialog(Activity context, int type) {
        super(context);
        mContext = context;
        mContentView = context.getWindow().getDecorView();
        mType = type;
        List<SelectionInfo> data = new ArrayList<>();
        if (type == TYPE_ALL || type == TYPE_PHOTO) {
            data.add(new SelectionInfo(mContext.getString(R.string.widget_dialog_take_photo)));
        }
        if (type == TYPE_ALL || type == TYPE_VIDEO) {
            data.add(new SelectionInfo(mContext.getString(R.string.widget_dialog_video)));
        }
        data.add(new SelectionInfo(mContext.getString(R.string.widget_dialog_select_from_gallery)));
        setCancelable(true);
        setDataList(data);
    }

    /**
     * 从URI 中获取图片真实地址
     *
     * @param data
     * @return
     */
    private File getImagePath(Uri data) {
        if (data == null) return null;
        CursorLoader cursorLoader = new CursorLoader(getContext(), data, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor == null) {
            return null;
        }
        int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return new File(path);
    }


    /**
     * 处理回调结果
     */
    public void handleIntent(int requestCode, int resultCode, Intent data) {
        if (mMediaSelectionListener == null || resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
            case REQUEST_TAKE_PHOTO:
            case REQUEST_TAKE_VIDEO:
                File file = null;
                if (data == null && mFileUri != null) {
                    file = new File(mFileUri.getPath());
                } else if (data != null) {
                    file = getImagePath(data.getData());
                }

                if (file == null || !file.exists()) {
                    return;
                }

                mMediaSelectionListener.onMediaSelected(file);
                break;
        }


    }

    @Override
    protected void onCurrentItemClick(SelectionInfo currentItem) {
        if (currentItemIsCancel()) {
            dismiss();
            return;
        }


        // 调用拍照
        if (currentItem.title.equalsIgnoreCase("拍照")) {
            startTakePhoto();
        } else if (currentItem.title.equalsIgnoreCase("录像")) {
            startTakeVideo();
        } else {
            startSystemMedia();
        }


        dismiss();
    }

    private Uri createTempFile(int type) {
        try {
            String fileName = String.valueOf(("rae_image_" + System.currentTimeMillis()).hashCode());
            fileName += type == TYPE_VIDEO ? ".mp4" : ".jpg";
            File file = new File(mContext.getExternalCacheDir(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            // 支持Android7.0
            return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
//            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 调用系统拍摄功能
    private void startTakeVideo() {
        mFileUri = createTempFile(TYPE_VIDEO); // 先创建一个临时文件
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 300);
        mContext.startActivityForResult(intent, REQUEST_TAKE_VIDEO);
    }

    // 调用系统的拍照功能
    private void startTakePhoto() {
        mFileUri = createTempFile(TYPE_PHOTO); // 先创建一个临时文件
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        mContext.startActivityForResult(intent, REQUEST_TAKE_PHOTO);

    }


    private void startSystemMedia() {
        Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
        photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mType == TYPE_VIDEO ? "video/*" : "image/*");
        mContext.startActivityForResult(photoIntent, REQUEST_SELECT_PHOTO);
    }
}
