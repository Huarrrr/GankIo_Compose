package com.huarrrr.gankio_compose.ui.girl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.huarrrr.gankio_compose.R
import com.huarrrr.gankio_compose.ui.girl.vm.GirlViewModel
import com.huarrrr.gankio_compose.ui.theme.*
import com.huarrrr.gankio_compose.ui.view.DraggableCard
import com.huarrrr.gankio_compose.ui.view.GankAppBar
import com.huarrrr.gankio_compose.ui.view.Toaster

/**
 * Profile
 *
 * @author Huarrrr on 2021/6/17
 */

@Composable
fun Girl(navController: NavHostController) {
    val viewModel: GirlViewModel = viewModel()
    Surface(modifier = Modifier.fillMaxSize()) {
        val boxModifier = Modifier
        Column(
            Modifier
                .fillMaxSize()
                .background(Colors.white),
        ) {
            GankAppBar(
                stringResource(id = R.string.mine_page),
                false
            )
            Box(
                modifier = boxModifier.verticalGradientBackground(
                    listOf(
                        Color.White,
                        Purple200.copy(alpha = 0.2f)
                    )
                )
            ) {
                val listEmpty = remember { mutableStateOf(false) }
                DatingLoader(modifier = boxModifier)
                viewModel.list.forEachIndexed { index, gank ->
                    if (!listEmpty.value) {
                        DraggableCard(
                            item = gank,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),{
//                                    navController.navigate("image?image={$it}")
                            },
                            { _, item ->
                                if (viewModel.list.isNotEmpty()) {
                                    viewModel.list.remove(item)
                                    if (viewModel.list.isEmpty()) {
                                        listEmpty.value = true
                                        viewModel.lazyLoad()
                                        listEmpty.value = false
                                    }
                                }
                            }
                        ) { item ->
                            Toaster.show("英雄所见略同")
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun DatingLoader(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
    ) {
        FloatMultiStateAnimationCircleCanvas(Purple200, 100f)
    }
}

enum class SwipeResult {
    ACCEPTED, REJECTED
}