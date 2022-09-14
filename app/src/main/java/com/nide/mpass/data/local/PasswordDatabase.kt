package com.nide.mpass.data.local

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nide.mpass.R
import com.nide.mpass.data.local.dao.CategoryDao
import com.nide.mpass.data.local.dao.PasswordDao
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Password::class, Category::class], version = 2, exportSchema = false)
abstract class PasswordDatabase() : RoomDatabase() {


    abstract fun passwordDao(): PasswordDao

    abstract fun categoryDao(): CategoryDao


    companion object {
        const val DATABASE_NAME = "Password_db"

        private var INSTANCE: PasswordDatabase? = null

        fun getInstance(context: Context): PasswordDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(CategoryCallback(context))
                        .build()
                }
            }
            return INSTANCE!!
        }

    }

    private class CategoryCallback(private val context: Context) : Callback() {
        private val TAG = this.javaClass.name

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val categoryDao = INSTANCE?.categoryDao()
//            if (categoryDao != null) {
                Log.i(TAG, "onCreate: ")
                val defaultCategory = context.resources.getStringArray(R.array.category_name)
                val categoryList = mutableListOf<Category>()
                for (name in defaultCategory) {
                    val category = Category(0, name)
                    categoryList.add(category)
                }
                CoroutineScope(Dispatchers.IO).launch {
                    categoryDao?.insertCategory(*categoryList.toTypedArray())
                }
//            }
        }
    }

}
