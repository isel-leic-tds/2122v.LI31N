package tds.galo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

/**
 * Window to edit the name of the game to start.
 * Has the buttons "Start" and "Cancel" and receives as parameters the functions [onCancel] and [onStart].
 * Is a stateful composable to store the name during editing.
 */
@Composable
fun DialogName(onCancel: ()->Unit, onStart: (String)->Unit) = Dialog(
    onCloseRequest = onCancel,
    title = "Game name",
    state = DialogState( height = Dp.Unspecified, width = 350.dp)
) {
    var name by remember { mutableStateOf("abc") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       OutlinedTextField(
           name,
           onValueChange = { name = it },
           label = { Text("name") }
       )
       Row(
           Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceEvenly
       ) {
           Button(onClick = {
                if (name.isNotBlank()) onStart(name)
           } ) { Text("Start") }
           Button(onClick = onCancel) { Text("Cancel")}
       }
   }
}