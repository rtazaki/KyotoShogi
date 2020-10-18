# KyotoShogi
京都将棋のAndroidアプリ

## 現在までに達成できたこと
- 先手番と後手番で押せるボタンが区別できている。
- 各駒の性能を実装し、移動可能範囲を設定できる。
- 移動可能範囲でボタン背景色を変更できる。
- 移動可能範囲内がクリックされたら手番が変わる
- 移動可能範囲外がクリックされたら移動可能範囲をクリアする。(ボタン背景色をクリアする。)
- 移動可能範囲内クリックによって駒を移動し、駒機能をひっくり返す。
(京都将棋ルールは、動かすたびに必ず駒をひっくり返す。)
- 移動可能範囲に相手の駒があれば、持ち駒に加える。

## これから実装すること
- 持ち駒を打てるようにする。

## リファクタリング予定
- listOfは、たぶん全部setOfに置き換え可能。(性質的にユニークなので。)
- https://www.codeflow.site/ja/article/kotlin-sort
↑ 範囲で判定するときは、ソートを使うことを考えたが、
containtsAllを使えばいいので、どうせなら順番を気にしないSetのほうが一番しっくりくるし
コードもスッキリするだろうと予想。
