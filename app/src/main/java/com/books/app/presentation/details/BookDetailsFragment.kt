package com.books.app.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.books.app.domain.model.Book
import com.books.app.presentation.main.RowWithTitle
import com.books.app.presentation.ui.BookAppTheme
import com.books.app.presentation.ui.appColors
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.absoluteValue


class BookDetailsFragment : Fragment() {

    private val args: BookDetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<BookDetailsViewModel> {
        parametersOf(args.bookId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BookAppTheme {

                    val uiState by viewModel.uiState.collectAsState()

                    BookDetailsScreen(bookId = viewModel.bookId, uiState = uiState) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BookDetailsScreen(
    bookId: Int,
    uiState: BookDetailsUIState,
    onBackClick: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = Color.Black,
        scaffoldState = scaffoldState
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            IconButton(
                modifier = Modifier.padding(start = 16.dp, top = 12.dp),
                onClick = {
                    onBackClick()
                }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
            when (uiState) {
                is BookDetailsUIState.Success -> {
                    val initialPage = uiState.list.indexOfFirst { it.id == bookId }
                    val pagerState = rememberPagerState(initialPage = initialPage)
                    val currentBook = uiState.list[pagerState.currentPage]

                    HorizontalImagePager(pagerState = pagerState, items = uiState.list)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = currentBook.name,
                        color = Color.White,
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = currentBook.author,
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(18.dp))
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        color = Color.White
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 68.dp)
                                .padding(horizontal = 16.dp)
                        ) {
                            Spacer(Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                InfoItem(
                                    modifier = Modifier.weight(1f),
                                    title = "Readers",
                                    value = currentBook.views
                                )
                                InfoItem(
                                    modifier = Modifier.weight(1f),
                                    title = "Likes",
                                    value = currentBook.likes
                                )
                                InfoItem(
                                    modifier = Modifier.weight(1f),
                                    title = "Quotes",
                                    value = currentBook.quotes
                                )
                                InfoItem(
                                    modifier = Modifier.weight(1f),
                                    title = "Genre",
                                    value = currentBook.genre
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Divider()
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "Summary",
                                color = Color.Black,
                                style = MaterialTheme.typography.subtitle1.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = currentBook.summary,
                                color = Color(0xFF393637),
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(Modifier.height(16.dp))
                            Divider()
                            Spacer(Modifier.height(16.dp))
                            RowWithTitle(
                                title = "You will also like",
                                books = uiState.alsoLike,
                                titleTextColor = Color.Black,
                                itemsTextColor = Color.Black,
                                onItemClick = {}
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(
                                modifier = Modifier
                                    .width(278.dp)
                                    .height(48.dp)
                                    .align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.appColors.pink),
                                shape = RoundedCornerShape(30.dp),
                                onClick = {

                                }) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Read Now",
                                    color = Color.White,
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                is BookDetailsUIState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalImagePager(
    pagerState: PagerState,
    items: List<Book>
) {
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        pageCount = items.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 100.dp),
    ) { page ->
        Card(
            Modifier
                .aspectRatio(0.9f)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    alpha = lerp(0.8f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        .also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                },
            shape = RoundedCornerShape(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = items[page].coverUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    }
}

@Composable
fun InfoItem(
    modifier: Modifier,
    title: String,
    value: String
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = Color.Black,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        Text(
            text = title,
            color = MaterialTheme.appColors.lightGray,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        )
    }
}