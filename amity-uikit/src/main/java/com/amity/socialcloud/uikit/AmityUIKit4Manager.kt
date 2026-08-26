package com.amity.socialcloud.uikit

import android.util.Log
import com.amity.socialcloud.sdk.api.core.AmityCoreClient
import com.amity.socialcloud.sdk.api.core.encryption.AmityDBEncryption
import com.amity.socialcloud.sdk.api.core.endpoint.AmityEndpoint
import com.amity.socialcloud.sdk.video.AmityStreamBroadcasterClient
import com.amity.socialcloud.sdk.video.AmityStreamPlayerClient
import com.amity.socialcloud.uikit.chat.compose.localization.DefaultAmityChatStringProvider
import com.amity.socialcloud.uikit.common.ad.AmityAdEngine
import com.amity.socialcloud.uikit.common.config.AmityUIKitConfigController
import com.amity.socialcloud.uikit.common.eventbus.NetworkConnectionEventPublisher
import com.amity.socialcloud.uikit.common.infra.db.AmityUIKitDB
import com.amity.socialcloud.uikit.common.infra.initializer.AmityAppContext
import com.amity.socialcloud.uikit.common.networkconfig.AmityNetworkConfigService
import com.amity.socialcloud.uikit.community.compose.localization.DefaultAmitySocialStringProvider
import com.amity.socialcloud.uikit.community.compose.post.detail.AmityPostDetailPageBehavior
import com.amity.socialcloud.uikit.community.compose.visitor.AmityVisitorUsageLimitObserver
import io.reactivex.rxjava3.core.Completable

object AmityUIKit4Manager {

    private const val BUILD_INFO_TAG = "AmityUIKit"

    val behavior = AmityUIKit4Behavior()

    fun setup(
        apiKey: String,
        endpoint: AmityEndpoint,
        dbEncryption: AmityDBEncryption = AmityDBEncryption.NONE
    ) {
        AmityUIKitDB.init()
        AmityCoreClient.setup(
            apiKey = apiKey,
            endpoint = endpoint,
            dbEncryption = dbEncryption
        )
        AmityStreamBroadcasterClient.setup(AmityCoreClient.getConfiguration())
        AmityStreamPlayerClient.setup(AmityCoreClient.getConfiguration())
        AmityAdEngine.init()
        AmityVisitorUsageLimitObserver.init()
        AmityNetworkConfigService.init(apiKey)
        AmityUIKitConfigController.setup(AmityAppContext.getContext())
        AmityUIKitConfigController.initializeShareableLinkPattern()
        DefaultAmitySocialStringProvider.initialize(AmityAppContext.getContext())
        DefaultAmityChatStringProvider.initialize(AmityAppContext.getContext())
        NetworkConnectionEventPublisher.initPublisher(context = AmityAppContext.getContext())
        logBuildInfo()
    }

    /**
     * One line at startup naming exactly what is running: the UIKit version, the SDK it resolved
     * against, and the commit the UIKit was built from. A bug report that quotes this identifies
     * the build without anyone having to guess which branch or artifact produced it.
     *
     * The SDK version is read at runtime rather than baked in, so it reports the artifact actually
     * on the classpath -- which is the point when a composite build or a version override has
     * replaced the declared one. The commit hash is "unknown" when the artifact was built from a
     * tree without git.
     */
    private fun logBuildInfo() {
        runCatching {
            Log.i(
                BUILD_INFO_TAG,
                "UIKit ${BuildConfig.AMITY_UIKIT_VERSION} " +
                        "(${BuildConfig.AMITY_UIKIT_COMMIT_HASH}) | " +
                        "SDK ${AmityCoreClient.getAmityCoreSdkVersion()}"
            )
        }
    }

    fun syncNetworkConfig(): Completable {
        return AmityNetworkConfigService.syncNetworkConfig()
    }
}