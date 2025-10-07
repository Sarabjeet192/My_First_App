package com.cgc.myfirstapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products")  // Make sure the table name is consistent
data class MyProduct(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,  // Auto-generating ID
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
) : Parcelable
