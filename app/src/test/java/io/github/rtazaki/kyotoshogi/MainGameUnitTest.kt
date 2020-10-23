package io.github.rtazaki.kyotoshogi

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * MainGameクラス(オブジェクト)のUnitTestクラス
 */
class MainGameUnitTest {
    /**
     * 範囲外チェックはしていないので、1..5を入力する。
     */
    @Test
    fun getMirrorPosTest() {
        assertEquals(
            MainGame.Pos(5, 5),
            MainGame.getMirrorPos(MainGame.Pos(1, 1))
        )
        assertEquals(
            MainGame.Pos(1, 1),
            MainGame.getMirrorPos(MainGame.Pos(5, 5))
        )
    }

    /**
     * 駒が裏返っているかチェック。
     * 実装も愚直にwhen(KotlinのSwitch/Case)で返しているだけ。
     */
    @Test
    fun invertPieceTest() {
        assertEquals("香", MainGame.invertPiece("と"))
        assertEquals("と", MainGame.invertPiece("香"))
        assertEquals("銀", MainGame.invertPiece("角"))
        assertEquals("角", MainGame.invertPiece("銀"))
        assertEquals("金", MainGame.invertPiece("桂"))
        assertEquals("桂", MainGame.invertPiece("金"))
        assertEquals("飛", MainGame.invertPiece("歩"))
        assertEquals("歩", MainGame.invertPiece("飛"))
        assertEquals("玉", MainGame.invertPiece("玉"))
    }

    /**
     * 駒が単純に追加されることを確認。
     */
    @Test
    fun addPutPiecePositiveTest() {
        val player = MainGame.Player()
        player.hands["金"] = 2

        // 1個目
        MainGame.addPutPiece(
            name = "金",
            button = MainGame.Pos(1, 4),
            player,
            false
        )
        assertEquals("金", player.pieces.getValue(MainGame.Pos(1, 4)))
        assertEquals(1, player.hands["金"])

        // 2個目
        MainGame.addPutPiece(
            name = "金",
            button = MainGame.Pos(2, 5),
            player,
            false
        )
        assertEquals("金", player.pieces.getValue(MainGame.Pos(2, 5)))
        assert(!player.hands.containsKey("金"))
    }

    /**
     * 後手
     */
    @Test
    fun addPutPieceNegativeTest() {
        val player = MainGame.Player()
        player.hands["金"] = 2

        // 1個目
        MainGame.addPutPiece(
            name = "金",
            button = MainGame.Pos(1, 4),
            player,
            true
        )
        assertEquals("金", player.pieces.getValue(MainGame.Pos(5, 2)))
        assertEquals(1, player.hands["金"])

        // 2個目
        MainGame.addPutPiece(
            name = "金",
            button = MainGame.Pos(2, 5),
            player,
            true
        )
        assertEquals("金", player.pieces.getValue(MainGame.Pos(4, 1)))
        assert(!player.hands.containsKey("金"))
    }

    /**
     * 先手 1五歩 → 1四飛
     */
    @Test
    fun changePiecePositiveTest() {
        val player = MainGame.Player()
        player.pieces[MainGame.Pos(1, 5)] = "歩"
        assertEquals("歩", player.pieces.getValue(MainGame.Pos(1, 5)))
        MainGame.changePiece(
            select = MainGame.Pos(1, 5),
            button = MainGame.Pos(1, 4),
            player,
            false
        )
        assert(!player.pieces.keys.containsAll(listOf(MainGame.Pos(1, 5))))
        assertEquals("飛", player.pieces.getValue(MainGame.Pos(1, 4)))
    }

    /**
     * 後手 2五金 → 3四桂 (盤: 4一金 → 3二桂)
     * 中の状態は先手と同じ。盤と関連する部分でひっくり返す。
     */
    @Test
    fun changePieceTestNegativeTest() {
        val player = MainGame.Player()
        player.pieces[MainGame.Pos(2, 5)] = "金"
        assertEquals("金", player.pieces.getValue(MainGame.Pos(2, 5)))
        MainGame.changePiece(
            select = MainGame.Pos(4, 1),
            button = MainGame.Pos(3, 2),
            player,
            true
        )
        assert(!player.pieces.keys.containsAll(listOf(MainGame.Pos(2, 5))))
        assertEquals("桂", player.pieces.getValue(MainGame.Pos(3, 4)))
    }

    /**
     * 先手が後手の と を取った場合、後手の と は無くなって、結果として 香 が手に入る。
     * 5五と(盤: 1一と) → 香
     * 2五香(盤: 4一と) → 香
     * NOTE: このmirrorは結構ややこしい。そのほかのメソッドではturnの反対を渡すが、
     * このメソッドでは、盤から見たターン変更前なので、mirrorはturnそのものを渡す。
     */
    @Test
    fun changeEnemyPiecePositiveTest() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(false).pieces[MainGame.Pos(5, 5)] = "と"
        players.getValue(false).pieces[MainGame.Pos(4, 5)] = "銀"
        players.getValue(false).pieces[MainGame.Pos(3, 5)] = "玉"
        players.getValue(false).pieces[MainGame.Pos(2, 5)] = "香"
        players.getValue(false).pieces[MainGame.Pos(1, 5)] = "歩"

