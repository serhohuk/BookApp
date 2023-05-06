package com.books.app.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.books.app.domain.model.Book
import com.books.app.domain.model.TopBannerSlide
import com.books.app.presentation.ui.BookAppTheme
import com.books.app.presentation.ui.appColors
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            BookAppTheme {
                val uiState by viewModel.uiState.collectAsState()
                val uiMessage by viewModel.uiMessage.collectAsState(null)

                MainScreen(
                    uiState = uiState,
                    uiMessage = uiMessage,
                    onItemClick = {
                        val action = MainFragmentDirections.actionMainFragmentToBookDetailsFragment(it)
                        findNavController().navigate(action)
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    uiState: MainFragmentUIState,
    uiMessage: String?,
    onItemClick: (Int) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2)
    val verticalScroll = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = Color.Black,
        scaffoldState = scaffoldState
    ) {
        if (!uiMessage.isNullOrEmpty()) {
            Toast.makeText(context, uiMessage, Toast.LENGTH_SHORT).show()
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            when (uiState) {
                is MainFragmentUIState.Success -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(verticalScroll)
                    ) {
                        Spacer(Modifier.height(38.dp))
                        Text(
                            text = "Library",
                            color = MaterialTheme.appColors.darkPink,
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(Modifier.height(28.dp))
                        HorizontalPagerImage(
                            pagerState = pagerState,
                            items = uiState.sliderBanners, onClick = { topBanner ->
                                onItemClick(topBanner.bookId)
                            }
                        )
                        Spacer(Modifier.height(36.dp))
                        uiState.booksMap.entries.forEach { entry ->
                            RowWithTitle(
                                title = entry.key,
                                books = entry.value,
                                onItemClick = { book ->
                                    onItemClick(book.id)
                                })
                        }
                        Spacer(Modifier.height(28.dp))
                    }
                }

                is MainFragmentUIState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}


@Composable
fun BookItem(
    book: Book,
    textColor: Color,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .width(120.dp)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.appColors.lightGray),
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.coverUrl)
                .build(),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Spacer(Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = book.name,
            color = textColor,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerImage(
    pagerState: PagerState,
    items: List<TopBannerSlide>,
    onClick: (TopBannerSlide) -> Unit
) {
    var currentIndex = pagerState.currentPage % items.size
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        pagerState.scrollToPage(pagerState.currentPage + 1)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(167.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        HorizontalPager(
            pageCount = Int.MAX_VALUE,
            state = pagerState
        ) { page ->
            currentIndex = page % items.size
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick(items[currentIndex]) },
                model = items[currentIndex].cover,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(items.size) { iteration ->
                val color =
                    if (currentIndex == iteration) MaterialTheme.appColors.darkPink else MaterialTheme.appColors.lightGray
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 5.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(7.dp)
                )
            }
        }
    }
}

@Composable
fun RowWithTitle(
    title: String,
    books: List<Book>,
    titleTextColor: Color = Color.White,
    itemsTextColor: Color = MaterialTheme.appColors.lightGray,
    onItemClick: (Book) -> Unit
) {
    Text(
        text = title,
        color = titleTextColor,
        style = MaterialTheme.typography.h6.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    )
    Spacer(Modifier.height(16.dp))
    LazyRow(
        Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                textColor = itemsTextColor,
                onClick = {
                    onItemClick(book)
                })
        }
    }
    Spacer(Modifier.height(26.dp))
}