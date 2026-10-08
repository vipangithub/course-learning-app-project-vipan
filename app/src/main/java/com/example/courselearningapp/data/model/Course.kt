package com.example.courselearningapp.data.model


data class Course(
    val id: Int,
    val title: String,
    val instructor: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: Int,
    val title: String,
    val completed: Boolean
)