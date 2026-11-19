package com.example.utestudentapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.utestudentapp.model.Student
import com.example.utestudentapp.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    val allStudents: LiveData<List<Student>> =
        repository.allStudents.asLiveData()

    private val _statusMessage = MutableLiveData<String>()

    val statusMessage: LiveData<String>
        get() = _statusMessage

    fun insert(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(student)
            _statusMessage.postValue("Thêm sinh viên thành công!")
        }
    }

    fun update(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(student)
            _statusMessage.postValue("Cập nhật thành công!")
        }
    }

    fun delete(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(student)
            _statusMessage.postValue("Xóa sinh viên thành công!")
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteById(id)
            _statusMessage.postValue("Xóa sinh viên thành công!")
        }
    }
}
