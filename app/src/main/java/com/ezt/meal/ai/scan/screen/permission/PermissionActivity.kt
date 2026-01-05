package com.ezt.meal.ai.scan.screen.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.ezt.meal.ai.scan.databinding.ActivityPermissionBinding
import com.ezt.meal.ai.scan.screen.home.MainActivity
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.camera.CameraActivity
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.utils.Common

class PermissionActivity :
    BaseActivity<ActivityPermissionBinding>(ActivityPermissionBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

            cameraStatus.setOnClickListener {
                if(!CameraActivity.hasCameraPermission(this@PermissionActivity)) {
                    showGoToSettingsDialog()
                }
            }
            enableCamera.setOnClickListener {
                if(!CameraActivity.hasCameraPermission(this@PermissionActivity)) {
                    showGoToSettingsDialog()
                }
            }

            rlNative.gone()
            skipBtn.setOnClickListener {
                startActivity(Intent(this@PermissionActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val image =
            if (CameraActivity.hasCameraPermission(this@PermissionActivity)) R.drawable.icon_enable else R.drawable.icon_disable
        binding.cameraStatus.setImageResource(image)

    }

    private fun showGoToSettingsDialog() {
        Common.showDialogGoToSetting(this) { result ->
            if (result) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}