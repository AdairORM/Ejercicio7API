package com.example.ejercicio7api.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ejercicio7api.R
import com.example.ejercicio7api.model.Character
import com.example.ejercicio7api.model.Location
import com.example.ejercicio7api.model.Origin
import com.example.ejercicio7api.viewmodel.RyMUiState

@Composable
fun HomeScreen(
    ryMUiState: RyMUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (ryMUiState) {
        is RyMUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is RyMUiState.Success -> CharactersGridScreen(
            characters = ryMUiState.characters,
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
        is RyMUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun CharactersGridScreen(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) { character ->
            CharacterCard(
                character = character,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun CharacterCard(character: Character, modifier: Modifier) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            error = painterResource(id = R.drawable.error_404__2_),
            placeholder = painterResource(id = R.drawable.carga2),
            contentScale = ContentScale.Crop
        )
        Text(
            text = character.name,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loader2),
            contentDescription = "Loading"
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_load2),
            contentDescription = "Error Loading"
        )
        Text(text = (stringResource(R.string.problem_with_connection)))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        // Crear una lista de personajes de muestra para la previsualización
        val sampleCharacters = List(10) { index ->
            Character(
                id = index,
                name = "Personaje $index",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = Origin(name = "Origin $index", url = ""),
                location = Location(name = "Location $index", url = ""),
                image = "https://rickandmortyapi.com/api/character/avatar/${index}.jpeg",
                episode = listOf(),
                url = "",
                created = ""
            )
        }
        HomeScreen(
            ryMUiState = RyMUiState.Success(sampleCharacters)
        )
    }
}
