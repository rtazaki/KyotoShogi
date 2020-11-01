package io.github.rtazaki.kyotoshogi.getMovePosUnitTest

import io.github.rtazaki.kyotoshogi.MainGame
import org.junit.Test

/**
 * 金(と)
 * 実装では、と, 金は同じ処理パスを通るため、金のみで試験を行う。
 * (と で試験したい場合、金 を と で置換してテスト実行すれば同じ結果が得られる。)
 * ホワイトボックス的観点から見て、2個に増やすことに意味がない。
 * (と が　TO になっても気づけないとか一理あるが、この場合だと、MainGameUnitTestでこけるため、
 * 単体試験としての狙いを移動可能範囲に絞る意味でこの対応とした。)
 */
class GoldUnitTest {
    /**
     * 通常系
     */
    @Test
    fun getMovePosGold_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "金").entries.first(),
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
            MainGame.Pos(3, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5筋)
     */
    @Test
    fun getMovePosGold_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "金").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(1筋)
     */
    @Test
    fun getMovePosGold_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "金").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(2, 2),
            MainGame.Pos(1, 2),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 境界(5段)
     */
    @Test
    fun getMovePosGold_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "金").entries.first(),
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
    fun getMovePosGold_5_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "金").entries.first(),
            players.getValue(true),
            players.getValue(false),
            false
        )
        val t = listOf(
            MainGame.Pos(4, 1),
            MainGame.Pos(2, 1),
            MainGame.Pos(3, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosGold_6_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 1)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(3, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 3)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(3, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "金").entries.first(),
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
    fun getMovePosGold_7_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "金").entries.first(),
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
            MainGame.Pos(3, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5筋)
     */
    @Test
    fun getMovePosGold_8_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(5, 3) to "金").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(1, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(1筋)
     */
    @Test
    fun getMovePosGold_9_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(1, 3) to "金").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(4, 4),
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 3),
            MainGame.Pos(5, 2)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_境界(5段)
     */
    @Test
    fun getMovePosGold_10_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 5) to "金").entries.first(),
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
    fun getMovePosGold_11_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 1) to "金").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf(
            MainGame.Pos(2, 5),
            MainGame.Pos(4, 5),
            MainGame.Pos(3, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }

    /**
     * 後手_移動先に自駒がいたら範囲外
     */
    @Test
    fun getMovePosGold_12_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 1)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(4, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(3, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 2)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(4, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 3)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(3, 4)] = "歩"
        val move = MainGame.getMovePos(
            piece = mapOf(MainGame.Pos(3, 3) to "金").entries.first(),
            players.getValue(false),
            players.getValue(true),
            true
        )
        val t = listOf<MainGame.Pos>()
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
