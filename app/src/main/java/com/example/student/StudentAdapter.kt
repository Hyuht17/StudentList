package com.example.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            val context = holder.itemView.context
            val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
            val editName = dialog.findViewById<EditText>(R.id.edit_text_name)
            val editId = dialog.findViewById<EditText>(R.id.edit_text_id)
            editName.setText(student.studentName)
            editId.setText(student.studentId)

            AlertDialog.Builder(context)
                .setView(dialog)
                .setTitle("Edit student")
                .setPositiveButton("Save") { _, _ ->
                    student.studentName = editName.text.toString()
                    student.studentId = editId.text.toString()
                    notifyItemChanged(position)
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }

        holder.imageRemove.setOnClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes") { _, _ ->
                    val removedStudent = students[position]
                    students.removeAt(position)
                    notifyItemRemoved(position)

                    Snackbar.make(holder.itemView, "Student deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            students.add(position, removedStudent)
                            notifyItemInserted(position)
                        }
                        .show()
                }
                .setNegativeButton("No", null)
                .create()
                .show()
        }
    }

}