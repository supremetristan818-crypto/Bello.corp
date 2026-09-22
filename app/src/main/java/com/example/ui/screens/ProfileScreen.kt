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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundManager
import com.example.data.model.Achievement
import com.example.data.model.UserProgress
import com.example.data.repository.QuestionBank
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowingButton
import com.example.ui.components.LevelXPBar
import com.example.ui.components.SecondaryGlowingButton
import com.example.ui.components.StatCard
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.FireOrange
import com.example.ui.theme.NeonGold
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderGlow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    userProgress: UserProgress,
    achievements: List<Achievement>,
    onUpdateProfile: (username: String, email: String, avatar: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditDialog by remember { mutableStateOf(false) }

    var editUsername by remember { mutableStateOf(userProgress.username) }
    var editEmail by remember { mutableStateOf(userProgress.email) }
    var editAvatar by remember { mutableStateOf(userProgress.avatarEmoji) }

    val availableAvatars = listOf("⚡", "🔥", "🧠", "⚔️", "👑", "🛡️", "🌌", "🎯", "💻", "🧬", "🌸", "🚀")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "STUDENT PROFILE",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )

                SecondaryGlowingButton(
                    text = "EDIT",
                    icon = "✏️",
                    onClick = {
                        editUsername = userProgress.username
                        editEmail = userProgress.email
                        editAvatar = userProgress.avatarEmoji
                        showEditDialog = true
                    },
                    modifier = Modifier.height(38.dp),
                    testTag = "edit_profile_btn"
                )
            }
        }

        // Profile Avatar Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowEffect = true,
                borderColor = SurfaceBorderGlow
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF1B1538), Color(0xFF0F1528))
                            )
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(ElectricPurple, ElectricBlue)))
                            .border(2.5.dp, BrightCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = userProgress.avatarEmoji, fontSize = 40.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = userProgress.username,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = userProgress.email,
                        color = TextSecondary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(NeonGold.copy(alpha = 0.15f))
                            .border(1.dp, NeonGold.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Level ${userProgress.level} Scholar • Elite Rank",
                            color = NeonGold,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Stats Matrix
        item {
            Text(
                text = "COMBAT CAREER STATS",
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
                    label = "Total XP",
                    value = "${userProgress.xp}",
                    icon = "⚡",
                    accentColor = BrightCyan,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Study Streak",
                    value = "${userProgress.streak} Days",
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
                    value = "${userProgress.battlesWon} / ${userProgress.battlesPlayed}",
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

        // Level Progress Bar
        item {
            LevelXPBar(
                level = userProgress.level,
                currentXp = userProgress.xp,
                progressFraction = userProgress.levelProgressFraction,
                nextLevelXp = userProgress.nextLevelTargetXp
            )
        }

        // Subject Mastery Bars
        item {
            Text(
                text = "SUBJECT MASTERY",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    QuestionBank.SUBJECTS.forEach { subject ->
                        val stat = userProgress.subjectsData[subject]
                        val completed = stat?.completedQuestions ?: 0
                        val percent = stat?.progressPercentage ?: 0

                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = subject,
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "$percent% ($completed answered)",
                                    color = BrightCyan,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
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
                                            Brush.horizontalGradient(listOf(ElectricPurple, BrightCyan))
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Achievements Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ACHIEVEMENTS & BADGES",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                val unlockedCount = achievements.count { it.isUnlocked }
                Text(
                    text = "$unlockedCount / ${achievements.size} Unlocked",
                    color = NeonGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                achievements.forEach { achievement ->
                    val isUnlocked = achievement.isUnlocked
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = if (isUnlocked) NeonGold.copy(alpha = 0.6f) else SurfaceBorder.copy(alpha = 0.4f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isUnlocked) SurfaceDarkElevated else SurfaceDark.copy(alpha = 0.5f))
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isUnlocked) NeonGold.copy(alpha = 0.2f) else SurfaceDarkElevated)
                                    .border(
                                        1.dp,
                                        if (isUnlocked) NeonGold else SurfaceBorder,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isUnlocked) achievement.icon else "🔒",
                                    fontSize = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = achievement.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isUnlocked) TextPrimary else TextMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = achievement.description,
                                    fontSize = 11.sp,
                                    color = if (isUnlocked) TextSecondary else TextMuted
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isUnlocked) CorrectGreen.copy(alpha = 0.2f) else Color.Transparent)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isUnlocked) "+${achievement.xpReward} XP" else "LOCKED",
                                    color = if (isUnlocked) CorrectGreen else TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    // Edit Profile Dialog
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = SurfaceDark,
            title = {
                Text(
                    text = "Edit Combat Profile",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("CHOOSE AVATAR", color = BrightCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(availableAvatars) { avatar ->
                            val isSelected = editAvatar == avatar
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) ElectricPurple else SurfaceDarkElevated)
                                    .border(1.5.dp, if (isSelected) BrightCyan else SurfaceBorder, CircleShape)
                                    .clickable { editAvatar = avatar }
                                    .testTag("avatar_option_$avatar"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = avatar, fontSize = 20.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = editUsername,
                        onValueChange = { editUsername = it },
                        label = { Text("Callsign / Username") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrightCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("edit_username_input")
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email Address") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrightCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("edit_email_input")
                    )
                }
            },
            confirmButton = {
                GlowingButton(
                    text = "SAVE CHANGES",
                    onClick = {
                        onUpdateProfile(editUsername, editEmail, editAvatar)
                        showEditDialog = false
                    },
                    modifier = Modifier.height(44.dp),
                    testTag = "save_profile_btn"
                )
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("CANCEL", color = TextMuted)
                }
            }
        )
    }
}
