package com.shishusneh.core.ui

import android.app.Application
import androidx.lifecycle.*
import com.shishusneh.core.data.*
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BabyViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        BabyDatabase::class.java, "baby-db"
    ).fallbackToDestructiveMigration()
     .build()
    
    val babyDao = db.babyDao()
    
    val growthRecords: Flow<List<GrowthRecord>> = babyDao.getAllGrowthRecords()
    val milestones: Flow<List<Milestone>> = babyDao.getAllMilestones()
    val vaccinations: Flow<List<Vaccination>> = babyDao.getAllVaccinations()

    fun addGrowthRecord(weight: Float, height: Float) {
        viewModelScope.launch {
            babyDao.insertGrowthRecord(GrowthRecord(date = System.currentTimeMillis(), weight = weight, height = height))
        }
    }

    fun toggleMilestone(milestone: Milestone) {
        viewModelScope.launch {
            babyDao.insertMilestone(milestone.copy(
                isAchieved = !milestone.isAchieved,
                achievedDate = if (!milestone.isAchieved) System.currentTimeMillis() else null
            ))
        }
    }

    fun toggleVaccination(vaccination: Vaccination) {
        viewModelScope.launch {
            babyDao.insertVaccination(vaccination.copy(isDone = !vaccination.isDone))
        }
    }

    fun initDefaults() {
        viewModelScope.launch {
            val currentMilestones = milestones.first()
            if (currentMilestones.isEmpty()) {
                val defaultMilestones = listOf(
                    Milestone("P1", "Holding head up", "Physical"),
                    Milestone("P2", "Rolling over", "Physical"),
                    Milestone("S1", "Social smiling", "Social"),
                    Milestone("C1", "Responding to sounds", "Communication")
                )
                defaultMilestones.forEach { babyDao.insertMilestone(it) }
            }
        }
    }

    fun scheduleVaccinations(birthDateMillis: Long) {
        viewModelScope.launch {
            val schedule = listOf(
                Triple("BCG", "Tuberculosis", 0L),
                Triple("OPV-0", "Polio", 0L),
                Triple("Hep-B", "Hepatitis B", 0L),
                Triple("Penta-1", "DTP, HepB, Hib", 42L),
                Triple("OPV-1", "Polio", 42L),
                Triple("ROTA-1", "Diarrhea", 42L)
            )
            
            schedule.forEach { (name, disease, days) ->
                val dueDate = birthDateMillis + TimeUnit.DAYS.toMillis(days)
                val label = if (days == 0L) "At Birth" else "${days/7} Weeks"
                babyDao.insertVaccination(Vaccination(
                    id = name,
                    name = name,
                    disease = disease,
                    dueDateMillis = dueDate,
                    dateLabel = label
                ))
            }
        }
    }
}
