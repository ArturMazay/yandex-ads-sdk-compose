package com.aistudio.advertisementyandexdemoapp.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerScreen(modifier: Modifier) {
    val context = LocalContext.current
    val bannerAdView = BannerAdView(context)
    val bannerAdViewSecond = BannerAdView(context)
    val bannerAdViewThird = BannerAdView(context)
    val bannerAdViewFour = BannerAdView(context)

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
            // Заголовок с градиентом
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
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
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
                                text = "Баннерная реклама",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Коллекция баннеров различных форматов",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Баннер 1
            item {
                BannerCard(
                    title = "Баннер 400x200",
                    description = "Крупный рекламный баннер"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = {
                            bannerAdView.apply {
                                setAdUnitId("R-M-2130972-1")
                                setAdSize(BannerAdSize.fixedSize(context = context, width = 400, height = 200))
                                val request = AdRequest.Builder().build()
                                loadAd(request)
                            }
                        }
                    )
                }
            }

            // Баннер 2
            item {
                BannerCard(
                    title = "Баннер 300x200",
                    description = "Средний рекламный баннер"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = {
                            bannerAdViewSecond.apply {
                                setAdUnitId("R-M-2130972-5")
                                setAdSize(BannerAdSize.fixedSize(context = context, width = 300, height = 200))
                                val requestSecond = AdRequest.Builder().build()
                                loadAd(requestSecond)
                            }
                        }
                    )
                }
            }

            // Баннер 3
            item {
                BannerCard(
                    title = "Sticky баннер",
                    description = "Адаптивный баннер с липким размером"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = {
                            bannerAdViewThird.apply {
                                setAdUnitId("R-M-2130972-6")
                                setAdSize(BannerAdSize.stickySize(context = context, 400))
                                val requestThird = AdRequest.Builder().build()
                                loadAd(requestThird)
                            }
                        }
                    )
                }
            }

            // Баннер 4
            item {
                BannerCard(
                    title = "Sticky баннер 2",
                    description = "Дополнительный адаптивный баннер"
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        factory = {
                            bannerAdViewFour.apply {
                                setAdUnitId("R-M-2130972-7")
                                setAdSize(BannerAdSize.stickySize(context = context, 400))
                                val requestFour = AdRequest.Builder().build()
                                loadAd(requestFour)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BannerCard(
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
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
                }
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(40.dp)
                        .width(2.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

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