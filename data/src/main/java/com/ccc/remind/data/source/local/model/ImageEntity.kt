package com.ccc.remind.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey
    val id: UUID,
    val fileName: String
)