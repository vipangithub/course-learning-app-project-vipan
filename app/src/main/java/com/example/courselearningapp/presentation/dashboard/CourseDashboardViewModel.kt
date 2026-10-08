package com.example.courselearningapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courselearningapp.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDashboardViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(
        CourseUiState.Loading
    )

    val uiState: StateFlow<CourseUiState> =
        _uiState.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {

        viewModelScope.launch {

            _uiState.value = CourseUiState.Loading

            try {

                val courses = repository.getCourses()

                _uiState.value =
                    if (courses.isEmpty()) {
                        CourseUiState.Empty
                    } else {
                        CourseUiState.Success(courses)
                    }

            } catch (e: Exception) {

                _uiState.value = CourseUiState.Error(
                    e.message ?: "Unable to load courses"
                )
            }
        }
    }
}