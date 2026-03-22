package com.example.examarchive.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val topicSuggestions = listOf(
    "Photosynthesis",
    "Ohm's Law",
    "Federalism",
    "Organic Chemistry Basics",
    "Newton's Laws",
    "Electrostatics",
    "Indian History 1857",
    "Consumer Theory",
)

private data class GeneratedNote(
    val topic: String,
    val content: String,
    val generatedAt: String,
)

@Composable
fun GenerateScreen() {
    var topic by rememberSaveable { mutableStateOf("") }
    var isGenerating by rememberSaveable { mutableStateOf(false) }
    val notes = remember { mutableStateListOf<GeneratedNote>() }
    var activeNote by remember { mutableStateOf<GeneratedNote?>(null) }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboard = LocalClipboardManager.current

    fun generate() {
        val trimmed = topic.trim()
        if (trimmed.isBlank() || isGenerating) return
        keyboardController?.hide()
        isGenerating = true
        scope.launch {
            delay(1800) // Simulate generation delay
            val timestamp = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault()).format(Date())
            val note = GeneratedNote(
                topic = trimmed,
                content = buildPlaceholderContent(trimmed),
                generatedAt = timestamp,
            )
            notes.add(0, note)
            activeNote = note
            topic = ""
            isGenerating = false
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = "AI Notes Generator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Generate detailed exam study notes for any topic.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        item {
            // Input card
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = topic,
                        onValueChange = { topic = it },
                        placeholder = { Text("e.g. PHYDSC101T electrostatics, Indian Constitution federalism…") },
                        label = { Text("Topic for study notes") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { generate() }),
                        enabled = !isGenerating,
                        trailingIcon = if (isGenerating) {
                            { CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) }
                        } else null,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { generate() },
                        enabled = topic.isNotBlank() && !isGenerating,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Icon(Icons.Filled.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(if (isGenerating) "Generating…" else "Generate Notes")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Quick suggestions:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(topicSuggestions) { suggestion ->
                            SuggestionChip(
                                onClick = { topic = suggestion },
                                label = { Text(suggestion) },
                                enabled = !isGenerating,
                            )
                        }
                    }
                }
            }
        }

        // Active note viewer
        activeNote?.let { note ->
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = note.topic,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "Generated ${note.generatedAt}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            IconButton(
                                onClick = { clipboard.setText(AnnotatedString(note.content)) },
                                modifier = Modifier.size(36.dp),
                            ) {
                                Icon(Icons.Filled.ContentCopy, contentDescription = "Copy notes")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = note.content,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        )
                    }
                }
            }
        }

        // Session history (when more than one note exists)
        if (notes.size > 1) {
            item {
                Text(
                    text = "Session history",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            items(notes.drop(if (activeNote == notes.firstOrNull()) 1 else 0)) { note ->
                Card(
                    onClick = { activeNote = note },
                    shape = RoundedCornerShape(8.dp),
                    border = if (activeNote == note)
                        BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                    else
                        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = note.topic,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            text = note.generatedAt,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        // Empty state
        if (notes.isEmpty() && !isGenerating) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No notes generated yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "Enter a topic above to get started.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }
    }
}

private fun buildPlaceholderContent(topic: String): String = """
# $topic — Study Notes

## Overview
This section provides a concise introduction to $topic and its significance in the curriculum.

## Key Concepts
• **Definition** – Understand the core definition and scope.
• **Principles** – Identify the fundamental principles governing this topic.
• **Applications** – Explore real-world applications and exam-relevant scenarios.

## Important Points for Revision
1. Review all standard derivations and proofs.
2. Memorise key formulas and their conditions of applicability.
3. Practice previous year questions related to this topic.

## Common Exam Questions
- Explain the significance of $topic with suitable examples.
- Derive the key relation / theorem associated with this topic.
- Compare and contrast with related concepts.

---
*Note: This is AI-generated placeholder content. Connect a backend (e.g. Groq API) for live generation.*
""".trimIndent()
