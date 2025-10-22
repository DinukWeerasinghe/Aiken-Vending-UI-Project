package com.aiken.vendingmachine

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aiken.vendingmachine.ui.navigation.MalibanVendingMachineNavHost
import com.aiken.vendingmachine.ui.navigation.VendingMachineNavHost
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            VendingMachineTheme {
                VendingMachineApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VendingMachineApp() {
    VendingMachineNavHost()
    //MalibanVendingMachineNavHost()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VendingMachineTheme {
        VendingMachineApp()
    }
}