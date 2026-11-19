package com.example.utestudentapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.utestudentapp.data.AppDatabase
import com.example.utestudentapp.repository.StudentRepository
import com.example.utestudentapp.ui.StudentListFragment
import com.example.utestudentapp.viewmodel.StudentViewModel
import com.example.utestudentapp.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    // Khởi tạo Room Database
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    // Khởi tạo Repository
    private val repository by lazy {
        StudentRepository(database.studentDao())
    }

    // Tạo ViewModel thông qua Factory
    private val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Khởi tạo ViewModel trước khi Fragment sử dụng
        viewModel

        // Chỉ thêm Fragment khi Activity được tạo mới
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    StudentListFragment()
                )
                .commit()
        }
    }
}
