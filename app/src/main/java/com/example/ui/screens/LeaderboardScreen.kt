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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.data.model.LeaderboardEntry
import com.example.data.repository.UserRepository
import com.example.ui.components.GlassCard
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.CorrectGreen
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

@Composable
fun LeaderboardScreen(
    userRepository: UserRepository,
    modifier: Modifier = Modifier
) {
    var activeFilter by remember { mutableStateOf("Global") }
    val filters = listOf("Global", "Weekly", "Monthly")

    val leaderboardEntries = remember(activeFilter) {
        userRepository.getLeaderboard(activeFilter)
    }

    val topThree = leaderboardEntries.take(3)
    val remainingEntries = leaderboardEntries.drop(3)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🏆", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "GLOBAL LEADERBOARD",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Compete with scholars worldwide. Season 1 underway.",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Time Filters Tab
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = activeFilter == filter
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isSelected) {
                                    Brush.horizontalGradient(listOf(ElectricPurple, ElectricBlue))
                                } else {
                                    Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
                                }
                            )
                            .clickable {
                                SoundManager.playClick()
                                activeFilter = filter
                            }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) TextPrimary else TextMuted,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Top 3 Podium
        if (topThree.isNotEmpty()) {
            item {
                PodiumSection(topThree = topThree)
            }
        }

        item {
            Text(
                text = "ALL PARTICIPANTS",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        // Remaining entries list
        items(remainingEntries) { entry ->
            val isUser = entry.isCurrentUser
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("leaderboard_rank_${entry.rank}"),
                borderColor = if (isUser) BrightCyan else SurfaceBorder,
                glowEffect = isUser
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isUser) SurfaceDarkElevated else SurfaceDark)
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rank badge
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(SurfaceDarkElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#${entry.rank}",
                            color = if (isUser) BrightCyan else TextMuted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SurfaceBorder),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = entry.avatar, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Username & level
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = entry.username,
                            color = if (isUser) BrightCyan else TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Lvl ${entry.level}",
                                color = NeonGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "• ${entry.battlesWon} wins",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // XP
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${entry.xp} XP",
                            color = TextPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                        if (isUser) {
                            Text(
                                text = "YOUR RANK",
                                color = BrightCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun PodiumSection(topThree: List<LeaderboardEntry>) {
    val first = topThree.getOrNull(0)
    val second = topThree.getOrNull(1)
    val third = topThree.getOrNull(2)

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        glowEffect = true,
        borderColor = NeonGold.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1E1C38), Color(0xFF101428))
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOP PODIUM CONTENDERS",
                color = NeonGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // 2nd Place
                if (second != null) {
                    PodiumPillar(entry = second, rank = 2, crownEmoji = "🥈", color = Color(0xFFC0C0C0), height = 110.dp)
                }

                // 1st Place
                if (first != null) {
                    PodiumPillar(entry = first, rank = 1, crownEmoji = "👑", color = NeonGold, height = 136.dp)
                }

                // 3rd Place
                if (third != null) {
                    PodiumPillar(entry = third, rank = 3, crownEmoji = "🥉", color = Color(0xFFCD7F32), height = 95.dp)
                }
            }
        }
    }
}

@Composable
fun PodiumPillar(
    entry: LeaderboardEntry,
    rank: Int,
    crownEmoji: String,
    color: Color,
    height: androidx.compose.ui.unit.Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(96.dp)
    ) {
        Text(text = crownEmoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(2.dp))

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(SurfaceDarkElevated)
                .border(2.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = entry.avatar, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = entry.username.split(" ").first(),
            color = if (entry.isCurrentUser) BrightCyan else TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            maxLines = 1
        )

        Text(
            text = "${entry.xp} XP",
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(height)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(color.copy(alpha = 0.35f), SurfaceDarkElevated)
                    )
                )
                .border(
                    1.dp,
                    color.copy(alpha = 0.6f),
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "#$rank",
                color = color,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )
        }
    }
}
