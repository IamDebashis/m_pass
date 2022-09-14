package com.nide.mpass.data.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nide.mpass.data.module.Category.Companion.CATEGORY_TABLE_NAME


@Entity(tableName = CATEGORY_TABLE_NAME,)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "name") val name : String,
){

    companion object{
        const val  CATEGORY_TABLE_NAME = "category_table"
    }
}
