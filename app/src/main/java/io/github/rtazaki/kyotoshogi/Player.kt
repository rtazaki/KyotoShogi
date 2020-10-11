package io.github.rtazaki.kyotoshogi

class Player {
    data class PiecePos(val onBoards: String, val boardPos: FieldPiece.Pos)

    var piecePos = mutableListOf(
        PiecePos("と", FieldPiece.Pos(5, 5)),
        PiecePos("銀", FieldPiece.Pos(4, 5)),
        PiecePos("玉", FieldPiece.Pos(3, 5)),
        PiecePos("金", FieldPiece.Pos(2, 5)),
        PiecePos("歩", FieldPiece.Pos(1, 5)),
    )
    var movePos = mutableListOf<FieldPiece.Pos>()
    var selectPos = FieldPiece.Pos(0, 0)
    var hands = mutableListOf<String>()
}
