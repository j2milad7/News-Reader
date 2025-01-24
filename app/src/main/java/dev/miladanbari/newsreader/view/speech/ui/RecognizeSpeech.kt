package dev.miladanbari.newsreader.view.speech.ui

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import dev.miladanbari.newsreader.R
import java.util.Locale

@Composable
internal fun LaunchSpeechRecognizer(
    onSpeechRecognized: (String?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            onSpeechRecognized(result?.get(0))
        } else {
            onSpeechRecognized(null)
        }
    }

    // TODO: The prompt could be localized
    val prompt: String = stringResource(id = R.string.prompt_speech_recognizer)
    LaunchedEffect(key1 = Unit) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
        launcher.launch(intent)
    }
}
