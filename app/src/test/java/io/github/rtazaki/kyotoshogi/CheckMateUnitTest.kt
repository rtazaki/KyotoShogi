package io.github.rtazaki.kyotoshogi

import org.junit.Test

/**
 * 詰み判定のUnitTestクラス
 */
class CheckMateUnitTest {
    /**
     * 持ち駒がないパターンでの詰み判定
     */
    @Test
    fun isCheckMate_1_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(4, 4)] = "香"
        players.getValue(true).pieces[MainGame.Pos(2, 4)] = "桂"
        players.getValue(true).pieces[MainGame.Pos(1, 4)] = "銀"
        players.getValue(true).pieces[MainGame.Pos(1, 5)] = "歩"

        players.getValue(false).pieces[MainGame.Pos(2, 5)] = "銀"
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "と"
        players.getValue(false).pieces[MainGame.Pos(1, 4)] = "飛"
        players.getValue(false).pieces[MainGame.Pos(3, 4)] = "桂"
        players.getValue(false).pieces[MainGame.Pos(5, 3)] = "玉"
        assert(MainGame.isCheckMate(players, false))
    }

    /**
     * 持ち駒があるパターンでの詰み判定
     */
    @Test
    fun isCheckMate_2_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(1, 2)] = "と"
        players.getValue(true).pieces[MainGame.Pos(4, 4)] = "香"
        players.getValue(true).pieces[MainGame.Pos(2, 4)] = "桂"
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"

        players.getValue(false).pieces[MainGame.Pos(1, 5)] = "歩"
        players.getValue(false).pieces[MainGame.Pos(2, 5)] = "金"
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(3, 4)] = "角"

        players.getValue(false).hands = mutableMapOf("銀" to 1, "飛" to 1)
        assert(MainGame.isCheckMate(players, false))
    }

    /**
     * 後手_持ち駒がないパターンでの詰み判定(盤面がひっくり返っただけ)
     */
    @Test
    fun isCheckMate_3_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(3, 3)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(4, 4)] = "香"
        players.getValue(false).pieces[MainGame.Pos(2, 4)] = "桂"
        players.getValue(false).pieces[MainGame.Pos(1, 4)] = "銀"
        players.getValue(false).pieces[MainGame.Pos(1, 5)] = "歩"

        players.getValue(true).pieces[MainGame.Pos(2, 5)] = "銀"
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "と"
        players.getValue(true).pieces[MainGame.Pos(1, 4)] = "飛"
        players.getValue(true).pieces[MainGame.Pos(3, 4)] = "桂"
        players.getValue(true).pieces[MainGame.Pos(5, 3)] = "玉"
        assert(MainGame.isCheckMate(players, true))
    }

    /**
     * 後手_持ち駒があるパターンでの詰み判定(盤面がひっくり返っただけ)
     */
    @Test
    fun isCheckMate_4_Test() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(1, 2)] = "と"
        players.getValue(false).pieces[MainGame.Pos(4, 4)] = "香"
        players.getValue(false).pieces[MainGame.Pos(2, 4)] = "桂"
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"

        players.getValue(true).pieces[MainGame.Pos(1, 5)] = "歩"
        players.getValue(true).pieces[MainGame.Pos(2, 5)] = "金"
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(3, 4)] = "角"

        players.getValue(true).hands = mutableMapOf("銀" to 1, "飛" to 1)
        assert(MainGame.isCheckMate(players, true))
    }
}
