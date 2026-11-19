package com.example.utestudentapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utestudentapp.databinding.FragmentStudentListBinding
import com.example.utestudentapp.model.Student
import com.example.utestudentapp.ui.adapter.StudentAdapter
import com.example.utestudentapp.viewmodel.StudentViewModel

class StudentListFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    // Sử dụng ViewModel của MainActivity
    private val viewModel: StudentViewModel by activityViewModels()

    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudentListBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {

        studentAdapter = StudentAdapter(
            onStudentClick = { student ->
                showStudentDetailDialog(student)
            },
            onDeleteClick = { student ->
                confirmDelete(student)
            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {

        viewModel.allStudents.observe(viewLifecycleOwner) { students ->

            studentAdapter.submitList(students)

            binding.layoutEmpty.visibility =
                if (students.isEmpty()) View.VISIBLE else View.GONE

            binding.rvStudents.visibility =
                if (students.isEmpty()) View.GONE else View.VISIBLE

            binding.tvStudentCountBadge.text =
                "${students.size} sinh viên"
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupListeners() {

        binding.fabAdd.setOnClickListener {

            AddStudentDialogFragment().show(
                childFragmentManager,
                "ADD_STUDENT_DIALOG"
            )
        }
    }

    private fun showStudentDetailDialog(student: Student) {

        AlertDialog.Builder(requireContext())
            .setTitle("Thông tin sinh viên")
            .setMessage(
                """
                ID: ${student.id}
                Họ tên: ${student.fullName}
                MSSV: ${student.studentCode}
                Email: ${student.email}
                GPA: ${student.gpa}
                Ngành: ${student.major}
                """.trimIndent()
            )
            .setPositiveButton("Đóng", null)
            .show()
    }

    private fun confirmDelete(student: Student) {

        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận xóa")
            .setMessage(
                "Bạn có chắc muốn xóa sinh viên ${student.fullName}?"
            )
            .setPositiveButton("Xóa") { _, _ ->

                viewModel.delete(student)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onDestroyView() {

        binding.rvStudents.adapter = null
        _binding = null

        super.onDestroyView()
    }
}
