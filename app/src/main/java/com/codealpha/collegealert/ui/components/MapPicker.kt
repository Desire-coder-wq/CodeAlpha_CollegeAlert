// In a new file, e.g., MapPicker.kt
package com.codealpha.collegealert.ui.components

import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun MapPicker(
    modifier: Modifier = Modifier,
    initialLat: Double = 0.3476,
    initialLon: Double = 32.5825,
    onLocationPicked: (lat: Double, lon: Double) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            // Ensure osmdroid knows our app's user agent (some tile servers block unknown agents)
            Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
            Configuration.getInstance().userAgentValue = ctx.packageName
            val map = MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(initialLat, initialLon))
            }

            val marker = Marker(map).apply {
                position = GeoPoint(initialLat, initialLon)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            map.overlays.add(marker)

            map.setOnTouchListener { v, event ->
                if (event.action == android.view.MotionEvent.ACTION_UP) {
                    val proj = map.projection
                    val geoPoint = proj.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint
                    marker.position = geoPoint
                    onLocationPicked(geoPoint.latitude, geoPoint.longitude)
                }
                false
            }

            // Hook lifecycle so map can pause/resume correctly
            val lifecycleObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> map.onResume()
                    Lifecycle.Event.ON_PAUSE -> map.onPause()
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

            map
        }
    )

    // Note: lifecycle observer is attached to the lifecycleOwner; no further cleanup required here.
}