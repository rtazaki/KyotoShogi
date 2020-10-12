package io.github.rtazaki.kyotoshogi

/**
 * MainGameクラス
 * 京都将棋のゲーム要素は、このクラスに集約する。
 */
class MainGame {
    data class Player(
        var piecePos: MutableList<PiecePos> = mutableListOf(
            PiecePos("と", Pos(5, 5)),
            PiecePos("銀", Pos(4, 5)),
            PiecePos("玉", Pos(3, 5)),
            PiecePos("金", Pos(2, 5)),
            PiecePos("歩", Pos(1, 5)),
        ),
        var movePos: MutableList<Pos> = mutableListOf(),
        var selectPos: Pos = Pos(0, 0),
        var latest: Pos = Pos(0, 0),
    )

    data class PiecePos(var onBoards: CharSequence, var boardPos: Pos)
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
     * 駒移動
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     */
    fun clearMoveSelect(p1: Player, p2: Player) {
        p1.movePos.clear()
        p1.selectPos = Pos(0, 0)
        p2.movePos.clear()
        p2.selectPos = Pos(0, 0)
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
     * @param m 選択位置
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param mirror 反転
     */
    fun changePiece(m: Pos, p1: Player, p2: Player, mirror: Boolean) {
        run loop@{
            p1.piecePos.forEach { p ->
                if (mirror) {
                    if (p.boardPos == getMirrorPos(p1.selectPos)) {
                        p.onBoards = invertPiece(p.onBoards)
                        p.boardPos = getMirrorPos(m)
                        p1.latest = m
                        return@loop
                    }
                } else {
                    if (p.boardPos == p1.selectPos) {
                        p.onBoards = invertPiece(p.onBoards)
                        p.boardPos = m
                        p1.latest = p.boardPos
                        return@loop
                    }
                }
            }
        }
    }

    /**
     * 移動可能範囲を返す
     * @param p 選択された駒情報
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param mirror 反転
     * @return 移動可能範囲
     */
    fun getMovePos(
        p: PiecePos,
        p1: Player,
        p2: Player,
        mirror: Boolean
    ): MutableList<Pos> {
        val move = mutableListOf<Pos>()
        when (p.onBoards) {
            "歩" -> {
                // 縦に一歩
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    val column = p.boardPos.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "桂" -> {
                // 2段飛び斜め
                if (p.boardPos.row > 2) {
                    val row = p.boardPos.row - 2
                    listOf(p.boardPos.column - 1, p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "銀" -> {
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 斜め下
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    listOf(p.boardPos.column - 1, p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "と", "金" -> {
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                listOf(p.boardPos.column - 1, p.boardPos.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = p.boardPos.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    val column = p.boardPos.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "玉" -> {
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                listOf(p.boardPos.column - 1, p.boardPos.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = p.boardPos.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下三方向
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "香" -> {
                // 縦に真っ直ぐ
                run loop@{
                    val column = p.boardPos.column
                    (p.boardPos.row - 1 downTo 1).forEach { row ->
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
                    var column = p.boardPos.column + 1
                    var row = p.boardPos.row - 1
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
                    var column = p.boardPos.column + 1
                    var row = p.boardPos.row + 1
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
                    var column = p.boardPos.column - 1
                    var row = p.boardPos.row - 1
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
                    var column = p.boardPos.column - 1
                    var row = p.boardPos.row + 1
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
                    val column = p.boardPos.column
                    (p.boardPos.row - 1 downTo 1).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 下
                run loop@{
                    val column = p.boardPos.column
                    (p.boardPos.row + 1..5).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 左
                run loop@{
                    val row = p.boardPos.row
                    (p.boardPos.column + 1..5).forEach { column ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 右
                run loop@{
                    val row = p.boardPos.row
                    (p.boardPos.column - 1 downTo 1).forEach { column ->
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
        move: MutableList<Pos>
    ): Boolean {
        var ret = false
        run loop@{
            player.piecePos.forEach {
                if (it.boardPos == Pos(column, row)) {
                    ret = true
                    return@loop
                }
            }
            if (mirror) {
                move.add(getMirrorPos(Pos(column, row)))
            } else {
                move.add(Pos(column, row))
            }
        }
        return ret
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
        move: MutableList<Pos>
    ): Boolean {
        var ret = false
        run loop@{
            player.piecePos.forEach {
                val mirrorPos = getMirrorPos(it.boardPos)
                if (mirrorPos == Pos(column, row)) {
                    if (mirror) {
                        move.add(getMirrorPos(Pos(column, row)))
                    } else {
                        move.add(Pos(column, row))
                    }
                    ret = true
                    return@loop
                }
            }
        }
        return ret
    }
}