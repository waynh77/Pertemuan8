package com.example.pertemuan8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(emptyList()) { user ->
            Toast.makeText(this, "Clicked: ${user.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = userAdapter

        fetchUsers()
    }
    private fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = RetrofitInstance.api.getUsers()
                withContext(Dispatchers.Main) {
                    userAdapter.updateData(users)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

