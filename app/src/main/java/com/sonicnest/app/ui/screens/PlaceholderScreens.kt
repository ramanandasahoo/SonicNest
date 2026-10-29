
package com.sonicnest.app.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.sonicnest.app.data.AudioTrack
import com.sonicnest.app.data.MediaStoreAudioScanner
import com.sonicnest.app.ui.components.LocalNotice
import com.sonicnest.app.ui.components.PillButton
import com.sonicnest.app.ui.components.ScreenHeader
import com.sonicnest.app.ui.components.SkeuoPanel
import com.sonicnest.app.ui.theme.SonicColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private fun audioPermission(): String {
    return if (Build.VERSION.SDK_INT >= 33) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
}

@Composable
fun LibraryScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tracks = remember { mutableStateListOf<AudioTrack>() }

    var loading by remember { mutableStateOf(false) }
    var scanned by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val scanLibrary: () -> Unit = {
        loading = true
        message = null

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    MediaStoreAudioScanner.scan(
                        context.contentResolver
                    )
                }
            }

            result.fold(
                onSuccess = { songs ->
                    tracks.clear()
                    tracks.addAll(songs)
                    scanned = true
                    if (songs.isEmpty()) {
                        message = "No music found. Add audio files to your phone and scan again."
                    }
                },
                onFailure = {
                    message = "Could not scan music. Please try again."
                }
            )

            loading = false
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                scanLibrary()
            } else {
                message = "Audio permission is needed to display your local music."
            }
        }

    fun requestPermissionAndScan() {
        val permission = audioPermission()
        val granted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            scanLibrary()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ScreenHeader(
            "Library",
            "Music stored locally on your phone"
        )

        SkeuoPanel(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${tracks.size} songs",
                    style = MaterialTheme.typography.titleMedium,
                    color = SonicColors.TextPrimary
                )

                Button(
                    onClick = { requestPermissionAndScan() },
                    enabled = !loading
                ) {
                    Text(
                        if (loading) "Scanning..." else "Scan my music"
                    )
                }

                if (loading) {
                    CircularProgressIndicator()
                }

                message?.let {
                    Text(
                        text = it,
                        color = SonicColors.TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (!scanned && !loading) {
                    Text(
                        text = "Tap Scan my music and allow audio access when asked.",
                        color = SonicColors.TextSecondary
                    )
                }
            }
        }

        if (tracks.isNotEmpty()) {
            Text(
                text = "ALL SONGS",
                style = MaterialTheme.typography.titleMedium,
                color = SonicColors.Accent
            )

            tracks.forEach { track ->
                SkeuoPanel(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = track.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = SonicColors.TextPrimary
                        )

                        Text(
                            text = "${track.artist} • ${track.album}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SonicColors.TextSecondary
                        )

                        Text(
                            text = formatDuration(track.durationMs),
                            style = MaterialTheme.typography.bodyMedium,
                            color = SonicColors.Accent
                        )
                    }
                }
            }
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
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
            "Playlists will be saved on your device in Phase 6."
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
