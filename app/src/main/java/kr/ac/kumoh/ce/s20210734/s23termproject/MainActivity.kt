package kr.ac.kumoh.ce.s20210734.s23termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            SkinList(skinList)
        }
    }
}

@Composable
fun SkinList(list: List<Skin>) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list) { skin ->
            SkinItem(skin)
        }
    }

}

@Composable
fun SkinItem(skin: Skin) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffffffcc))
            .padding(16.dp)
    ) {
        TextSkin("${skin.theme + skin.name}")
        TextRole("이 챔피언의 역할은 ${skin.role} ${skin.subrole} 입니다")
    }
}

@Composable
fun TextSkin(name :String) {
    Text(name, fontSize = 30.sp)
}

@Composable
fun TextRole(role: String) {
    Text(role, fontSize = 20.sp)
}