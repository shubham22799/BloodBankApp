package com.example.bloodbankapp

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

class TwoColorTextview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs)


@BindingAdapter(
    "app:txt1",
    "app:txt2",
    "app:color1",
    "app:color2",
    requireAll = true
)


fun setColors(
    txtView: AppCompatTextView,
    txt1: String,
    txt2: String,
    color1: Int,
    color2: Int
) {
    txtView.setColors(txt1 = txt1, txt2 = txt2, color1 = color1, color2)
}


@JvmName("setColors1")
fun AppCompatTextView.setColors(txt1: String, txt2: String, color1: Int, color2: Int) {


    val word: Spannable = SpannableString(txt1)
    word.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this.context, color1)),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.text = word
    val wordTwo: Spannable = SpannableString(txt2)

    wordTwo.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this.context, color2)),
        0,
        wordTwo.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.append(wordTwo)

}