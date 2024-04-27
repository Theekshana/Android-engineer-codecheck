package jp.co.yumemi.android.code_check.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView

/**
 * Hides the software keyboard when called. This function is useful when you want to hide the keyboard
 * after an input operation, such as clicking a button, is performed.
 *
 * @param view The [View] associated with the keyboard input field. The keyboard will be hidden
 * for this view.
 */
fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Initializes a RecyclerView with the specified layout manager and adapter.
 *
 * @param layoutManager The layout manager to be set for the RecyclerView.
 * @param adapter The adapter to be set for the RecyclerView.
 */
fun RecyclerView.initRecyclerView(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
}

/**
 * Sets the visibility of the view based on the specified boolean flag.
 * If `isShowLoading` is true, the view will be set to visible; otherwise, it will be set to gone.
 * Additionally, if the view is an instance of LottieAnimationView and `isShowLoading` is true,
 * it will start playing the animation.
 *
 * @param isShowLoading Determines whether the view should be shown or hidden.
 */
fun View.isVisible(isShowLoading: Boolean) {
    visibility = if (isShowLoading) View.VISIBLE else View.GONE
    if (isShowLoading && this is LottieAnimationView) {
        playAnimation()
    }
}