package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundManager
import com.example.data.model.UserProgress
import com.example.data.repository.QuestionBank
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowingButton
import com.example.ui.components.LevelXPBar
import com.example.ui.components.SecondaryGlowingButton
import com.example.ui.components.StatCard
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BgDeepBlue
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.FireOrange
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonRose
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderGlow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    userProgress: UserProgress,
    onStartBattle: (subject: String?) -> Unit,
    onStartPractice: (subject: String?) -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSoundOn by remember { mutableStateOf(SoundManager.isSoundEnabled) }
    var showNotificationDialog by remember { mutableStateOf(false) }

    val todayStr = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
    val isDailyCompleted = userProgress.dailyChallengeCompletedDate == todayStr

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Top App Bar / Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⚔️ STUDY",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            color = TextPrimary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BATTLE",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            color = BrightCyan,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Study Hard. Battle Smart. Level Up.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Sound toggle
                    Box(
                        modifier = Modifier
                            .testTag("sound_toggle")
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceDark)
                            .border(1.dp, if (isSoundOn) BrightCyan else SurfaceBorder, CircleShape)
                            .clickable {
                                isSoundOn = !isSoundOn
                                SoundManager.isSoundEnabled = isSoundOn
                                SoundManager.playClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (isSoundOn) "🔊" else "🔇", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Notification button
                    Box(
                        modifier = Modifier
                            .testTag("notification_btn")
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceDark)
                            .border(1.dp, SurfaceBorder, CircleShape)
                            .clickable {
                                SoundManager.playClick()
                                showNotificationDialog = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🔔", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Profile quick button
                    Box(
                        modifier = Modifier
                            .testTag("profile_avatar_header")
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(ElectricPurple, ElectricBlue)))
                            .border(1.5.dp, BrightCyan, CircleShape)
                            .clickable {
                                SoundManager.playClick()
                                onOpenProfile()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = userProgress.avatarEmoji, fontSize = 18.sp)
                    }
                }
            }
        }

        // Hero Section
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowEffect = true,
                borderColor = ElectricPurple.copy(alpha = 0.6f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF1E103C).copy(alpha = 0.85f),
                                    Color(0xFF0F1E44).copy(alpha = 0.85f),
                                    Color(0xFF090D1E)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BrightCyan.copy(alpha = 0.15f))
                                .border(1.dp, BrightCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("⚡ ARENA OPEN", color = BrightCyan, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "READY FOR BATTLE?",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Test your knowledge. Earn XP. Climb the leaderboard.",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            GlowingButton(
                                text = "START BATTLE",
                                icon = "⚔️",
                                onClick = { onStartBattle(null) },
                                modifier = Modifier.weight(1f),
                                testTag = "home_start_battle"
                            )

                            SecondaryGlowingButton(
                                text = "PRACTICE",
                                icon = "📖",
                                onClick = { onStartPractice(null) },
                                modifier = Modifier.weight(0.9f),
                                testTag = "home_start_practice"
                            )
                        }
                    }
                }
            }
        }

        // Stats Section
        item {
            Text(
                text = "PLAYER STATS",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "XP",
                    value = "${userProgress.xp}",
                    icon = "⚡",
                    accentColor = BrightCyan,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Level",
                    value = "${userProgress.level}",
                    icon = "⭐",
                    accentColor = NeonGold,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Streak",
                    value = "${userProgress.streak} 🔥",
                    icon = "🔥",
                    accentColor = FireOrange,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "Battles Won",
                    value = "${userProgress.battlesWon}",
                    icon = "🏆",
                    accentColor = ElectricPurple,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Accuracy",
                    value = "${userProgress.overallAccuracy}%",
                    icon = "🎯",
                    accentColor = CorrectGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Level Progress
        item {
            LevelXPBar(
                level = userProgress.level,
                currentXp = userProgress.xp,
                progressFraction = userProgress.levelProgressFraction,
                nextLevelXp = userProgress.nextLevelTargetXp
            )
        }

        // Daily Challenge Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = if (isDailyCompleted) CorrectGreen else NeonGold
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isDailyCompleted) {
                                Brush.horizontalGradient(listOf(Color(0xFF07271E), Color(0xFF0F1E2A)))
                            } else {
                                Brush.horizontalGradient(listOf(Color(0xFF261908), Color(0xFF141A2E)))
                            }
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🎯", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "DAILY CHALLENGE",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = if (isDailyCompleted) CorrectGreen else NeonGold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isDailyCompleted) CorrectGreen.copy(alpha = 0.2f) else NeonGold.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (isDailyCompleted) "COMPLETED ✓" else "+500 XP",
                                fontWeight = FontWeight.Bold,
                                color = if (isDailyCompleted) CorrectGreen else NeonGold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Complete 10 questions with 80%+ accuracy.",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!isDailyCompleted) {
                        GlowingButton(
                            text = "START CHALLENGE",
                            icon = "⚡",
                            onClick = { onStartBattle("All Subjects") },
                            gradientColors = listOf(NeonGold, FireOrange),
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "start_daily_challenge"
                        )
                    } else {
                        Text(
                            text = "Great job! Daily reward claimed. Next challenge refreshes tomorrow.",
                            color = CorrectGreen,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Streak System Info
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = FireOrange.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "${userProgress.streak} DAY STREAK",
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Next milestone: 14 Days (+700 XP)",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FireOrange.copy(alpha = 0.15f))
                            .border(1.dp, FireOrange.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("ACTIVE 🔥", color = FireOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Subjects Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SUBJECTS & MASTERY",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "7 Disciplines",
                    color = BrightCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        items(QuestionBank.SUBJECTS) { subject ->
            val stat = userProgress.subjectsData[subject]
            val completed = stat?.completedQuestions ?: 0
            val percent = stat?.progressPercentage ?: 0

            val icon = when (subject) {
                "Mathematics" -> "📐"
                "Physics" -> "⚛️"
                "Chemistry" -> "🧪"
                "Biology" -> "🧬"
                "English" -> "📖"
                "Social Studies" -> "🌍"
                "ICT / Computer Science" -> "💻"
                else -> "📚"
            }

            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("subject_card_$subject")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceDarkElevated)
                                    .border(1.dp, SurfaceBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = icon, fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = subject,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "$completed Questions Mastered",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Text(
                            text = "$percent%",
                            fontWeight = FontWeight.ExtraBold,
                            color = BrightCyan,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF0F1426))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(percent / 100f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(ElectricPurple, BrightCyan)
                                    )
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SecondaryGlowingButton(
                            text = "PRACTICE",
                            icon = "▶",
                            onClick = { onStartPractice(subject) },
                            modifier = Modifier.height(38.dp),
                            testTag = "practice_sub_$subject"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        GlowingButton(
                            text = "BATTLE",
                            icon = "⚔️",
                            onClick = { onStartBattle(subject) },
                            modifier = Modifier.height(38.dp),
                            testTag = "battle_sub_$subject"
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            containerColor = SurfaceDark,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔔", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Battle Field Dispatch", color = TextPrimary, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• Season 1 Ranked Battles are LIVE! Climb to Top 3 for Golden Avatar frames.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• Streak bonus active: Complete 1 battle daily to earn up to +1500 bonus XP.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• ICT & Physics tournaments scheduled this weekend.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotificationDialog = false }) {
                    Text("DISMISS", color = BrightCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
