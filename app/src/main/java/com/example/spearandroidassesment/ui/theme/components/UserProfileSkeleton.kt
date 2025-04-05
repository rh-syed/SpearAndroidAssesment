package com.example.spearandroidassesment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun UserProfileSkeleton() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    val placeholderColor = Color.LightGray.copy(alpha = 0.3f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .shimmer(shimmerInstance),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(placeholderColor)
        )

        repeat(4) {
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .width(if (it == 0) 120.dp else 180.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(placeholderColor)
            )
        }
    }
}
