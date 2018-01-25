package com.androidchatapp;

/**
 * Created by billkao on 4/26/17.
 */

import android.view.View;

import com.eftimoff.viewpagertransformers.BaseTransformer;

public class FlipHorizontalTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        final float rotation = 180f * position;

        view.setAlpha(rotation > 90f || rotation < -90f ? 0 : 1);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(rotation);
    }

}