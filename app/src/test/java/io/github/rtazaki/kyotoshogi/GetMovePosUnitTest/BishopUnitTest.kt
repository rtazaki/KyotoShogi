package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 角
 */
class BishopUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosBishop_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1),
            MainGame.Pos(1, 1),
            MainGame.Pos(4, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(5, 5),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosBishop_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 1),
            MainGame.Pos(4, 2),
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosBishop_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 1),
            MainGame.Pos(2, 2),
            MainGame.Pos(2, 4),
            MainGame.Pos(3, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5段)
     */
    @Test
    fun getMovePosBishop_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 3),
            MainGame.Pos(1, 3),
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1段)
     */
    @Test
    fun getMovePosBishop_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(5, 3),
            MainGame.Pos(1, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 筋違い
     */
    @Test
    fun getMovePosBishop_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 2) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1),
            MainGame.Pos(3, 1),
            MainGame.Pos(5, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(2, 4),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosBishop_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(1, 5)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosBishop_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(1, 5)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(2, 4) to "角").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 3),
            MainGame.Pos(1, 3),
            MainGame.Pos(3, 5),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_通常系
     */
    @Test
    fun getMovePosBishop_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5),
            MainGame.Pos(5, 5),
            MainGame.Pos(2, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(1, 1),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosBishop_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 5),
            MainGame.Pos(2, 4),
            MainGame.Pos(2, 2),
            MainGame.Pos(3, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosBishop_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 5),
            MainGame.Pos(4, 4),
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5段)
     */
    @Test
    fun getMovePosBishop_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 3),
            MainGame.Pos(5, 3),
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1段)
     */
    @Test
    fun getMovePosBishop_13_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(1, 3),
            MainGame.Pos(5, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_筋違い
     */
    @Test
    fun getMovePosBishop_14_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 2) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5),
            MainGame.Pos(3, 5),
            MainGame.Pos(1, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(4, 2),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosBishop_15_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(1, 5)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosBishop_16_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(1, 5)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(2, 4) to "角").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 3),
            MainGame.Pos(5, 3),
            MainGame.Pos(3, 1),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
