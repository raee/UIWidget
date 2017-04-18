package com.raeblog.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rae.widget.dialog.DialogBuilder;
import com.rae.widget.dialog.IAppDialog;
import com.rae.widget.dialog.IAppDialogClickListener;
import com.rae.widget.dialog.impl.MediaSelectionDialog;
import com.rae.widget.dialog.impl.SelectionDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WidgetDialogDemoActivity extends AppCompatActivity {

    private MediaSelectionDialog mMediaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_dialog_demo);
        ButterKnife.bind(this);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_dialog_default)
    void onDefaultDialogClick() {
        new DialogBuilder(this)
                .setMessage("默认对话框演示")
                .positiveButtonText("点我确定")
                .positiveButtonClickListener(new IAppDialogClickListener() {
                    @Override
                    public void onClick(IAppDialog dialog, int buttonType) {
                        showToast("你点了按钮");
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick(R.id.btn_dialog_default_one)
    void onOneDefaultDialogClick() {
        new DialogBuilder(this)
                .setMessage("一个按钮的弹窗")
                .negativeButtonHidden()
                .positiveButtonText("点我确定")
                .positiveButtonClickListener(new IAppDialogClickListener() {
                    @Override
                    public void onClick(IAppDialog dialog, int buttonType) {
                        showToast("你点了按钮");
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick(R.id.btn_dialog_media)
    void onMediaDialogClick() {
        mMediaDialog = (MediaSelectionDialog) new DialogBuilder(this, DialogBuilder.TYPE_MEDIA_SELECTION).build();
        mMediaDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMediaDialog.handleIntent(resultCode, resultCode, data); // 处理数据
    }

    @OnClick(R.id.btn_dialog_loading)
    void onLoadingDialogClick() {
        final IAppDialog dialog = new DialogBuilder(this)
                .loading()
                .setCanceledOnTouchOutside(false) // 点击不取消
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        showToast("取消加载了");
                    }
                })
                .show();

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    @OnClick(R.id.btn_dialog_list)
    void onSelectDialog() {

        // 快速示例
        new DialogBuilder(this, DialogBuilder.TYPE_LIST_ITEM)
                .addItem("男")
                .addItem("女")
                .setCancelable(false)
                .setOnItemSelectedListener(new SelectionDialog.SelectionListener() {
                    @Override
                    public void onItemSelected(SelectionDialog.SelectionInfo item) {
                        showToast("当前选择为：" + item.title);
                    }
                })
                .show();


//        final SelectionDialog selectionDialog = new SelectionDialog(this);
//        List<SelectionDialog.SelectionInfo> data = new ArrayList<>();
//        data.add(new SelectionDialog.SelectionInfo("男"));
//        data.add(new SelectionDialog.SelectionInfo("女"));
//        selectionDialog.setDataList(data);
//        selectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                SelectionDialog.SelectionInfo item = selectionDialog.getCurrentItem();
//                showToast("当前项为：" + item);
//            }
//        });
//        selectionDialog.show();
    }
}
