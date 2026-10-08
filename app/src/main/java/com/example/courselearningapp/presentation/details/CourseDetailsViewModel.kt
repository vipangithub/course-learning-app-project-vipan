package com.example.courselearningapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.Lesson
import com.example.courselearningapp.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    private val repository: CourseRepository,
    private val courseId: Int
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CourseDetailsUiState>(
            CourseDetailsUiState.Loading
        )

    val uiState: StateFlow<CourseDetailsUiState> =
        _uiState.asStateFlow()

    init {
        loadCourse()
    }

    private fun loadCourse() {
        viewModelScope.launch {
            try {
                val result = repository.getCourses()

                val course = result.courses.find {
                    it.id == courseId
                }

                if (course != null) {
                    _uiState.value =
                        CourseDetailsUiState.Success(course)
                } else {
                    _uiState.value =
                        CourseDetailsUiState.Error(
                            "Course not found"
                        )
                }

            } catch (e: Exception) {
                _uiState.value =
                    CourseDetailsUiState.Error(
                        e.message ?: "Unable to load course"
                    )
            }
        }
    }

    fun markLessonCompleted(lessonId: Int) {
        viewModelScope.launch {

            // Save completion in Room
            repository.markLessonCompleted(lessonId)

            // Update the current UI immediately
            val currentState =
                _uiState.value as? CourseDetailsUiState.Success
                    ?: return@launch

            val updatedLessons = currentState.course.lessons.map { lesson ->
                if (lesson.id == lessonId) {
                    lesson.copy(completed = true)
                } else {
                    lesson
                }
            }

            val updatedCourse = currentState.course.copy(
                lessons = updatedLessons
            )

            _uiState.value = CourseDetailsUiState.Success(updatedCourse)
        }
    }}