package com.example.utestudentapp.repository

import com.example.utestudentapp.data.StudentDao
import com.example.utestudentapp.model.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao
) {

    val allStudents: Flow<List<Student>> =
        studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun deleteById(id: Int) {
        studentDao.deleteStudentById(id)
    }

    suspend fun getStudentByCode(code: String): Student? {
        return studentDao.getStudentByCode(code)
    }
}
