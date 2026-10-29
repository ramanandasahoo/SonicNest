package com.sonicnest.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonicnest.app.ui.theme.SonicColors

/** Shows a short message for features that are not built yet. Provided by SonicNestApp. */
val LocalNotice = staticCompositionLocalOf<(String) -> Unit> { { _ -> } }

enum class IconKind {
    Home, Library, Playlists, Settings, Play, Pause, Next, Previous,
    Shuffle, Repeat, Heart, Timer, Queue, Image, Back
}

private fun buildPath(block: Path.() -> Unit): Path = Path().apply(block)

@Composable
fun SonicIcon(kind: IconKind, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val sw = w * 0.09f
        val line = Stroke(width = sw, cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (kind) {
            IconKind.Play -> drawPath(
                buildPath {
                    moveTo(w * 0.25f, h * 0.12f)
                    lineTo(w * 0.88f, h * 0.5f)
                    lineTo(w * 0.25f, h * 0.88f)
                    close()
                },
                tint
            )
            IconKind.Pause -> {
                drawRoundRect(tint, Offset(w * 0.2f, h * 0.15f), Size(w * 0.22f, h * 0.7f), CornerRadius(w * 0.04f))
                drawRoundRect(tint, Offset(w * 0.58f, h * 0.15f), Size(w * 0.22f, h * 0.7f), CornerRadius(w * 0.04f))
            }
            IconKind.Next -> {
                drawPath(
                    buildPath {
                        moveTo(w * 0.15f, h * 0.2f)
                        lineTo(w * 0.68f, h * 0.5f)
                        lineTo(w * 0.15f, h * 0.8f)
                        close()
                    },
                    tint
                )
                drawRoundRect(tint, Offset(w * 0.72f, h * 0.2f), Size(w * 0.14f, h * 0.6f), CornerRadius(w * 0.03f))
            }
            IconKind.Previous -> {
                drawPath(
                    buildPath {
                        moveTo(w * 0.85f, h * 0.2f)
                        lineTo(w * 0.32f, h * 0.5f)
                        lineTo(w * 0.85f, h * 0.8f)
                        close()
                    },
                    tint
                )
                drawRoundRect(tint, Offset(w * 0.14f, h * 0.2f), Size(w * 0.14f, h * 0.6f), CornerRadius(w * 0.03f))
            }
            IconKind.Home -> {
                drawPath(
                    path = buildPath {
                        moveTo(w * 0.1f, h * 0.5f)
                        lineTo(w * 0.5f, h * 0.15f)
                        lineTo(w * 0.9f, h * 0.5f)
                    },
                    color = tint,
                    style = line
                )
                drawPath(
                    path = buildPath {
                        moveTo(w * 0.22f, h * 0.42f)
                        lineTo(w * 0.22f, h * 0.85f)
                        lineTo(w * 0.78f, h * 0.85f)
                        lineTo(w * 0.78f, h * 0.42f)
                    },
                    color = tint,
                    style = line
                )
            }
            IconKind.Library -> {
                drawRoundRect(tint, Offset(w * 0.14f, h * 0.2f), Size(w * 0.18f, h * 0.65f), CornerRadius(w * 0.03f))
                drawRoundRect(tint, Offset(w * 0.41f, h * 0.12f), Size(w * 0.18f, h * 0.73f), CornerRadius(w * 0.03f))
                drawRoundRect(tint, Offset(w * 0.68f, h * 0.25f), Size(w * 0.18f, h * 0.6f), CornerRadius(w * 0.03f))
            }
            IconKind.Playlists, IconKind.Queue -> {
                drawLine(tint, Offset(w * 0.15f, h * 0.28f), Offset(w * 0.85f, h * 0.28f), sw, StrokeCap.Round)
                drawLine(tint, Offset(w * 0.15f, h * 0.5f), Offset(w * 0.85f, h * 0.5f), sw, StrokeCap.Round)
                drawLine(tint, Offset(w * 0.15f, h * 0.72f), Offset(w * 0.55f, h * 0.72f), sw, StrokeCap.Round)
            }
            IconKind.Settings -> {
                val ys = listOf(0.25f, 0.5f, 0.75f)
                val xs = listOf(0.65f, 0.35f, 0.6f)
                for (i in ys.indices) {
                    drawLine(tint, Offset(w * 0.12f, h * ys[i]), Offset(w * 0.88f, h * ys[i]), sw, StrokeCap.Round)
                    drawCircle(tint, w * 0.11f, Offset(w * xs[i], h * ys[i]))
                }
            }
            IconKind.Shuffle -> {
                drawPath(
                    path = buildPath {
                        moveTo(w * 0.1f, h * 0.3f)
                        lineTo(w * 0.35f, h * 0.3f)
                        lineTo(w * 0.65f, h * 0.7f)
                        lineTo(w * 0.85f, h * 0.7f)
                    },
                    color = tint,
                    style = line
                )
                drawPath(
                    path = buildPath {
                        moveTo(w * 0.1f, h * 0.7f)
                        lineTo(w * 0.35f, h * 0.7f)
                        lineTo(w * 0.65f, h * 0.3f)
                        lineTo(w * 0.85f, h * 0.3f)
                    },
                    color = tint,
                    style = line
                )
            }
            IconKind.Repeat -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.12f, h * 0.25f),
                    size = Size(w * 0.76f, h * 0.5f),
                    cornerRadius = CornerRadius(w * 0.2f),
                    style = line
                )
                drawPath(
                    buildPath {
                        moveTo(w * 0.62f, h * 0.1f)
                        lineTo(w * 0.82f, h * 0.25f)
                        lineTo(w * 0.62f, h * 0.4f)
                        close()
                    },
                    tint
                )
            }
            IconKind.Heart -> drawPath(
                buildPath {
                    moveTo(w * 0.5f, h * 0.85f)
                    cubicTo(w * 0.1f, h * 0.55f, w * 0.1f, h * 0.2f, w * 0.3f, h * 0.2f)
                    cubicTo(w * 0.42f, h * 0.2f, w * 0.5f, h * 0.3f, w * 0.5f, h * 0.35f)
                    cubicTo(w * 0.5f, h * 0.3f, w * 0.58f, h * 0.2f, w * 0.7f, h * 0.2f)
                    cubicTo(w * 0.9f, h * 0.2f, w * 0.9f, h * 0.55f, w * 0.5f, h * 0.85f)
                    close()
                },
                tint
            )
            IconKind.Timer -> {
                drawCircle(tint, w * 0.34f, Offset(w * 0.5f, h * 0.56f), style = line)
                drawLine(tint, Offset(w * 0.5f, h * 0.56f), Offset(w * 0.5f, h * 0.36f), sw, StrokeCap.Round)
                drawLine(tint, Offset(w * 0.5f, h * 0.56f), Offset(w * 0.64f, h * 0.62f), sw, StrokeCap.Round)
                drawLine(tint, Offset(w * 0.4f, h * 0.1f), Offset(w * 0.6f, h * 0.1f), sw, StrokeCap.Round)
            }
            IconKind.Image -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.1f, h * 0.18f),
                    size = Size(w * 0.8f, h * 0.64f),
                    cornerRadius = CornerRadius(w * 0.08f),
                    style = line
                )
                drawCircle(tint, w * 0.07f, Offset(w * 0.7f, h * 0.38f))
                drawPath(
                    path = buildPath {
                        moveTo(w * 0.14f, h * 0.78f)
                        lineTo(w * 0.38f, h * 0.5f)
                        lineTo(w * 0.55f, h * 0.68f)
                        lineTo(w * 0.68f, h * 0.56f)
                        lineTo(w * 0.86f, h * 0.78f)
                    },
                    color = tint,
                    style = line
                )
            }
            IconKind.Back -> drawPath(
                path = buildPath {
                    moveTo(w * 0.62f, h * 0.15f)
                    lineTo(w * 0.28f, h * 0.5f)
                    lineTo(w * 0.62f, h * 0.85f)
                },
                color = tint,
                style = line
            )
        }
    }
}

