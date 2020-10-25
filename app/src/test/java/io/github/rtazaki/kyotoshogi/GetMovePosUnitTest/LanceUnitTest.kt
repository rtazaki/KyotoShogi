package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 香
 */
class LanceUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosLance_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 1),
            MainGame.Pos(5, 2),
            MainGame.Pos(5, 3),
            MainGame.Pos(5, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界
     */
    @Test
    fun getMovePosLance_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 2)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 2) to "香").entries.first(),
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
    fun getMovePosLance_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 1)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 1) to "香").entries.first(),
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
    fun getMovePosLance_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
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
     * 移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosLance_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 4),
            MainGame.Pos(5, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_通常系
     */
    @Test
    fun getMovePosLance_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 5),
            MainGame.Pos(1, 4),
            MainGame.Pos(1, 3),
            MainGame.Pos(1, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界
     */
    @Test
    fun getMovePosLance_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 2)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 2) to "香").entries.first(),
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
    fun getMovePosLance_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 1)] = "香"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 1) to "香").entries.first(),
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
    fun getMovePosLance_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "角"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
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
     * 後手_移動先に相手の駒がいたら相手の駒以降、範囲外
     */
    @Test
    fun getMovePosLance_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 2),
            MainGame.Pos(1, 3)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 無視するフラグ有効かつ、移動先の相手駒が玉の場合はその玉を無視する。
     */
    @Test
    fun getMovePosLance_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(false).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false,
            isIgnoreKing = true
        )
        val t = listOf(
            MainGame.Pos(5, 4),
            MainGame.Pos(5, 3),
            MainGame.Pos(5, 2),
            MainGame.Pos(5, 1)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_無視するフラグ有効かつ、移動先の相手駒が玉の場合はその玉を無視する。
     */
    @Test
    fun getMovePosLance_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "香"
        players.getValue(true).pieces[MainGame.Pos(1, 3)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 5) to "香").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true,
            isIgnoreKing = true

        )
        val t = listOf(
            MainGame.Pos(1, 2),
            MainGame.Pos(1, 3),
            MainGame.Pos(1, 4),
            MainGame.Pos(1, 5)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
