package com.example.githubfavrepo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class AddRepo : AppCompatActivity() {
   private lateinit var gitHubRepository: GitHubRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addrepolayout)
        val actionBar: ActionBar? = supportActionBar
         supportActionBar?.title = "Add Repository"
        actionBar?.setDisplayHomeAsUpEnabled(true)
       // gitHubRepository = GitHubRepository(this)
        val addButton = findViewById<Button>(R.id.btn_add)
        val ownerEditText = findViewById<EditText>(R.id.et_owner)
        val repoEditText=findViewById<EditText>(R.id.et_repo)
        addButton.setOnClickListener {
            // add your code here to handle the button click event

            val owner = ownerEditText.text.toString()
            val repoName=repoEditText.text.toString()
            val gitHubRepository = GitHubRepository(this)
            val insertionSuccessful = gitHubRepository.getRepositoryInfo(owner, repoName)

            if (insertionSuccessful) {
                // Display a success message
                Toast.makeText(this, "Repository added successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Finish the activity
                finish()
            } else {
                // Display an error message
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Failed to add repository. Please check owner and repo name and try again.")
                builder.setPositiveButton("OK", null)
                builder.show()
            }


        }

    }
    override fun onSupportNavigateUp(): Boolean {
        // This method is called when the back button is pressed.
        // Navigate back to the MainActivity
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
}
}