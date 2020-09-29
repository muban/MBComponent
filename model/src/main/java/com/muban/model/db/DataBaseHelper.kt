package com.muban.model.db

import android.content.Context
import androidx.room.Room
import com.muban.model.db.database.DemoDatabase
import kotlin.concurrent.thread

/**
 * 数据库帮助类
 */
class DataBaseHelper {
    private var isInit = false
    private var demoDb: DemoDatabase? = null

    companion object {
        val instance: DataBaseHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataBaseHelper()
        }
    }

    /**
     * 在application中创建并初始化数据库
     */
    fun init(context: Context) {
        synchronized(this) {
            thread(start = true) {
                if (isInit) return@thread
                demoDb = Room.databaseBuilder(context, DemoDatabase::class.java, "demo.db").build()
                isInit = true
            }
        }
    }

    fun demoDb(): DemoDatabase {
        if (demoDb == null) throw NullPointerException("DemoDatabase 没有初始化")
        return demoDb!!
    }
}