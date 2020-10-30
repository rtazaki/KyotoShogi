package io.github.rtazaki.kyotoshogi

/**
 * MainGameクラス(オブジェクト)
 * 京都将棋のゲーム要素は、このクラスに集約する。
 */
object MainGame {
    /**
     * Playerクラス
     * @property pieces 自駒(駒位置: 駒名)
     * @property hands 持ち駒(駒名: 個数)
     */
    data class Player(
        var pieces: MutableMap<Pos, CharSequence> = mutableMapOf(),
        var hands: MutableMap<CharSequence, Int> = mutableMapOf()
    )

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
     * 打ち駒を自駒に加える
     * @param name 打ち駒名
     * @param button 実際に選択した位置
     * @param player 自分駒情報
     * @param mirror 反転
     */
    fun addPutPiece(name: CharSequence, button: Pos, player: Player, mirror: Boolean) {
        val b = if (mirror) getMirrorPos(button) else button
        player.pieces[b] = name
        // 持ち駒の個数を整理
        val chn = convertHandsName(name)
        if (player.hands.getValue(chn) < 2) {
            player.hands.remove(chn)
        } else {
            player.hands[chn] = player.hands.getValue(chn).dec()
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
     * @param button 実際に選択した位置
     * @param p2 相手駒情報
     * @param p1 自分駒情報
     * @param mirror 反転
     */
    fun changeEnemyPiece(button: Pos, p2: Player, p1: Player, mirror: Boolean) {
        val b = if (mirror) getMirrorPos(button) else button
        if (p2.pieces.containsKey(b)) {
            // 持ち駒の個数を整理
            val chn = convertHandsName(p2.pieces.getValue(b))
            if (p1.hands.containsKey(chn)) {
                p1.hands[chn] = p1.hands.getValue(chn).inc()
            } else {
                p1.hands[chn] = 1
            }
            p2.pieces.remove(b)
        }
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
     * 打ち駒の移動可能範囲を返す
     * 押された駒名と手番は移動制限をかけるために必要となった。
     * (打ち駒の場合は、双方の駒が存在しない位置を探す処理だったため。)
     * @param players 自駒と相手駒
     * @param putPiece 押された駒名(オプション)
     * @param turn 手番(オプション)
     * @return 打ち駒の移動可能範囲
     */
    fun getPutPiecePos(
        players: Map<Boolean, Player>,
        putPiece: CharSequence = "",
        turn: Boolean = false
    ): MutableSet<Pos> {
        val move = mutableSetOf<Pos>()
        (1..5).forEach { column ->
            (1..5).forEach { row ->
                val pos = Pos(column, row)
                if (!players.getValue(true).pieces.containsKey(pos)) {
                    if (!players.getValue(false).pieces.containsKey(getMirrorPos(pos))) {
                        move.add(pos)
                    }
                }
            }
        }
        val removePos = mutableSetOf<Pos>()
        val piece = mapOf(Pos(0, 0) to putPiece).entries.first()
        move.forEach {
            if (isKingCheck(
                    piece,
                    it,
                    players.getValue(turn),
                    players.getValue(!turn),
                    turn
                )
            ) {
                removePos.add(it)
            }
        }
        move.removeAll(removePos)
        return move
    }

    /**
     * 詰み判定
     * @param players 自駒と相手駒
     * @param turn 手番
     * @return 詰みならtrue
     */
    fun isCheckMate(players: Map<Boolean, Player>, turn: Boolean): Boolean {
        val move = mutableSetOf<Pos>()
        // 自駒の全移動可能範囲を取得する。
        players.getValue(turn).pieces.forEach {
            move.addAll(getMovePos(it, players.getValue(turn), players.getValue(!turn), turn))
        }
        // 持ち駒の全移動可能範囲を取得する。
        if (players.getValue(turn).hands.count() > 0) {
            move.addAll(getPutPiecePos(players, "", turn))
        }
        return move.isEmpty()
    }

    /**
     * 移動可能範囲を返す
     * @param piece 選択された駒情報
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param mirror 反転
     * @param isRecursive 再帰フラグ(オプション)
     * @return 移動可能範囲
     */
    fun getMovePos(
        piece: Map.Entry<Pos, CharSequence>,
        p1: Player,
        p2: Player,
        mirror: Boolean,
        isRecursive: Boolean = false
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
        if (!isRecursive) {
            val removePos = mutableSetOf<Pos>()
            move.forEach {
                if (isKingCheck(piece, it, p1, p2, !mirror)) {
                    removePos.add(it)
                }
            }
            move.removeAll(removePos)
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
        val p = Pos(column, row)
        if (player.pieces.containsKey(p)) {
            return true
        }
        val m = if (mirror) getMirrorPos(p) else p
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
        val mp = getMirrorPos(Pos(column, row))
        if (player.pieces.containsKey(mp)) {
            val m = if (mirror) mp else Pos(column, row)
            move.add(m)
            return true
        }
        return false
    }

    /**
     * 1手進めて玉が王手されていないか確認する
     * @param piece 選択された駒情報
     * @param m 移動可能範囲(1つ)
     * @param p1 自分駒情報
     * @param p2 相手駒情報
     * @param turn 手番
     * @return ぶつかったらtrue
     */
    private fun isKingCheck(
        piece: Map.Entry<Pos, CharSequence>,
        m: Pos,
        p1: Player,
        p2: Player,
        turn: Boolean
    ): Boolean {
        // p1Copy: 壊さないようにコピーを取る。
        val p1Copy = Player(p1.pieces.toMutableMap())
        // 王手がかかる手順があるか確認するために、1手進める。
        val mmp1 = if (turn) m else getMirrorPos(m)
        p1Copy.pieces[mmp1] = piece.value
        if (p1Copy.pieces.containsKey(piece.key)) {
            p1Copy.pieces.remove(piece.key)
        }
        // 1手進めた後で自玉位置を特定する。(玉だけではなく、どの駒が移動しているか分からないため。)
        val king = p1Copy.pieces.filterValues { it.contains("玉") }
        // 玉は通常1つしかいないが、テストケースには玉が含まれないパターンがあるため、countを見る。
        if (king.count() != 1) {
            return false
        }
        val mk = if (turn) king.entries.first().key else getMirrorPos(king.entries.first().key)

        // p2Copy: 壊さないようにコピーを取る。
        val p2Copy = Player(p2.pieces.toMutableMap())
        // 移動した先に相手の駒がいたら、相手の駒を取り除く。
        val mmp2 = if (!turn) m else getMirrorPos(m)
        if (p2Copy.pieces.containsKey(mmp2)) {
            p2Copy.pieces.remove(mmp2)
        }

        // 相手の駒の移動可能範囲を総当たりする
        p2Copy.pieces.forEach {
            val move = getMovePos(
                it,
                p2Copy,
                p1Copy,
                turn,
                isRecursive = true
            )
            // 移動可能範囲内に、玉がいたらtrueを返す。
            if (move.contains(mk)) {
                return true
            }
        }
        return false
    }
}
