package com.example.examarchive.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private enum class ThemeOption(val label: String, val icon: ImageVector) {
    System("System", Icons.Filled.BrightnessAuto),
    Light("Light", Icons.Filled.BrightnessHigh),
    Dark("Dark", Icons.Filled.Brightness4),
}

@Composable
fun SettingsScreen() {
    var selectedTheme by rememberSaveable { mutableStateOf(ThemeOption.System.name) }
    var dsc by rememberSaveable { mutableStateOf("") }
    var dsm1 by rememberSaveable { mutableStateOf("") }
    var dsm2 by rememberSaveable { mutableStateOf("") }
    var sec by rememberSaveable { mutableStateOf("") }
    var idc by rememberSaveable { mutableStateOf("") }
    var aec by rememberSaveable { mutableStateOf("") }
    var vac by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }

        // Theme preference
        item {
            SettingsCard(
                title = "Theme",
                subtitle = "Choose how ExamArchive looks.",
                icon = Icons.Filled.BrightnessAuto,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    ThemeOption.entries.forEachIndexed { index, option ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = ThemeOption.entries.size,
                            ),
                            onClick = { selectedTheme = option.name },
                            selected = selectedTheme == option.name,
                            icon = {
                                Icon(
                                    imageVector = option.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text(option.label)
                        }
                    }
                }
            }
        }

        // Course preferences
        item {
            SettingsCard(
                title = "Course Preferences",
                subtitle = "Personalise Library and Generate screens with your subjects.",
                icon = Icons.Filled.School,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                CourseField(label = "DSC (Discipline Specific Core)", value = dsc, onValueChange = { dsc = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "DSM 1 (Discipline Specific Minor)", value = dsm1, onValueChange = { dsm1 = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "DSM 2", value = dsm2, onValueChange = { dsm2 = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "SEC (Skill Enhancement Course)", value = sec, onValueChange = { sec = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "IDC (Interdisciplinary Course)", value = idc, onValueChange = { idc = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "AEC (Ability Enhancement Course)", value = aec, onValueChange = { aec = it })
                Spacer(modifier = Modifier.height(8.dp))
                CourseField(label = "VAC (Value Added Course)", value = vac, onValueChange = { vac = it })
            }
        }

        // About
        item {
            SettingsCard(
                title = "About",
                subtitle = "ExamArchive — community exam paper archive.",
                icon = Icons.Filled.Info,
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(8.dp))
                AboutRow(label = "Version", value = "1.0.0")
                AboutRow(label = "Platform", value = "Android (Jetpack Compose)")
                AboutRow(label = "Backend", value = "Appwrite Cloud")
                AboutRow(label = "AI", value = "Groq — multi-model fallback")
            }
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            content()
        }
    }
}

@Composable
private fun CourseField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
private fun AboutRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
        )
    }
}
