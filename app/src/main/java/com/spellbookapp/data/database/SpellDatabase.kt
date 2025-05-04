package com.spellbookapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.spellbookapp.data.models.SpellEntity

// Room database that stores spells in the local storage
@Database(entities = [SpellEntity::class], version = 2, exportSchema = false)
abstract class SpellDatabase : RoomDatabase() {

    // Use DAO to access the database
    abstract fun spellDao(): SpellDao

    companion object {
        @Volatile
        private var INSTANCE: SpellDatabase? = null

        // Get the singleton instance of the database
        fun getDatabase(context: Context): SpellDatabase {
            return INSTANCE ?: synchronized(this) {
                // Build the database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpellDatabase::class.java,
                    "spell_database"
                )
                    // rebuild if the schema is changed
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
