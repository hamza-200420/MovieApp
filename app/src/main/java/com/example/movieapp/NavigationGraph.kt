package com.example.movieapp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.movieapp.presentation.DetailsScreen
import com.example.movieapp.presentation.ExploreScreen
import com.example.movieapp.presentation.HomeScreen
import com.example.movieapp.presentation.OnBoardingScreen
//import com.example.movieapp.presentation.FavouriteScreen
import com.example.movieapp.presentation.SplashScreen
import com.example.movieapp.presentation.TopTenScreen
import com.example.movieapp.presentation.UpcomingScreen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.presentation.FavouriteScreen

object NavRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "on_boarding"
    const val HOME = "home"
    const val EXPLORE = "explore"
    const val FAVOURITES = "favourites"
    const val TOPTEN = "top_ten"
    const val NEWRELEASES = "new_releases"

    //    const val EXPLORE ="explore"
    const val MOVIE_DETAILS = "movie_details/{movieId}"

    fun movieDetails(movieId: Int) = "movie_details/$movieId"
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavItem(NavRoutes.HOME, "Home", R.drawable.home)
    object Explore : BottomNavItem(NavRoutes.EXPLORE, "Explore", R.drawable.explore)
    object Favourite : BottomNavItem(NavRoutes.FAVOURITES, "Favourite", R.drawable.profile)
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        NavRoutes.HOME,
        NavRoutes.EXPLORE,
        NavRoutes.FAVOURITES
    )

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.SPLASH) {
                SplashScreen(
                    onNavigateToOnboarding = {
                        navController.navigate(NavRoutes.ONBOARDING) {
                            popUpTo(NavRoutes.SPLASH) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(NavRoutes.HOME) {
                            popUpTo(NavRoutes.SPLASH) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(NavRoutes.ONBOARDING) {
                OnBoardingScreen(onNavigateToHome = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.ONBOARDING) {
                            inclusive = true
                        }
                    }
                })
            }

            composable(NavRoutes.HOME) {
                HomeScreen(
                    onToptenClick = { navController.navigate(NavRoutes.TOPTEN) },
                    onUpcomingCLick = {
                        navController.navigate(NavRoutes.NEWRELEASES)
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.movieDetails(movieId))
                    }
                )
            }

            composable(NavRoutes.EXPLORE) {
                ExploreScreen()
            }

            composable(NavRoutes.FAVOURITES) {
                FavouriteScreen(onItemCLick = {movieId ->
                    navController.navigate(NavRoutes.movieDetails(movieId))})
            }

            composable(NavRoutes.TOPTEN) {
                TopTenScreen(
                    onBackClick = { navController.popBackStack() },
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.movieDetails(movieId))
                    }
                )
            }

            composable(NavRoutes.NEWRELEASES) {
                UpcomingScreen(
                    onBackClick = { navController.popBackStack() },
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.movieDetails(movieId))
                    }
                )
            }

            composable(
                route = NavRoutes.MOVIE_DETAILS,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                DetailsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.movieDetails(movieId))
                    }
                )
            }
            composable(route = NavRoutes.EXPLORE) {
                ExploreScreen(onMovieClick = { movieId ->
                    navController.navigate(NavRoutes.movieDetails(movieId))
                })
            }
        }
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Explore,
        BottomNavItem.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.Transparent,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFE21221),
                    selectedTextColor = Color(0xFFE21221),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(NavRoutes.HOME) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}