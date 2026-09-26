package dev.pol.safetyguide.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.pol.safetyguide.R
import dev.pol.safetyguide.ui.theme.CompletedGreen
import dev.pol.safetyguide.ui.theme.IncompleteGray
import dev.pol.safetyguide.ui.theme.PrepperTheme
import kotlin.math.roundToInt

@Composable
fun SquareProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Gray,
    progressColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Green,
    height: androidx.compose.ui.unit.Dp = 8.dp
) {
    val progressPercent = (progress * 100).roundToInt()
    val accessibilityText = stringResource(R.string.accessibility_progress_percent, progressPercent)
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .semantics {
                contentDescription = accessibilityText
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())

        // Draw track (gray background)
        drawRoundRect(
            color = trackColor,
            topLeft = Offset.Zero,
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = cornerRadius
        )

        // Draw progress (green portion)
        val progressWidth = canvasWidth * progress.coerceIn(0f, 1f)
        if (progressWidth > 0) {
            drawRoundRect(
                color = progressColor,
                topLeft = Offset.Zero,
                size = Size(progressWidth, canvasHeight),
                cornerRadius = cornerRadius
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 300)
@Composable
private fun SquareProgressIndicatorPreview() {
    PrepperTheme {
        SquareProgressIndicator(
            progress = 0.6f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(8.dp),
            progressColor = CompletedGreen,
            trackColor = IncompleteGray
        )
    }
}

@Preview(showBackground = true, widthDp = 300)
@Composable
private fun SquareProgressIndicatorEmptyPreview() {
    PrepperTheme {
        SquareProgressIndicator(
            progress = 0f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(8.dp),
            progressColor = CompletedGreen,
            trackColor = IncompleteGray
        )
    }
}
