package xyz.kida.rawg.presentation.video;

import java.util.List;

import xyz.kida.rawg.presentation.model.GameVideoViewModel;

public interface GameVideoContract {

    interface View {
        void displayVideosForFavoritesGames(List<GameVideoViewModel> gameVideoViewModels);
    }

    interface Presenter {
        void attachView(View view);

        void getVideosForFavorites();

        void cancelSubscription();

        void detachView();

    }
}
