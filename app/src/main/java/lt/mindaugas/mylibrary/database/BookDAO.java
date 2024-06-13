package lt.mindaugas.mylibrary.database;

import static androidx.room.OnConflictStrategy.REPLACE;
import static lt.mindaugas.mylibrary.models.Book.PRODUCT_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import lt.mindaugas.mylibrary.models.Book;

@Dao
public interface BookDAO {

    @Insert(onConflict = REPLACE)
    void insert(Book book);

    @Query("SELECT * FROM " + PRODUCT_TABLE_NAME + " ORDER BY title ASC")
    List<Book> getAll();

    @Query("UPDATE books SET title = :title, author = :author, stars = :stars, " +
            "description = :description WHERE book_id = :id")
    void update(int id, String title, String author, int stars, String description);

    @Query("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE book_id =:id")
    Book getById(int id);

    @Delete
    void delete(Book book);
}
