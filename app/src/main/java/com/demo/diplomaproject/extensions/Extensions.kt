package com.demo.diplomaproject.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.BuildConfig
import com.google.android.material.textfield.TextInputEditText
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.io.Serializable

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {

    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

fun Navigator.executeCommands(vararg commands: Command) {
    applyCommands(commands)
}

inline fun debug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

fun OkHttpClient.Builder.addLoggingInterceptor(loggingLevel: HttpLoggingInterceptor.Level) {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = loggingLevel
    }
    addNetworkInterceptor(loggingInterceptor)
}

fun TextInputEditText.onImeActionDone(clickAction: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clickAction.invoke()
        }
        false
    }
}

fun Fragment.tryToGetString(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): String {
    return this.arguments?.getString(key) ?: throwArgumentException(exceptionMessage)
}

fun Fragment.tryToGetInt(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Int {
    return this.arguments?.getInt(key) ?: throwArgumentException(exceptionMessage)
}

fun Fragment.tryToGetLong(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Long {
    return this.arguments?.getLong(key) ?: throwArgumentException(exceptionMessage)
}

fun Fragment.tryToGetBoolean(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Boolean {
    return this.arguments?.getBoolean(key) ?: throwArgumentException(exceptionMessage)
}

fun <T : Parcelable> Fragment.tryToGetParcelable(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): T {
    return this.arguments?.getParcelable(key) ?: throwArgumentException(exceptionMessage)
}

fun <T : Parcelable> Fragment.tryToGetArrayList(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): ArrayList<T> {
    return this.arguments?.getParcelableArrayList(key) ?: throwArgumentException(exceptionMessage)
}

fun Fragment.tryToGetSerializable(
    key: String,
    exceptionMessage: String = composeErrorMessage(key)
): Serializable {
    return this.arguments?.getSerializable(key) ?: throwArgumentException(exceptionMessage)
}

fun Fragment.hideKeyboard() {
    val activity = requireActivity()
    val focusedFromActivity = activity.currentFocus
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    if (focusedFromActivity != null) {
        inputMethodManager.hideSoftInputFromWindow(focusedFromActivity.windowToken, 0)
    } else {
        (view as? ViewGroup)?.let {
            val focusedFromFragment = it.focusedChild
            if (focusedFromFragment != null) {
                inputMethodManager.hideSoftInputFromWindow(focusedFromFragment.windowToken, 0)
            }
        }
    }
}

private fun <T> throwArgumentException(message: String): T = throw IllegalArgumentException(message)

private fun composeErrorMessage(key: String) = "Argument $key hasn't been provided"

fun Fragment.getColor(@ColorRes id: Int) = ContextCompat.getColor(this.requireContext(), id)

fun TextView.textColor(@ColorRes id: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, id))
}

fun View.goneUnless(predicate: Boolean) = if (predicate) this.visible() else this.gone()

fun View.invisibleUnless(predicate: Boolean) = if (predicate) this.visible() else this.invisible()

fun <T : View> T.visible() = this.apply { this.visibility = View.VISIBLE }

fun <T : View> T.invisible() = this.apply { this.visibility = View.INVISIBLE }

fun <T : View> T.gone() = this.apply { this.visibility = View.GONE }

val RecyclerView.ViewHolder.context: Context get() = itemView.context

val RecyclerView.ViewHolder.resources: Resources get() = itemView.resources

fun RecyclerView.ViewHolder.getString(@StringRes id: Int) = this.context.getString(id)

fun RecyclerView.ViewHolder.getString(@StringRes id: Int, vararg format: Any) =
    this.context.getString(id, *format)

fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int) = ContextCompat.getColor(this.context, id)

fun RecyclerView.ViewHolder.getInteger(@IntegerRes id: Int) = this.resources.getInteger(id)
