package com.example.courselearningapp.presentation.dashboard

import com.example.courselearningapp.data.model.Course

sealed interface CourseUiState {

    data object Loading : CourseUiState

    data class Success(
        val courses: List<Course>,
        val isOffline: Boolean = false
    ) : CourseUiState

    data object Empty : CourseUiState

    data class Error(
        val message: String
    ) : CourseUiState
}