package com.pndoo.grown123_new.view;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by BAO on 2017-07-14.
 */

public class CircleBitmapDisplayer implements BitmapDisplayer {
    protected  final int margin ;

    public CircleBitmapDisplayer() {
        this(0);
    }

    public CircleBitmapDisplayer(int margin) {
        this.margin = margin;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(new CircleDrawable(bitmap, margin));
    }




//    DisplayImageOptions options = new DisplayImageOptions.Builder()
//            .cacheInMemory(true)
//            .cacheOnDisk(true)
//            .displayer(new CircleBitmapDisplayer())
//            .build();
//
//    ImageLoader.getInstance().displayImage(imageUrl, iViewCircleImageDisplayer, options);
}
