package br.com.alexf.minhastarefas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexf.minhastarefas.database.dao.TaskDao
import br.com.alexf.minhastarefas.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class MinhasTarefasDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}