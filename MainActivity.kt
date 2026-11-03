package com.example.nutrired

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.nutrired.ui.donation.DonationViewModel
import com.example.nutrired.ui.donation.WindowSizeClass
import com.example.nutrired.ui.navigation.NutriNavGraph
import com.example.nutrired.ui.inventory.InventoryViewModel

class MainActivity : ComponentActivity() {

    private val donationViewModel: DonationViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    // LÓGICA SIMPLIFICADA PARA DETECTAR RESPONSIVIDAD DE PANTALLA
                    val metrics = resources.displayMetrics
                    val widthDp = metrics.widthPixels / metrics.density
                    val windowSize = if (widthDp > 600) WindowSizeClass.EXPANDED else WindowSizeClass.COMPACT

                    // Llamada
                    NutriNavGraph(viewModel = donationViewModel, inventoryViewModel = inventoryViewModel, windowSize = windowSize
                    )
                }
            }
        }
    }
}
