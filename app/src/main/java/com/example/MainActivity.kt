package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.model.Question
import com.example.data.repository.QuestionBank
import com.example.data.repository.UserRepository
import com.example.ui.components.NavDestination
import com.example.ui.components.StudyBattleBottomBar
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.BattleSetupScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ResultsScreen
import com.example.ui.theme.BgDarkNavy
import com.example.ui.theme.MyApplicationTheme

data class BattleResultState(
    val scoreXp: Int = 0,
    val correctCount: Int = 0,
    val totalCount: Int = 0,
    val timeSpentSeconds: Int = 0,
    val maxCombo: Int = 0,
    val leveledUp: Boolean = false,
    val newLevel: Int = 1,
    val newAchievements: List<String> = emptyList()
)

class MainActivity : ComponentActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        userRepository = UserRepository(applicationContext)

        setContent {
            MyApplicationTheme {
                StudyBattleApp(userRepository = userRepository)
            }
        }
    }
}

@Composable
fun StudyBattleApp(userRepository: UserRepository) {
    val userProgress by userRepository.userProgress.collectAsState()

    var currentScreen by remember { mutableStateOf("home") }
    var battleSetupSubject by remember { mutableStateOf<String?>("All Subjects") }
    var practiceSubject by remember { mutableStateOf<String?>(null) }

    // Active Quiz State
    var activeQuizQuestions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var activeQuizDifficulty by remember { mutableStateOf("Medium") }
    var activeBattleSubject by remember { mutableStateOf("All Subjects") }

    // Last Battle Result State
    var battleResult by remember { mutableStateOf(BattleResultState()) }

    // Intercept back presses
    BackHandler(enabled = currentScreen != "home") {
        when (currentScreen) {
            "quiz" -> currentScreen = "battle_setup"
            "results" -> currentScreen = "home"
            else -> currentScreen = "home"
        }
    }

    val showBottomBar = currentScreen in listOf("home", "battle_setup", "practice", "leaderboard", "profile")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDarkNavy),
        containerColor = BgDarkNavy,
        bottomBar = {
            if (showBottomBar) {
                val navRoute = when (currentScreen) {
                    "battle_setup" -> "battle"
                    else -> currentScreen
                }
                StudyBattleBottomBar(
                    currentRoute = navRoute,
                    onNavigate = { dest ->
                        when (dest) {
                            NavDestination.HOME -> currentScreen = "home"
                            NavDestination.BATTLE -> {
                                battleSetupSubject = "All Subjects"
                                currentScreen = "battle_setup"
                            }
                            NavDestination.PRACTICE -> {
                                practiceSubject = null
                                currentScreen = "practice"
                            }
                            NavDestination.LEADERBOARD -> currentScreen = "leaderboard"
                            NavDestination.PROFILE -> currentScreen = "profile"
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    "home" -> {
                        HomeScreen(
                            userProgress = userProgress,
                            onStartBattle = { subject ->
                                battleSetupSubject = subject ?: "All Subjects"
                                currentScreen = "battle_setup"
                            },
                            onStartPractice = { subject ->
                                practiceSubject = subject
                                currentScreen = "practice"
                            },
                            onOpenProfile = {
                                currentScreen = "profile"
                            }
                        )
                    }

                    "battle_setup" -> {
                        BattleSetupScreen(
                            initialSubject = battleSetupSubject,
                            onLaunchBattle = { subject, difficulty, count ->
                                activeBattleSubject = subject
                                activeQuizDifficulty = difficulty
                                activeQuizQuestions = QuestionBank.getQuestions(
                                    subject = subject,
                                    difficulty = difficulty,
                                    limit = count
                                )
                                currentScreen = "quiz"
                            }
                        )
                    }

                    "quiz" -> {
                        QuizScreen(
                            questions = activeQuizQuestions,
                            difficulty = activeQuizDifficulty,
                            onComplete = { scoreXp, correct, total, time, maxCombo ->
                                val won = (correct.toFloat() / total) >= 0.5f
                                val (leveledUp, newAchievements) = userRepository.recordBattleResult(
                                    subject = activeBattleSubject,
                                    correctCount = correct,
                                    totalCount = total,
                                    xpEarned = scoreXp,
                                    won = won,
                                    maxCombo = maxCombo
                                )

                                battleResult = BattleResultState(
                                    scoreXp = scoreXp,
                                    correctCount = correct,
                                    totalCount = total,
                                    timeSpentSeconds = time,
                                    maxCombo = maxCombo,
                                    leveledUp = leveledUp,
                                    newLevel = userProgress.level,
                                    newAchievements = newAchievements
                                )
                                currentScreen = "results"
                            },
                            onQuit = {
                                currentScreen = "battle_setup"
                            }
                        )
                    }

                    "results" -> {
                        ResultsScreen(
                            scoreXp = battleResult.scoreXp,
                            correctCount = battleResult.correctCount,
                            totalCount = battleResult.totalCount,
                            timeSpentSeconds = battleResult.timeSpentSeconds,
                            maxCombo = battleResult.maxCombo,
                            leveledUp = battleResult.leveledUp,
                            newLevel = battleResult.newLevel,
                            newAchievements = battleResult.newAchievements,
                            onPlayAgain = {
                                activeQuizQuestions = QuestionBank.getQuestions(
                                    subject = activeBattleSubject,
                                    difficulty = activeQuizDifficulty,
                                    limit = battleResult.totalCount
                                )
                                currentScreen = "quiz"
                            },
                            onPractice = {
                                practiceSubject = activeBattleSubject
                                currentScreen = "practice"
                            },
                            onBackToHome = {
                                currentScreen = "home"
                            }
                        )
                    }

                    "practice" -> {
                        PracticeScreen(
                            initialSubject = practiceSubject,
                            onAnswerPracticeQuestion = { subject, correct, xp ->
                                userRepository.recordPracticeQuestion(
                                    subject = subject,
                                    correct = correct,
                                    xpEarned = xp
                                )
                            }
                        )
                    }

                    "leaderboard" -> {
                        LeaderboardScreen(
                            userRepository = userRepository
                        )
                    }

                    "profile" -> {
                        ProfileScreen(
                            userProgress = userProgress,
                            achievements = userRepository.getAchievements(),
                            onUpdateProfile = { username, email, avatar ->
                                userRepository.updateProfile(username, email, avatar)
                            }
                        )
                    }

                    "auth" -> {
                        AuthScreen(
                            onLoginSuccess = { username, email ->
                                userRepository.updateProfile(username, email, userProgress.avatarEmoji)
                                currentScreen = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}
