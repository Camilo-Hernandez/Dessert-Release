package com.camihruiz24.dessert_release

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.camihruiz24.dessert_release.data.PreferencesStorage
import com.camihruiz24.dessert_release.ui.DessertReleaseApp
import com.camihruiz24.dessert_release.ui.theme.DessertReleaseTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val darkTheme by mainViewModel.darkThemeState.collectAsStateWithLifecycle()
            DessertReleaseTheme(darkTheme = darkTheme) {
                DessertReleaseApp(
                    setDarkTheme = { mainViewModel.setDarkThemePreference(!darkTheme) }
                )
            }
        }
    }
}

/**
 * The [MainViewModel] does not control the UI state at all. This view model only sends the
 * signal to the [PreferencesStorage] to update the [PreferencesStorage.isDarkTheme] variable and
 * collect the value in a flow.
 * The [DessertReleaseViewModel] is the only view model responsible for handling the UI state.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : ViewModel() {
    val darkThemeState: StateFlow<Boolean> = preferencesStorage.isDarkTheme
        .stateIn(
            scope = viewModelScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    /**
     * [setDarkThemePreference] change the layout and icons accordingly and
     * save the selection in DataStore through [PreferencesStorage]
     */
    fun setDarkThemePreference(darkTheme: Boolean) {
        viewModelScope.launch {
            preferencesStorage.setDarkThemePreference(darkTheme)
        }
    }
}
