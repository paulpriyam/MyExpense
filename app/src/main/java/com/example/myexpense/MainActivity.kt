package com.example.myexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myexpense.navigation.ExpenseNavHost
import com.example.myexpense.ui.components.ExpenseBottomNavigationBar
import com.example.myexpense.ui.screens.Screens
import com.example.myexpense.ui.screens.settings.EditProfileScreen
import com.example.myexpense.ui.screens.settings.viewmodel.SettingsScreenViewModel
import com.example.myexpense.ui.theme.MyExpenseTheme
import com.example.myexpense.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpenseApp(preferenceManager)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExpenseApp(preferenceManager: PreferenceManager? = null) {
    MyExpenseTheme {
        val navController = rememberNavController()
        val viewModel: SettingsScreenViewModel = hiltViewModel()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        // Get local instance of PreferenceManager if not provided (for Preview)
        val prefs = remember { preferenceManager ?: PreferenceManager(context) }

        // State to control the bottom sheet visibility based on userId existence
        var showBottomSheet by remember { mutableStateOf(prefs.getUserId() == null) }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val screens = listOf(
            Screens.DashBoard,
            Screens.Investment,
            Screens.Expense,
            Screens.Settings,
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                ExpenseBottomNavigationBar(
                    navController = navController,
                    screens = screens
                )
            }
        ) { innerPadding ->
            ExpenseNavHost(
                navController = navController,
                startDestination = Screens.DashBoard.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )

            // Show EditProfileScreen as BottomSheet if no profile exists
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        // Don't allow dismiss by gesture if no userId exists (force user to create profile)
                        if (prefs.getUserId() != null) {
                            showBottomSheet = false
                        }
                    },
                    sheetState = bottomSheetState,
                ) {
                    EditProfileScreen(
                        viewModel = viewModel,
                        onProfileSaved = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                showBottomSheet = false
                            }
                        }
                    )
                }
            }
        }
    }
}
