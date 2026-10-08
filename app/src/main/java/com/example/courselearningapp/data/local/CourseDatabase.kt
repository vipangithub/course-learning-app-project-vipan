package com.example.courselearningapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courselearningapp.data.local.dao.CourseDao
import com.example.courselearningapp.data.local.entity.CourseEntity
import com.example.courselearningapp.data.local.entity.LessonEntity

@Database(
    entities = [
        CourseEntity::class,
        LessonEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
}