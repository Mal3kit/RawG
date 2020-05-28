package xyz.kida.rawg.presentation.search;

import java.util.List;

import xyz.kida.rawg.presentation.model.GameViewModel;

public interface SearchContract {

    interface View {
        void showGames(List<GameViewModel> gameViewModels);

        void onGameAddToFavorites();
    }

    interface Presenter {
        void searchGames(String keywords);

        void attachView(View view);

        void cancelSubscription();

        void addGameToFavorites(String gameID);

        void detachView();
    }

}
