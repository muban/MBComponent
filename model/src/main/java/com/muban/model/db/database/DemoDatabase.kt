package com.muban.model.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muban.model.db.dao.DemoDao
import com.muban.model.db.entity.DemoEntity

/**
 * 数据库本体例子
 */
@Database(entities = [DemoEntity::class], version = 1, exportSchema = false)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun demoDao(): DemoDao
}