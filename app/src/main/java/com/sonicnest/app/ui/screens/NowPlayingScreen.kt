package com.sonicnest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonicnest.app.ui.components.IconKind
import com.sonicnest.app.ui.components.LocalNotice
import com.sonicnest.app.ui.components.RoundButton
import com.sonicnest.app.ui.components.StatusBadge
import com.sonicnest.app.ui.components.VinylDisc
import com.sonicnest.app.ui.theme.SonicColors

@Composable
fun NowPlayingScreen(onBack: () -> Unit, onOpenSettings: () -> Unit) {
    val notice = LocalNotice.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top bar. Back is WORKING; Queue is a placeholder.
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundButton(IconKind.Back, "Back", onBack, size = 48.dp)
            Text(
                "NOW PLAYING",
                color = SonicColors.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            RoundButton(IconKind.Queue, "Queue", {
                notice("Queue management arrives in Phase 4.")
            }, size = 48.dp)
        }

        VinylDisc(Modifier.fillMaxWidth(0.72f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "No song playing",
                color = SonicColors.TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Song details appear here in Phase 4",
                color = SonicColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Disabled seek bar: honest placeholder, not interactive.
        Column(modifier = Modifier.fillMaxWidth()) {
            Slider(
                value = 0f,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("--:--", color = SonicColors.TextSecondary, fontSize = 12.sp)
                Text("--:--", color = SonicColors.TextSecondary, fontSize = 12.sp)
            }
        }

        // Main transport controls (placeholders until Phase 4).
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundButton(IconKind.Shuffle, "Shuffle", {
                notice("Shuffle arrives in Phase 4.")
            }, size = 48.dp)
            RoundButton(IconKind.Previous, "Previous song", {
                notice("Previous arrives in Phase 4.")
            }, size = 56.dp)
            RoundButton(IconKind.Play, "Play", {
                notice("Playback arrives in Phase 4.")
            }, size = 76.dp, accent = true)
            RoundButton(IconKind.Next, "Next song", {
                notice("Next arrives in Phase 4.")
            }, size = 56.dp)
            RoundButton(IconKind.Repeat, "Repeat", {
                notice("Repeat arrives in Phase 4.")
            }, size = 48.dp)
        }

        // Secondary actions.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LabeledButton(IconKind.Heart, "Favorite") {
                notice("Favorites arrive in Phase 6.")
            }
            LabeledButton(IconKind.Timer, "Sleep Timer") {
                notice("Sleep Timer arrives in Phase 5.")
            }
            LabeledButton(IconKind.Image, "Background", onOpenSettings)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusBadge(4)
            Text(
                "Controls on this screen are placeholders.",
                color = SonicColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun LabeledButton(kind: IconKind, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        RoundButton(kind, label, onClick, size = 52.dp)
        Text(label, color = SonicColors.TextSecondary, fontSize = 12.sp)
    }
}
