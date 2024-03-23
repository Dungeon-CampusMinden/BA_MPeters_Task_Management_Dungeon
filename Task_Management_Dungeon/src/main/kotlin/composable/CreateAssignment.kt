package composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Assignment
import icon.addIcon
import icon.deleteIcon

@Composable
fun createAssignment(
    modifier: Modifier,
    onValueChange: (SnapshotStateList<Assignment>) -> Unit,
    taskLabel: String,
    outputLabel: String,
    minAmount: Int
) {
    var termB by rememberSaveable { mutableStateOf("") }
    var termA by rememberSaveable { mutableStateOf("") }
    val assignments = remember { mutableStateListOf<Assignment>() }
    Column {
        bodyText(taskLabel)
        Row(content = {
            Image(
                addIcon(MaterialTheme.colorScheme.onBackground),
                "Add",
                Modifier.padding(4.dp, top = 26.dp).clickable {
                    if (termA.isNotEmpty()||termB.isNotEmpty()) {
                        if(termA.isEmpty()){
                            assignments.add(Assignment(termB = termB))
                        }
                        else if (termB.isEmpty()){
                            assignments.add(Assignment(termA = termA))
                        }else{
                            assignments.add(Assignment(termA,termB))
                        }
                    }
                    termA = ""
                    termB = ""
                })
            OutlinedTextField(
                modifier = modifier.padding(8.dp).fillMaxWidth().weight(4f),
                value = termA,
                onValueChange = { termA = it },
                label = { Text("Term A") },
                isError = assignments.size < minAmount
            )
            OutlinedTextField(
                modifier = modifier.padding(8.dp).fillMaxWidth().weight(4f),
                value = termB,
                onValueChange = { termB = it },
                label = { Text("Term B") },
                isError = assignments.size < minAmount
            )
        })
        LazyColumn(
            Modifier.fillMaxWidth().size(300.dp).clip(shape = RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onSecondary),
        ) {
            item {
                Text(
                    text = outputLabel,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = modifier.padding(bottom = 10.dp, start = 8.dp, top = 8.dp)
                )
            }

            items(items = assignments) { assignment ->
                Card(
                    modifier = modifier.fillMaxWidth().padding(20.dp, end = 16.dp, bottom = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Box {
                        Row(
                            modifier.align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                deleteIcon(MaterialTheme.colorScheme.onSurfaceVariant),
                                "Remove Item",
                                Modifier.padding(10.dp).clickable { assignments.remove(assignment) })
                            Text(
                                "Term A: ${assignment.termA}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 24.sp,
                                lineHeight = 25.sp,
                                modifier = modifier.clickable {})
                            Text(
                                "Term B: ${assignment.termB}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 24.sp,
                                lineHeight = 25.sp,
                                modifier = modifier.clickable {})
                        }
                    }
                }
            }
            onValueChange(assignments)
        }
    }
}