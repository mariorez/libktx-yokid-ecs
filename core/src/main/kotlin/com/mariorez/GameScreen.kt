package com.mariorez

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mariorez.component.AnimationComponent
import com.mariorez.component.RenderComponent
import com.mariorez.component.TransformComponent
import com.mariorez.system.AnimationSystem
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
            entity { // KTX
                with<RenderComponent> {
                    sprite.setRegion(kotlinLogo)
                }
                with<TransformComponent> {
                    position.x = 170f
                    position.y = 300f
                }
            }
            entity { // Yokid
                with<AnimationComponent>()
                with<RenderComponent>()
                with<TransformComponent> {
                    position.x = 25f
                    position.y = 25f
                }
            }
        }

        engine.apply {
            addSystem(AnimationSystem())
            addSystem(RenderingSystem(batch))
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        kotlinLogo.disposeSafely()
        batch.disposeSafely()
    }
}
