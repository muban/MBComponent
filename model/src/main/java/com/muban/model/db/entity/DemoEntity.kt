package com.muban.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据库数据类例子
 */
@Entity(tableName = "demo")
class DemoEntity(
    @PrimaryKey(autoGenerate = true) var id: Long
)