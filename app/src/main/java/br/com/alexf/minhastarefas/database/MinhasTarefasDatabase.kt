package br.com.alexf.minhastarefas.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexf.minhastarefas.database.dao.TaskDao
import br.com.alexf.minhastarefas.database.entities.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1, 2)
    ]
)
abstract class MinhasTarefasDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}