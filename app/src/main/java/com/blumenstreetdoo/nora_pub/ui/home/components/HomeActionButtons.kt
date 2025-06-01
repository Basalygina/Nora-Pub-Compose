package com.blumenstreetdoo.nora_pub.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun HomeActionButtons(
    onMenuClick: () -> Unit,
    onUntappdClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = onMenuClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                stringResource(id = R.string.menu),
                style = NoraTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Button(
            onClick = onUntappdClick,
            modifier = Modifier.weight(2f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                stringResource(id = R.string.untappd),
                style = NoraTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeActionButtonsPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        HomeActionButtons(
            onMenuClick = {},
            onUntappdClick = {}
        )
    }
}