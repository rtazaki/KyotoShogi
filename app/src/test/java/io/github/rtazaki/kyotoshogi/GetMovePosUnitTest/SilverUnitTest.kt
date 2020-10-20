package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 銀
 */
class SilverUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosSilver_1_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(3, 3)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosSilver_2_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(5, 3)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(4, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosSilver_3_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(1, 3)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(1, 2),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5段)
     */
    @Test
    fun getMovePosSilver_4_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(3, 5)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1段)
     */
    @Test
    fun getMovePosSilver_5_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(3, 1)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(2, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosSilver_6_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(true).pieces[MainGame.Pos(3, 3)] = "銀"
        player.getValue(true).pieces[MainGame.Pos(4, 2)] = "歩"
        player.getValue(true).pieces[MainGame.Pos(3, 2)] = "歩"
        player.getValue(true).pieces[MainGame.Pos(2, 2)] = "歩"
        player.getValue(true).pieces[MainGame.Pos(4, 4)] = "歩"
        player.getValue(true).pieces[MainGame.Pos(2, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "銀").entries.first(),
            player.getValue(true),
            player.getValue(false),
            false
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosSilver_7_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(false).pieces[MainGame.Pos(5, 3)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "銀").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(2, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosSilver_8_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(false).pieces[MainGame.Pos(1, 3)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "銀").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(4, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5段)
     */
    @Test
    fun getMovePosSilver_9_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(false).pieces[MainGame.Pos(3, 5)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "銀").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1段)
     */
    @Test
    fun getMovePosSilver_10_Test() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.getValue(false).pieces[MainGame.Pos(3, 1)] = "銀"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "銀").entries.first(),
            player.getValue(false),
            player.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
