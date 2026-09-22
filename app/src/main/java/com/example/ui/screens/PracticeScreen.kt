package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.Question
import com.example.data.repository.QuestionBank
import com.example.ui.components.GlassCard
import com.example.ui.components.GlowingButton
import com.example.ui.components.SecondaryGlowingButton
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

@Composable
fun PracticeScreen(
    initialSubject: String? = null,
    onAnswerPracticeQuestion: (subject: String, correct: Boolean, xp: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubject by remember { mutableStateOf(initialSubject ?: QuestionBank.SUBJECTS.first()) }
    var selectedTopic by remember { mutableStateOf("All Topics") }
    var selectedDifficulty by remember { mutableStateOf("All Difficulties") }

    val topics = remember(selectedSubject) {
        listOf("All Topics") + QuestionBank.getTopicsForSubject(selectedSubject)
    }
    val difficulties = listOf("All Difficulties", "Easy", "Medium", "Hard", "Extreme")

    var questionsList by remember(selectedSubject, selectedTopic, selectedDifficulty) {
        mutableStateOf(
            QuestionBank.getQuestions(
                subject = selectedSubject,
                topic = selectedTopic,
                difficulty = selectedDifficulty,
                limit = 20
            )
        )
    }

    var currentIndex by remember(questionsList) { mutableIntStateOf(0) }
    var selectedOptionIndex by remember(currentIndex) { mutableStateOf<Int?>(null) }
    var hasAnswered by remember(currentIndex) { mutableStateOf(false) }

    val currentQuestion = questionsList.getOrNull(currentIndex)
    val optionLetters = listOf("A", "B", "C", "D")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDarkNavy)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📚", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PRACTICE & STUDY LAB",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
            Text(
                text = "Untimed study mode. Review detailed solutions and earn +25 XP per problem.",
                color = TextSecondary,
                fontSize = 13.sp
            )
        }

        // Subject selector pills
        item {
            Text(
                text = "SUBJECT",
                color = BrightCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(QuestionBank.SUBJECTS) { subject ->
                    val isSelected = subject == selectedSubject
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ElectricPurple else SurfaceDark)
                            .border(1.dp, if (isSelected) BrightCyan else SurfaceBorder, RoundedCornerShape(10.dp))
                            .clickable {
                                SoundManager.playClick()
                                selectedSubject = subject
                                selectedTopic = "All Topics"
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = subject,
                            color = if (isSelected) TextPrimary else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Topic selector pills
        item {
            Text(
                text = "TOPIC",
                color = BrightCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(topics) { topic ->
                    val isSelected = topic == selectedTopic
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ElectricBlue else SurfaceDark)
                            .border(1.dp, if (isSelected) BrightCyan else SurfaceBorder, RoundedCornerShape(10.dp))
                            .clickable {
                                SoundManager.playClick()
                                selectedTopic = topic
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = topic,
                            color = if (isSelected) TextPrimary else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        if (currentQuestion != null) {
            // Question Card
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    glowEffect = true,
                    borderColor = SurfaceBorderGlow
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "QUESTION ${currentIndex + 1} OF ${questionsList.size}",
                                color = BrightCyan,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                letterSpacing = 1.sp
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceDarkElevated)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = currentQuestion.difficulty,
                                    color = NeonGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
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
            }

            // Options
            items(currentQuestion.options.indices.toList()) { index ->
                val optionText = currentQuestion.options[index]
                val letter = optionLetters.getOrElse(index) { "?" }
                val isSelected = selectedOptionIndex == index
                val isCorrect = index == currentQuestion.correctIndex

                val (bg, stroke) = when {
                    !hasAnswered -> Pair(SurfaceDark, SurfaceBorder)
                    isCorrect -> Pair(CorrectGreenBg.copy(alpha = 0.6f), CorrectGreen)
                    isSelected -> Pair(IncorrectRedBg.copy(alpha = 0.6f), IncorrectRed)
                    else -> Pair(SurfaceDark.copy(alpha = 0.4f), SurfaceBorder.copy(alpha = 0.3f))
                }

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = bg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("practice_opt_$letter")
                        .border(1.5.dp, stroke, RoundedCornerShape(14.dp))
                        .clickable(enabled = !hasAnswered) {
                            selectedOptionIndex = index
                            hasAnswered = true
                            val correct = isCorrect
                            if (correct) {
                                SoundManager.playCorrect()
                            } else {
                                SoundManager.playIncorrect()
                            }
                            onAnswerPracticeQuestion(selectedSubject, correct, if (correct) 25 else 5)
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
                                .background(if (hasAnswered && isCorrect) CorrectGreen else SurfaceDarkElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = optionText,
                            color = TextPrimary,
                            fontWeight = if (isSelected || (hasAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        if (hasAnswered) {
                            if (isCorrect) {
                                Text("✓ Correct (+25 XP)", color = CorrectGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            } else if (isSelected) {
                                Text("✕ Incorrect", color = IncorrectRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Solution explanation
            if (hasAnswered) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = BrightCyan
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("💡", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "DETAILED SOLUTION & EXPLANATION",
                                    color = BrightCyan,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentQuestion.explanation,
                                color = TextPrimary,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Correct Answer: ${currentQuestion.correctAnswer}",
                                color = CorrectGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SecondaryGlowingButton(
                            text = "RETRY QUESTION",
                            icon = "🔄",
                            onClick = {
                                hasAnswered = false
                                selectedOptionIndex = null
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "practice_retry"
                        )

                        GlowingButton(
                            text = if (currentIndex < questionsList.size - 1) "NEXT QUESTION" else "NEW SET",
                            icon = "▶",
                            onClick = {
                                if (currentIndex < questionsList.size - 1) {
                                    currentIndex++
                                } else {
                                    // Refresh set
                                    questionsList = QuestionBank.getQuestions(
                                        subject = selectedSubject,
                                        topic = selectedTopic,
                                        difficulty = selectedDifficulty,
                                        limit = 20
                                    )
                                    currentIndex = 0
                                }
                                hasAnswered = false
                                selectedOptionIndex = null
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "practice_next"
                        )
                    }
                }
            }
        } else {
            item {
                Text("No questions found matching your filter criteria.", color = TextSecondary)
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
