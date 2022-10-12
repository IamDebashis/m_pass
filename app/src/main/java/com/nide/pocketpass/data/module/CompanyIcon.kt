package com.nide.pocketpass.data.module

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nide.pocketpass.data.module.CompanyIcon.Companion.COMPANY_TABLE

@Entity(tableName = COMPANY_TABLE)
data class CompanyIcon(
    @PrimaryKey
    @ColumnInfo(name = "company_url") val companyUrl: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "company_logo") val companyLogo: Bitmap
) {

    companion object {
        const val COMPANY_TABLE = "company_table"
    }
}