package com.example.listdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.listdummy.viewmodel.HiringViewModel
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listdummy.model.HiringDataClass
import com.example.listdummy.network.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: HiringViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //mainViewModel = ViewModelProvider(this, PostViewModelFactory())[PostViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContent {
            mainViewModel.getHiringList()
            MaterialTheme {
                MainScreen(mainViewModel.hiringState.collectAsState().value)
            }
        }
    }
}

@Composable
fun MainScreen(countriesState: Resource<List<HiringDataClass>>) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (countriesState) {
            is Resource.Loading -> {
                Text("Loading...")
            }
            is Resource.Success -> {
                val countries = countriesState.data
                LazyColumn {
                    items(countries) { post ->
                        CountryInfoCard(
                            post.id.toString() ?: "",
                            post.name ?: "",
                            post.listId ?: "",
                        )
                    }
                }
            }
            is Resource.Error -> {
                val errorMessage = countriesState.message
                Text("Error: $errorMessage")
            }
        }

    }
}


@Composable
fun CountryInfoCard(id: String, name: String, listId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Name:- $name",
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Id:- $id",
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ListId:- $listId",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val fakeResponse = Resource.Success(
        listOf(HiringDataClass(name = "USA", id = 2, listId = "XX"))
    )
    MainScreen(fakeResponse)
}
