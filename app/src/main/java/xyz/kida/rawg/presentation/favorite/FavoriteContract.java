package xyz.kida.rawg.presentation.favorite;

import java.util.List;

import xyz.kida.rawg.presentation.model.GameViewModel;

public interface FavoriteContract {

    interface View {
        void displayFavorites(List<GameViewModel> gameViewModels);

        void onGameRemoveFromFavorites();
    }

    interface Presenter {
        void attachView(View view);

        void getFavorites();

        void deleteGameFromFavorites(String gameID);

        void detachView();

    }


}
