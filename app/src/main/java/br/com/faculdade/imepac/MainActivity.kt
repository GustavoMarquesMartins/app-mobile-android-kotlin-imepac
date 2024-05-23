package br.com.faculdade.imepac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.faculdade.imepac.ui.theme.ImepacTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o conteúdo da activity com o Compose
        setContent {
            // Define o tema da aplicação
            ImepacTheme {
                // Superfície que contém o conteúdo da aplicação
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama a função Greeting para exibir uma mensagem na tela
                    Greeting("Android")
                }
            }
        }
    }
}

// Função composable que exibe uma saudação
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Pré-visualização da saudação
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Define o tema para a pré-visualização
    ImepacTheme {
        // Chama a função Greeting para a pré-visualização
        Greeting("Android")
    }
}
