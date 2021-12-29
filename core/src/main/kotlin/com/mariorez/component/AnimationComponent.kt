package com.mariorez.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

enum class AnimationType(
    val key: String,
    val playMode: PlayMode = NORMAL,
    val frames: Int = 1,
    val frameDuration: Float = 1f
) {
    IDLE("idle", LOOP, 7, 0.1f),
    RUNNING("run", LOOP, 8, 0.07f)
}

class AnimationComponent : Component, Poolable {
    var stateTime = 0f
    var type = AnimationType.IDLE
    lateinit var animation: Animation<TextureRegion>

    override fun reset() {
        stateTime = 0f
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}
