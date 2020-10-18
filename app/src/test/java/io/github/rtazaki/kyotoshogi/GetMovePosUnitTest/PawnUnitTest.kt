package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Assert
import org.junit.Test

/**
 * 歩
 */
class PawnUnitTest {
    /**
     * 1歩
     */
    @Test
    fun getMovePosPawn_1_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(true).pieces[MainGame.Pos(5, 5)] = "歩"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(setOf(MainGame.Pos(5, 4)), move)
    }

    /**
     * 境界
     */
    @Test
    fun getMovePosPawn_2_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(true).pieces[MainGame.Pos(5, 2)] = "歩"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 2) to "歩").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(setOf(MainGame.Pos(5, 1)), move)
    }

    /**
     * 範囲外
     */
    @Test
    fun getMovePosPawn_3_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(true).pieces[MainGame.Pos(5, 1)] = "歩"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 1) to "歩").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(setOf<MainGame.Pos>(), move)
    }

    /**
     * 相手駒がいても関係ない
     */
    @Test
    fun getMovePosPawn_4_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(true).pieces[MainGame.Pos(5, 5)] = "歩"
        player.getValue(false).pieces[MainGame.Pos(1, 2)] = "角"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(setOf(MainGame.Pos(5, 4)), move)
    }

    /**
     * 後手_境界
     */
    @Test
    fun getMovePosPawn_5_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(false).pieces[MainGame.Pos(5, 2)] = "歩"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 2) to "歩").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        Assert.assertEquals(setOf(MainGame.Pos(1, 5)), move)
    }

    /**
     * 後手_範囲外
     */
    @Test
    fun getMovePosPawn_6_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.pieces.clear()
        }
        player.getValue(false).pieces[MainGame.Pos(5, 2)] = "歩"
        val move = MainGame.getMovePos(
            mapOf(MainGame.Pos(5, 1) to "歩").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        Assert.assertEquals(setOf<MainGame.Pos>(), move)
    }
}
