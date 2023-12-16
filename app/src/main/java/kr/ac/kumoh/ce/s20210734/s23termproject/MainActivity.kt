package kr.ac.kumoh.ce.s20210734.s23termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import kr.ac.kumoh.ce.s20210734.s23termproject.ui.theme.S23TermprojectTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SkinViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: SkinViewModel) {
    val skinList by viewModel.skinList.observeAsState(emptyList())

    S23TermprojectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SkinApp(skinList)
        }
    }
}
