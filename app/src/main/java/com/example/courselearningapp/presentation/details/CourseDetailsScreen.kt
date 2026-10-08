package com.example.courselearningapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.Lesson
import com.example.courselearningapp.data.model.progress
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailsScreen(
    viewModel: CourseDetailsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Course Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (val state = uiState) {

                CourseDetailsUiState.Loading -> {
                    LoadingContent()
                }

                is CourseDetailsUiState.Success -> {
                    CourseDetailsContent(
                        course = state.course,
                        onLessonCompleted =
                            viewModel::markLessonCompleted
                    )
                }

                is CourseDetailsUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onBack = onBack
                    )
                }
            }
        }
    }
}

@Composable
fun CourseDetailsContent(
    course: Course,
    onLessonCompleted: (Int) -> Unit
) {
    val progress = course.progress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = course.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text("Instructor: ${course.instructor}")

        Text("Progress: $progress%")

        LinearProgressIndicator(
            progress = { progress / 100f },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Lessons",
            style = MaterialTheme.typography.titleLarge
        )

        course.lessons.forEach { lesson ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = lesson.title,
                    modifier = Modifier.weight(1f)
                )

                if (lesson.completed) {
                    Text("✓ Completed")
                } else {
                    Button(
                        onClick = {
                            onLessonCompleted(lesson.id)
                        }
                    ) {
                        Text("Complete")
                    }
                }
            }
        }
    }
}
@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()

        Text(
            text = "Loading course...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = message)

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}