package dev.miladanbari.newsreader.view.article.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> navTypeOf(
    isNullableAllowed: Boolean = true,
    hasUrl: Boolean = false,
): NavType<T> {
    return object : NavType<T>(isNullableAllowed) {
        override fun get(bundle: Bundle, key: String): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, T::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): T {
            val json = if (hasUrl) Uri.decode(value) else value
            return Json.decodeFromString<T>(json)
        }

        override fun serializeAsValue(value: T): String {
            val json = Json.encodeToString(value)
            return if (hasUrl) Uri.encode(json) else json
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }
    }
}
