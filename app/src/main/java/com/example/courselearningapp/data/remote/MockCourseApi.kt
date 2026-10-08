package com.example.courselearningapp.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.Lesson
import kotlinx.coroutines.delay
import java.io.IOException

class MockCourseApi(
    private val context: Context
) {
    suspend fun getCourses(): List<Course> {
        if (!isInternetAvailable()) {
            throw IOException("No internet connection")
        }
        delay(1200)
        return listOf(

            Course(
                id = 1,
                title = "Python Programming",
                instructor = "John Smith",
                lessons = listOf(
                    Lesson(1, "Introduction", true),
                    Lesson(2, "Variables & Data Types", true),
                    Lesson(3, "Functions", false),
                    Lesson(4, "OOP", false)
                )
            ),

            Course(
                id = 2,
                title = "Generative AI",
                instructor = "Sarah Williams",
                lessons = listOf(
                    Lesson(5, "Introduction to AI", true),
                    Lesson(6, "Prompt Engineering", false),
                    Lesson(7, "LLMs", false),
                    Lesson(8, "AI Applications", false)
                )
            ),

            Course(
                id = 3,
                title = "Full Stack Development",
                instructor = "David Brown",
                lessons = listOf(
                    Lesson(9, "HTML", true),
                    Lesson(10, "CSS", false),
                    Lesson(11, "JavaScript", false),
                    Lesson(12, "Backend", false)
                )
            )
        )
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val network = connectivityManager.activeNetwork
            ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}
