package com.aistudio.advertisementyandexdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aistudio.advertisementyandexdemoapp.navigation.MainScreen
import com.aistudio.advertisementyandexdemoapp.ui.theme.AdvertisementYandexDemoAppTheme
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.instream.MobileInstreamAds

class MainActivity : ComponentActivity() {
    private var appOpenAd: AppOpenAd? = null

    companion object {
        const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvertisementYandexDemoAppTheme {
                MainScreen()
            }
        }

        // Инициализация Яндекс Рекламы
        MobileAds.initialize(this) {
            Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized")
        }
        MobileInstreamAds.setAdGroupPreloading(true)
        MobileAds.enableLogging(true)
    }

    // Метод для отображения рекламы
    private fun showAppOpenAd() {
        if (appOpenAd != null) {
            appOpenAd?.show(this)
        } else {
            Log.e(YANDEX_MOBILE_ADS_TAG, "Ad not ready to be shown")
        }
    }

    // Очистка ресурсов после закрытия рекламы
    private fun clearAppOpenAd() {
        appOpenAd?.setAdEventListener(null)
        appOpenAd = null
    }

    // Загрузка новой рекламы
    private fun loadAppOpenAd() {
        val appOpenAdLoader = AppOpenAdLoader(application)
        val AD_UNIT_ID = "R-M-13576103-1"
        val adRequestConfiguration = AdRequestConfiguration.Builder(AD_UNIT_ID).build()

        val appOpenAdLoadListener = object : AppOpenAdLoadListener {
            override fun onAdLoaded(ad: AppOpenAd) {
                this@MainActivity.appOpenAd = ad
                ad.setAdEventListener(adEventListener)
                showAppOpenAd()
                Log.d(YANDEX_MOBILE_ADS_TAG, "Ad loaded successfully")
            }

            override fun onAdFailedToLoad(error: AdRequestError) {
                Log.e(YANDEX_MOBILE_ADS_TAG, "Failed to load ad: ${error.description}")
            }
        }

        appOpenAdLoader.setAdLoadListener(appOpenAdLoadListener)
        appOpenAdLoader.loadAd(adRequestConfiguration)
    }

    private val adEventListener = object : AppOpenAdEventListener {
        override fun onAdShown() {
            Log.d(YANDEX_MOBILE_ADS_TAG, "Ad shown")
        }

        override fun onAdFailedToShow(adError: AdError) {
            Log.e(YANDEX_MOBILE_ADS_TAG, "Ad failed to show: ${adError.description}")
        }

        override fun onAdDismissed() {
            Log.d(YANDEX_MOBILE_ADS_TAG, "Ad dismissed")
            clearAppOpenAd()
        }

        override fun onAdClicked() {
            Log.d(YANDEX_MOBILE_ADS_TAG, "Ad clicked")
        }

        override fun onAdImpression(impressionData: ImpressionData?) {
            Log.d(YANDEX_MOBILE_ADS_TAG, "Ad impression recorded")
        }
    }
}
