package lt.mindaugas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lt.mindaugas.models.Book;
import lt.mindaugas.mylibrary.R;
import lt.mindaugas.ui.BookClickListener;

public class BooksListAdapter extends RecyclerView.Adapter<BooksViewHolder> {
    Context context;
    List<Book> booksList;
    BookClickListener listener;

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BooksViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.book_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.titleView.setText(booksList.get(position).getTitle());
        holder.starsView.setText(
                String.valueOf(booksList.get(position).getStars()).concat(" ★")
        );
        holder.authorView.setText(booksList.get(position).getAuthor());

        holder.bookContainer.setOnClickListener(
                view -> listener.onClick(booksList.get(holder.getAdapterPosition()))
        );

        holder.bookContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(
                        booksList.get(holder.getAdapterPosition()), holder.bookContainer
                );
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return booksList.size();
    }
}

class BooksViewHolder extends RecyclerView.ViewHolder {
    CardView bookContainer;
    TextView starsView, titleView, authorView;

    public BooksViewHolder(@NonNull View itemView) {
        super(itemView);

        bookContainer = itemView.findViewById(R.id.productContainer);
        starsView = itemView.findViewById(R.id.starsView);
        titleView = itemView.findViewById(R.id.titleView);
        authorView = itemView.findViewById(R.id.authorView);
    }
}
