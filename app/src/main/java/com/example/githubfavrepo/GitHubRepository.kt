package com.example.githubfavrepo
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import java.util.concurrent.CountDownLatch

class GitHubRepository(private val context: Context) {

    fun getRepositoryInfo(owner: String, repo: String): Boolean {
        val url = "https://api.github.com/repos/$owner/$repo"
        var insertionSuccessful = false
        val latch = CountDownLatch(1)
        Fuel.get(url).responseJson { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value.obj()
                    val name = data.getString("name")
                    val description = data.getString("description")
                    val link = data.getString("html_url")
                    val ownerName = data.getJSONObject("owner").getString("login")

                    // You can now store the name, description, link, and owner name in SQLite
                    val dbHelper = MyDatabaseHelper(context)
                    val db = dbHelper.writableDatabase

                    // Insert the name, description, link, and owner name into the database
                    val values = ContentValues().apply {
                        put(MyDatabaseHelper.COLUMN_NAME, name)
                        put(MyDatabaseHelper.COLUMN_DESCRIPTION, description)
                        put(MyDatabaseHelper.COLUMN_LINK, link)
                        put(MyDatabaseHelper.COLUMN_OWNER_NAME, ownerName)
                    }

                    val insertionResult = db.insert(MyDatabaseHelper.TABLE_NAME, null, values)
                    db.close()
                    insertionSuccessful = (insertionResult != -1L)
                }
                is Result.Failure -> {
                    println(result.error)
                }
            }
            latch.countDown()
        }
        latch.await()
        return insertionSuccessful
    }
}
