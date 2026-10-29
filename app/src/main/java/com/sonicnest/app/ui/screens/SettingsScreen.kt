package com.sonicnest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sonicnest.app.ui.components.FeatureRow
import com.sonicnest.app.ui.components.ScreenHeader
import com.sonicnest.app.ui.components.SectionTitle

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val versionName = remember {
        runCatching {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }.getOrNull() ?: "unknown"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ScreenHeader("Settings", "Most options below are placeholders.")

        SectionTitle("Appearance")
        FeatureRow("Theme", "Dark, light and pure-black AMOLED", 7)
        FeatureRow("Choose background", "Pick an image from your phone", 7)
        FeatureRow("Background appearance", "Dimming, blur, opacity, placement", 7)
        FeatureRow("Accent color", "Choose your highlight color", 7)

        SectionTitle("Playback")
        FeatureRow("Sleep Timer preferences", "Default durations and behavior", 5)
        FeatureRow("Default playback options", "Shuffle, repeat, speed", 4)

        SectionTitle("Library")
        FeatureRow("Refresh library", "Scan your phone for new songs", 3)
        FeatureRow("Playback history", "View or clear history", 6)
        FeatureRow("Reset preferences", "Restore default settings", 7)

        SectionTitle("About")
        FeatureRow("SonicNest", "Version $versionName (foundation build)", null)
    }
}
