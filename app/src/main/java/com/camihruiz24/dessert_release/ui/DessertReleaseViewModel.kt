package com.camihruiz24.dessert_release.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camihruiz24.dessert_release.R
import com.camihruiz24.dessert_release.data.PreferencesStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * View model of Dessert Release components
 */
@HiltViewModel
class DessertReleaseViewModel @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
) : ViewModel() {

    /** UI states access for various [DessertReleaseUiState] */
    val uiState: StateFlow<DessertReleaseUiState> =
        preferencesStorage.isDarkTheme
            .combine(preferencesStorage.isLinearLayout) { isDarkTheme, isLinearLayout ->
                DessertReleaseUiState(
                    isDarkTheme = isDarkTheme,
                    isLinearLayout = isLinearLayout
                )
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = DessertReleaseUiState(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    /**
     * [selectLayout] change the layout and icons accordingly and
     * save the selection in DataStore through [preferencesStorage]
     */
    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            preferencesStorage.setLinearLayoutPreference(isLinearLayout)
        }
    }

}

/**
 * Data class containing various UI States for Dessert Release screens
 */
data class DessertReleaseUiState(
    val isLinearLayout: Boolean = false,
    val layoutContentDescription: Int =
        if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val layoutIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout,

    val isDarkTheme: Boolean = true,
    val themeContentDescription: Int =
        if (isDarkTheme) R.string.light_theme_toggle else R.string.dark_theme_toggle,
    val themeIcon: ImageVector =
        if (isDarkTheme) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
)