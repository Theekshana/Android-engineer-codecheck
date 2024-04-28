package jp.co.yumemi.android.code_check.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import jp.co.yumemi.android.code_check.R

/**
 * Custom BindingAdapter to load an image from a URL into an ImageView using Glide.
 *
 * @param view The target ImageView to load the image into.
 * @param url The URL of the image to load.
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let { imageUrl ->
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(view)
    }
}