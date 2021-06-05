package ru.osipov.nmediaapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AppDb private constructor(db: SQLiteDatabase){
    val postDao: PostDao = PostDaoImpl(db)

    companion object{
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb{
            return instance ?: synchronized(this){
                instance ?: AppDb(buildDataBase(context, arrayOf(PostDaoImpl.DDL)))
            }
        }

        private fun buildDataBase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }
}