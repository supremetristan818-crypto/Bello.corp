package com.example.data.model

data class SubjectStat(
    val completedQuestions: Int = 0,
    val correctQuestions: Int = 0,
    val totalAvailable: Int = 10
) {
    val progressPercentage: Int
        get() = if (totalAvailable > 0) ((completedQuestions.toFloat() / totalAvailable) * 100).toInt().coerceIn(0, 100) else 0

    val accuracyPercentage: Int
        get() = if (completedQuestions > 0) ((correctQuestions.toFloat() / completedQuestions) * 100).toInt().coerceIn(0, 100) else 0
}

data class UserProgress(
    val username: String = "CyberWarrior",
    val email: String = "student@studybattle.gg",
    val xp: Int = 2450,
    val streak: Int = 7,
    val lastActiveDate: String = "",
    val battlesPlayed: Int = 45,
    val battlesWon: Int = 38,
    val correctAnswers: Int = 168,
    val totalAnswers: Int = 200,
    val subjectsData: Map<String, SubjectStat> = emptyMap(),
    val unlockedAchievements: Set<String> = setOf("first_victory", "streak_7", "speed_demon"),
    val dailyChallengeCompletedDate: String = "",
    val avatarEmoji: String = "⚡"
) {
    val level: Int
        get() = (xp / 500) + 1

    val currentLevelBaseXp: Int
        get() = (level - 1) * 500

    val nextLevelTargetXp: Int
        get() = level * 500

    val levelProgressFraction: Float
        get() {
            val needed = nextLevelTargetXp - currentLevelBaseXp
            if (needed <= 0) return 1f
            val currentInLevel = xp - currentLevelBaseXp
            return (currentInLevel.toFloat() / needed).coerceIn(0f, 1f)
        }

    val overallAccuracy: Int
        get() = if (totalAnswers > 0) ((correctAnswers.toFloat() / totalAnswers) * 100).toInt().coerceIn(0, 100) else 0
}

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val xpReward: Int,
    val isUnlocked: Boolean = false
)

data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val level: Int,
    val xp: Int,
    val battlesWon: Int,
    val avatar: String,
    val isCurrentUser: Boolean = false
)
