package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowingButton
import com.example.ui.components.SecondaryGlowingButton
import com.example.ui.components.StatCard
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonRose
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.Locale

@Composable
fun ResultsScreen(
    scoreXp: Int,
    correctCount: Int,
    totalCount: Int,
    timeSpentSeconds: Int,
    maxCombo: Int,
    leveledUp: Boolean,
    newLevel: Int,
    newAchievements: List<String>,
    onPlayAgain: () -> Unit,
    onPractice: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accuracy = if (totalCount > 0) ((correctCount.toFloat() / totalCount) * 100).toInt() else 0
    val minutes = timeSpentSeconds / 60
    val seconds = timeSpentSeconds % 60
    val timeFormatted = String.format(Locale.US, "%02d:%02d", minutes, seconds)

    LaunchedEffect(Unit) {
        if (leveledUp) {
            SoundManager.playLevelUp()
        } else if (accuracy >= 70) {
            SoundManager.playCorrect()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "levelup_glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Header Banner
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(ElectricPurple.copy(alpha = 0.3f), Color.Transparent)
                        )
                    )
                    .border(1.dp, ElectricPurple.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "⚔️ BATTLE COMPLETE",
                    style = MaterialTheme.typography.headlineLarge,
                    color = BrightCyan,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp
                )
            }
        }

        // Level Up Banner if leveled up!
        if (leveledUp) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(NeonGold.copy(alpha = 0.3f), ElectricPurple.copy(alpha = 0.3f))
                            )
                        )
                        .border(2.dp, NeonGold, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🎉 LEVEL UP! 🎉",
                            color = NeonGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You reached Level $newLevel! Master tier rank increased.",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Main Score & XP Banner
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowEffect = true,
                borderColor = BrightCyan
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF141F3C), Color(0xFF0F1528))
                            )
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TOTAL REWARD",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "+$scoreXp XP",
                        color = NeonGold,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (accuracy >= 80) "Exceptional Performance! 🌟" else if (accuracy >= 50) "Solid Battle! Keep leveling up. 👍" else "Study harder and try again! 📚",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Stats Matrix
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "Accuracy",
                    value = "$accuracy%",
                    icon = "🎯",
                    accentColor = if (accuracy >= 70) CorrectGreen else NeonGold,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Correct",
                    value = "$correctCount / $totalCount",
                    icon = "✓",
                    accentColor = BrightCyan,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "Time Taken",
                    value = timeFormatted,
                    icon = "⏱️",
                    accentColor = ElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Max Combo",
                    value = "${maxCombo}x",
                    icon = "🔥",
                    accentColor = NeonRose,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // New Achievements unlocked banner
        if (newAchievements.isNotEmpty()) {
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = NeonGold
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🏆", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "NEW ACHIEVEMENT UNLOCKED!",
                                color = NeonGold,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Check your Profile to view your newly unlocked combat badges.",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Action Buttons
        item {
            Spacer(modifier = Modifier.height(8.dp))
            GlowingButton(
                text = "PLAY AGAIN",
                icon = "🔄",
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth(),
                testTag = "results_play_again"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SecondaryGlowingButton(
                    text = "PRACTICE",
                    icon = "📖",
                    onClick = onPractice,
                    modifier = Modifier.weight(1f),
                    testTag = "results_practice"
                )

                SecondaryGlowingButton(
                    text = "BACK TO HOME",
                    icon = "🏠",
                    onClick = onBackToHome,
                    modifier = Modifier.weight(1f),
                    borderColor = ElectricPurple,
                    testTag = "results_back_to_home"
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
