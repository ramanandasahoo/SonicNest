package com.sonicnest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sonicnest.app.R
import com.sonicnest.app.ui.components.FeatureRow
import com.sonicnest.app.ui.components.PillButton
import com.sonicnest.app.ui.components.ScreenHeader
import com.sonicnest.app.ui.components.SectionTitle
import com.sonicnest.app.ui.components.SkeuoPanel
import com.sonicnest.app.ui.components.VinylDisc
import com.sonicnest.app.ui.theme.SonicColors

@Composable
fun HomeScreen(onOpenNowPlaying: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ScreenHeader(
            title = stringResource(R.string.app_name),
            subtitle = "Your music, offline."
        )

        SkeuoPanel(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VinylDisc(Modifier.fillMaxWidth(0.55f))
                Text(
                    "Nothing playing yet",
                    color = SonicColors.TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Music playback arrives in Phase 4.",
                    color = SonicColors.TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                PillButton("Open Now Playing", onOpenNowPlaying)
            }
        }

        SectionTitle("Build progress")
        FeatureRow("Navigation and theme", "Bottom bar, screens, dark skeuomorphic style", null)
        FeatureRow("Music library", "Find songs stored on your phone", 3)
        FeatureRow("Playback engine", "Background playback, notification, lock screen", 4)
        FeatureRow("Sleep Timer", "Pause music automatically", 5)
        FeatureRow("Playlists and favorites", "Saved on your device", 6)
        FeatureRow("Custom backgrounds and themes", "Use your own images", 7)
    }
}
