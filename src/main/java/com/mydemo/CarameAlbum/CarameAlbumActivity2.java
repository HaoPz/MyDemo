package com.mydemo.CarameAlbum;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by HaoPz on 2017/12/6.
 */

public class CarameAlbumActivity2 extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    @BindView(R.id.shenfenzheng)
    ImageView shenfenzheng;
    @BindView(R.id.shenfenfan)
    ImageView shenfenfan;
    private PopupWindow carameAlbumPopUpWindow; // 弹出相机和相册pop
    private View carameAlbumLayout; // 相机和相册View
    // pop ID
    private TextView mLayoutCarame;
    private TextView mLayoutAlbum;
    private TextView mLayoutCancle;
    private String[] needShootPermissionPerms =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}; // 拍照权限,访问相册,读写权限
    private static final int REQUEST_CAMERA = 1; // 拍摄
    private static final int REQUEST_ALBUM = 3; // 相册
    private static final int PHOTO_GRAPH = 2;// 拍照
    private static final int PHOTO_ALBUM = 4; // 相册
    private static final String IMAGE_UNSPECIFIED = "image/*";
    public long time; // 照片时间戳
    public boolean isShowZheng = true; // 判断是点击的身份证正面flag 还是 反面 flag


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carame_album);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.shenfenzheng, R.id.shenfenfan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shenfenzheng:
                if (carameAlbumPopUpWindow != null && carameAlbumPopUpWindow.isShowing()) {
                    carameAlbumPopUpWindow.dismiss();
                } else {
                    isShowZheng = true;
                    openOrClosePop();
                }
                break;
            case R.id.shenfenfan:
                if (carameAlbumPopUpWindow != null && carameAlbumPopUpWindow.isShowing()) {
                    carameAlbumPopUpWindow.dismiss();
                } else {
                    isShowZheng = false;
                    openOrClosePop();
                }
                break;
        }
    }

    /**
     * 弹出对话框
     */
    private void openOrClosePop() {
        carameAlbumLayout = LayoutInflater.from(CarameAlbumActivity2.this).inflate(R.layout.layout_carame_ablum, null);
        carameAlbumPopUpWindow = new PopupWindow(carameAlbumLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        carameAlbumPopUpWindow.setOutsideTouchable(true);
        carameAlbumPopUpWindow.setBackgroundDrawable(new BitmapDrawable());
        carameAlbumPopUpWindow.setFocusable(true);
        carameAlbumPopUpWindow.setAnimationStyle(R.style.productPagePopStyle);

        mLayoutCarame = (TextView) carameAlbumLayout.findViewById(R.id.layout_carame);
        mLayoutAlbum = (TextView) carameAlbumLayout.findViewById(R.id.layout_album);
        mLayoutCancle = (TextView) carameAlbumLayout.findViewById(R.id.layout_cancle);
        carameAlbumPopUpWindow.showAtLocation(carameAlbumLayout, Gravity.BOTTOM, 0, 0);

        mLayoutCarame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(CarameAlbumActivity2.this, needShootPermissionPerms)) {
                    takeCarame();
                } else {
                    EasyPermissions.requestPermissions(CarameAlbumActivity2.this, "拍照需要摄像头权限", REQUEST_CAMERA, needShootPermissionPerms);
                }
            }
        });
        mLayoutAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(CarameAlbumActivity2.this, needShootPermissionPerms)) {
                    takePhoto();
                } else {
                    EasyPermissions.requestPermissions(CarameAlbumActivity2.this, "相册需要读取权限", REQUEST_ALBUM, needShootPermissionPerms);
                }
            }
        });
        mLayoutCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carameAlbumPopUpWindow.isShowing()) carameAlbumPopUpWindow.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 相册
     */
    private void takePhoto() {
        Intent intentAlbum = new Intent(Intent.ACTION_PICK, null);
        intentAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intentAlbum, PHOTO_ALBUM);
    }

    /**
     * 拍照
     */
    private void takeCarame() {
        Intent intentShoot = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        time = System.currentTimeMillis();
        intentShoot.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), time + ".jpg")));
        startActivityForResult(intentShoot, PHOTO_GRAPH);
        Log.i("onPermissionsGranted", "getFilesDir : " + CarameAlbumActivity2.this.getFilesDir());
    }

    // 设置显示的透明度 1.0 - 0.0
    public void setAlpha(float alpha) {
        WindowManager.LayoutParams attributes = CarameAlbumActivity2.this.getWindow().getAttributes();
        attributes.alpha = alpha;
        CarameAlbumActivity2.this.getWindow().setAttributes(attributes);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PHOTO_GRAPH: // 拍照的回调
                if (carameAlbumPopUpWindow.isShowing())
                    carameAlbumPopUpWindow.dismiss(); // 隐藏 carameAlbumPopUpWindow
                if (resultCode == Activity.RESULT_OK) {
                    String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.i("123", "adf");
                    // String absolutePath = MyInfoActivity.this.getFilesDir().getAbsolutePath();
                    File file = new File(absolutePath + "/" + time + ".jpg");
                    Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                    Log.i("onActivityResult:", "" + absolutePath); // /storage/emulated/0/1501747340848.jpg
                    Log.i("bitmap:", "" + bitmap);
                    if (isShowZheng) {
                        Glide.with(CarameAlbumActivity2.this).load(file.toString()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(shenfenzheng);
                    } else {
                        Glide.with(CarameAlbumActivity2.this).load(file.toString()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(shenfenfan);
                    }
                    // 上传
                    // myInfoPresenter.upLoadHeadPicture(file);
                    // WaitDialog.getInstance().showDialog(CarameAlbumActivity.this, "上传修改中");
                }
                break;

            case PHOTO_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (carameAlbumPopUpWindow.isShowing())
                        carameAlbumPopUpWindow.dismiss(); // 隐藏 carameAlbumPopUpWindow

                    Uri selectedImage = geturi(data);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = CarameAlbumActivity2.this.managedQuery(selectedImage, proj, null, null, null);
                    if (cursor != null) {
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(column_index);// 图片在的路径

                        if (isShowZheng) {
                            Glide.with(CarameAlbumActivity2.this).load(imagePath).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(shenfenzheng);
                        } else {
                            Glide.with(CarameAlbumActivity2.this).load(imagePath).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(shenfenfan);
                        }
                    }
                    // WaitDialog.getInstance().showDialog(MyInfoActivity.this, "上传修改中");
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri geturi(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case REQUEST_CAMERA: // 拍照
                takeCarame();
                break;
            case REQUEST_ALBUM: // 相册
                takePhoto();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "权限开启失败,请到设置-权限管理中开启权限", Toast.LENGTH_SHORT).show();
    }
}
