package com.example.scaffoldlayout

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scaffoldlayout.ui.theme.ScaffoldLayoutTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldLayoutTheme {
                SupportingPaneScaffoldDemo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SupportingPaneScaffoldDemo() {
    val navigator = rememberSupportingPaneScaffoldNavigator()

    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            MainPane(
                shouldShowSupportingPaneButton = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden,
                onNavigateToSupportingPane = { navigator.navigateTo(SupportingPaneScaffoldRole.Supporting) }
            )
        },
        supportingPane = { SupportingPane(onHideSupportingPane = { navigator.navigateBack() }) },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainPane(
    shouldShowSupportingPaneButton: Boolean,
    onNavigateToSupportingPane: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (shouldShowSupportingPaneButton) {
            Button(onClick = onNavigateToSupportingPane) {
                Text("Show supporting pane")
            }
        } else {
            Text("Supporting pane is shown", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SupportingPane(
    onHideSupportingPane: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Button(onClick = onHideSupportingPane) {
                Text("Hide supporting pane")
            }
        }
    }
}