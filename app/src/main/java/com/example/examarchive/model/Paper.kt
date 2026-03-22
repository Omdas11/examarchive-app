package com.example.examarchive.model

/** Represents a single exam paper entry. */
data class Paper(
    val id: String,
    val title: String,
    val courseCode: String,
    val courseName: String,
    val year: Int,
    val semester: String,
    val examType: String,       // "Theory" | "Practical"
    val department: String,
    val programme: String,      // "FYUGP" | "CBCS" | "Other"
    val paperType: String,      // "DSC" | "DSM" | "SEC" | "IDC" | "GE"
    val institution: String,
    val uploadedBy: String,
    val createdAt: String,
    val viewCount: Int = 0,
    val downloadCount: Int = 0,
)
