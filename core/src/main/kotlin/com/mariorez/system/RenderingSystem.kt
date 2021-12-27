package com.mariorez.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mariorez.component.TextureComponent
import com.mariorez.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class RenderingSystem(
    private val batch: SpriteBatch
) : IteratingSystem(
    allOf(TextureComponent::class, TransformComponent::class).get()
) {

    private val textureMapper = mapperFor<TextureComponent>()
    private val transformMapper = mapperFor<TransformComponent>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val texture = textureMapper.get(entity).texture
        val position = transformMapper.get(entity).position

        batch.begin()
        batch.draw(texture!!, position.x, position.y)
        batch.end()
    }
}
