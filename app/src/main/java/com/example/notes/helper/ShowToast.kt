package com.example.notes.helper

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
        /**
            * Toast.LENGTH_SHORT is a constant representing
            * a predefined duration for displaying a Toast message.
            *
            * Toast.LENGTH_SHORT means that the Toast message will be displayed
            * for a short period of time, which is approximately 2 seconds.
            * -----------------------------------------------------------------------
            * Toast.LENGTH_SHORT - константа, представляющая
            * (предопределенную продолжительность) некоторый период времени для отображения toast.
            *
            * Toast.LENGTH_SHORT значит, что toast будет отображаться в течение
            * небольшого промежутка времени - примерно 2 секунды.
        */
    ).show()
}