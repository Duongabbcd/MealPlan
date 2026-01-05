package com.ezt.meal.ai.scan.screen.home.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.ezt.meal.ai.scan.BuildConfig
import com.ezt.meal.ai.scan.databinding.FragmentSettingsBinding
import com.ezt.meal.ai.scan.screen.BaseFragment
import com.ezt.meal.ai.scan.screen.language.LanguageActivity
import com.ezt.meal.ai.scan.utils.Common.composeEmail
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.utils.Common.openPrivacy
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.screen.setting.feedback.ShowRateDialog
import com.ezt.meal.ai.scan.utils.Common

import java.io.File

class SettingsFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            language.setOnClickListener {
                startActivity(Intent(requireContext(), LanguageActivity::class.java))
            }
            term.gone()

            share.setOnClickListener {
                withSafeContext { ctx ->
                    shareApk(ctx)
                }

            }

            policy.setOnClickListener {
                withSafeContext { ctx ->
                   ctx.openPrivacy()
                }
            }

            contact.setOnClickListener {
                withSafeContext { ctx ->
                    ctx.composeEmail(
                        getString(R.string.contact_email),
                        getString(
                            R.string.email_feedback_title,
                            ctx.getString(R.string.app_name),   // %1$s
                            BuildConfig.VERSION_NAME            // %2$s
                        )
                    )
                }
            }

            rating.setOnClickListener {
                withSafeContext { ctx ->
                    val dialog = ShowRateDialog(ctx)
                    dialog.show()
                }

            }

            langCurrent.text = Common.getLang(requireContext())
        }
    }

    fun sharePlayStoreLink(context: Context) {
        val shareText =
            "Check out this app: https://play.google.com/store/apps/details?id=com.ezt.ringify.ringtonewallpaper"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        val chooser = Intent.createChooser(intent, "Share via")
        context.startActivity(chooser)
    }

    fun shareApk(context: Context) {
        val apkFile = File(context.applicationInfo.sourceDir)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            apkFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.android.package-archive"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share app")
        )
    }

}