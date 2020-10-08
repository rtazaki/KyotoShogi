package io.github.rtazaki.kyotoshogi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.rtazaki.kyotoshogi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /**
         * 画面配置的には、左上→右下だが、将棋は右上→左下なので
         * まずは泥臭く実装する。
         * @param column 筋
         * @param row 段
         */
        val posMap = mapOf(
            Piece.Pos(1, 1) to binding.board11,
            Piece.Pos(1, 2) to binding.board12,
            Piece.Pos(1, 3) to binding.board13,
            Piece.Pos(1, 4) to binding.board14,
            Piece.Pos(1, 5) to binding.board15,
            Piece.Pos(2, 1) to binding.board21,
            Piece.Pos(2, 2) to binding.board22,
            Piece.Pos(2, 3) to binding.board23,
            Piece.Pos(2, 4) to binding.board24,
            Piece.Pos(2, 5) to binding.board25,
            Piece.Pos(3, 1) to binding.board31,
            Piece.Pos(3, 2) to binding.board32,
            Piece.Pos(3, 3) to binding.board33,
            Piece.Pos(3, 4) to binding.board34,
            Piece.Pos(3, 5) to binding.board35,
            Piece.Pos(4, 1) to binding.board41,
            Piece.Pos(4, 2) to binding.board42,
            Piece.Pos(4, 3) to binding.board43,
            Piece.Pos(4, 4) to binding.board44,
            Piece.Pos(4, 5) to binding.board45,
            Piece.Pos(5, 1) to binding.board51,
            Piece.Pos(5, 2) to binding.board52,
            Piece.Pos(5, 3) to binding.board53,
            Piece.Pos(5, 4) to binding.board54,
            Piece.Pos(5, 5) to binding.board55,
        )
        // 初期配置
        val player1 = Player()
        for (p in player1.getOnBoards()) {
            posMap.getValue(p.boardPos).text = p.onBoards
        }
        val player2 = Player()
        for (p in player2.getOnBoards()) {
            val mirrorPos = Piece.getMirrorPos(p.boardPos)
            posMap.getValue(mirrorPos).text = p.onBoards
            posMap.getValue(mirrorPos).rotation = 180.0F
        }
        // 駒操作
        binding.board51.setOnClickListener {
        }
    }
}