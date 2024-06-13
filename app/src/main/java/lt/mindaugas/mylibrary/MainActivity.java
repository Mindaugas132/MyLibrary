package lt.mindaugas.mylibrary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lt.mindaugas.adapters.BooksListAdapter;
import lt.mindaugas.database.MainDB;
import lt.mindaugas.models.Book;
import lt.mindaugas.ui.BookClickListener;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BooksListAdapter booksListAdapter;
    List<Book> books = new ArrayList<>();
    MainDB database;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMain);
        fabAdd = findViewById(R.id.floatButtonMain);
        database = MainDB.getInstance(this);
        books = database.bookDAO().getAll();

        updateRecycler(books);
    }

    private final BookClickListener bookClickListener = new BookClickListener() {
        @Override
        public void onClick(Book book) {
        }

        @Override
        public void onLongClick(Book book, CardView cardView) {
        }
    };

    private void updateRecycler(List<Book> books) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        );
        booksListAdapter =
                new BooksListAdapter(MainActivity.this, books, bookClickListener);

        recyclerView.setAdapter(booksListAdapter);
    }
}