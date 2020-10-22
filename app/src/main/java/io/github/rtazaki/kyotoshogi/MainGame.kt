package io.github.rtazaki.kyotoshogi

/**
 * MainGameクラス(オブジェクト)
 * 京都将棋のゲーム要素は、このクラスに集約する。
 */
object MainGame {
    /**
     * Playerクラス
     * Posをキーに駒検索をする。
     * @property pieces
     */
    data class Player(var pieces: MutableMap<Pos, CharSequence> = mutableMapOf())

    /**
     * 位置情報クラス
     * @property column 筋
     * @property row 段
     */
    data class Pos(val column: Int, val row: Int)

    /**
     * 反転した位置を返す
     * @param pos 位置情報
     * @return 6-1=5, 6-2=4, 6-3=3, 6-4=2, 6-5=1
     */
    fun getMirrorPos(pos: Pos): Pos {
        return Pos(6 - pos.column, 6 - pos.row)
    }

    /**
     * 駒反転処理
     * @param name 駒名
     * @return 変換駒名
     */
    fun invertPiece(name: CharSequence): CharSequence {
        return when (name) {
            "香" -> "と"
            "と" -> "香"
            "銀" -> "角"
            "角" -> "銀"
            "金" -> "桂"
            "桂" -> "金"
            "飛" -> "歩"
            "歩" -> "飛"
            else -> "玉"
        }
    }

    /**
     * 駒移動
     * @param select 選択した駒(位置)
     * @param button 実際に選択した位置
     * @param player 自分駒情報
     * @param mirror 反転
     */
    fun changePiece(select: Pos, button: Pos, player: Player, mirror: Boolean) {
        val s = if (mirror) getMirrorPos(select) else select
        val b = if (mirror) getMirrorPos(button) else button
        if (player.pieces.containsKey(s)) {
            player.pieces[b] = invertPiece(player.pieces.getValue(s))
            player.pieces.remove(s)
        }
    }

    /**
     * 持ち駒取得処理
     * @param move 実際に選択した位置
     * @param player 相手駒情報
     * @param mirror 反転
     * @return 取った駒の名前
     */
    fun changeEnemyPiece(move: Pos, player: Player, mirror: Boolean): CharSequence {
        val m = if (mirror) getMirrorPos(move) else move
        if (player.pieces.containsKey(m)) {
            val name = convertHandsName(player.pieces.getValue(m))
            player.pieces.remove(m)
            return name
        }
        return ""
    }

    /**
     * 表面を向くように持ち駒をそろえる。
     * @param name 駒名
     * @return 表駒名
     */
    private fun convertHandsName(name: CharSequence): CharSequence {
        return when (name) {
            "香", "と" -> "香"
            "銀", "角" -> "銀"
            "金", "桂" -> "金"
            "飛", "歩" -> "飛"
            else -> "玉"
        }
    }

    /**
     * 移動可能範囲を返す
     * @param piece 選択された駒情報
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param mirror 反転
     * @return 移動可能範囲
     */
    fun getMovePos(
        piece: Map.Entry<Pos, CharSequence>,
        p1: Player,
        p2: Player,
        mirror: Boolean
    ): MutableSet<Pos> {
        val move = mutableSetOf<Pos>()
        when (piece.value) {
            "歩" -> {
                // 縦に一歩
                if (piece.key.row > 1) {
                    val row = piece.key.row - 1
                    val column = piece.key.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "桂" -> {
                // 2段飛び斜め
                if (piece.key.row > 2) {
                    val row = piece.key.row - 2
                    setOf(piece.key.column - 1, piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "銀" -> {
                // 上三方向
                if (piece.key.row > 1) {
                    val row = piece.key.row - 1
                    (piece.key.column - 1..piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 斜め下
                if (piece.key.row < 5) {
                    val row = piece.key.row + 1
                    setOf(piece.key.column - 1, piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "と", "金" -> {
                // 上三方向
                if (piece.key.row > 1) {
                    val row = piece.key.row - 1
                    (piece.key.column - 1..piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                setOf(piece.key.column - 1, piece.key.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = piece.key.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下
                if (piece.key.row < 5) {
                    val row = piece.key.row + 1
                    val column = piece.key.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "玉" -> {
                // 上三方向
                if (piece.key.row > 1) {
                    val row = piece.key.row - 1
                    (piece.key.column - 1..piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                setOf(piece.key.column - 1, piece.key.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = piece.key.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下三方向
                if (piece.key.row < 5) {
                    val row = piece.key.row + 1
                    (piece.key.column - 1..piece.key.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "香" -> {
                // 縦に真っ直ぐ
                run loop@{
                    val column = piece.key.column
                    (piece.key.row - 1 downTo 1).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
            }
            "角" -> {
                // 左上
                run loop@{
                    var column = piece.key.column + 1
                    var row = piece.key.row - 1
                    while (column <= 5 && row >= 1) {
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                        column++
                        row--
                    }
                }
                // 左下
                run loop@{
                    var column = piece.key.column + 1
                    var row = piece.key.row + 1
                    while (column <= 5 && row <= 5) {
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                        column++
                        row++
                    }
                }
                // 右上
                run loop@{
                    var column = piece.key.column - 1
                    var row = piece.key.row - 1
                    while (column >= 1 && row >= 1) {
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                        column--
                        row--
                    }
                }
                // 右下
                run loop@{
                    var column = piece.key.column - 1
                    var row = piece.key.row + 1
                    while (column >= 1 && row <= 5) {
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                        column--
                        row++
                    }
                }
            }
            "飛" -> {
                // 上
                run loop@{
                    val column = piece.key.column
                    (piece.key.row - 1 downTo 1).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 下
                run loop@{
                    val column = piece.key.column
                    (piece.key.row + 1..5).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 左
                run loop@{
                    val row = piece.key.row
                    (piece.key.column + 1..5).forEach { column ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 右
                run loop@{
                    val row = piece.key.row
                    (piece.key.column - 1 downTo 1).forEach { column ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
            }
        }
        return move
    }

    /**
     * 自駒にぶつかったら終了
     * @param player 自分駒情報
     * @param column 筋
     * @param row 段
     * @param mirror 反転
     * @param move 移動可能範囲
     * @return ぶつかったらtrue
     */
    private fun myPiece(
        player: Player,
        column: Int,
        row: Int,
        mirror: Boolean,
        move: MutableSet<Pos>
    ): Boolean {
        if (player.pieces.containsKey(Pos(column, row))) {
            return true
        }
        val m = if (mirror) getMirrorPos(Pos(column, row)) else Pos(column, row)
        move.add(m)
        return false
    }

    /**
     * 相手駒にぶつかったら、その駒の位置をリストに含めて終了
     * @param player 相手駒情報
     * @param column 筋
     * @param row 段
     * @param mirror 反転
     * @param move 移動可能範囲
     * @return ぶつかったらtrue
     */
    private fun enemyPiece(
        player: Player,
        column: Int,
        row: Int,
        mirror: Boolean,
        move: MutableSet<Pos>
    ): Boolean {
        if (player.pieces.containsKey(getMirrorPos(Pos(column, row)))) {
            val m = if (mirror) getMirrorPos(Pos(column, row)) else Pos(column, row)
            move.add(m)
            return true
        }
        return false
    }
}
