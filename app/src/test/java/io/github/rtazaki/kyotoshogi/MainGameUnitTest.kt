package io.github.rtazaki.kyotoshogi

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
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

    // テストを書いてて気づいたこと
    // selectの概念と同じく、moveも盤面要素な気がしてきた。
    // latest: 最後に移動した駒(色付きかつ駒の向きにより、先手/後手が区別できる)
    // move: 駒の移動可能範囲。MainGameクラス内で反転処理や行けない場所は完全に閉じている。
    // select: 選択した駒情報。打ち駒を実装する際に、select(0, 0)とか使えないかな?
    // TODO: moveをActivity側でもっておき、引数で渡すことを検討する。(いずれこのメソッドを削除する。)
    /**
     * クリア処理
     */
    @Test
    fun clearMoveSelectTest() {
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        player.forEach {
            it.value.move = mutableListOf(MainGame.Pos(0, 0))
            it.value.select = MainGame.Pos(1, 1)
            assertNotEquals(0, it.value.move.size)
            assertNotEquals(MainGame.Pos(0, 0), it.value.select)
        }
        MainGame.clearMoveSelect(player)
        player.forEach {
            assertEquals(0, it.value.move.size)
            assertEquals(MainGame.Pos(0, 0), it.value.select)
        }
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

    // テスト書いてて気づいたこと
    // selectの概念がゲーム要素ではなく、盤面要素な気がしてきた。
    // TODO: selectはlatestと同じく、Activity側でもっておき、引数で渡すことを検討する。
    /**
     * 先手 1五歩 → 1四飛
     */
    @Test
    fun changePiecePositiveTest() {
        val player = MainGame.Player()
        player.select = MainGame.Pos(1, 5)
        assertEquals("歩", player.piece[4].name)
        assertEquals(MainGame.Pos(1, 5), player.piece[4].pos)
        MainGame.changePiece(MainGame.Pos(1, 4), player, false)
        assertEquals("飛", player.piece[4].name)
        assertEquals(MainGame.Pos(1, 4), player.piece[4].pos)
    }

    /**
     * 後手 2五金 → 3四桂 (盤: 4一金 → 3二桂)
     * 中の状態は先手と同じ。盤と関連する部分でひっくり返す。
     */
    @Test
    fun changePieceTestNegativeTest() {
        val player = MainGame.Player()
        player.select = MainGame.Pos(4, 1)
        assertEquals("金", player.piece[3].name)
        assertEquals(MainGame.Pos(2, 5), player.piece[3].pos)
        MainGame.changePiece(MainGame.Pos(3, 2), player, true)
        assertEquals("桂", player.piece[3].name)
        assertEquals(MainGame.Pos(3, 4), player.piece[3].pos)
    }

    /**
     * 先手が後手の と を取った場合、後手の と は無くなって、結果として 香 が手に入る。
     * 5五と(盤: 1一と) → 香
     * NOTE: このmirrorは結構ややこしい。そのほかのメソッドではturnの反対を渡すが、
     * このメソッドでは、盤から見たターン変更前なので、mirrorはturnそのものを渡す。
     */
    @Test
    fun changeEnemyPiecePositiveTest() {
        val player = MainGame.Player()
        assertEquals("と", player.piece[0].name)
        assertEquals(MainGame.Pos(5, 5), player.piece[0].pos)
        val getPiece = MainGame.changeEnemyPiece(
            MainGame.Pos(1, 1), player, true
        )
        assertEquals(4, player.piece.size)
        assertEquals("銀", player.piece[0].name)
        assertEquals("香", getPiece)
    }

    /**
     * 後手が先手の 銀 を取った場合、先手の 銀 は無くなって、結果として 銀 が手に入る。
     */
    @Test
    fun changeEnemyPieceNegativeTest() {
        val player = MainGame.Player()
        assertEquals("銀", player.piece[1].name)
        assertEquals(MainGame.Pos(4, 5), player.piece[1].pos)
        val getPiece = MainGame.changeEnemyPiece(
            MainGame.Pos(4, 5), player, false
        )
        assertEquals(4, player.piece.size)
        assertEquals("玉", player.piece[1].name)
        assertEquals("銀", getPiece)
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
}