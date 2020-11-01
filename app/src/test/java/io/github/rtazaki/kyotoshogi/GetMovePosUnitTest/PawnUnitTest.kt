package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 歩
 */
class PawnUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosPawn_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界
     */
    @Test
    fun getMovePosPawn_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 2) to "歩").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 範囲外
     */
    @Test
    fun getMovePosPawn_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 1) to "歩").entries.first(),
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
    fun getMovePosPawn_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(5, 4)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
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
    fun getMovePosPawn_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界
     */
    @Test
    fun getMovePosPawn_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 2) to "歩").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_範囲外
     */
    @Test
    fun getMovePosPawn_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 1) to "歩").entries.first(),
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
    fun getMovePosPawn_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(5, 4)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "歩").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
