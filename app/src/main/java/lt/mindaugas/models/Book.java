package lt.mindaugas.models;

import static lt.mindaugas.models.Book.PRODUCT_TABLE_NAME;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Entity(tableName = PRODUCT_TABLE_NAME)
@Data
public class Book implements Serializable {

    @Ignore
    public static final String PRODUCT_TABLE_NAME = "books";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "stars")
    private int stars;

    @ColumnInfo(name = "description")
    private String description;

}
