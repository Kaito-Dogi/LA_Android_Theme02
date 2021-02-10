package app.doggy.memorygame

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        //残り手数の定数。
        private const val HAND_LIMIT = 12
    }

    //何枚カードをめくったか数える変数。
    var turnUpCount = 0
    //揃えたペア数を数える変数。
    var pairCount = 0
    //ゲーム中かどうかを管理する変数。
    var isPlaying = 0
    //残り手数のセット。
    var handLimit = HAND_LIMIT

    //めくったカードの場所を記録する配列。
    val memoryPosition: Array<Int> = arrayOf(-1, -1)
    //めくったカードの番号を記録する配列。
    val memoryNum: Array<Int> = arrayOf(-1, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初期手数を表示する。
        textView.text = "残り${handLimit}手"

        //カードの番号の配列。
        val cardNums: Array<Int> = arrayOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8)

        //カードの画像の配列。
        val cardImages: Array<Int> = arrayOf(
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
        )

        //imageViewの配列。
        val imageViews: Array<ImageView> = arrayOf(
            findViewById(R.id.imageView1),
            imageView2,
            imageView3,
            imageView4,
            imageView5,
            imageView6,
            imageView7,
            imageView8,
            imageView9,
            imageView10,
            imageView11,
            imageView12,
            imageView13,
            imageView14,
            imageView15,
            imageView16
        )

        //MediaPlayerの配列。
        val mediaPlayers: Array<MediaPlayer> = arrayOf(
            MediaPlayer.create(applicationContext, R.raw.correct),
            MediaPlayer.create(applicationContext, R.raw.incorrect)
        )

        button.setOnClickListener {

            when(isPlaying) {
                0 -> {

                    for (i in imageViews.indices) {
                        //カードを裏返す。
                        imageViews[i].setImageResource(R.drawable.back)
                        //カードを押せるようにする。
                        imageViews[i].isEnabled = true
                    }

                    //初期手数を表示する。
                    textView.text = "残り${handLimit}手"

                    //カードの番号をシャッフルする。
                    cardNums.shuffle()

                    //裏返すボタンを押せなくする。
                    changeButtonStatus("裏返す", true, "#585858", button)

                    //プレイ中。
                    isPlaying = 1
                }

                1 -> when (turnUpCount) {
                    2 -> {
                        //カードを押せるようにする。
                        imageViews[memoryPosition[0]].isEnabled = true
                        imageViews[memoryPosition[1]].isEnabled = true

                        //カードを裏返す。
                        imageViews[memoryPosition[0]].setImageResource(R.drawable.back)
                        imageViews[memoryPosition[1]].setImageResource(R.drawable.back)

                        //めくったカードの枚数をリセットする。
                        turnUpCount = 0

                        //「裏返す」ボタンを押せなくする。
                        changeButtonStatus("裏返す", false, "#585858", button)
                    }
                }
            }
        }

        //カードのクリックリスナ。
        for (i in imageViews.indices) {

            imageViews[i].setOnClickListener {

                when(isPlaying) {
                    1 -> when (turnUpCount) {
                        0 -> {

                            //カードをめくる。
                            when (cardNums[i]) {
                                1 -> imageViews[i].setImageResource(cardImages[0])
                                2 -> imageViews[i].setImageResource(cardImages[1])
                                3 -> imageViews[i].setImageResource(cardImages[2])
                                4 -> imageViews[i].setImageResource(cardImages[3])
                                5 -> imageViews[i].setImageResource(cardImages[4])
                                6 -> imageViews[i].setImageResource(cardImages[5])
                                7 -> imageViews[i].setImageResource(cardImages[6])
                                8 -> imageViews[i].setImageResource(cardImages[7])
                            }

                            //カードを押せなくする。
                            imageViews[i].isEnabled = false

                            //めくったカードの場所を記録する。
                            memoryPosition[turnUpCount] = i

                            //めくったカードの番号を記録する。
                            memoryNum[turnUpCount] = cardNums[i]

                            //めくった枚数を更新。
                            turnUpCount += 1
                        }

                        1 -> {
                            //カードをめくる。
                            when (cardNums[i]) {
                                1 -> imageViews[i].setImageResource(cardImages[0])
                                2 -> imageViews[i].setImageResource(cardImages[1])
                                3 -> imageViews[i].setImageResource(cardImages[2])
                                4 -> imageViews[i].setImageResource(cardImages[3])
                                5 -> imageViews[i].setImageResource(cardImages[4])
                                6 -> imageViews[i].setImageResource(cardImages[5])
                                7 -> imageViews[i].setImageResource(cardImages[6])
                                8 -> imageViews[i].setImageResource(cardImages[7])
                            }

                            //手数を減らす。
                            handLimit -= 1

                            //残り手数を表示する。
                            textView.text = "残り${handLimit}手"

                            //カードを押せなくする。
                            imageViews[i].isEnabled = false

                            //めくったカードの場所を記録する。
                            memoryPosition[turnUpCount] = i

                            //めくったカードの番号を記録する。
                            memoryNum[turnUpCount] = cardNums[i]

                            //めくったカードが揃ったか判定。
                            if (memoryNum[0] == memoryNum[1]) {

                                //揃えたペア数を更新
                                pairCount += 1

                                if (pairCount == 8) {

                                    //「もう一度」ボタンを押せるようにする。
                                    changeButtonStatus("もう一度", true, "#3700B3", button)

                                    //クリア画面の表示。
                                    textView.text = "ゲームクリア！"

                                    //初期状態に戻す。
                                    allReset()
                                }

                                //めくったカードの枚数をリセットする。
                                turnUpCount = 0

                                //正解の音声を流す。
                                mediaPlayers[0].seekTo(0)
                                mediaPlayers[0].start()

                            } else if(handLimit == 0) {

                                //不正解の音声を流す。
                                mediaPlayers[1].seekTo(0)
                                mediaPlayers[1].start()

                                //「もう一度」ボタンを表示。
                                changeButtonStatus("もう一度", true, "#3700B3", button)

                                //ゲームオーバーと表示。
                                textView.text = "ゲームオーバー！"

                                //初期状態に戻す。
                                allReset()

                            } else {
                                //めくった枚数を更新。
                                turnUpCount += 1

                                //「裏返す」ボタンを押せるようにする。
                                changeButtonStatus("裏返す", true, "#3700B3", button)

                                //不正解の音声を流す。
                                mediaPlayers[1].seekTo(0)
                                mediaPlayers[1].start()
                            }
                        }
                    }
                }
            }
        }
    }

    //ゲーム開始時に戻す処理。
    fun allReset() {

        //カードをめくった枚数をリセット。
        turnUpCount = 0

        //揃えたペア数をリセット。
        pairCount = 0

        //プレイ終了。
        isPlaying = 0

        //残り手数をリセット。
        handLimit = HAND_LIMIT

        //めくったカードの情報をリセット。
        for (i in 0..1) {
            memoryPosition[i] = -1
            memoryNum[i] = -1
        }
    }

    //ボタンの状態を変更する処理。
    fun changeButtonStatus(text: String, isEnabled: Boolean, bgc: String, button: Button) {
        button.setBackgroundColor(Color.parseColor(bgc))
        button.text = text
        button.isEnabled = isEnabled
    }
}
