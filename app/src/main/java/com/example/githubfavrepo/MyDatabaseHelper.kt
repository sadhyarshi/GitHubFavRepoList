package com.example.githubfavrepo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "my_database.db"
        const val TABLE_NAME = "GitData"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LINK = "link"
        const val COLUMN_OWNER_NAME = "ownername"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_LINK TEXT,
                $COLUMN_OWNER_NAME TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllData(): List<Pair<Long, Quadruple<String, String, String, String>>> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, null, null,
            null, null, null, null
        )
        val dataList = mutableListOf<Pair<Long, Quadruple<String, String, String, String>>>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val link = getString(getColumnIndexOrThrow(COLUMN_LINK))
                val ownername = getString(getColumnIndexOrThrow(COLUMN_OWNER_NAME))
                dataList.add(Pair(id, Quadruple(name, description, link, ownername)))
            }
        }
        return dataList
    }
    fun getData(): List<Pair<Long, Pair<String, String>>> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, null, null,
            null, null, null, null
        )
        val dataList = mutableListOf<Pair<Long, Pair<String, String>>>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                dataList.add(Pair(id, Pair(name, description)))
            }
        }
        return dataList
    }


       // delete function to delete may be further required if user wants to remove one of repo or all from the list
    fun deleteData(id: Long): Int {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete(TABLE_NAME, selection, selectionArgs)
    }



}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

