package com.mfk.roomexample.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.mfk.roomexample.databinding.LayoutDialogErrorBinding

/**
 * Created by M.Furkan KÜÇÜK on 15.12.2022
 **/
object ErrorPopupHelper {

    fun showErrorDialog(
        context: Context,
        layoutInflater: LayoutInflater,
        message: String
    ): AlertDialog {
        val errorDialogBuilder = AlertDialog.Builder(context).create()
        val errorDialogBinding = LayoutDialogErrorBinding.inflate(layoutInflater)
        errorDialogBinding.tvError.text = message
        errorDialogBuilder.setView(errorDialogBinding.root)
        errorDialogBuilder.setCanceledOnTouchOutside(false)
        errorDialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 0, 64, 0, 0)
        errorDialogBuilder.window?.setBackgroundDrawable(inset)
        errorDialogBuilder.window?.setGravity(Gravity.TOP)
        errorDialogBinding.imgClose.setOnClickListener {
            errorDialogBuilder.dismiss()
        }
        errorDialogBuilder.show()
        return errorDialogBuilder
    }


}