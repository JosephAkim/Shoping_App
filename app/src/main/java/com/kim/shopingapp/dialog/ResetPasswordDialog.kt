package com.kim.shopingapp.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kim.shopingapp.R

//this extension function because this dialog is going to be used in another fragment
fun Fragment.setUpBottomSheetDialog(
    //we will send the email as an onSend function
    onSendClick: (String) -> Unit
){
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    //behaviour for dialog button show above keyboard
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    //inflate the views
    val buttonSend = view.findViewById<Button>(R.id.buttonSendResetPassword)
    val buttonCancel = view.findViewById<Button>(R.id.buttonCancelResetPassword)
    val edEmail = view.findViewById<EditText>(R.id.edResetPassword)

    //setOnclick listeners for the two buttons
    buttonSend.setOnClickListener {
        val email = edEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }
    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
    //password reset function is in the viewModel**/
}