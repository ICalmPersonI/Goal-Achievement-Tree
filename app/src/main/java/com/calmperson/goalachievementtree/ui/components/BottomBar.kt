package com.calmperson.goalachievementtree.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.calmperson.goalachievementtree.ui.components.screen.Screen

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navBackStackEntry: State<NavBackStackEntry?>,
    screens: List<Screen>,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val currDestination = navBackStackEntry.value?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.testTag(screen.route),
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(screen.icon),
                        contentDescription = null
                    )
                },
                label = { },
                alwaysShowLabel = false,
                selected = currDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}