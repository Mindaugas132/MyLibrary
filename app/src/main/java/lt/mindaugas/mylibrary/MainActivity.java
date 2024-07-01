package lt.mindaugas.mylibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lt.mindaugas.mylibrary.activities.BookDetails;
import lt.mindaugas.mylibrary.activities.BookEditor;
import lt.mindaugas.mylibrary.adapters.BooksListAdapter;
import lt.mindaugas.mylibrary.database.MainDB;
import lt.mindaugas.mylibrary.models.Book;
import lt.mindaugas.mylibrary.ui.BookClickListener;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    BooksListAdapter booksListAdapter;
    List<Book> books = new ArrayList<>();
    MainDB database;
    FloatingActionButton fabAdd;
    SearchView searchViewMain;
    Book selectBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMain);
        fabAdd = findViewById(R.id.floatButtonMain);
        searchViewMain = findViewById(R.id.searchViewMain);
        database = MainDB.getInstance(this);
        books = database.bookDAO().getAll();

        updateRecycler(books);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookEditor.class);
                startActivityForResult(intent, 101);
            }
        });
        searchViewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                filter(searchText);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Book newBook = (Book) data.getSerializableExtra("book");

                database.bookDAO().insert(newBook);
                books.clear();
                books.addAll(database.bookDAO().getAll());
                booksListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "NEW BOOK ADDED!", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Book updatedBook = (Book) data.getSerializableExtra("book");

                database.bookDAO().update(
                        updatedBook.getId(),
                        updatedBook.getTitle(),
                        updatedBook.getAuthor(),
                        updatedBook.getStars(),
                        updatedBook.getDescription()
                );
                books.clear();
                books.addAll(database.bookDAO().getAll());
                booksListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "BOOK UPDATED!", Toast.LENGTH_LONG).show();
            }

        }
    }

    private final BookClickListener bookClickListener = new BookClickListener() {
        @Override
        public void onClick(Book book) {
            Intent intent = new Intent(MainActivity.this, BookDetails.class);
            intent.putExtra("bookId", book.getId());
            startActivity(intent);
        }

        @Override
        public void onLongClick(Book book, CardView cardView) {
            selectBook = book;
            showPopup(cardView);
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

    private void filter(String searchText) {
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredBooks.add(book);
            }
            booksListAdapter.filterList(filteredBooks);
        }
    }

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_book_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.editBook && selectBook != null) {
            Intent intent = new Intent(MainActivity.this, BookEditor.class);
            intent.putExtra("selectedBook", selectBook);
            startActivityForResult(intent, 102);
            return true;
        }
        if (item.getItemId() == R.id.deleteBook && selectBook != null) {
            database.bookDAO().delete(selectBook);
            books.remove(selectBook);
            booksListAdapter.notifyDataSetChanged();
            Toast.makeText(
                    MainActivity.this, "BOOK DELETED!", Toast.LENGTH_SHORT
            ).show();
            return true;
        }
        return false;
    }
}