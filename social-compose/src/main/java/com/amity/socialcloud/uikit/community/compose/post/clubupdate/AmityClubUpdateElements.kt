package com.amity.socialcloud.uikit.community.compose.post.clubupdate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amity.socialcloud.uikit.common.compose.R as CommonComposeR
import com.amity.socialcloud.uikit.common.ui.atoms.AmityToggle
import com.amity.socialcloud.uikit.common.ui.theme.AmityTheme
import com.amity.socialcloud.uikit.community.compose.localization.amitySocialString

private val ClubUpdateCardShape = RoundedCornerShape(16.dp)

/**
 * Outlined card around a Club Update post, as in the design. No-op for other posts.
 *
 * In a feed the margin is filled with the feed divider colour, so with the 8dp
 * [AmityNewsFeedDivider] after each post the card sits on 16dp of grey, as on iOS.
 * On the detail page the margin stays on the page background.
 */
@Composable
internal fun Modifier.clubUpdateCard(isClubUpdate: Boolean, isFeed: Boolean): Modifier {
    if (!isClubUpdate) return this
    return this
        .then(if (isFeed) Modifier.background(AmityTheme.colors.newsfeedDivider) else Modifier)
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .border(
            width = 1.dp,
            color = AmityTheme.colors.primary.copy(alpha = 0.4f),
            shape = ClubUpdateCardShape,
        )
        .clip(ClubUpdateCardShape)
}

/** "Club Update" pill shown at the top right of the post header. */
@Composable
internal fun AmityClubUpdateBadge(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .background(AmityTheme.colors.primary.copy(alpha = 0.12f), CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = CommonComposeR.drawable.amity_ic_info_circle_r),
            contentDescription = null,
            tint = AmityTheme.colors.primary,
            modifier = Modifier.size(14.dp),
        )
        Text(
            text = amitySocialString("amity_social_club_update_badge"),
            style = AmityTheme.typography.captionLegacy.copy(
                fontWeight = FontWeight.SemiBold,
                color = AmityTheme.colors.primary,
            ),
        )
    }
}

/** Composer switch that marks the post as a Club Update. */
@Composable
internal fun AmityClubUpdateToggle(
    isClubUpdate: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = amitySocialString("amity_social_club_update_composer_toggle_title"),
                style = AmityTheme.typography.bodyLegacy.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = amitySocialString("amity_social_club_update_composer_toggle_description"),
                style = AmityTheme.typography.captionLegacy.copy(
                    fontWeight = FontWeight.Normal,
                    color = AmityTheme.colors.baseShade1,
                ),
            )
        }
        AmityToggle(isOn = isClubUpdate, onChange = onChange)
    }
}
