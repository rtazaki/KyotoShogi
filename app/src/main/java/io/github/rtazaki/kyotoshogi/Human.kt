package io.github.rtazaki.kyotoshogi

class Player {
    data class PiecePos(
        var onBoards: String,
        var boardPos: Piece.Pos
    )

    private var piecePos = mutableListOf(
        PiecePos("と", Piece.Pos(5, 5)),
        PiecePos("銀", Piece.Pos(4, 5)),
        PiecePos("玉", Piece.Pos(3, 5)),
        PiecePos("金", Piece.Pos(2, 5)),
        PiecePos("歩", Piece.Pos(1, 5)),
    )
    private var hands = mutableListOf<String>()
    fun getOnBoards(): MutableList<PiecePos> {
        return piecePos
    }
}
