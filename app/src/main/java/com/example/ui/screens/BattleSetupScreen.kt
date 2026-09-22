package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundManager
import com.example.data.repository.QuestionBank
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowingButton
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonRose
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun BattleSetupScreen(
    initialSubject: String? = null,
    onLaunchBattle: (subject: String, difficulty: String, questionCount: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubject by remember { mutableStateOf(initialSubject ?: "All Subjects") }
    var selectedDifficulty by remember { mutableStateOf("Medium") }
    var selectedQuestionCount by remember { mutableIntStateOf(10) }

    var isCountdownActive by remember { mutableStateOf(false) }
    var countdownStep by remember { mutableIntStateOf(3) }

    val subjects = listOf("All Subjects") + QuestionBank.SUBJECTS
    val difficulties = listOf("Easy", "Medium", "Hard", "Extreme")
    val questionCounts = listOf(5, 10, 20)

    // Countdown effect
    LaunchedEffect(isCountdownActive) {
        if (isCountdownActive) {
            countdownStep = 3
            SoundManager.playTick()
            delay(900)
            countdownStep = 2
            SoundManager.playTick()
            delay(900)
            countdownStep = 1
            SoundManager.playTick()
            delay(900)
            countdownStep = 0 // "BATTLE!"
            SoundManager.playLevelUp()
            delay(600)
            onLaunchBattle(selectedSubject, selectedDifficulty, selectedQuestionCount)
        }
    }

    if (isCountdownActive) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BgDarkNavy),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "BATTLE STARTING...",
                    color = BrightCyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(24.dp))

                AnimatedContent(
                    targetState = countdownStep,
                    transitionSpec = {
                        (scaleIn(animationSpec = tween(300)) + fadeIn()) togetherWith
                                (scaleOut(animationSpec = tween(300)) + fadeOut())
                    },
                    label = "countdown_anim"
                ) { step ->
                    if (step > 0) {
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(ElectricPurple.copy(alpha = 0.6f), Color.Transparent)
                                    )
                                )
                                .border(3.dp, BrightCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$step",
                                color = TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 64.sp
                            )
                        }
                    } else {
                        Text(
                            text = "⚔️ BATTLE!",
                            color = NeonGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 44.sp,
                            letterSpacing = 2.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "$selectedSubject • $selectedDifficulty • $selectedQuestionCount Questions",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(BgDarkNavy)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚔️", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "BATTLE ARENA SETUP",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Select your discipline, difficulty, and battle length.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }

            // Subject Selection
            item {
                Text(
                    text = "SELECT SUBJECT",
                    color = BrightCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    subjects.forEach { subject ->
                        val isSelected = selectedSubject == subject
                        val icon = when (subject) {
                            "Mathematics" -> "📐"
                            "Physics" -> "⚛️"
                            "Chemistry" -> "🧪"
                            "Biology" -> "🧬"
                            "English" -> "📖"
                            "Social Studies" -> "🌍"
                            "ICT / Computer Science" -> "💻"
                            else -> "⚡"
                        }

                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    SoundManager.playClick()
                                    selectedSubject = subject
                                }
                                .testTag("select_subject_$subject"),
                            borderColor = if (isSelected) BrightCyan else SurfaceBorder,
                            glowEffect = isSelected
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (isSelected) SurfaceDarkElevated else SurfaceDark)
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = icon, fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = subject,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) TextPrimary else TextSecondary,
                                        fontSize = 14.sp
                                    )
                                }

                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(BrightCyan),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("✓", color = BgDarkNavy, fontWeight = FontWeight.Black, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Difficulty Selection
            item {
                Text(
                    text = "SELECT DIFFICULTY",
                    color = BrightCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    difficulties.forEach { diff ->
                        val isSelected = selectedDifficulty == diff
                        val (color, xpLabel) = when (diff) {
                            "Easy" -> Pair(NeonCyan, "+50 XP")
                            "Medium" -> Pair(ElectricBlue, "+75 XP")
                            "Hard" -> Pair(ElectricPurple, "+100 XP")
                            else -> Pair(NeonRose, "+150 XP")
                        }

                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    SoundManager.playClick()
                                    selectedDifficulty = diff
                                }
                                .testTag("select_diff_$diff"),
                            borderColor = if (isSelected) color else SurfaceBorder
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (isSelected) color.copy(alpha = 0.15f) else SurfaceDark)
                                    .padding(vertical = 12.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = diff,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) color else TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = xpLabel,
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Question Count Selection
            item {
                Text(
                    text = "NUMBER OF QUESTIONS",
                    color = BrightCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    questionCounts.forEach { count ->
                        val isSelected = selectedQuestionCount == count
                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    SoundManager.playClick()
                                    selectedQuestionCount = count
                                }
                                .testTag("select_count_$count"),
                            borderColor = if (isSelected) BrightCyan else SurfaceBorder
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (isSelected) SurfaceDarkElevated else SurfaceDark)
                                    .padding(vertical = 14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "$count",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = if (isSelected) BrightCyan else TextPrimary
                                )
                                Text(
                                    text = "Questions",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Launch Button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                GlowingButton(
                    text = "ENTER BATTLE ARENA",
                    icon = "⚔️",
                    onClick = {
                        isCountdownActive = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "enter_battle_arena_btn"
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
