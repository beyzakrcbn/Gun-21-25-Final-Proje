package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.ui.theme.ECommerceAppTheme
import com.example.ecommerceapp.screens.*
import com.example.ecommerceapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECommerceAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Login : Screen("login", "Giriş", Icons.Filled.Person)
    object Home : Screen("home", "Ürünler", Icons.Filled.Home)
    object Favorites : Screen("favorites", "Favoriler", Icons.Filled.Favorite)
    object Cart : Screen("cart", "Sepet", Icons.Filled.ShoppingCart)
    object ProductDetail : Screen("product_detail", "Ürün Detayı", Icons.Filled.Info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen { isLoggedIn = true }
    } else {
        val bottomBarScreens = listOf(
            Screen.Home,
            Screen.Favorites,
            Screen.Cart
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    bottomBarScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Home.route) {
                    ProductListScreen(viewModel, navController)
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(viewModel)
                }
                composable(Screen.Cart.route) {
                    CartScreen(viewModel)
                }
                composable("${Screen.ProductDetail.route}/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                    ProductDetailScreen(viewModel, productId, navController)
                }
            }
        }
    }
}