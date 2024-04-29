package jp.co.yumemi.android.code_check.views.favourite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.repository.FavoriteAccountRepository
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteAccountRepository: FavoriteAccountRepository
) : ViewModel() {
}