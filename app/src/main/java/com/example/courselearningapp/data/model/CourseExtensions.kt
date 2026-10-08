package com.example.courselearningapp.data.model

fun Course.progress(): Int {
    if (lessons.isEmpty()) return 0

    val completedLessons = lessons.count { it.completed }

    return (completedLessons * 100) / lessons.size
}