package com.nide.mpass.data.truple

import androidx.room.ColumnInfo

data class PasswordTuple(
    val id: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "category_id") val categoryId: Int?,
    @ColumnInfo(name = "category_name") val categoryName : String,

    ) {
}