package io.github.rtazaki.kyotoshogi

object FieldPiece {
    data class Pos(val column: Int, val row: Int)

    fun getMovePos(
        p: Player.PiecePos,
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
                    run loop@{
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
                    }
                }
            }
            "桂" -> {
                // 2段飛び斜め
                if (p.boardPos.row > 2) {
                    val row = p.boardPos.row - 2
                    listOf(p.boardPos.column - 1, p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            // 自駒にぶつかったら無効
                            run loop@{
                                p1.piecePos.forEach {
                                    if (it.boardPos == Pos(column, row)) {
                                        return@loop
                                    }
                                }
                            }
                            if (mirror) {
                                move.add(getMirrorPos(Pos(column, row)))
                            } else {
                                move.add(Pos(column, row))
                            }
                        }
                }
            }
            "銀" -> {
                val check = mutableListOf<Pos>()
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            check.add(Pos(column, row))
                        }
                }
                // 斜め下
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    listOf(p.boardPos.column - 1, p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            check.add(Pos(column, row))
                        }
                }
                check.forEach { checkPos ->
                    // 自駒にぶつかったら無効
                    run loop@{
                        p1.piecePos.forEach {
                            if (it.boardPos == checkPos) {
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(checkPos))
                        } else {
                            move.add(checkPos)
                        }
                    }
                }
            }
            "と", "金" -> {
                val check = mutableListOf<Pos>()
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            check.add(Pos(column, row))
                        }
                }
                // 左右
                listOf(p.boardPos.column - 1, p.boardPos.column + 1).takeWhile { it in 1..5 }
                    .forEach { column ->
                        val row = p.boardPos.row
                        check.add(Pos(column, row))
                    }
                // 下
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    val column = p.boardPos.column
                    check.add(Pos(column, row))
                }
                check.forEach { checkPos ->
                    // 自駒にぶつかったら無効
                    run loop@{
                        p1.piecePos.forEach {
                            if (it.boardPos == checkPos) {
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(checkPos))
                        } else {
                            move.add(checkPos)
                        }
                    }
                }
            }
            "玉" -> {
                val check = mutableListOf<Pos>()
                // 上三方向
                if (p.boardPos.row > 1) {
                    val row = p.boardPos.row - 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            check.add(Pos(column, row))
                        }
                }
                // 左右
                listOf(p.boardPos.column - 1, p.boardPos.column + 1).takeWhile { it in 1..5 }
                    .forEach { column ->
                        val row = p.boardPos.row
                        check.add(Pos(column, row))
                    }
                // 下三方向
                if (p.boardPos.row < 5) {
                    val row = p.boardPos.row + 1
                    (p.boardPos.column - 1..p.boardPos.column + 1).takeWhile { it in 1..5 }
                        .forEach { column ->
                            check.add(Pos(column, row))
                        }
                }
                check.forEach { checkPos ->
                    // 自駒にぶつかったら無効
                    run loop@{
                        p1.piecePos.forEach {
                            if (it.boardPos == checkPos) {
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(checkPos))
                        } else {
                            move.add(checkPos)
                        }
                    }
                }
            }
            "香" -> {
                // 縦に真っ直ぐ
                run loop@{
                    val column = p.boardPos.column
                    (p.boardPos.row - 1 downTo 1).forEach { row ->
                        // 自駒にぶつかったら終了
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
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
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
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
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
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
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
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
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
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
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
                    }
                }
                // 下
                run loop@{
                    val column = p.boardPos.column
                    (p.boardPos.row + 1..5).forEach { row ->
                        // 自駒にぶつかったら終了
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
                    }
                }
                // 左
                run loop@{
                    val row = p.boardPos.row
                    (p.boardPos.column + 1..5).forEach { column ->
                        // 自駒にぶつかったら終了
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
                    }
                }
                // 右
                run loop@{
                    val row = p.boardPos.row
                    (p.boardPos.column - 1 downTo 1).forEach { column ->
                        // 自駒にぶつかったら終了
                        p1.piecePos.forEach {
                            if (it.boardPos == Pos(column, row)) {
                                return@loop
                            }
                        }
                        // 相手駒にぶつかったら、その駒の位置をリストに含めて終了。
                        p2.piecePos.forEach {
                            val mirrorPos = getMirrorPos(it.boardPos)
                            if (mirrorPos == Pos(column, row)) {
                                if (mirror) {
                                    move.add(getMirrorPos(Pos(column, row)))
                                } else {
                                    move.add(Pos(column, row))
                                }
                                return@loop
                            }
                        }
                        if (mirror) {
                            move.add(getMirrorPos(Pos(column, row)))
                        } else {
                            move.add(Pos(column, row))
                        }
                    }
                }
            }
        }
        return move
    }

    fun getMirrorPos(pos: Pos): Pos {
        return Pos(6 - pos.column, 6 - pos.row)
    }
}