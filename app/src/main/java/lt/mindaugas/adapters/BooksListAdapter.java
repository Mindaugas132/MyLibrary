package lt.mindaugas.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import lt.mindaugas.mylibrary.R;

public class BooksListAdapter extends RecyclerView.ViewHolder {
    public BooksListAdapter(@NonNull View itemView) {
        super(itemView);
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
