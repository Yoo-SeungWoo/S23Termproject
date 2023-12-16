package kr.ac.kumoh.ce.s20210734.s23termproject

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage


enum class SkinScreen {
    List,
    Detail
}

@Composable
fun SkinApp(skinList: List<Skin>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SkinScreen.List.name,
    ) {
        composable(route = SkinScreen.List.name) {
            SkinList(skinList) {
                navController.navigate(it)
            }
        }
        composable(
            route = SkinScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                SkinDetail(skinList[index])
        }
    }
}

@Composable
fun SkinList(list: List<Skin>,onNavigateToDetail: (String) -> Unit) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list.size) {
            SkinItem(it, list[it], onNavigateToDetail)
        }
    }

}

@Composable
fun SkinItem(index: Int, skin: Skin, onNavigateToDetail: (String) -> Unit) {
    Card(
        modifier = Modifier.clickable { onNavigateToDetail(SkinScreen.Detail.name + "/$index") },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color(255, 220, 220))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = skin.skin_image,
                contentDescription = "챔피언 스킨 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TextSkin("${skin.theme} ${skin.name}")
                TextRole("이 챔피언의 역할은 ${skin.role}와 ${skin.subrole} 입니다")
            }
        }
    }
}

@Composable
fun TextSkin(name :String) {
    Text(name, fontSize = 25.sp)
}

@Composable
fun TextRole(role: String) {
    Text(role, fontSize = 15.sp)
}

@Composable
fun SkinDetail(skin: Skin) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingBar(skin.rating)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "${skin.theme} ${skin.name}",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = skin.skin_image,
            contentDescription = "챔피언 스킨 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(percent = 10))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = skin.image,
                contentDescription = "챔피언 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
            )
            Text(skin.name, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))

        skin.script?.let {
            Text(
                it,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/results?search_query=+${skin.theme}+${skin.name}")
            )
            startActivity(context, intent, null)
        }) {
            Text("유튜브 롤 스킨 검색")
        }
    }
}

@Composable
fun RatingBar(hearts: Int) {
    Row {
        repeat(hearts) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "stars",
                modifier = Modifier.size(48.dp),
                tint = Color.Red
            )
        }
        repeat(5 - hearts) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "stars",
                modifier = Modifier.size(48.dp),
                tint = Color.Red
            )
        }
    }
}