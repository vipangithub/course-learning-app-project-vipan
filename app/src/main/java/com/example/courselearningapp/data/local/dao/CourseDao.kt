package com.example.courselearningapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.courselearningapp.data.local.entity.CourseEntity
import com.example.courselearningapp.data.local.entity.LessonEntity

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    suspend fun getCourses(): List<CourseEntity>

    @Query("SELECT * FROM lessons")
    suspend fun getLessons(): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Query("UPDATE lessons SET completed = 1 WHERE id = :lessonId")
    suspend fun markLessonCompleted(lessonId: Int)
}