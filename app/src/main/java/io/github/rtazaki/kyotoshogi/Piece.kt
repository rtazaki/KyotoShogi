package io.github.rtazaki.kyotoshogi

object Piece {
    data class Pos(val column: Int, val row: Int)
    fun getMirrorPos(pos: Pos): Pos {
        return Pos(6 - pos.column, 6 - pos.row)
    }
//
//    val pieceMap = mapOf(
//        "" to 0,
//        "玉" to 1,
//        2 to mapOf(true to "香", false to "と"),
//        3 to mapOf(true to "銀", false to "角"),
//        4 to mapOf(true to "金", false to "桂"),
//        5 to mapOf(true to "飛", false to "歩"),
//    )
//
}