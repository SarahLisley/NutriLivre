// app/src/main/java/com/example/myapplication/ui/components/BottomNavigationBar.kt
package com.example.myapplication.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.AppScreens
import com.google.rpc.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector




@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavButton(
            icon    = Icons.Default.Home,
            label   = "Receitas",
            onClick = { navController.navigate(AppScreens.TelaInicialScreen.route) }
        )

        BottomNavButton(
            icon    = Icons.Default.Search,
            label   = "Buscar",
            onClick = { navController.navigate(AppScreens.BuscaScreen.route) }
        )

        BottomNavButton(
            icon    = Icons.Default.Favorite,
            label   = "Favoritos",
            onClick = { navController.navigate(AppScreens.FavoritosScreen.route) }
        )

        BottomNavButton(
            icon    = Icons.Default.Settings,
            label   = "Configurações",
            onClick = { navController.navigate(AppScreens.ConfiguracoesScreen.route) }
        )


    }
}

@Composable
private fun BottomNavButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors  = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor   = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier
            .height(40.dp)
            .widthIn(min = 64.dp)
    ) {
        Icon(icon, contentDescription = label)
        Spacer(Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
