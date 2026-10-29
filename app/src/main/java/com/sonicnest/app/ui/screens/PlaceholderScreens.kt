
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
import androidx.compose.ui.unit.dp
import com.sonicnest.app.ui.components.FeatureRow
import com.sonicnest.app.ui.components.LocalNotice
import com.sonicnest.app.ui.components.PillButton
import com.sonicnest.app.ui.components.ScreenHeader
import com.sonicnest.app.ui.components.SkeuoPanel
import com.sonicnest.app.ui.theme.SonicColors

@Composable
fun LibraryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ScreenHeader(
            "Library",
            "Placeholder sections. No songs are scanned yet."
        )

        FeatureRow("All songs", "Every audio file on your phone", 3)
        FeatureRow("Albums", "Grouped by album", 3)
        FeatureRow("Artists", "Grouped by artist", 3)
        FeatureRow("Folders", "Where Android allows it", 3)
        FeatureRow("Recently played", "Your listening history", 6)
        FeatureRow("Most played", "Your favorites by play count", 6)
        FeatureRow("Favorites", "Songs you have hearted", 6)
    }
}

@Composable
fun PlaylistsScreen() {
    val notice = LocalNotice.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenHeader(
            "Playlists",
            "Placeholder. Playlists are saved in Phase 6."
        )

        SkeuoPanel(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "No playlists yet",
                    color = SonicColors.TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Create, rename and delete playlists here.",
                    color = SonicColors.TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                PillButton(
                    text = "Create playlist",
                    onClick = {
                        notice("Playlists arrive in Phase 6.")
                    }
                )
            }
        }
    }
}
