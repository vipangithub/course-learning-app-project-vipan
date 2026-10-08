package com.example.courselearningapp.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.progress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDashboardScreen(
    viewModel: CourseDashboardViewModel,
    onCourseClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Courses")
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

                CourseUiState.Loading -> {
                    LoadingContent()
                }

                is CourseUiState.Success -> {
                    CourseList(
                        courses = state.courses,
                        isOffline = state.isOffline,
                        onCourseClick = onCourseClick
                    )
                }

                CourseUiState.Empty -> {
                    EmptyContent(
                        onRetry = viewModel::loadCourses
                    )
                }

                is CourseUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = viewModel::loadCourses
                    )
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
            text = "Loading courses...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun CourseList(
    courses: List<Course>,
    isOffline: Boolean,
    onCourseClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if (isOffline) {
            item {
                Text(
                    text = "You're offline. Showing cached courses.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        items(
            items = courses,
            key = { it.id }
        ) { course ->

            CourseCard(
                course = course,
                onClick = {
                    onCourseClick(course.id)
                }
            )
        }
    }
}

@Composable
private fun CourseCard(
    course: Course,
    onClick: () -> Unit
) {
    val progress = course.progress()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = course.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Instructor: ${course.instructor}",
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Progress: $progress%",
                modifier = Modifier.padding(top = 12.dp)
            )

            LinearProgressIndicator(
                progress = {
                    progress / 100f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )

            Text(
                text = "${course.lessons.size} lessons",
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun EmptyContent(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No courses available")

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Retry")
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = message,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Retry")
        }
    }
}