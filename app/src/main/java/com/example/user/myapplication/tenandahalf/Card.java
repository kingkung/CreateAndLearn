package com.example.user.myapplication.tenandahalf;

import android.support.annotation.DrawableRes;

public class Card {
    @DrawableRes
    final int imageId;
    final double value;

    Card(@DrawableRes int imageId, double value) {
        this.imageId = imageId;
        this.value = value;
    }
}