        // 1個目
        MainGame.changeEnemyPiece(
            button = MainGame.Pos(1, 1),
            players.getValue(false),
            players.getValue(true),
            true
        )
        assertEquals(4, players.getValue(false).pieces.size)
        assertEquals("香", players.getValue(true).hands.entries.first().key)
        assertEquals(1, players.getValue(true).hands.getValue("香"))

        // 2個目
        MainGame.changeEnemyPiece(
            button = MainGame.Pos(4, 1),
            players.getValue(false),
            players.getValue(true),
            true
        )
        assertEquals(3, players.getValue(false).pieces.size)
        assertEquals("香", players.getValue(true).hands.entries.first().key)
        assertEquals(2, players.getValue(true).hands.getValue("香"))
    }

    /**
     * 後手が先手の 銀 を取った場合、先手の 銀 は無くなって、結果として 銀 が手に入る。
     */
    @Test
    fun changeEnemyPieceNegativeTest() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.getValue(true).pieces[MainGame.Pos(5, 5)] = "と"
        players.getValue(true).pieces[MainGame.Pos(4, 5)] = "銀"
        players.getValue(true).pieces[MainGame.Pos(3, 5)] = "玉"
        players.getValue(true).pieces[MainGame.Pos(2, 5)] = "金"
        players.getValue(true).pieces[MainGame.Pos(1, 5)] = "角"

        // 1個目
        MainGame.changeEnemyPiece(
            button = MainGame.Pos(4, 5),
            players.getValue(true),
            players.getValue(false),
            false
        )
        assertEquals(4, players.getValue(true).pieces.size)
        assertEquals("銀", players.getValue(false).hands.entries.first().key)
        assertEquals(1, players.getValue(false).hands.getValue("銀"))

        // 2個目
        MainGame.changeEnemyPiece(
            button = MainGame.Pos(1, 5),
            players.getValue(true),
            players.getValue(false),
            false
        )
        assertEquals(3, players.getValue(true).pieces.size)
        assertEquals("銀", players.getValue(false).hands.entries.first().key)
        assertEquals(2, players.getValue(false).hands.getValue("銀"))
    }

    /**
     * 駒が表を向くかチェック。
     * privateメソッドをユニットテストするには、ちょっとしたテクニックがいる。
     * getDeclaredMethodで、メソッド名、引数の型を渡してリフレクションを取得し、
     * アクセス権をisAccessibleで有効化し、invoke(クラス名, 引数)のようにする。
     */
    @Test
    fun convertHandsNameTest() {
        val method = MainGame.javaClass.getDeclaredMethod(
            "convertHandsName", CharSequence::class.java
        )
        method.isAccessible = true
        assertEquals("香", method.invoke(MainGame, "香"))
        assertEquals("香", method.invoke(MainGame, "と"))
        assertEquals("銀", method.invoke(MainGame, "銀"))
        assertEquals("銀", method.invoke(MainGame, "角"))
        assertEquals("金", method.invoke(MainGame, "金"))
        assertEquals("金", method.invoke(MainGame, "桂"))
        assertEquals("飛", method.invoke(MainGame, "飛"))
        assertEquals("飛", method.invoke(MainGame, "歩"))
        assertEquals("玉", method.invoke(MainGame, "玉"))
    }

    /**
     * 打ち駒の移動可能範囲は先手/後手関係なく、
     * 駒のあるところには打てないというだけ。
     */
    @Test
    fun getPutPiecePosTest() {
        val players = mapOf(true to MainGame.Player(), false to MainGame.Player())
        players.forEach { (_, player) ->
            player.pieces[MainGame.Pos(5, 5)] = "と"
            player.pieces[MainGame.Pos(4, 5)] = "銀"
            player.pieces[MainGame.Pos(3, 5)] = "玉"
            player.pieces[MainGame.Pos(2, 5)] = "金"
            player.pieces[MainGame.Pos(1, 5)] = "歩"
        }
        val move = MainGame.getPutPiecePos(players)
        val t = listOf(
            MainGame.Pos(5, 2),
            MainGame.Pos(4, 2),
            MainGame.Pos(3, 2),
            MainGame.Pos(2, 2),
            MainGame.Pos(1, 2),
            MainGame.Pos(5, 3),
            MainGame.Pos(4, 3),
            MainGame.Pos(3, 3),
            MainGame.Pos(2, 3),
            MainGame.Pos(1, 3),
            MainGame.Pos(5, 4),
            MainGame.Pos(4, 4),
            MainGame.Pos(3, 4),
            MainGame.Pos(2, 4),
            MainGame.Pos(1, 4)
        )
        assert(move.containsAll(t))
        assert(t.containsAll(move))
    }
}
