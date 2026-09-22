package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.model.Question
import com.example.ui.components.ComboNotification
import com.example.ui.components.GlassCard
import com.example.ui.components.QuestionTimer
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.BrightCyan
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.CorrectGreenBg
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.IncorrectRed
import com.example.ui.theme.IncorrectRedBg
import com.example.ui.theme.NeonGold
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderGlow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    questions: List<Question>,
    difficulty: String,
    onComplete: (scoreXp: Int, correctCount: Int, totalCount: Int, timeSpentSeconds: Int, maxCombo: Int) -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }
    var scoreXp by remember { mutableIntStateOf(0) }
    var correctCount by remember { mutableIntStateOf(0) }
    var currentCombo by remember { mutableIntStateOf(0) }
    var maxCombo by remember { mutableIntStateOf(0) }
    var totalTimeSpent by remember { mutableIntStateOf(0) }

    val currentQuestion = questions.getOrNull(currentIndex)

    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var secondsRemaining by remember { mutableIntStateOf(30) }
    var lastXpAwarded by remember { mutableIntStateOf(0) }
    var showXpPopup by remember { mutableStateOf(false) }

    fun goToNextQuestion() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            selectedAnswerIndex = null
            isAnswerSubmitted = false
            secondsRemaining = 30
        } else {
            // Battle finished!
            onComplete(scoreXp, correctCount, questions.size, totalTimeSpent, maxCombo)
        }
    }

    // Timer countdown
    LaunchedEffect(currentIndex, isAnswerSubmitted) {
        if (!isAnswerSubmitted) {
            secondsRemaining = 30
            while (secondsRemaining > 0 && !isAnswerSubmitted) {
                delay(1000)
                secondsRemaining--
                totalTimeSpent++
                if (secondsRemaining in 1..4) {
                    SoundManager.playTick()
                }
            }
            if (secondsRemaining <= 0 && !isAnswerSubmitted) {
                // Time up! Mark unanswered
                isAnswerSubmitted = true
                selectedAnswerIndex = -1 // Unanswered
                currentCombo = 0
                SoundManager.playIncorrect()
                delay(1400)
                goToNextQuestion()
            }
        }
    }

    fun handleAnswerSelect(index: Int) {
        if (isAnswerSubmitted || currentQuestion == null) return
        isAnswerSubmitted = true
        selectedAnswerIndex = index

        val isCorrect = index == currentQuestion.correctIndex
        if (isCorrect) {
            correctCount++
            currentCombo++
            if (currentCombo > maxCombo) {
                maxCombo = currentCombo
            }

            // XP Calculation
            val baseXP = when (difficulty) {
                "Easy" -> 50
                "Medium" -> 75
                "Hard" -> 100
                "Extreme" -> 150
                else -> currentQuestion.xp
            }
            val speedBonus = if (secondsRemaining > 20) 25 else if (secondsRemaining > 10) 15 else 5
            val comboBonus = when {
                currentCombo >= 10 -> 50
                currentCombo >= 5 -> 30
                currentCombo >= 3 -> 15
                else -> 0
            }
            val totalAwarded = baseXP + speedBonus + comboBonus
            lastXpAwarded = totalAwarded
            scoreXp += totalAwarded
            showXpPopup = true

            if (currentCombo >= 3) {
                SoundManager.playCombo()
            } else {
                SoundManager.playCorrect()
            }
        } else {
            currentCombo = 0
            SoundManager.playIncorrect()
        }

        // Delay to show feedback before moving to next question
        coroutineScope.launch {
            delay(1500)
            showXpPopup = false
            goToNextQuestion()
        }
    }

    if (currentQuestion == null) {
        Box(modifier = Modifier.fillMaxSize().background(BgDarkNavy), contentAlignment = Alignment.Center) {
            Text("No questions found for this battle.", color = TextPrimary)
        }
        return
    }

    val optionLetters = listOf("A", "B", "C", "D")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Header: Question number & Quit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "QUESTION ${currentIndex + 1} / ${questions.size}",
                    color = BrightCyan,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    letterSpacing = 1.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceDarkElevated)
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Score: $scoreXp XP",
                        color = NeonGold,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SurfaceDark)
                        .border(1.dp, SurfaceBorder, CircleShape)
                        .clickable { onQuit() }
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✕", color = TextMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Progress bar
        val progress = (currentIndex + 1).toFloat() / questions.size
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF0F1426))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(listOf(ElectricPurple, BrightCyan))
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timer Bar
        QuestionTimer(
            secondsRemaining = secondsRemaining,
            totalSeconds = 30
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Combo & XP Notification
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ComboNotification(comboCount = currentCombo)

            AnimatedVisibility(
                visible = showXpPopup,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CorrectGreen.copy(alpha = 0.2f))
                        .border(1.dp, CorrectGreen, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "+$lastXpAwarded XP",
                        color = CorrectGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Question Card
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            glowEffect = true,
            borderColor = SurfaceBorderGlow
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ElectricPurple.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentQuestion.subject,
                            color = BrightCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = currentQuestion.topic,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 26.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Answers List
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            currentQuestion.options.forEachIndexed { index, optionText ->
                val letter = optionLetters.getOrElse(index) { "?" }
                val isSelected = selectedAnswerIndex == index
                val isCorrect = index == currentQuestion.correctIndex

                val (cardBg, borderStrokeColor, letterBg) = when {
                    !isAnswerSubmitted -> Triple(
                        SurfaceDark,
                        SurfaceBorder,
                        SurfaceDarkElevated
                    )
                    isCorrect -> Triple(
                        CorrectGreenBg.copy(alpha = 0.6f),
                        CorrectGreen,
                        CorrectGreen
                    )
                    isSelected -> Triple(
                        IncorrectRedBg.copy(alpha = 0.6f),
                        IncorrectRed,
                        IncorrectRed
                    )
                    else -> Triple(
                        SurfaceDark.copy(alpha = 0.5f),
                        SurfaceBorder.copy(alpha = 0.3f),
                        SurfaceDarkElevated
                    )
                }

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quiz_option_$letter")
                        .border(1.5.dp, borderStrokeColor, RoundedCornerShape(14.dp))
                        .clickable(enabled = !isAnswerSubmitted) {
                            handleAnswerSelect(index)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(letterBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                color = TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = optionText,
                            color = TextPrimary,
                            fontWeight = if (isSelected || (isAnswerSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )

                        if (isAnswerSubmitted) {
                            if (isCorrect) {
                                Text("✓", color = CorrectGreen, fontWeight = FontWeight.Black, fontSize = 18.sp)
                            } else if (isSelected) {
                                Text("✕", color = IncorrectRed, fontWeight = FontWeight.Black, fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }

        // Explanation reveal if answered incorrectly
        if (isAnswerSubmitted) {
            Spacer(modifier = Modifier.height(14.dp))
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = SurfaceBorderGlow
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "EXPLANATION",
                        color = BrightCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentQuestion.explanation,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
