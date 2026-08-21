package com.amity.socialcloud.uikit.chat.compose.group.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.davidlloyd.core.platform.compose.component.OneAppToolbarBackButton
import co.davidlloyd.core.platform.compose.component.OneAppToolbarIconButton
import co.davidlloyd.core.platform.compose.component.OneAppToolbarLayout
import co.davidlloyd.core.platform.compose.component.OneAppToolbarTitle
import co.davidlloyd.core.platform.compose.modifier.shimmerBackground
import co.davidlloyd.core.platform.compose.theme.OneAppColors
import co.davidlloyd.core.platform.compose.theme.OneAppDimensions
import co.davidlloyd.core.platform.compose.theme.OneAppTheme
import com.amity.socialcloud.uikit.chat.compose.message.element.AmityChatWaitingForNetworkRow
import co.davidlloyd.core.platform.R as PlatformR

/** Share of the title zone the shimmer placeholder takes while the channel name loads. */
private const val TITLE_SHIMMER_WIDTH_FRACTION = 0.7f

/**
 * APP-14863: host-app toolbar for the group chat page, replacing the stock Amity header so the
 * screen matches the rest of the app
 * @param isTitleLoading shows the shimmer placeholder instead of [title]; pass the page's
 * `isHeaderLoading` so the toolbar switches exactly when the stock header would.
 * @param isDisconnected shows the stock "waiting for network" row under the toolbar.
 */
@Composable
fun OneAppGroupChatToolbar(
    title: String,
    isTitleLoading: Boolean,
    isDisconnected: Boolean,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OneAppTheme {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = OneAppColors.Background,
        ) {
            Column {
                OneAppToolbarLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .defaultMinSize(minHeight = OneAppDimensions.ToolbarMinHeight)
                        .padding(horizontal = OneAppDimensions.Offset16),
                    startContent = { OneAppToolbarBackButton(onClick = onBackClick) },
                    centerContent = {
                        val titleModifier = Modifier.padding(horizontal = OneAppDimensions.Offset8)
                        if (isTitleLoading) {
                            Box(
                                modifier = titleModifier
                                    .fillMaxWidth(TITLE_SHIMMER_WIDTH_FRACTION)
                                    .height(OneAppDimensions.Offset20)
                                    .shimmerBackground(),
                            )
                        } else {
                            OneAppToolbarTitle(title = title, modifier = titleModifier)
                        }
                    },
                    endContent = {
                        OneAppToolbarIconButton(
                            iconRes = PlatformR.drawable.ic_more_vertical,
                            contentDescription = stringResource(PlatformR.string.aqa_view_id_club_life_settings),
                            onClick = onSettingsClick,
                        )
                    },
                )

                if (isDisconnected) {
                    AmityChatWaitingForNetworkRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = OneAppDimensions.Offset4),
                    )
                }
            }
        }
    }
}
