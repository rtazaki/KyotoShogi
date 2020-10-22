package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 飛
 */
class RookUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosRook_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 1),
            MainGame.Pos(3, 2),
            MainGame.Pos(5, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 3),
            MainGame.Pos(3, 4),
            MainGame.Pos(3, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosRook_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1),
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 3),
            MainGame.Pos(5, 4),
            MainGame.Pos(5, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosRook_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(1, 1),
            MainGame.Pos(1, 2),
            MainGame.Pos(5, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 4),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5段)
     */
    @Test
    fun getMovePosRook_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 1),
            MainGame.Pos(3, 2),
            MainGame.Pos(3, 3),
            MainGame.Pos(3, 4),
            MainGame.Pos(5, 5),
            MainGame.Pos(4, 5),
            MainGame.Pos(2, 5),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1段)
     */
    @Test
    fun getMovePosRook_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 1)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1),
            MainGame.Pos(4, 1),
            MainGame.Pos(2, 1),
            MainGame.Pos(1, 1),
            MainGame.Pos(3, 2),
            MainGame.Pos(3, 3),
            MainGame.Pos(3, 4),
            MainGame.Pos(3, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 筋違い
     */
    @Test
    fun getMovePosRook_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(4, 2)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 2) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 1),
            MainGame.Pos(5, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(1, 2),
            MainGame.Pos(4, 3),
            MainGame.Pos(4, 4),
            MainGame.Pos(4, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosRook_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "飛"
        players.getValue(true).pieces[MainGame.Pos(3, 1)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(3, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosRook_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(2, 4)] = "飛"
        players.getValue(false).pieces[MainGame.Pos(5, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(4, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(2, 4) to "飛").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(2, 3),
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(1, 4),
            MainGame.Pos(2, 5)

        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_通常系
     */
    @Test
    fun getMovePosRook_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 5),
            MainGame.Pos(3, 4),
            MainGame.Pos(1, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 3),
            MainGame.Pos(3, 2),
            MainGame.Pos(3, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosRook_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5),
            MainGame.Pos(1, 4),
            MainGame.Pos(2, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 3),
            MainGame.Pos(1, 2),
            MainGame.Pos(1, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosRook_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(5, 5),
            MainGame.Pos(5, 4),
            MainGame.Pos(1, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 2),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5段)
     */
    @Test
    fun getMovePosRook_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 5),
            MainGame.Pos(3, 4),
            MainGame.Pos(3, 3),
            MainGame.Pos(3, 2),
            MainGame.Pos(1, 1),
            MainGame.Pos(2, 1),
            MainGame.Pos(4, 1),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1段)
     */
    @Test
    fun getMovePosRook_13_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 1)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5),
            MainGame.Pos(2, 5),
            MainGame.Pos(4, 5),
            MainGame.Pos(5, 5),
            MainGame.Pos(3, 4),
            MainGame.Pos(3, 3),
            MainGame.Pos(3, 2),
            MainGame.Pos(3, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_筋違い
     */
    @Test
    fun getMovePosRook_14_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(4, 2)] = "飛"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 2) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 5),
            MainGame.Pos(1, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(5, 4),
            MainGame.Pos(2, 3),
            MainGame.Pos(2, 2),
            MainGame.Pos(2, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosRook_15_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "飛"
        players.getValue(false).pieces[MainGame.Pos(3, 1)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(3, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(3, 4),
            MainGame.Pos(4, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosRook_16_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(2, 4)] = "飛"
        players.getValue(true).pieces[MainGame.Pos(5, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(4, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(2, 4) to "飛").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(4, 3),
            MainGame.Pos(1, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
