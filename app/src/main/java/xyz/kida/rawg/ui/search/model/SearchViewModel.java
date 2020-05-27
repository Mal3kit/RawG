package xyz.kida.rawg.ui.search.model;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.kida.rawg.R;

public class SearchViewModel extends RecyclerView.ViewHolder {


    @BindView(R.id.add_button)
    Button addButton;

    private SearchListener searchListener;
    private View view;

    public SearchViewModel(View view, SearchListener searchListener) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
        setupListeners();
        this.searchListener = searchListener;
    }

    private void setupListeners() {
    }
}