package com.example.utestudentapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.utestudentapp.databinding.DialogAddStudentBinding
import com.example.utestudentapp.model.Student
import com.example.utestudentapp.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogAddStudentBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Thêm sinh viên mới")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room", null)
            .setNegativeButton("Hủy", null)
            .create()

        dialog.setOnShowListener {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {

                    val name = binding.etFullName.text
                        .toString().trim()

                    val code = binding.etStudentCode.text
                        .toString().trim()

                    val email = binding.etEmail.text
                        .toString().trim()

                    val gpaText = binding.etGpa.text
                        .toString().trim()

                    if (name.isEmpty()) {
                        binding.etFullName.error =
                            "Vui lòng nhập họ tên"
                        return@setOnClickListener
                    }

                    if (code.isEmpty()) {
                        binding.etStudentCode.error =
                            "Vui lòng nhập mã sinh viên"
                        return@setOnClickListener
                    }

                    val gpa = gpaText.toDoubleOrNull()

                    if (gpa == null || gpa !in 0.0..4.0) {
                        binding.etGpa.error =
                            "GPA phải từ 0 đến 4"
                        return@setOnClickListener
                    }

                    val student = Student(
                        fullName = name,
                        studentCode = code,
                        email = if (email.isNotEmpty()) {
                            email
                        } else {
                            "${code.lowercase()}@ute.udn.vn"
                        },
                        gpa = gpa,
                        major = "Công Nghệ Thông Tin"
                    )

                    viewModel.insert(student)

                    dialog.dismiss()
                }
        }

        return dialog
    }
}
