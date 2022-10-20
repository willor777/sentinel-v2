package com.willor.lib_data.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.willor.lib_data.domain.dataobjs.entities.TriggerEntity


@Suppress("unused")
@Dao
interface TriggerTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrigger(triggerEntity: TriggerEntity)


    @Delete
    suspend fun deleteTrigger(triggerEntity: TriggerEntity)


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE id = :id")
    suspend fun getTriggerById(id: Int): TriggerEntity?


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE ticker = :ticker")
    suspend fun getAllTriggersForTicker(ticker: String): List<TriggerEntity?>


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE triggerValue > 0")
    suspend fun getAllLongTriggers(): List<TriggerEntity?>


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE triggerValue < 0")
    suspend fun getAllShortTriggers(): List<TriggerEntity?>


    @Query(
        "SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE timestamp BETWEEN :startTime AND :endTime"
    )
    suspend fun getAllTriggersInTimeframe(startTime: Long, endTime: Long): List<TriggerEntity?>


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE triggerValue > 0 AND ticker = :ticker")
    suspend fun getAllLongTriggersForTicker(ticker: String): List<TriggerEntity?>


    @Query("SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE triggerValue < 0 AND ticker = :ticker")
    suspend fun getAllShortTriggersForTicker(ticker: String): List<TriggerEntity?>


    @Query(
        "SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE ticker = :ticker " +
                "AND timestamp < :endTime AND timestamp > :startTime"
    )
    suspend fun getAllTriggersForTickerInTimeframe(ticker: String, startTime: Long, endTime: Long)
    : List<TriggerEntity?>


    @Query(
        "SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE ticker = :ticker AND triggerValue > 0 " +
                "AND timestamp BETWEEN :endTime AND  :startTime"
    )
    suspend fun getAllLongTriggersForTickerInTimeframe(ticker: String, startTime: Long, endTime: Long)
    : List<TriggerEntity?>


    @Query(
        "SELECT * FROM ${StockDataDb.TRIGGER_TABLE} WHERE ticker = :ticker AND triggerValue < 0 " +
                "AND timestamp BETWEEN :endTime AND  :startTime"
    )
    suspend fun getAllShortTriggersForTickerInTimeframe(ticker: String, startTime: Long, endTime: Long)
    : List<TriggerEntity?>

}
















