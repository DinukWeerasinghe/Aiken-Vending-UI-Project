package com.aiken.vendingmachine

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.data.database.DatabaseInitializer
import com.aiken.vendingmachine.ui.navigation.VendingMachineNavHost
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import com.aiken.vendingmachine.utils.ParamConst
import com.aiken.vendingmachine.utils.SharedParams
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Kick off DB initialization (non-blocking)
        DatabaseInitializer.initDatabase(this)

        setContent {
            VendingMachineTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppEntry()
                }
            }
        }
    }
}

@Composable
fun AppEntry() {
    // Check DB readiness via SharedParams boolean flag (set by your DB callbacks)
    val context = LocalContext.current
    var dbReady by remember { mutableStateOf(false) }

    // Launch a coroutine to poll SharedParams for DB initialization state.
    // This polls every 250ms for up to a safe limit. You can adjust polling interval.
    LaunchedEffect(Unit) {
        // small initial delay to allow DB initializer to start
        delay(100)
        // Poll until DB_INITIALIZED becomes true or until timeout
        val timeoutMillis = 30_000L
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMillis) {
            try {
                if (SharedParams.contains(ParamConst.DB_INITIALIZED) && SharedParams.getBoolean(ParamConst.DB_INITIALIZED, false)) {
                    dbReady = true
                    break
                }
            } catch (e: Exception) {
                // If SharedParams isn't ready yet, just continue polling
            }
            delay(250)
        }
        // If timeout reached and DB not ready, you may still proceed (dbReady=false),
        // or show an error / retry. Here we proceed to main UI even if not ready to avoid blocking forever.
        if (!dbReady) {
            // Optional: keep false to show error UI instead. For now proceed anyway:
            dbReady = true
        }
    }

    if (!dbReady) {
        DatabaseLoadingScreen()
    } else {
        VendingMachineApp() // main nav host
    }
}

@Composable
fun DatabaseLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = "Initializing database…",
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun VendingMachineApp() {
    VendingMachineNavHost()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VendingMachineTheme {
        VendingMachineApp()
    }
}
