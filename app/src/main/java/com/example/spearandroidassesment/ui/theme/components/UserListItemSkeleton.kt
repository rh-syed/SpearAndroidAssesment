package com.example.spearandroidassesment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.ShimmerBounds

@Composable
fun UserListItemSkeleton() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    val placeholderColor = Color.LightGray.copy(alpha = 0.3f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shimmer(shimmer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.small)
                .background(placeholderColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .width(120.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(placeholderColor)
            )

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(80.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(placeholderColor)
            )
        }
    }
}
