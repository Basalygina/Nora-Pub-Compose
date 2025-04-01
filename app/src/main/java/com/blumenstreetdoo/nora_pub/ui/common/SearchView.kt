package com.blumenstreetdoo.nora_pub.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blumenstreetdoo.nora_pub.R

@Composable
fun SearchView(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 8.dp,
        color = Color.White
    ) {
        Box(modifier = Modifier.padding(vertical = 0.dp)) {
            TextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                placeholder = { Text(stringResource(R.string.craft_search_helper_text)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_small),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = onClearClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear),
                                contentDescription = stringResource(id = R.string.clear)
                            )
                        }
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    MaterialTheme {
        SearchView("ipa", {}, {})
    }
}