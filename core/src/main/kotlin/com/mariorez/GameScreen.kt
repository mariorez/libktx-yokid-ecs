package com.mariorez

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mariorez.component.RenderComponent
import com.mariorez.component.TransformComponent
import com.mariorez.system.RenderingSystem
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.with
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile

class GameScreen : KtxScreen {

    private val engine = PooledEngine()
    private val batch = SpriteBatch()
    private val kotlinLogo = Texture("logo.png".toInternalFile()).apply {
        setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        )
    }

    init {
        engine.add {
            entity {
                with<RenderComponent> {
                    sprite.setRegion(kotlinLogo)
                }
                with<TransformComponent> {
                    position.x = 50f
                    position.y = 50f
                }
            }
        }

        engine.addSystem(RenderingSystem(batch))
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        kotlinLogo.disposeSafely()
        batch.disposeSafely()
    }
}
