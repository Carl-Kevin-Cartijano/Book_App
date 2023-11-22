package com.thebrownfoxx.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.thebrownfoxx.components.extension.Elevation
import com.thebrownfoxx.components.extension.ExpandedTopBarPreview
import kotlin.math.abs

object ExpandedTopAppBar {
    val PinnedHeight = 64.dp
    val MaxHeight = 256.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedTopAppBar(
    collapsedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    background: @Composable BoxScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    pinCollapsedContent: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val pinnedHeightPx: Float
    val maxHeightPx: Float

    with(LocalDensity.current) {
        pinnedHeightPx = ExpandedTopAppBar.PinnedHeight.toPx()
        maxHeightPx = ExpandedTopAppBar.MaxHeight.toPx()
    }

    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }
    }

    // Obtain the container Color from the TopAppBarColors using the `collapsedFraction`, as the
    // bottom part of this TwoRowsTopAppBar changes color at the same rate the app bar expands or
    // collapse.
    // This will potentially animate or interpolate a transition between the container color and the
    // container's scrolled color according to the app bar's scroll state.
    val colorTransitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val appBarContainerColor =
        colorScheme.surfaceColorAtElevation(Elevation.level(3))
//    val appBarContainerColor by rememberUpdatedState(
//        lerp(
//            start = colorScheme.surfaceColorAtElevation(Elevation.level(1)),
//            stop = colorScheme.surfaceColorAtElevation(Elevation.level(3)),
//            fraction = FastOutLinearInEasing.transform(colorTransitionFraction)
//        )
//    )

    // Wrap the given actions in a Row.
    val actionsRow: (@Composable () -> Unit)? = actions?.let {
        {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
    val collapsedContentAlpha = topTitleAlphaEasing.transform(
        if (pinCollapsedContent) 1f else 2 * colorTransitionFraction - 1f
    )
    val contentAlpha = 1f - 2 * colorTransitionFraction

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(appBarDragModifier),
        color = appBarContainerColor,
    ) {
        Box(modifier = Modifier.statusBarsPadding()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            (maxHeightPx +
                                    (scrollBehavior?.state?.heightOffset ?: 0f)).toDp()
                        }
                    ),
            ) {
                background()
            }
            Column {
                Box(
                    modifier = Modifier
                        .height(
                            with(LocalDensity.current) {
                                (maxHeightPx - pinnedHeightPx + (scrollBehavior?.state?.heightOffset
                                    ?: 0f)).toDp()
                            }
                        )
                        .padding(top = ExpandedTopAppBar.PinnedHeight)
                        .fillMaxWidth()
                        .graphicsLayer { alpha = contentAlpha }
                        .zIndex(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    ProvideTextStyle(value = typography.headlineLarge) {
                        content()
                    }
                }
                Box(
                    modifier = Modifier
                        .height(ExpandedTopAppBar.PinnedHeight)
                        .fillMaxWidth(),
                ) {
                    if (navigationIcon != null) {
                        Row(modifier = Modifier.align(Alignment.CenterStart)) {
                            HorizontalSpacer(width = 8.dp)
                            navigationIcon()
                        }
                    }
                    HorizontalSpacer(width = 16.dp)
                    Box(
                        modifier = Modifier
                            .graphicsLayer { alpha = collapsedContentAlpha }
                            .align(Alignment.Center),
                    ) {
                        ProvideTextStyle(value = typography.titleLarge) {
                            collapsedContent()
                        }
                    }
                    HorizontalSpacer(width = 16.dp)
                    if (actionsRow != null) {
                        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                            actionsRow()
                            HorizontalSpacer(width = 8.dp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedTopAppBar(
    pinnedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    pinCollapsedContent: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val pinnedHeightPx: Float
    val maxHeightPx: Float

    with(LocalDensity.current) {
        pinnedHeightPx = ExpandedTopAppBar.PinnedHeight.toPx()
        maxHeightPx = ExpandedTopAppBar.MaxHeight.toPx()
    }

    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }
    }

    // Obtain the container Color from the TopAppBarColors using the `collapsedFraction`, as the
    // bottom part of this TwoRowsTopAppBar changes color at the same rate the app bar expands or
    // collapse.
    // This will potentially animate or interpolate a transition between the container color and the
    // container's scrolled color according to the app bar's scroll state.
    val colorTransitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val appBarContainerColor by rememberUpdatedState(
        lerp(
            start = colorScheme.surfaceColorAtElevation(Elevation.level(1)),
            stop = colorScheme.surfaceColorAtElevation(Elevation.level(3)),
            fraction = FastOutLinearInEasing.transform(colorTransitionFraction)
        )
    )
    val collapsedContentAlpha = topTitleAlphaEasing.transform(
        if (pinCollapsedContent) 1f else 2 * colorTransitionFraction - 1f
    )
    val contentAlpha = 1f - 2 * colorTransitionFraction

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(appBarDragModifier),
        color = appBarContainerColor,
    ) {
        Box(modifier = Modifier.statusBarsPadding()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            (maxHeightPx +
                                    (scrollBehavior?.state?.heightOffset ?: 0f)).toDp()
                        }
                    ),
            ) {
                background()
            }
            Column {
                Box(
                    modifier = Modifier
                        .height(
                            with(LocalDensity.current) {
                                (maxHeightPx - pinnedHeightPx + (scrollBehavior?.state?.heightOffset
                                    ?: 0f)).toDp()
                            }
                        )
                        .padding(top = ExpandedTopAppBar.PinnedHeight)
                        .fillMaxWidth()
                        .graphicsLayer { alpha = contentAlpha }
                        .zIndex(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    ProvideTextStyle(value = typography.headlineLarge) {
                        content()
                    }
                }
                Row(
                    modifier = Modifier.height(ExpandedTopAppBar.PinnedHeight),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .graphicsLayer { alpha = collapsedContentAlpha }
                            .weight(1f),
                    ) {
                        ProvideTextStyle(value = typography.titleLarge) {
                            pinnedContent()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ExpandedTopAppBarPreview() {
    ExpandedTopBarPreview { scrollBehavior ->
        ExpandedTopAppBar(
            collapsedContent = { Text(text = "Collapsed") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }
            },
            scrollBehavior = scrollBehavior,
        ) {
            Text(text = "Expanded")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}

private val topTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)