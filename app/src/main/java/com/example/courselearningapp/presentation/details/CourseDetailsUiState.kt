package com.example.courselearningapp.presentation.details

import com.example.courselearningapp.data.model.Course

sealed interface CourseDetailsUiState {

    data object Loading : CourseDetailsUiState

    data class Success(
        val course: Course
    ) : CourseDetailsUiState

    data class Error(
        val message: String
    ) : CourseDetailsUiState
}