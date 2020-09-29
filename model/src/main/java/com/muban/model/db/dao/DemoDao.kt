package com.muban.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muban.model.db.entity.DemoEntity

/**
 * 数据库操作类例子
 */
@Dao
interface DemoDao {

    @Insert(entity = DemoEntity::class)
    suspend fun insert(entity: DemoEntity)

    @Query("select * from demo")
    suspend fun queryAllLogs(): List<DemoEntity>

    @Query("delete from demo")
    suspend fun clearTable()
}