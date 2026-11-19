package com.example.utestudentapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.utestudentapp.model.Student

@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ute_student_room.db"
                )
                    .addCallback(DatabaseCallback())
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val students = listOf(
                Student(
                    fullName = "Nguyễn Huỳnh Nhật Duy",
                    studentCode = "22115141122104",
                    email = "duy.nhn@ute.udn.vn",
                    gpa = 3.85,
                    major = "Công Nghệ Thông Tin"
                ),
                Student(
                    fullName = "Trần Thị Mai",
                    studentCode = "20110002",
                    email = "mai.tt@ute.udn.vn",
                    gpa = 3.92,
                    major = "Kỹ Thuật Phần Mềm"
                ),
                Student(
                    fullName = "Lê Hoàng Phúc",
                    studentCode = "20110003",
                    email = "phuc.lh@ute.udn.vn",
                    gpa = 3.45,
                    major = "An Toàn Thông Tin"
                ),
                Student(
                    fullName = "Phạm Quốc Dũng",
                    studentCode = "20110004",
                    email = "dung.pq@ute.udn.vn",
                    gpa = 3.60,
                    major = "Mạng Máy Tính"
                )
            )

            students.forEach { student ->
                db.execSQL(
                    """
                    INSERT INTO students_table
                    (full_name, student_code, email, gpa, major)
                    VALUES (?, ?, ?, ?, ?)
                    """.trimIndent(),
                    arrayOf(
                        student.fullName,
                        student.studentCode,
                        student.email,
                        student.gpa,
                        student.major
                    )
                )
            }
        }
    }
}
