package app.company.sportpop.core.extensions

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.company.sportpop.R
import com.squareup.picasso.Picasso
import com.tapadoo.alerter.Alerter
import java.util.regex.Pattern

const val PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
const val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

fun Fragment.hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    val currentFocus = activity.currentFocus
    if (currentFocus != null && inputManager != null) {
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun ImageView.loadFromUrl(url: String?) = Picasso.get().load(url).placeholder(R.drawable.ic_error_placeholder).into(this)

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Activity.alert(title: String, description: String, color: Int) {
    Alerter.create(this)
        .setTitle(title)
        .setText(description)
        .setBackgroundColorRes(color)
        .show()
}

fun CharSequence?.isValidPassword() = !isNullOrEmpty() && Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Pattern.compile(EMAIL_PATTERN).matcher(this).matches()
