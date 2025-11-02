package com.aistudio.advertisementyandexdemoapp.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.template.NativeBannerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NativeAdvertisementScreen(modifier: Modifier) {

    val native = NativeAdLoader(LocalContext.current)
    val nativeSecond = NativeAdLoader(LocalContext.current)

    val adFoxParameters = mapOf(
        "adf_ownerid" to "270901",
        "adf_p1" to "cqtgi",
        "adf_p2" to "fksh"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
        ) {
            // Заголовок
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    )
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Нативная реклама",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Реклама, интегрированная в контент приложения",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Нативная реклама 1
            item {
                NativeAdCard(
                    title = "Нативный баннер 1",
                    description = "AdFox интеграция с параметрами"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = { context: Context ->
                            val request = NativeAdRequestConfiguration
                                .Builder("R-M-2130972-2")
                                .setParameters(adFoxParameters)
                                .setShouldLoadImagesAutomatically(true)
                                .build()
                            native.loadAd(request)

                            NativeBannerView(context).apply {
                                native.setNativeAdLoadListener(object : NativeAdLoadListener {
                                    override fun onAdLoaded(nativeAd: NativeAd) {
                                        Log.i("Native Load", "Native ad loaded")
                                        setAd(nativeAd)
                                    }

                                    override fun onAdFailedToLoad(error: AdRequestError) {
                                        Log.i(
                                            "Native_Error",
                                            "Native ad failed to load with code ${error.code}: ${error.description}"
                                        )
                                    }
                                })
                            }
                        }
                    )
                }
            }

            // Нативная реклама 2
            item {
                NativeAdCard(
                    title = "Нативный баннер 2",
                    description = "Дополнительный нативный блок"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = { context: Context ->
                            val requestSecond = NativeAdRequestConfiguration
                                .Builder("R-M-2130972-8")
                                .setParameters(adFoxParameters)
                                .setShouldLoadImagesAutomatically(true)
                                .build()
                            nativeSecond.loadAd(requestSecond)

                            NativeBannerView(context).apply {
                                nativeSecond.setNativeAdLoadListener(object : NativeAdLoadListener {
                                    override fun onAdLoaded(nativeAd: NativeAd) {
                                        Log.i("Native LoadSecond", "NativeSecond ad loaded")
                                        setAd(nativeAd)
                                    }

                                    override fun onAdFailedToLoad(error: AdRequestError) {
                                        Log.i(
                                            "NativeSecond Error",
                                            "Native ad failed to load with code ${error.code}: ${error.description}"
                                        )
                                    }
                                })
                            }
                        }
                    )
                }
            }

            // Информационная карточка
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "💡 Особенности нативной рекламы",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Нативная реклама органично вписывается в контент приложения и обеспечивает лучший пользовательский опыт. Она выглядит как часть интерфейса и менее навязчива.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NativeAdCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}