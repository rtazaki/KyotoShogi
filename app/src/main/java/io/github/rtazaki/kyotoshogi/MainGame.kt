package io.github.rtazaki.kyotoshogi

/**
 * MainGameクラス(オブジェクト)
 * 京都将棋のゲーム要素は、このクラスに集約する。
 */
object MainGame {
    data class Player(
        var pieces: MutableList<Piece> = mutableListOf(
            Piece("と", Pos(5, 5)),
            Piece("銀", Pos(4, 5)),
            Piece("玉", Pos(3, 5)),
            Piece("金", Pos(2, 5)),
            Piece("歩", Pos(1, 5)),
        ),
        var move: MutableList<Pos> = mutableListOf(),
    )

    data class Piece(var name: CharSequence, var pos: Pos)
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
     * @param player プレイヤー情報
     */
    fun clearMoveSelect(player: Map<Boolean, Player>) {
        player.values.forEach {
            it.move.clear()
        }
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
     * @param player 自分駒情報
     * @param mirror 反転
     */
    fun changePiece(select: Pos, m: Pos, player: Player, mirror: Boolean) {
        run loop@{
            player.pieces.forEach { p ->
                if (mirror) {
                    if (p.pos == getMirrorPos(select)) {
                        p.pos = getMirrorPos(m)
                        p.name = invertPiece(p.name)
                        return@loop
                    }
                } else {
                    if (p.pos == select) {
                        p.pos = m
                        p.name = invertPiece(p.name)
                        return@loop
                    }
                }
            }
        }
    }

    /**
     * 持ち駒取得処理
     * @param m 選択位置
     * @param player 相手駒情報
     * @param mirror 反転
     * @return 取った駒の名前
     */
    fun changeEnemyPiece(m: Pos, player: Player, mirror: Boolean): CharSequence {
        var ret: CharSequence = ""
        run loop@{
            player.pieces.forEachIndexed { i, p ->
                if (mirror) {
                    if (p.pos == getMirrorPos(m)) {
                        ret = convertHandsName(p.name)
                        player.pieces.removeAt(i)
                        return@loop
                    }
                } else {
                    if (p.pos == m) {
                        ret = convertHandsName(p.name)
                        player.pieces.removeAt(i)
                        return@loop
                    }
                }
            }
        }
        return ret
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
     * @param p 選択された駒情報
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param mirror 反転
     * @return 移動可能範囲
     */
    fun getMovePos(
        p: Piece,
        p1: Player,
        p2: Player,
        mirror: Boolean
    ): MutableList<Pos> {
        val move = mutableListOf<Pos>()
        when (p.name) {
            "歩" -> {
                // 縦に一歩
                if (p.pos.row > 1) {
                    val row = p.pos.row - 1
                    val column = p.pos.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "桂" -> {
                // 2段飛び斜め
                if (p.pos.row > 2) {
                    val row = p.pos.row - 2
                    listOf(p.pos.column - 1, p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "銀" -> {
                // 上三方向
                if (p.pos.row > 1) {
                    val row = p.pos.row - 1
                    (p.pos.column - 1..p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 斜め下
                if (p.pos.row < 5) {
                    val row = p.pos.row + 1
                    listOf(p.pos.column - 1, p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "と", "金" -> {
                // 上三方向
                if (p.pos.row > 1) {
                    val row = p.pos.row - 1
                    (p.pos.column - 1..p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                listOf(p.pos.column - 1, p.pos.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = p.pos.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下
                if (p.pos.row < 5) {
                    val row = p.pos.row + 1
                    val column = p.pos.column
                    // 自駒にぶつかったら終了
                    myPiece(p1, column, row, mirror, move)
                }
            }
            "玉" -> {
                // 上三方向
                if (p.pos.row > 1) {
                    val row = p.pos.row - 1
                    (p.pos.column - 1..p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
                // 左右
                listOf(p.pos.column - 1, p.pos.column + 1).filter { it in 1..5 }
                    .forEach { column ->
                        val row = p.pos.row
                        // 自駒にぶつかったら終了
                        myPiece(p1, column, row, mirror, move)
                    }
                // 下三方向
                if (p.pos.row < 5) {
                    val row = p.pos.row + 1
                    (p.pos.column - 1..p.pos.column + 1).filter { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら終了
                            myPiece(p1, column, row, mirror, move)
                        }
                }
            }
            "香" -> {
                // 縦に真っ直ぐ
                run loop@{
                    val column = p.pos.column
                    (p.pos.row - 1 downTo 1).forEach { row ->
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
                    var column = p.pos.column + 1
                    var row = p.pos.row - 1
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
                    var column = p.pos.column + 1
                    var row = p.pos.row + 1
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
                    var column = p.pos.column - 1
                    var row = p.pos.row - 1
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
                    var column = p.pos.column - 1
                    var row = p.pos.row + 1
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
                    val column = p.pos.column
                    (p.pos.row - 1 downTo 1).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 下
                run loop@{
                    val column = p.pos.column
                    (p.pos.row + 1..5).forEach { row ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 左
                run loop@{
                    val row = p.pos.row
                    (p.pos.column + 1..5).forEach { column ->
                        // 自駒にぶつかったら終了
                        if (myPiece(p1, column, row, mirror, move)) return@loop
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了
                        if (enemyPiece(p2, column, row, mirror, move)) return@loop
                    }
                }
                // 右
                run loop@{
                    val row = p.pos.row
                    (p.pos.column - 1 downTo 1).forEach { column ->
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
            player.pieces.forEach {
                if (it.pos == Pos(column, row)) {
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
            player.pieces.forEach {
                val mirrorPos = getMirrorPos(it.pos)
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