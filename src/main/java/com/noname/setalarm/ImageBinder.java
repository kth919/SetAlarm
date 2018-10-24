package com.noname.setalarm;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

/**
 * Created by kth919 on 2017-06-06.
 */

public class ImageBinder {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String resID){
        Glide.with(imageView.getContext()).load(resID).into(imageView);
    }

    @BindingAdapter("imageInt")
    public static void loadImage(ImageView imageView, int resID){
        RequestOptions cropOptions = new RequestOptions().circleCrop();

        Glide.with(imageView.getContext()).load(resID)
                .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                .into(imageView);

    }

}
