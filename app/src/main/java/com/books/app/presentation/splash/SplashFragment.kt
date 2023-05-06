package com.books.app.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.books.app.R
import com.books.app.presentation.ui.BookAppTheme
import com.books.app.presentation.ui.appColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            BookAppTheme {

                val scaffoldState = rememberScaffoldState()

                Scaffold(scaffoldState = scaffoldState) {

                    Column(
                        Modifier
                            .fillMaxSize()
                            .paint(
                                painterResource(R.drawable.back),
                                contentScale = ContentScale.FillBounds
                            )
                            .paint(
                                painterResource(R.drawable.heart_back),
                                contentScale = ContentScale.FillBounds
                            )
                            .padding(it),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 52.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Book App",
                                color = MaterialTheme.appColors.pink,
                                style = MaterialTheme.typography.h3.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 52.sp
                                )
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Welcome to Book App",
                                color = Color.White,
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(Modifier.height(32.dp))
                            LinearProgressIndicator(modifier = Modifier.clip(RoundedCornerShape(6.dp)),
                                color = Color.White)
                        }
                    }
                }
            }
        }
    }

}