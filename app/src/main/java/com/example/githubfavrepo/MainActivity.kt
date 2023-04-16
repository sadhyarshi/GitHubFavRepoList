package com.example.githubfavrepo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //code for button with + symbol
        val recyclerView = findViewById<RecyclerView>(R.id.repo_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        val dbHelper = MyDatabaseHelper(applicationContext)
        val dataList = dbHelper.getAllData()
        adapter.addData(dataList)
        val repoList: RecyclerView = findViewById(R.id.repo_list)
        val emptyView: LinearLayout = findViewById(R.id.empty_view)
        val emptyLabel: TextView = findViewById(R.id.empty_label)
        val addNowButton: Button = findViewById(R.id.btn_add_now)

        if (adapter.itemCount > 0) {
            emptyView.visibility = View.GONE
            emptyLabel.visibility = View.GONE
            addNowButton.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
            emptyLabel.visibility = View.VISIBLE
            addNowButton.visibility = View.VISIBLE
        }

        val btn = findViewById<ImageButton>(R.id.btn_add_repo)
        btn.setOnClickListener{
            val intent = Intent(this, AddRepo::class.java)
            startActivity(intent)
        }
        //code for empty button and textBox
        val btnAddNow = findViewById<Button>(R.id.btn_add_now)
        btnAddNow.setOnClickListener {
            val intent = Intent(this, AddRepo::class.java)
            startActivity(intent)

        }

    }
}