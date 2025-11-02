package com.aistudio.advertisementyandexdemoapp.navigation

import android.graphics.RenderEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aistudio.advertisementyandexdemoapp.R
import kotlin.math.PI
import kotlin.math.sin
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController



@RequiresApi(Build.VERSION_CODES.S)
private fun getRenderEffect(): RenderEffect {
    val blurEffect = RenderEffect.createBlurEffect(
        80f,
        80f,
       TileMode.MIRROR // ← правильный импорт
    )

    val colorMatrix = ColorMatrix(
        floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 50f, -5000f
        )
    )

    val alphaMatrix = RenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(colorMatrix)
    )

    return RenderEffect.createChainEffect(alphaMatrix, blurEffect)
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavigationItems.FullScreen,
        BottomNavigationItems.Video
    )

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(items = items, navController = navController)
        },
    ) { innerPadding ->
            AppNavigation(navController = navController)
    }
}


@Composable
fun CustomBottomNavigation(
    items: List<BottomNavigationItems>,
    navController: NavController
) {
    val isMenuExtended = remember { mutableStateOf(false) }

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(1000, easing = LinearEasing), label = "fab_animation"
    )
    val clickAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(400, easing = LinearEasing), label = "click_animation"
    )

    val renderEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getRenderEffect().asComposeRenderEffect()
    } else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Рисуем фон панели с вырезом с современным дизайном
        Canvas(modifier = Modifier.height(80.dp).fillMaxWidth()) {
            val width = size.width
            val height = size.height
            val fabRadius = 36.dp.toPx()
            val cutoutRadius = fabRadius + 12.dp.toPx()
            val cornerRadius = 24.dp.toPx()

            val path = Path().apply {
                moveTo(0f, height)
                lineTo(0f, cornerRadius)
                quadraticTo(0f, 0f, cornerRadius, 0f)
                lineTo(width / 2 - cutoutRadius, 0f)
                arcTo(
                    rect = Rect(
                        center = Offset(width / 2, 0f),
                        radius = cutoutRadius
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = -180f,
                    forceMoveTo = false
                )
                lineTo(width - cornerRadius, 0f)
                quadraticTo(width, 0f, width, cornerRadius)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            // Используем современный градиент вместо однотонного цвета
            drawPath(
                path = path,
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        androidx.compose.ui.graphics.Color(0xFF4A4458),
                        androidx.compose.ui.graphics.Color(0xFF2B2930)
                    )
                )
            )
        }

        // Иконки навигации с современными цветами
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 40.dp, vertical = 8.dp)
        ) {
            items.forEach { item ->
                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = androidx.compose.ui.graphics.Color(0xFF6750A4).copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                ) {
                    androidx.compose.foundation.layout.Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = when (item) {
                                is BottomNavigationItems.FullScreen -> Icons.Default.PlayArrow
                                is BottomNavigationItems.Video -> Icons.Default.PlayArrow
                                else -> Icons.Default.Info
                            },
                            contentDescription = item.title,
                            tint = androidx.compose.ui.graphics.Color(0xFFD0BCFF),
                            modifier = Modifier.size(24.dp)
                        )
                        androidx.compose.material3.Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = androidx.compose.ui.graphics.Color(0xFFEADDFF)
                        )
                    }
                }
            }
        }
        FabGroup(navController, renderEffect = renderEffect, animationProgress = fabAnimationProgress)
        FabGroup(
            navController = navController,
            renderEffect = null,
            animationProgress = fabAnimationProgress,
            toggleAnimation = { isMenuExtended.value = !isMenuExtended.value }
        )
    }
}

@Composable
fun FabGroup(
    navController: NavController,
    animationProgress: Float = 0f,
    renderEffect: androidx.compose.ui.graphics.RenderEffect? = null,
    toggleAnimation: () -> Unit = { }
) {

    Box(
        Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .graphicsLayer { this.renderEffect = renderEffect }
            .padding(bottom = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        AnimatedFab(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 72.dp,
                        end = 210.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            opacity = LinearEasing.transform(0.2f, 0.7f, animationProgress),
            onClick = {
                navController.navigate(BottomNavigationItems.Banners.route)
            }
        )

        AnimatedFab(
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 88.dp,
                ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.3f, 0.8f, animationProgress),
            onClick = {
                navController.navigate(BottomNavigationItems.Banners.route)
            }
        )

        AnimatedFab(
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 72.dp,
                    start = 210.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress),
            onClick = {
                navController.navigate(BottomNavigationItems.FullScreen.route)
            }
        )

        AnimatedFab(
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
            onClick = {
                navController.navigate(BottomNavigationItems.Video.route)
            }
        )

        AnimatedFab(
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing
                        .transform(0.35f, 0.65f, animationProgress)
                ),
            onClick = {
                toggleAnimation()
            },
        )
    }
}

@Composable
fun AnimatedFab(
    modifier: Modifier,
    opacity: Float = 1f,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(12.dp, 12.dp, 14.dp, 12.dp),
        containerColor = androidx.compose.ui.graphics.Color(0xFF6750A4),
        shape = CircleShape,
        modifier = modifier.scale(1.25f)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    brush = androidx.compose.ui.graphics.Brush.radialGradient(
                        colors = listOf(
                            androidx.compose.ui.graphics.Color(0xFF7D5260).copy(alpha = 0.3f),
                            androidx.compose.ui.graphics.Color(0xFF6750A4)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.ad_video_ic),
                contentDescription = null,
                tint = androidx.compose.ui.graphics.Color(0xFFEADDFF).copy(alpha = opacity)
            )
        }
    }
}

fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)
@Composable
@Preview(device = "id:pixel_4a", showBackground = true, backgroundColor = 0xFF3A2F6E)
private fun MainScreenPreview() {
        MainScreen()

}