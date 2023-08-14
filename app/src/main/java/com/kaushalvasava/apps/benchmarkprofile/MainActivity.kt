package com.kaushalvasava.apps.benchmarkprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kaushalvasava.apps.benchmarkprofile.ui.theme.BenchMarkProfileTheme

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BenchMarkProfileTheme {
                // A surface container using the 'background' color from the theme
                var counter by remember {
                    mutableStateOf(0)
                }

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "start",
                    modifier = Modifier
                        .semantics {
                            testTagsAsResourceId = true
                        }
                ) {
                    composable("start") {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("item_list")
                        ) {
                            item {
                                Button(onClick = { counter++ }) {
                                    Text(text = "Click me")
                                }
                            }
                            items(counter) {
                                val text = "Element $it"
                                Text(
                                    text = text,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("detail/$text")
                                        }
                                        .padding(32.dp)
                                )
                            }
                        }
                    }
                    composable(
                        route = "detail/{text}",
                        arguments = listOf(
                            navArgument("text") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val text = it.arguments?.getString("text") ?: "Default"
                        DetailsScreen(details = text)
                    }
                }
            }
        }
    }
}
@Composable
fun DetailsScreen(details: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Detail: $details")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BenchMarkProfileTheme {
        Greeting("Android")
    }
}