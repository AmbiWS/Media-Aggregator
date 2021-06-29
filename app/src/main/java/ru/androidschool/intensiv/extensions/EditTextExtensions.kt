package ru.androidschool.intensiv.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import timber.log.Timber

object EditTextExtensions {

    fun EditText.onChange(string: (String) -> Unit) {

        val watcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                string(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Timber.d(p0.toString())
            }
        }

        this.addTextChangedListener(watcher)
    }
}
