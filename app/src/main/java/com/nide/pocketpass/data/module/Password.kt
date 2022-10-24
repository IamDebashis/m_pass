package com.nide.pocketpass.data.module

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nide.pocketpass.data.module.Password.Companion.PASSWORD_TABLE


@Entity(
    tableName = PASSWORD_TABLE,
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "password_strength") var strength : Int =0,
    @ColumnInfo(name = "category_id") val categoryId: Int?,
    @ColumnInfo(name = "create_time") val createTime: Long = 0L,
    @ColumnInfo(name = "company_icon") val icon: Bitmap
) {


    companion object {
        const val PASSWORD_TABLE = "password_table"
    }

}
