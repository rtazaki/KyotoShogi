package io.github.rtazaki.kyotoshogi

class Player {
    data class PiecePos(var onBoards: String, var boardPos: Pos)
    data class Pos(val column: Int, val row: Int)

    private var piecePos = mutableListOf(
        PiecePos("と", Pos(5, 5)),
        PiecePos("銀", Pos(4, 5)),
        PiecePos("玉", Pos(3, 5)),
        PiecePos("金", Pos(2, 5)),
        PiecePos("歩", Pos(1, 5)),
    )
    private var hands = mutableListOf<String>()
    fun getOnBoards(): MutableList<PiecePos> {
        return piecePos
    }

    fun getMirrorPos(pos: Pos): Pos {
        return Pos(6 - pos.column, 6 - pos.row)
    }
}
