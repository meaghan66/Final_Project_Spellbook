package com.spellbookapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.spellbookapp.data.models.SpellEntity

@Database(entities = [SpellEntity::class], version = 1, exportSchema = false)
abstract class SpellDatabase : RoomDatabase() {

    abstract fun spellDao(): SpellDao

    companion object {
        @Volatile
        private var INSTANCE: SpellDatabase? = null

        fun getDatabase(context: Context): SpellDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpellDatabase::class.java,
                    "spell_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
