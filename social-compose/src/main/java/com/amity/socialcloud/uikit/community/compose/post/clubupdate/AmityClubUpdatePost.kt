package com.amity.socialcloud.uikit.community.compose.post.clubupdate

import com.amity.socialcloud.sdk.model.social.post.AmityPost
import com.google.gson.JsonObject

/**
 * David Lloyd — Club Update posts (APP-15330).
 *
 * A Club Update is an ordinary post marked in its metadata. It is read-only for members:
 * no reactions, no comments, and a "Club Update" pill on the card. The marker is the same
 * on iOS (`dlPostKind = clubUpdate`), so a post created on one platform renders on the other.
 */
private const val CLUB_UPDATE_METADATA_KEY = "dlPostKind"
private const val CLUB_UPDATE_METADATA_VALUE = "clubUpdate"

internal fun AmityPost.isClubUpdate(): Boolean {
    return getMetadata()
        ?.get(CLUB_UPDATE_METADATA_KEY)
        ?.takeIf { it.isJsonPrimitive }
        ?.asString == CLUB_UPDATE_METADATA_VALUE
}

/**
 * Returns the metadata to send for a post, with the Club Update marker set or removed.
 *
 * `createMetadata` returns null when the post has no mentions or hashtags, and the SDK
 * calls only send metadata when it is non-null. So:
 * - a new Club Update without mentions still needs an object to carry the marker;
 * - an edit that turns a Club Update off must send an object without it, otherwise the
 *   server keeps the old metadata and the post stays a Club Update.
 *
 * @param wasClubUpdate whether the edited post was a Club Update before this edit.
 */
internal fun JsonObject?.withClubUpdateFlag(
    isClubUpdate: Boolean,
    wasClubUpdate: Boolean = false,
): JsonObject? {
    return when {
        isClubUpdate -> (this ?: JsonObject()).apply {
            addProperty(CLUB_UPDATE_METADATA_KEY, CLUB_UPDATE_METADATA_VALUE)
        }

        wasClubUpdate -> (this ?: JsonObject()).apply { remove(CLUB_UPDATE_METADATA_KEY) }
        else -> this
    }
}
