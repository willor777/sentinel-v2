package com.willor.lib_data.domain.dataobjs.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.willor.lib_data.data.local.db.StockDataDb


@Entity(tableName = StockDataDb.TRIGGER_TABLE)
data class TriggerEntity(
    @ColumnInfo val ticker: String,
    @ColumnInfo val triggerAnalysisName: String,
    @ColumnInfo val triggerAnalysisDesc: String,
    @ColumnInfo val triggerValue: Int,
    @ColumnInfo val triggerStrengthPercentage: Double,
    @ColumnInfo val suggestedStop: Double,
    @ColumnInfo val suggestedTakeProfit: Double,
    @ColumnInfo val shouldCloseLong: Boolean,
    @ColumnInfo val shouldCloseShort: Boolean,
    @ColumnInfo val stockPriceAtTime: Double,
    @ColumnInfo val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    )


//val triggerStrengthPercentage: Double,
//val suggestedStop: Double,
//val suggestedTakeProfit: Double,
//val shouldCloseLongPositions: Boolean,
//val shouldCloseShortPositions: Boolean,
//val timestamp: Long
//)