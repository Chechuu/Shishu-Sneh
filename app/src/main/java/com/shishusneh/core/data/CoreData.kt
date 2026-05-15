package com.shishusneh.core.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "growth_records")
data class GrowthRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val weight: Float,
    val height: Float
)

@Entity(tableName = "milestones")
data class Milestone(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val isAchieved: Boolean = false,
    val achievedDate: Long? = null
)

@Entity(tableName = "vaccinations")
data class Vaccination(
    @PrimaryKey val id: String,
    val name: String,
    val disease: String,
    val dueDateMillis: Long,
    val isDone: Boolean = false,
    val dateLabel: String // e.g. "6 Weeks"
)

@Dao
interface BabyDao {
    @Query("SELECT * FROM growth_records ORDER BY date ASC")
    fun getAllGrowthRecords(): Flow<List<GrowthRecord>>

    @Insert
    suspend fun insertGrowthRecord(record: GrowthRecord)

    @Query("SELECT * FROM milestones")
    fun getAllMilestones(): Flow<List<Milestone>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: Milestone)

    @Query("SELECT * FROM vaccinations ORDER BY dueDateMillis ASC")
    fun getAllVaccinations(): Flow<List<Vaccination>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccination(vaccination: Vaccination)
}

@Database(entities = [GrowthRecord::class, Milestone::class, Vaccination::class], version = 2)
abstract class BabyDatabase : RoomDatabase() {
    abstract fun babyDao(): BabyDao
}
