package com.psablik.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.psablik.composeplayground.component.ElementComponent
import com.psablik.composeplayground.state.MainActivityViewState
import com.psablik.composeplayground.ui.theme.ComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePlaygroundTheme {
                val viewModel by viewModels<MainActivityViewModel>()

                // Nasłuchiwanie na stan z ViewModelu
                val state = viewModel.state.collectAsState()

                Content(
                    data = state.value,
                    onChangeSize = viewModel::changeContainerSize,
                    onGenerateRandomSize = viewModel::generateRandomSize,
                )
            }
        }
    }
}

/**
 * Akcje przekazywane są jako lambdy (funkcje anonimowe) wyżej do ViewModelu aby zareagował
 */
@Composable
fun Content(
    data: MainActivityViewState,
    onChangeSize: (Int) -> Unit,
    onGenerateRandomSize: () -> Unit,
) {
    // Główny konetener
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 18.dp,
                horizontal = 8.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Sekcja z przyciskiem i sliderem
        TopSection(
            size = data.size,
            onChangeSize = onChangeSize,
            onGenerateRandomSize = onGenerateRandomSize
        )

        // Sekcja z wyświetlonymi elementami
        BottomSection(size = data.size)
    }
}

@Composable
private fun TopSection(
    size: Int,
    onChangeSize: (Int) -> Unit,
    onGenerateRandomSize: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onGenerateRandomSize
        ) {
            Text("Generate random size")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Pick size, current size: $size")

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = size.toFloat(),
            steps = CONTAINER_SIZE_MAX,
            onValueChange = { newValue -> onChangeSize(newValue.toInt()) },
            valueRange = CONTAINER_SIZE_MIN.toFloat()..CONTAINER_SIZE_MAX.toFloat()
        )
    }
}

@Composable
private fun BottomSection(
    size: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val elementHeight =
        if (size == 0) {
            screenWidth
        } else {
            screenWidth / size
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(size) { rowNumber ->
            Row {
                repeat(size) {
                    ElementComponent(row = rowNumber, height = elementHeight.dp)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ComposePlaygroundTheme {
        Content(data = MainActivityViewState(), onChangeSize = {}, onGenerateRandomSize = {})
    }
}

