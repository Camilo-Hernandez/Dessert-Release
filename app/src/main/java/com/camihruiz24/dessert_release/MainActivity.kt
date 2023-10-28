package com.camihruiz24.dessert_release

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.camihruiz24.dessert_release.ui.DessertReleaseApp
import com.camihruiz24.dessert_release.ui.theme.DessertReleaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DessertReleaseTheme {
                DessertReleaseApp()
            }
        }
    }
}
