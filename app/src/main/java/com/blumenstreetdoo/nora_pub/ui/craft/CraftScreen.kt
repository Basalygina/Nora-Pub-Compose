package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.ui.common.SearchView
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CraftScreen(
    craftViewModel: CraftViewModel,
    onBeerClick: (BeerDetails) -> Unit,
) {
    val craftState by craftViewModel.craftState.collectAsState()
    val filterState by craftViewModel.craftFilterState.collectAsState()
    val defaultFilter = craftViewModel.defaultFilterState

    var searchQuery by remember { mutableStateOf(filterState.searchQuery ?: "") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    var showFilterSheet by remember { mutableStateOf(false) }

    // Sync tabs with pager
    LaunchedEffect(selectedTabIndex) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    SearchView(
                        searchQuery = searchQuery,
                        onQueryChange = { query ->
                            searchQuery = query
                            craftViewModel.updateFilter(filterState.copy(searchQuery = query))
                        },
                        onClearClick = {
                            searchQuery = ""
                            craftViewModel.updateFilter(filterState.copy(searchQuery = ""))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                    )
                },
                actions = {
                    BadgedBox(
                        modifier = Modifier.padding(end = 8.dp),
                        badge = {
                            if (filterState.activeFilterCount > 0) {
                                Badge(
                                    modifier = Modifier.offset(x = (-6).dp, y = 4.dp),
                                    containerColor = Color(0xFFFFD700),
                                    contentColor = Color.Black
                                ) {
                                    Text(filterState.activeFilterCount.toString())
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { showFilterSheet = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter),
                                contentDescription = stringResource(R.string.filters)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        MaterialTheme(
            colorScheme = NoraColors.copy(
                primary = MaterialTheme.colorScheme.onBackground,
                onPrimary = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text(stringResource(R.string.title_beer_on_tap)) }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text(stringResource(R.string.title_cans_in_fridge)) }
                    )
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val drinkType = if (page == 0) DrinkType.TAP_BEER else DrinkType.CANNED_BEER
                    BeerListScreen(
                        drinkType = drinkType,
                        craftState = craftState,
                        onBeerClick = { beerDetails -> onBeerClick(beerDetails) }
                    )
                }
            }
        }
    }

    // Bottom Sheet with filters
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            containerColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                val countries = remember {
                    context.resources.getStringArray(R.array.country_list).toList()
                }
                CraftFilterScreen(
                    filterState = filterState,
                    defaultFilter = defaultFilter,
                    countries = countries,
                    onApply = { newFilter ->
                        craftViewModel.updateFilter(newFilter)
                        showFilterSheet = false
                    },
                    onReset = {
                        craftViewModel.resetFilter()
                    }
                )
            }
        }
    }
}