package com.willor.lib_data.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.willor.lib_data.domain.dataobjs.entities.StockChartEntity
import com.willor.lib_data.domain.dataobjs.entities.TriggerEntity


@Database(
    entities = [
        StockChartEntity::class,
        TriggerEntity::class
               ],
    version = StockDataDb.DB_VERSION
)
abstract class StockDataDb: RoomDatabase() {

    abstract fun getChartTableDao(): ChartTableDao
    abstract fun getTriggerTableDao(): TriggerTableDao

    companion object{
        const val DB_NAME = "StockDataDb"
        const val DB_VERSION = 1
        const val CHART_TABLE = "CHARTS"
        const val TRIGGER_TABLE = "TRIGGERS"
    }
}