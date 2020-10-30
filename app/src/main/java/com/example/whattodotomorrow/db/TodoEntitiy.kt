package com.example.whattodotomorrow.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
 data class TodoEntitiy (
    @PrimaryKey(autoGenerate = true) var uid: Int?,
    @ColumnInfo(name = "time") var time: String?,
    @ColumnInfo(name = "content") var content: String?
)
