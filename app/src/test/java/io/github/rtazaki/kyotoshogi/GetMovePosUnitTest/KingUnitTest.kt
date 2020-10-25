package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 玉
 */
class KingUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosKing_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(4, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosKing_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosKing_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(1, 2),
            MainGame.Pos(2, 3),
            MainGame.Pos(2, 4),
            MainGame.Pos(1, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5段)
     */
    @Test
    fun getMovePosKing_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(4, 5),
            MainGame.Pos(2, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1段)
     */
    @Test
    fun getMovePosKing_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 1),
            MainGame.Pos(2, 1),
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosKing_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(3, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(4, 4)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(3, 4)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_通常系
     */
    @Test
    fun getMovePosKing_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(2, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(2, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(4, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosKing_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 2),
            MainGame.Pos(2, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosKing_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 3),
            MainGame.Pos(4, 2),
            MainGame.Pos(5, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5段)
     */
    @Test
    fun getMovePosKing_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(2, 1),
            MainGame.Pos(4, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1段)
     */
    @Test
    fun getMovePosKing_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 5),
            MainGame.Pos(4, 5),
            MainGame.Pos(2, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(4, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosKing_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(3, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(4, 4)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(3, 4)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 自玉の移動可能範囲が相手駒によって制限される
     */
    @Test
    fun getMovePosKing_13_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "飛"
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "香"
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 2),
            MainGame.Pos(2, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_自玉の移動可能範囲が相手駒によって制限される
     */
    @Test
    fun getMovePosKing_14_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "飛"
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "香"
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "玉").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 4),
            MainGame.Pos(4, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
