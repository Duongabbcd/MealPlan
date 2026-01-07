package com.ezt.meal.ai.scan.screen.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ezt.meal.ai.scan.ads.AdmobUtils
import com.ezt.meal.ai.scan.ads.RemoteConfig
import com.ezt.meal.ai.scan.databinding.ActivityIntroNewBinding
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.home.MainActivity
import com.ezt.meal.ai.scan.screen.permission.PermissionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivityNew : BaseActivity<ActivityIntroNewBinding>(ActivityIntroNewBinding::inflate),
    IntroFragmentNew.CallbackIntro {
//    @Inject
//    lateinit var analyticsLogger: AnalyticsLogger

    private lateinit var introViewPagerAdapter: SlideAdapter
    var position: Int = 0
    private var now = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        now = System.currentTimeMillis()
        viewPager()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun viewPager() {
        if (!AdmobUtils.isNetworkConnected(this)) {
            if (!isIntroFullFail1) {
                isIntroFullFail1 = true
            }
        }

        println("isIntroFullFail:$isIntroFullFail1")
        println("isIntroFullFail:${RemoteConfig.NATIVE_FULL_SCREEN_INTRO_070625}")
        numberPage = 3


        introViewPagerAdapter = SlideAdapter(this)
        binding.viewpager.adapter = introViewPagerAdapter
        binding.viewpager.setOffscreenPageLimit(numberPage)
        binding.viewpager.isUserInputEnabled = true
    }

    override fun onStop() {
        super.onStop()
//        binding.viewGone.gone()
    }

    private fun startAc() {
//        val writingGranted = RingtoneHelper.hasWriteSettingsPermission(this)
//        val externalStorageGranted = RingtoneHelper.getMissingWritePermissions(this).isEmpty()
//        Log.d(
//            TAG,
//            "writingGranted: $writingGranted and externalStorageGranted: $externalStorageGranted"
//        )

        startActivity(Intent(this, MainActivity::class.java))

//        val duration = System.currentTimeMillis() - now
//        if (!writingGranted || !externalStorageGranted) {
//            val isFirst = Common.getCountOpenApp(this@IntroActivityNew) == 0
//            if (!isFirst && RemoteConfig.NATIVE_PERMISSION.contains("1") && RemoteConfig.ADS_DISABLE_2 != "0") {
//                NativeAds.preloadNativeAds(
//                    this@IntroActivityNew, NativeAds.ALIAS_NATIVE_PERMISSION,
//                    NativeAds.NATIVE_PERMISSION
//                )
//            }
//            analyticsLogger.updateUserProperties(this, "intro_screen", -1)
//            analyticsLogger.logScreenGo("permission_screen", "intro_screen", duration)
//            startActivity(Intent(this, PermissionActivity::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            })
//            finish()
//        } else {
//            analyticsLogger.updateUserProperties(this, "intro_screen", -1)
//            analyticsLogger.logScreenGo("main_screen", "intro_screen", duration)
//
//
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        AdsManager.isEnableClick = true
    }

    companion object {
        var isIntroFullFail1: Boolean = true
        var numberPage = 3
        var isFailedOfFullScreen = false
        private val TAG = IntroActivityNew::class.java.simpleName
    }

    override fun onNext(position: Int, introPos: Int) {
        println("onNext: $position and $introPos")
        if (position < numberPage - 1) {
            when (introPos) {
                1 -> {
                    showAfterIntro1 {
                        binding.viewpager.currentItem++
                    }
                }

                2 -> {
                    showAfterIntro2 {
                        binding.viewpager.currentItem++
                    }
                }

                else -> {
                    binding.viewpager.currentItem++
                }
            }
        } else {
            startAc()
        }
    }

    override fun closeAds() {
//        binding.screenViewpager.isUserInputEnabled = true
        binding.viewpager.currentItem++
    }

    override fun disableSwip() {
//        binding.screenViewpager.isUserInputEnabled = false
    }

    private fun showAfterIntro1(callback: () -> Unit) {
        callback.invoke()
    }

    private fun showAfterIntro2(callback: () -> Unit) {
        callback.invoke()
    }

    private fun showInter(callback: () -> Unit) {
        Log.d(TAG, "showInter: $callback")
    }

}