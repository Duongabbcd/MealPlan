package com.ezt.meal.ai.scan.screen.camera.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.DialogBackBinding
import com.ezt.meal.ai.scan.databinding.DialogGuidanceBinding

class GuidanceDialog(
    context: Context,
) : Dialog(context) {
    private val binding by lazy { DialogGuidanceBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(R.color.transparent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            textCancel.setOnClickListener {
                dismiss()
            }
            closeBtn.setOnClickListener {
                dismiss()
            }

            textOk.setOnClickListener {
                dismiss()
            }
        }
    }
}