package com.ariete.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ariete.notes.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        /**
            * The @Volatile annotation is used to mark
            * a variable as volatile.
            *
            * Volatile is a keyword that indicates that
            * a variable may be modified asynchronously
            * by multiple threads.
            *
            * When a variable is marked as volatile,
            * it ensures that any changes made to it are immediately visible
            * to other threads.
            * --------------------------------------------------------------------
            * Аннотация @Volatile используется для обозначения
            * переменной как volatile (изменчивый).
            *
            * Volatile - это ключевое слово, которое указывает,
            * что переменная может быть изменена асинхронно
            * несколькими потоками.
            *
            * Когда переменная помечается как volatile,
            * это гарантирует, что любые внесенные изменения
            * будут немедленно видны
            * другим потокам.
        */
        private var instance: NoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        /**
            * This code defines an "invoke" operator function
            * that creates and returns a database instance.
            *
            * The first line checks if the "instance" variable has
            * a non-null value and returns it if it is not null.
            * This is done to prevent re-creation
            * of the database instance.
            *
            * If the "instance" is null,
            * the code enters the synchronized block using
            * the "LOCK" object as the lock.
            * (This ensures that only one thread can access
            * to modify an instance of the database.)
            *
            * Inside the synchronized block,
            * the code checks again if the "instance" is still null.
            * If it is null, the createDatabase() function
            * is called to create a new database.
            * The "also" function is then used to assign the newly
            * created database to "instance" and return it.
            * ------------------------------------------------------
            * Этот код определяет функцию - оператор invoke(),
            * которая создает
            * и вовзвращает экземпляр базы данных.
            *
            * В первой строчке происходит проверка переменной instance на null
            * и возврат этой переменной, если она != null.
            * Это делается для предотвращения пересоздания
            * экземпляра базы данных.
            *
            * Если instance == null, запускается блок кода,
            * выполняющийся синхронно (блок кода A) и
            * использующий LOCK как объект блокировки.
            * (Это гарантирует, что только один поток может получить доступ
            * к изменению экземпляра базы данных.)
            *
            * Внутри блокa кода A
            * проверяется, содержит ли instance null.
            * Если да - вызывается функция createDatabase() для создания
            * нового экземпляра базы данных.
            * Затем используется фнукция also, которая помещает экземпляр созданной
            * базы данных в instance и возвращает эту переменную.
        */

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }
}