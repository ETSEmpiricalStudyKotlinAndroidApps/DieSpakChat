/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.gmail

import java.security.AccessController
import java.security.PrivilegedAction
import java.security.Provider

// https://stackoverflow.com/a/2033124
class JSSEProvider : Provider("HarmonyJSSE", 1.0, "Harmony JSSE Provider") {
    init {
        AccessController.doPrivileged(
            PrivilegedAction<Void?> {
                put(
                    "SSLContext.TLS",
                    "org.apache.harmony.xnet.provider.jsse.SSLContextImpl"
                )
                put("Alg.Alias.SSLContext.TLSv1", "TLS")
                put(
                    "KeyManagerFactory.X509",
                    "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl"
                )
                put(
                    "TrustManagerFactory.X509",
                    "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl"
                )
                null
            }
        )
    }
}
