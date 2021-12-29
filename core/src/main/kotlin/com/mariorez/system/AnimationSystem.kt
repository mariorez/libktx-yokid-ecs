package com.mariorez.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mariorez.component.AnimationComponent
import com.mariorez.component.AnimationType
import com.mariorez.component.RenderComponent
import ktx.ashley.allOf
import ktx.assets.toInternalFile
import ktx.collections.gdxArrayOf
import java.util.*

class AnimationSystem :
    IteratingSystem(
        allOf(AnimationComponent::class, RenderComponent::class).get()
    ) {

    private val animationCache = EnumMap<AnimationType, Animation<TextureRegion>>(AnimationType::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animationComponent = AnimationComponent.mapper.get(entity).apply {
            stateTime += deltaTime
            animation = loadAnimationFromFiles(type)
        }

        RenderComponent.mapper.get(entity).apply {
            sprite.setRegion(
                animationComponent.animation.getKeyFrame(animationComponent.stateTime)
            )
        }
    }

    private fun loadAnimationFromFiles(
        animationType: AnimationType
    ): Animation<TextureRegion> {
        return animationCache.getOrPut(animationType) {
            val textureArray = gdxArrayOf<TextureRegion>().apply {
                (1..animationType.frames).forEach {
                    add(
                        TextureRegion(
                            Texture("${animationType.key}-$it.png".toInternalFile()).apply {
                                setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                            }
                        )
                    )
                }
            }

            Animation(animationType.frameDuration, textureArray).apply {
                setPlayMode(animationType.playMode)
            }
        }
    }
}
