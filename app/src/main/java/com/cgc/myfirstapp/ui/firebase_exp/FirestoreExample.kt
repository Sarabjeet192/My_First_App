package com.cgc.myfirstapp.ui.firebase_exp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgc.myfirstapp.R
import com.cgc.myfirstapp.databinding.ActivityFirestoreExampleBinding
import com.cgc.myfirstapp.databinding.DialogAddStudentBinding
import com.cgc.myfirstapp.databinding.ItemStudentBinding
import com.cgc.myfirstapp.model.Student
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreExample : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreExampleBinding
    private lateinit var adapter: StudentAdapter

    private val studentList = mutableListOf<Student>()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val database = FirebaseDatabase.getInstance().getReference("students")

    private var dialogBinding: DialogAddStudentBinding? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Ensure this is from google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        adapter = StudentAdapter(studentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnAddStudent.setOnClickListener {
            showAddStudentDialog()
        }

        fetchStudents()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.btnLogout.visibility = View.VISIBLE
        } else {
            binding.btnLogout.visibility = View.GONE
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut()
        }


    }

    private fun fetchStudents() {
        //TODO FIREBASE REALTIME DATABASE
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()
                for (data in snapshot.children) {
                    val student = data.getValue(Student::class.java)
                   // student?.documentId = data.key.toString()
                    student?.let { studentList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FirestoreExample, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })

//        //TODO FIRESTORE
//        db.collection("students").addSnapshotListener { value, error ->
//            if (error != null) {
//                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
//                return@addSnapshotListener
//            }
//
//            studentList.clear()
//            value?.documents?.forEach { document ->
//                val student = document.toObject(Student::class.java)
//                student?.documentId = document.id
//                student?.let { studentList.add(it) }
//            }
//            adapter.notifyDataSetChanged()
//        }
    }

    private fun showAddStudentDialog() {
        dialog = Dialog(this)
        dialogBinding = DialogAddStudentBinding.inflate(LayoutInflater.from(this))
        dialog?.setContentView(dialogBinding!!.root)

        dialogBinding!!.btnSubmit.setOnClickListener {
            addStudent()
        }

        dialog?.show()
    }

    private fun addStudent() {
        val name = dialogBinding?.etName?.text.toString().trim()
        val department = dialogBinding?.etDepartment?.text.toString().trim()
        val rollNumber = dialogBinding?.etRollNumber?.text.toString().trim()

        if (name.isEmpty() || department.isEmpty() || rollNumber.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val studentId = database.push().key ?: return
        val student = Student(name, department, rollNumber, studentId)

        //TODO REALTIME DATABASE
        database.child(studentId).setValue(student)
            .addOnSuccessListener {
                Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show()
            }

//        //TODO FIRESTORE
//        val student = Student(name, department, rollNumber)
//        db.collection("students").add(student)
//            .addOnSuccessListener {
//                Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
//                dialog?.dismiss()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show()
//            }
    }


    inner class StudentAdapter(private val students: List<Student>) :
        RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

        inner class StudentViewHolder(private val binding: ItemStudentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(student: Student) {
                binding.tvName.text = "Name: ${student.name}"
                binding.tvDepartment.text = "Department: ${student.department}"
                binding.tvRollNumber.text = "Roll Number: ${student.rollNumber}"

                binding.btnDelete.setOnClickListener {
                    deleteStudent(student.documentId)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StudentViewHolder(binding)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students[position])
        }

        override fun getItemCount(): Int = students.size
    }

    private fun deleteStudent(documentId: String) {
        //TODO REALTIME DATABASE
        documentId.let {
            database.child(it).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Student Deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
        }

        //TODO FIRESTORE
//        db.collection("students").document(documentId).delete()
//            .addOnSuccessListener {
//                Toast.makeText(this, "Student Deleted", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
//            }
    }
}
