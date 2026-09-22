package com.example.data.model

data class Question(
    val id: String,
    val subject: String,
    val topic: String,
    val difficulty: String, // "Easy", "Medium", "Hard", "Extreme"
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val xp: Int
) {
    val correctAnswer: String
        get() = options.getOrElse(correctIndex) { "" }
}

enum class BattleDifficulty(val label: String, val baseXP: Int) {
    EASY("Easy", 50),
    MEDIUM("Medium", 75),
    HARD("Hard", 100),
    EXTREME("Extreme", 150)
}
