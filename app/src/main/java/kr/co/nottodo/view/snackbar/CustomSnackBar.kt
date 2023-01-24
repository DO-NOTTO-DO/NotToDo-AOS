package kr.co.nottodo.view.snackbar

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewSnackBarBinding
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.BLANK
import kr.co.nottodo.util.extension.dpToPx

class CustomSnackBar(view: View, private val message: String) {
    companion object {
        fun makeSnackBar(view: View, message: String) = CustomSnackBar(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, BLANK, 2000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: ViewSnackBarBinding =
        DataBindingUtil.inflate(inflater, R.layout.view_snack_bar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, context.dpToPx(85))
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.tvSnackBarTitle.text = message
    }

    fun show() {
        snackbar.show()
    }
}