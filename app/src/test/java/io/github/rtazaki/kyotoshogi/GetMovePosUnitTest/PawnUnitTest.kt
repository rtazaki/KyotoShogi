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
            it.value.piece.clear()
        }
        player.getValue(true).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 5))
        )
        val move = MainGame.getMovePos(
            player.getValue(true).piece[0],
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(listOf(MainGame.Pos(5, 4)), move)
    }

    /**
     * 境界
     */
    @Test
    fun getMovePosPawn_2_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.piece.clear()
        }
        player.getValue(true).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 2))
        )
        val move = MainGame.getMovePos(
            player.getValue(true).piece[0],
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(listOf(MainGame.Pos(5, 1)), move)
    }

    /**
     * 範囲外
     */
    @Test
    fun getMovePosPawn_3_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.piece.clear()
        }
        player.getValue(true).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 1))
        )
        val move = MainGame.getMovePos(
            player.getValue(true).piece[0],
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(listOf<MainGame.Pos>(), move)
    }

    /**
     * 相手駒がいても関係ない
     */
    @Test
    fun getMovePosPawn_4_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.piece.clear()
        }
        player.getValue(true).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 5))
        )
        player.getValue(false).piece.add(
            MainGame.Piece("角", MainGame.Pos(5, 4))
        )
        val move = MainGame.getMovePos(
            player.getValue(true).piece[0],
            player.getValue(true),
            player.getValue(false),
            false
        )
        Assert.assertEquals(listOf(MainGame.Pos(5, 4)), move)
    }

    /**
     * 後手_境界
     */
    @Test
    fun getMovePosPawn_5_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.piece.clear()
        }
        player.getValue(false).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 2))
        )
        val move = MainGame.getMovePos(
            player.getValue(false).piece[0],
            player.getValue(false),
            player.getValue(true),
            true
        )
        Assert.assertEquals(listOf(MainGame.Pos(1, 5)), move)
    }

    /**
     * 後手_範囲外
     */
    @Test
    fun getMovePosPawn_6_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        // 一旦空にする
        player.forEach {
            it.value.piece.clear()
        }
        player.getValue(false).piece.add(
            MainGame.Piece("歩", MainGame.Pos(5, 1))
        )
        val move = MainGame.getMovePos(
            player.getValue(false).piece[0],
            player.getValue(false),
            player.getValue(true),
            true
        )
        Assert.assertEquals(listOf<MainGame.Pos>(), move)
    }
}
