package com.aistudio.advertisementyandexdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.aistudio.advertisementyandexdemoapp.screens.TitleScreen
import com.aistudio.advertisementyandexdemoapp.ui.theme.AdvertisementYandexDemoAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yandex.mobile.ads.common.MobileAds

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AdvertisementYandexDemoAppTheme {
                TitleScreen(modifier = Modifier)
            }

            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {

                MobileAds.initialize(this@MainActivity) {
                    Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized")
                }

                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
                onDispose {}
            }
        }
    }

    companion object {
        const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }
}

