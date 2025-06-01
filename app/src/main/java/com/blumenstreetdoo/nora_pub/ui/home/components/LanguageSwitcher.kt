package com.blumenstreetdoo.nora_pub.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun LanguageSwitcher(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(LanguageItem.English) }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .clickable { expanded = true }
            .padding(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = selectedLanguage.displayName(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_drop_down),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(NoraColors.background)
        ) {
            listOf(
                LanguageItem.English,
                LanguageItem.Montenegrin,
                LanguageItem.Russian
            ).forEach { language ->
                DropdownMenuItem(
                    text = { Text(language.displayName()) },
                    onClick = {
                        selectedLanguage = language
                        expanded = false
                        // TODO: handle language change
                    }
                )
            }
        }
    }
}

enum class LanguageItem(private val stringResId: Int) {
    English(R.string.language_english),
    Montenegrin(R.string.language_montenegrin),
    Russian(R.string.language_russian);

    @Composable
    fun displayName(): String = stringResource(id = stringResId)
}

@Preview(showBackground = true)
@Composable
fun LanguageSwitcherPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        LanguageSwitcher()
    }
}
