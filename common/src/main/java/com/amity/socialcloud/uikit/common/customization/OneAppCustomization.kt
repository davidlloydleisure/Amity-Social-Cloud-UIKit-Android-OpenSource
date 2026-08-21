package com.amity.socialcloud.uikit.common.customization

/**
 * Single switch guarding every One-App customization made to this UIKit fork.
 *
 * This repository is a fork: `main` receives upstream source drops, `develop` carries our changes,
 * and every `main -> develop` merge is a conflict risk. To keep that merge cheap:
 *
 * - Prefer putting new code in files upstream does not have (this package is one of them).
 * - When an upstream file must be touched, guard the edit with this flag and keep the original
 *   code path in the `else` branch instead of deleting it, so upstream edits to it merge cleanly.
 * - Mark every such edit with a comment carrying the ticket key (e.g. `// APP-14863: ...`).
 *
 * Lives in `:amity:common` so it is visible from every module of the fork.
 */
const val ONE_APP_CUSTOMIZATION = true
