package com.panda.todopanda.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panda.todopanda.detail.ui.theme.ToDoPandaTheme
import com.panda.todopanda.list.Task
import org.w3c.dom.Text
import java.util.UUID

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val initialTask = intent.getSerializableExtra("task") as? Task
        setContent {
            ToDoPandaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Detail(initialTask = initialTask, onValidate = { task ->
                        intent.putExtra("task", task)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    })
                }
            }
        }
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            
        }
    }

@Composable
fun Detail(initialTask: Task?, onValidate: (Task) -> Unit) {
    val newTask = Task(id = UUID.randomUUID().toString(), title = "", description = "")
    var task by remember { mutableStateOf(initialTask ?: newTask) }


    Column {
        OutlinedTextField(
            value = task.title,
            onValueChange = { task = task.copy(title = it) },
            label = { Text("Title") }
        )
        OutlinedTextField(
            value = task.description,
            onValueChange = { task = task.copy(description = it) },
            label = { Text("Description") }
        )
        Button(onClick = { onValidate(task) }) {
            Text("Valider")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ToDoPandaTheme {
        val initialTask = intent.getSerializableExtra("task") as? Task
        Detail(initialTask = initialTask, onValidate = { task ->
            intent.putExtra("task", task)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }
}
}
