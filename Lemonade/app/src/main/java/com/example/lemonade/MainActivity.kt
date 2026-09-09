package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeScreen(modifier: Modifier = Modifier) {
    var stage by remember { mutableIntStateOf(0) }
    var squeezes by remember { mutableIntStateOf(0) }
    var pause by remember { mutableStateOf(false) }

    val imageResource = when (stage) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val imageDescription = when (stage) {
        0 -> R.string.lemon_tree_description
        1 -> R.string.lemon_squeeze_description
        2 -> R.string.lemon_drink_description
        else -> R.string.lemon_restart_description
    }

    val textResource = when (stage) {
        0 -> R.string.lemon_tree
        1 -> R.string.lemon_squeeze
        2 -> R.string.lemon_drink
        else -> R.string.lemon_restart
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button (onClick =
            {
                if (!pause) {
                    stage = (stage + 1) % 4
                }
                if (stage == 1 && !pause) {
                    pause = true
                    squeezes = (2..4).random()
                }
                else if (stage == 1) {
                    squeezes -= 1
                }
                if (squeezes == 0 && pause) {
                    pause = false
                    stage = (stage + 1) % 4
                }
            }
        ) {
            Image (
                painter = painterResource(imageResource),
                contentDescription = stringResource(imageDescription)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text (
            text = stringResource(textResource),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        LemonadeScreen(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
        )
    }
}