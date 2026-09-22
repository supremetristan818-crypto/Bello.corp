package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.Achievement
import com.example.data.model.LeaderboardEntry
import com.example.data.model.SubjectStat
import com.example.data.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("study_battle_prefs", Context.MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val _userProgress = MutableStateFlow(loadUserProgress())
    val userProgress: StateFlow<UserProgress> = _userProgress.asStateFlow()

    private val allAchievementsList = listOf(
        Achievement(
            id = "first_victory",
            title = "First Victory",
            description = "Complete your first battle victory",
            icon = "🏆",
            xpReward = 100
        ),
        Achievement(
            id = "streak_7",
            title = "7 Day Streak",
            description = "Maintain a 7-day study streak",
            icon = "🔥",
            xpReward = 300
        ),
        Achievement(
            id = "speed_demon",
            title = "Speed Demon",
            description = "Answer questions with lightning speed under 8 seconds",
            icon = "⚡",
            xpReward = 150
        ),
        Achievement(
            id = "genius",
            title = "Genius",
            description = "Get a 5x or higher combo streak in battle",
            icon = "🧠",
            xpReward = 250
        ),
        Achievement(
            id = "battle_master",
            title = "Battle Master",
            description = "Win 25 or more battles",
            icon = "👑",
            xpReward = 500
        ),
        Achievement(
            id = "perfect_score",
            title = "Perfect Score",
            description = "Complete a battle with 100% accuracy",
            icon = "💯",
            xpReward = 350
        ),
        Achievement(
            id = "level_10",
            title = "Elite Rank",
            description = "Reach Level 10 on the platform",
            icon = "⭐",
            xpReward = 400
        ),
        Achievement(
            id = "polymath",
            title = "Polymath",
            description = "Answer questions in all 7 subjects",
            icon = "🌐",
            xpReward = 300
        )
    )

    private fun loadUserProgress(): UserProgress {
        val username = prefs.getString("username", "CyberWarrior") ?: "CyberWarrior"
        val email = prefs.getString("email", "student@studybattle.gg") ?: "student@studybattle.gg"
        val xp = prefs.getInt("xp", 2450)
        val streak = prefs.getInt("streak", 7)
        val lastActiveDate = prefs.getString("last_active_date", dateFormat.format(Date())) ?: ""
        val battlesPlayed = prefs.getInt("battles_played", 45)
        val battlesWon = prefs.getInt("battles_won", 38)
        val correctAnswers = prefs.getInt("correct_answers", 168)
        val totalAnswers = prefs.getInt("total_answers", 200)
        val achievementsString = prefs.getString("achievements", "first_victory,streak_7,speed_demon") ?: ""
        val unlockedAchievements = achievementsString.split(",").filter { it.isNotBlank() }.toSet()
        val dailyCompleted = prefs.getString("daily_challenge_date", "") ?: ""
        val avatar = prefs.getString("avatar", "⚡") ?: "⚡"

        val subjectJson = prefs.getString("subjects_json", null)
        val subjectsMap = mutableMapOf<String, SubjectStat>()
        if (subjectJson != null) {
            try {
                val json = JSONObject(subjectJson)
                val keys = json.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val obj = json.getJSONObject(key)
                    subjectsMap[key] = SubjectStat(
                        completedQuestions = obj.optInt("completed", 0),
                        correctQuestions = obj.optInt("correct", 0),
                        totalAvailable = 10
                    )
                }
            } catch (_: Exception) {}
        }

        // Fill defaults if empty
        if (subjectsMap.isEmpty()) {
            subjectsMap["Mathematics"] = SubjectStat(completedQuestions = 8, correctQuestions = 7)
            subjectsMap["Physics"] = SubjectStat(completedQuestions = 7, correctQuestions = 6)
            subjectsMap["Chemistry"] = SubjectStat(completedQuestions = 6, correctQuestions = 5)
            subjectsMap["Biology"] = SubjectStat(completedQuestions = 9, correctQuestions = 8)
            subjectsMap["English"] = SubjectStat(completedQuestions = 7, correctQuestions = 6)
            subjectsMap["Social Studies"] = SubjectStat(completedQuestions = 5, correctQuestions = 4)
            subjectsMap["ICT / Computer Science"] = SubjectStat(completedQuestions = 8, correctQuestions = 7)
        }

        return UserProgress(
            username = username,
            email = email,
            xp = xp,
            streak = streak,
            lastActiveDate = lastActiveDate,
            battlesPlayed = battlesPlayed,
            battlesWon = battlesWon,
            correctAnswers = correctAnswers,
            totalAnswers = totalAnswers,
            subjectsData = subjectsMap,
            unlockedAchievements = unlockedAchievements,
            dailyChallengeCompletedDate = dailyCompleted,
            avatarEmoji = avatar
        )
    }

    private fun saveUserProgress(progress: UserProgress) {
        val subjectJson = JSONObject()
        progress.subjectsData.forEach { (subject, stat) ->
            val obj = JSONObject()
            obj.put("completed", stat.completedQuestions)
            obj.put("correct", stat.correctQuestions)
            subjectJson.put(subject, obj)
        }

        prefs.edit()
            .putString("username", progress.username)
            .putString("email", progress.email)
            .putInt("xp", progress.xp)
            .putInt("streak", progress.streak)
            .putString("last_active_date", progress.lastActiveDate)
            .putInt("battles_played", progress.battlesPlayed)
            .putInt("battles_won", progress.battlesWon)
            .putInt("correct_answers", progress.correctAnswers)
            .putInt("total_answers", progress.totalAnswers)
            .putString("achievements", progress.unlockedAchievements.joinToString(","))
            .putString("daily_challenge_date", progress.dailyChallengeCompletedDate)
            .putString("avatar", progress.avatarEmoji)
            .putString("subjects_json", subjectJson.toString())
            .apply()

        _userProgress.value = progress
    }

    fun recordBattleResult(
        subject: String,
        correctCount: Int,
        totalCount: Int,
        xpEarned: Int,
        won: Boolean,
        maxCombo: Int
    ): Pair<Boolean, List<String>> {
        val current = _userProgress.value
        val oldLevel = current.level
        val newXp = current.xp + xpEarned
        val newBattlesPlayed = current.battlesPlayed + 1
        val newBattlesWon = if (won) current.battlesWon + 1 else current.battlesWon
        val newCorrect = current.correctAnswers + correctCount
        val newTotal = current.totalAnswers + totalCount

        // Update subject stats
        val subjectMap = current.subjectsData.toMutableMap()
        val oldSub = subjectMap[subject] ?: SubjectStat()
        subjectMap[subject] = oldSub.copy(
            completedQuestions = oldSub.completedQuestions + totalCount,
            correctQuestions = oldSub.correctQuestions + correctCount
        )

        // Streak check
        val todayStr = dateFormat.format(Date())
        val newStreak = if (current.lastActiveDate != todayStr) current.streak + 1 else current.streak

        // Check newly unlocked achievements
        val unlockedNow = mutableListOf<String>()
        val currentUnlocked = current.unlockedAchievements.toMutableSet()

        fun checkUnlock(id: String) {
            if (!currentUnlocked.contains(id)) {
                currentUnlocked.add(id)
                unlockedNow.add(id)
            }
        }

        if (won) checkUnlock("first_victory")
        if (newStreak >= 7) checkUnlock("streak_7")
        if (maxCombo >= 5) checkUnlock("genius")
        if (newBattlesWon >= 25) checkUnlock("battle_master")
        if (correctCount == totalCount && totalCount >= 5) checkUnlock("perfect_score")
        if ((newXp / 500) + 1 >= 10) checkUnlock("level_10")
        if (subjectMap.keys.size >= 7 && subjectMap.values.all { it.completedQuestions > 0 }) checkUnlock("polymath")

        val updatedProgress = current.copy(
            xp = newXp,
            streak = newStreak,
            lastActiveDate = todayStr,
            battlesPlayed = newBattlesPlayed,
            battlesWon = newBattlesWon,
            correctAnswers = newCorrect,
            totalAnswers = newTotal,
            subjectsData = subjectMap,
            unlockedAchievements = currentUnlocked
        )

        saveUserProgress(updatedProgress)

        val leveledUp = updatedProgress.level > oldLevel
        return Pair(leveledUp, unlockedNow)
    }

    fun completeDailyChallenge(bonusXp: Int = 500): Boolean {
        val todayStr = dateFormat.format(Date())
        val current = _userProgress.value
        if (current.dailyChallengeCompletedDate == todayStr) return false

        val updated = current.copy(
            xp = current.xp + bonusXp,
            dailyChallengeCompletedDate = todayStr
        )
        saveUserProgress(updated)
        return true
    }

    fun recordPracticeQuestion(subject: String, correct: Boolean, xpEarned: Int = 25) {
        val current = _userProgress.value
        val subjectMap = current.subjectsData.toMutableMap()
        val oldSub = subjectMap[subject] ?: SubjectStat()
        subjectMap[subject] = oldSub.copy(
            completedQuestions = oldSub.completedQuestions + 1,
            correctQuestions = if (correct) oldSub.correctQuestions + 1 else oldSub.correctQuestions
        )

        val updated = current.copy(
            xp = current.xp + xpEarned,
            correctAnswers = if (correct) current.correctAnswers + 1 else current.correctAnswers,
            totalAnswers = current.totalAnswers + 1,
            subjectsData = subjectMap
        )
        saveUserProgress(updated)
    }

    fun updateProfile(username: String, email: String, avatar: String) {
        val updated = _userProgress.value.copy(
            username = username.ifBlank { "CyberWarrior" },
            email = email.ifBlank { "student@studybattle.gg" },
            avatarEmoji = avatar
        )
        saveUserProgress(updated)
    }

    fun getAchievements(): List<Achievement> {
        val currentUnlocked = _userProgress.value.unlockedAchievements
        return allAchievementsList.map { ach ->
            ach.copy(isUnlocked = currentUnlocked.contains(ach.id))
        }
    }

    fun getLeaderboard(filter: String): List<LeaderboardEntry> {
        val user = _userProgress.value

        val baseOpponents = when (filter) {
            "Weekly" -> listOf(
                LeaderboardEntry(1, "Aria_Valkyrie", 18, 5200, 74, "⚡"),
                LeaderboardEntry(2, "Kaito_Cyber", 16, 4400, 61, "🔥"),
                LeaderboardEntry(3, "Neo_Scholar", 14, 3850, 52, "🧠"),
                LeaderboardEntry(4, "Luna_Quantum", 13, 3100, 44, "🌌"),
                LeaderboardEntry(5, "Ren_Striker", 11, 2600, 39, "⚔️"),
                LeaderboardEntry(6, "Vector_Prime", 10, 2200, 32, "🎯"),
                LeaderboardEntry(7, "Pixel_Sage", 9, 1850, 28, "💻"),
                LeaderboardEntry(8, "Helix_Bio", 8, 1500, 22, "🧬"),
                LeaderboardEntry(9, "Cipher_Queen", 7, 1200, 19, "👑"),
                LeaderboardEntry(10, "Zen_Seeker", 6, 950, 15, "🌸")
            )
            "Monthly" -> listOf(
                LeaderboardEntry(1, "Kaito_Cyber", 28, 14200, 198, "🔥"),
                LeaderboardEntry(2, "Aria_Valkyrie", 25, 12600, 172, "⚡"),
                LeaderboardEntry(3, "Neo_Scholar", 22, 10800, 145, "🧠"),
                LeaderboardEntry(4, "Atlas_Titan", 19, 9400, 128, "🛡️"),
                LeaderboardEntry(5, "Luna_Quantum", 17, 8200, 114, "🌌"),
                LeaderboardEntry(6, "Vortex_Math", 15, 7100, 96, "📐"),
                LeaderboardEntry(7, "Ren_Striker", 14, 6400, 88, "⚔️"),
                LeaderboardEntry(8, "Pixel_Sage", 12, 5300, 71, "💻"),
                LeaderboardEntry(9, "Cipher_Queen", 10, 4200, 59, "👑"),
                LeaderboardEntry(10, "Echo_Rider", 8, 3100, 45, "🚀")
            )
            else -> listOf(
                LeaderboardEntry(1, "Aria_Valkyrie", 32, 18450, 260, "⚡"),
                LeaderboardEntry(2, "Kaito_Cyber", 30, 16900, 241, "🔥"),
                LeaderboardEntry(3, "Neo_Scholar", 27, 14500, 205, "🧠"),
                LeaderboardEntry(4, "Atlas_Titan", 24, 12300, 180, "🛡️"),
                LeaderboardEntry(5, "Luna_Quantum", 21, 10700, 155, "🌌"),
                LeaderboardEntry(6, "Vortex_Math", 18, 8900, 129, "📐"),
                LeaderboardEntry(7, "Ren_Striker", 16, 7650, 110, "⚔️"),
                LeaderboardEntry(8, "Vector_Prime", 15, 6900, 98, "🎯"),
                LeaderboardEntry(9, "Pixel_Sage", 13, 5800, 83, "💻"),
                LeaderboardEntry(10, "Helix_Bio", 11, 4700, 68, "🧬")
            )
        }

        // Insert current user and sort dynamically
        val userEntry = LeaderboardEntry(
            rank = 0,
            username = "${user.username} (You)",
            level = user.level,
            xp = user.xp,
            battlesWon = user.battlesWon,
            avatar = user.avatarEmoji,
            isCurrentUser = true
        )

        val combined = (baseOpponents + userEntry).sortedByDescending { it.xp }
        return combined.mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        }
    }
}
