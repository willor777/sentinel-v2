package com.willor.lib_data.domain.dataobjs.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.willor.lib_data.data.local.db.StockDataDb


@Entity(tableName = StockDataDb.TRIGGER_TABLE)
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val ticker: String,
    @ColumnInfo val triggerAnalysisName: String,
    @ColumnInfo val triggerAnalysisDesc: String,
    @ColumnInfo val triggerValue: Int,
    @ColumnInfo val stockPriceAtTime: Double,
    @ColumnInfo val pctGainAtTime: Double,
    @ColumnInfo val dollarGainAtTime: Double,
    @ColumnInfo val timestamp: Long,
    @ColumnInfo val curVolAvgVolRatio: Double
    )
