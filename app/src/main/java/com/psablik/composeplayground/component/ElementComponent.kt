package com.psablik.composeplayground.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Funkcja rozszerzająca [RowScope], ponieważ aby użyć: Modifier.weight() Compose
 * musi wiedzieć czy działa w obrębie wiersza czy kolumny. Gdybym nie zrobił tego jako
 * osobny komponent, tylko użył bezpośrenio w środku Row { ... } to Compose
 * sam by to oszacował i nie było by to potrzebne.
 */
@Composable
fun RowScope.ElementComponent(row: Int, height: Dp) {
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .weight(1f)
            .height(height)
            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = row.toString(),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
