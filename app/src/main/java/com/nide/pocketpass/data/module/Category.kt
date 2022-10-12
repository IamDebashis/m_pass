package com.nide.pocketpass.data.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nide.pocketpass.data.module.Category.Companion.CATEGORY_TABLE


@Entity(tableName = CATEGORY_TABLE,)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id : Int ,
    @ColumnInfo(name = "category_name") val categoryName : String,
){


    companion object{
        const val  CATEGORY_TABLE = "category_table"
    }
}