/** A raised, beveled card. */
@Composable
fun SkeuoPanel(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(8.dp, shape)
            .background(
                Brush.verticalGradient(listOf(SonicColors.PanelHigh, SonicColors.Panel)),
                shape
            )
            .border(
                1.dp,
                Brush.verticalGradient(listOf(SonicColors.Highlight, SonicColors.Shadow)),
                shape
            ),
        content = content
    )
}

/** A round tactile button that visually depresses when pressed. */
@Composable
fun RoundButton(
    kind: IconKind,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    accent: Boolean = false
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val colors = when {
        accent -> listOf(SonicColors.AccentLight, SonicColors.Accent)
        pressed -> listOf(SonicColors.Panel, SonicColors.PanelHigh)
        else -> listOf(SonicColors.PanelHigh, SonicColors.Panel)
    }
    val tint = if (accent) Color(0xFF1C1200) else SonicColors.TextPrimary
    Box(
        modifier = modifier
            .size(size)
            .shadow(if (pressed) 2.dp else 8.dp, CircleShape)
            .clip(CircleShape)
            .background(Brush.verticalGradient(colors), CircleShape)
            .border(
                1.dp,
                Brush.verticalGradient(listOf(SonicColors.Highlight, SonicColors.Shadow)),
                CircleShape
            )
            .clickable(
                interactionSource = interaction,
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center
    ) {
        SonicIcon(kind, tint, Modifier.size(size * 0.42f))
    }
}

@Composable
fun PillButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = modifier
            .heightIn(min = 48.dp)
            .shadow(6.dp, shape)
            .clip(shape)
            .background(
                Brush.verticalGradient(listOf(SonicColors.AccentLight, SonicColors.Accent)),
                shape
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 28.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color(0xFF1C1200), fontWeight = FontWeight.Bold)
    }
}

/** WORKING when phase is null, otherwise shows the phase in which the feature arrives. */
@Composable
fun StatusBadge(phase: Int?, modifier: Modifier = Modifier) {
    val working = phase == null
    val color = if (working) SonicColors.Working else SonicColors.Accent
    val label = if (working) "WORKING" else "PHASE $phase"
    Text(
        text = label,
        color = color,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        modifier = modifier
            .border(1.dp, color.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

@Composable
fun FeatureRow(
    title: String,
    subtitle: String,
    phase: Int?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val notice = LocalNotice.current
    val action: () -> Unit = onClick ?: {
        if (phase != null) notice("$title arrives in Phase $phase.")
    }
    SkeuoPanel(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Button, onClick = action)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    color = SonicColors.TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    subtitle,
                    color = SonicColors.TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            StatusBadge(phase)
        }
    }
}

@Composable
fun ScreenHeader(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            title,
            color = SonicColors.TextPrimary,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            subtitle,
            color = SonicColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text.uppercase(),
        color = SonicColors.Accent,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        modifier = modifier.padding(top = 8.dp)
    )
}

/** A static vinyl disc drawn with Compose. Rotation is added in Phase 8. */
@Composable
fun VinylDisc(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val r = size.minDimension / 2f
        drawCircle(Color(0xFF0B0B0C), r, center)
        var g = 0.93f
        while (g > 0.4f) {
            drawCircle(Color(0xFF212327), r * g, center, style = Stroke(width = 1.dp.toPx()))
            g -= 0.045f
        }
        drawCircle(SonicColors.Accent, r * 0.3f, center)
        drawCircle(SonicColors.Background, r * 0.05f, center)
    }
}
