package lt.mindaugas.database;

import static lt.mindaugas.database.MainDB.DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lt.mindaugas.models.Book;

@Database(entities = Book.class,
        version = DATABASE_VERSION,
        exportSchema = false)
public abstract class MainDB extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main.db";
    public static MainDB instance;

    public synchronized static MainDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MainDB.class,
                            DATABASE_NAME
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract BookDAO bookDAO();

}
