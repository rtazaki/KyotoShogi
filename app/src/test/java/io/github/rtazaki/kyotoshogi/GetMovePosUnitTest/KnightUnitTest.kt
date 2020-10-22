package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 桂
 */
class KnightUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosKnight_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(4, 5)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 5) to "桂").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 3),
            MainGame.Pos(3, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosKnight_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "桂").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosKnight_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "桂").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(2, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 範囲外(2段)
     */
    @Test
    fun getMovePosKnight_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 2)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 2) to "桂").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 範囲外(1段)
     */
    @Test
    fun getMovePosKnight_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 1)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "桂").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosKnight_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "桂"
        players.getValue(true).pieces[MainGame.Pos(4, 1)] = "銀"
        players.getValue(true).pieces[MainGame.Pos(2, 1)] = "金"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "桂").entries.first(),
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
    fun getMovePosKnight_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(4, 5)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(4, 5) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 3),
            MainGame.Pos(3, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosKnight_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosKnight_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_範囲外(2段)
     */
    @Test
    fun getMovePosKnight_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 2)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 2) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_範囲外(1段)
     */
    @Test
    fun getMovePosKnight_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 1)] = "桂"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosKnight_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "桂"
        players.getValue(false).pieces[MainGame.Pos(4, 1)] = "銀"
        players.getValue(false).pieces[MainGame.Pos(2, 1)] = "金"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "桂").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
