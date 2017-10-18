package com.nshane.avatar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nshane.avatar.img.GlideCircleTransform;
import com.nshane.avatar.img.GlideImageLoader;
import com.nshane.avatar.ui.AvatarActivityAdv;

import rain.coder.photopicker.controller.PhotoPickConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_avatar;
    private Button btn_avatar_adv;
    private ImageView iv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btn_avatar = (Button) findViewById(R.id.btn_avatar);
        btn_avatar.setOnClickListener(this);
        btn_avatar_adv = (Button) findViewById(R.id.btn_avatar_adv);
        btn_avatar_adv.setOnClickListener(this);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        iv_show.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_avatar:
                clipPhoto();
                break;

            case R.id.btn_avatar_adv:
                AvatarActivityAdv.startAvatarEdit(this);
                break;
            case R.id.iv_show:
                //点击看大图
                /**
                 * base interface call back, user avatar contains normal & HD
                 */
                break;
            default:
                break;
        }
    }

    private void clipPhoto() {
        new PhotoPickConfig.Builder(this)
                .imageLoader(new GlideImageLoader())
                .showCamera(true)
                .clipPhoto(true)
                .clipCircle(true)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case PhotoPickConfig.PICK_REQUEST_CODE:
                if (PhotoPickConfig.photoPickBean.isClipPhoto()) {
                    Uri resultUri = Uri.parse(data.getStringExtra(PhotoPickConfig.EXTRA_CLIP_PHOTO));
                    Glide.with(MainActivity.this).load(resultUri)
                            .transform(new GlideCircleTransform(this))
                            .into(iv_show);
                } else
//                {
//                    ArrayList<String> photoLists = data.getStringArrayListExtra(PhotoPickConfig.EXTRA_STRING_ARRAYLIST);
//                    if (photoLists != null && !photoLists.isEmpty()) {
//                        for (int i = 0; i < photoLists.size(); i++)
//                            if (mImagePaths.size() < 9)
//                                mImagePaths.add(photoLists.get(i));
//                    }
//                    mAddPicture.setPaths(mImagePaths);
//                }
                    break;
        }
    }


}
