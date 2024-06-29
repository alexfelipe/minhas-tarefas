package br.com.alexf.minhastarefas.ui.screens

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.minhastarefas.R
import br.com.alexf.minhastarefas.ui.theme.PurpleGrey40
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SpiritBoxScreen(modifier: Modifier = Modifier) {

    Column(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Text(
            "RF SPIRITBOX",
            Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(
                color = White,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            ),
        )
        Text(
            "DISPOSITIVO DE PESQUISA PARANORMAL",
            Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(
                color = White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(White)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(16.dp))

        var isPlay by remember {
            mutableStateOf(false)
        }
        var showText by remember {
            mutableStateOf(false)
        }

        val sounds = remember {
            mapOf(
                "Atrás de você" to R.raw.atras_de_voce,
                "Chiado" to R.raw.chiado,
                "Luiza" to R.raw.luiza,
                "Não" to R.raw.nao,
                "Sim" to R.raw.sim
            )
        }

        var soundName = remember {
            sounds.keys.random()
        }

        val context = LocalContext.current

        LaunchedEffect(soundName, isPlay) {
            if (isPlay) {
                delay(Random.nextLong(2000, 4000))
                showText = true
                sounds[soundName]?.let { sound ->
                    val mediaPlayer = MediaPlayer.create(context, sound)
                    mediaPlayer.start()
                    while (mediaPlayer.isPlaying) {
                        delay(1000)
                    }
                    showText = false
                    delay(Random.nextLong(2000, 3000))
                    soundName = sounds.keys.shuffled().first { it != soundName }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Green)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Green)
        ) {
            this@Column.AnimatedVisibility(
                visible = showText,
                Modifier.align(Alignment.Center),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = soundName,
                    Modifier.align(
                        Alignment.Center,
                    ),
                    style = LocalTextStyle.current.copy(PurpleGrey40, 32.sp)
                )
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            val color = remember(isPlay) {
                if (isPlay) {
                    Cyan
                } else {
                    Red
                }
            }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color, shape = CircleShape)
                    .align(Alignment.Center)
                    .clickable {
                        isPlay = !isPlay
                    }
            ) {
                val text = if (isPlay) "ON" else {
                    "OFF"
                }
                Text(text = text, Modifier.align(Alignment.Center))
            }
        }

    }
}

@Preview
@Composable
private fun SpiritBoxScreenPreview() {
    SpiritBoxScreen()
}