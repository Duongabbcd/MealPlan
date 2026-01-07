package com.ezt.meal.ai.scan.screen.welcome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ezt.meal.ai.scan.ads.RemoteConfig
import com.ezt.meal.ai.scan.ads.type.OpenAds
import com.ezt.meal.ai.scan.databinding.ActivityWelcomeBinding
import com.ezt.meal.ai.scan.screen.base.BaseActivity

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(ActivityWelcomeBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            if (RemoteConfig.AD_OPEN_APP == "1") {
                OpenAds.initOpenAds(this@WelcomeActivity) { isReady ->
                    if (isReady) {
                        OpenAds.showOpenAds(
                            this@WelcomeActivity
                        ) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 1000)
                        }
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1000)
                    }
                }
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 1000)
            }

        }
    }
}