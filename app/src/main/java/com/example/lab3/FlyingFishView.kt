package com.example.lab3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import android.content.Intent



class FlyingFishView(context: Context) : View(context) {
        private val fish = arrayOfNulls<Bitmap>(2)
        private var fishX = 10
        private var fishY = 0
        private var fishSpeed = 0
        private var canvasWidth = 0
        private var canvasHeight = 0
        private var yellowX = 0
        private var yellowY = 0
        private var yellowSpeed = 16
        private val yellowPaint = Paint()
        private var greenX = 0
        private var greenY = 0
        private var greenSpeed = 20
        private var score = 0
        private var lifeCounterOfFish = 0
        private val greenPaint = Paint()
        private var redX = 0
        private var redY = 0
        private var redSpeed = 25
        private val redPaint = Paint()
        private var touch = false
        private val backgroundImage: Bitmap
        private val scorePaint = Paint()
        private val life = arrayOfNulls<Bitmap>(2)

        init {
            fish[0] = BitmapFactory.decodeResource(resources, R.drawable.fish1)
            fish[1] = BitmapFactory.decodeResource(resources, R.drawable.fish2)
            backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.background)
            yellowPaint.color = Color.YELLOW
            yellowPaint.isAntiAlias = false
            greenPaint.color = Color.GREEN
            greenPaint.isAntiAlias = false
            redPaint.color = Color.RED
            redPaint.isAntiAlias = false
            scorePaint.color = Color.WHITE
            scorePaint.textSize = 70f
            scorePaint.typeface = Typeface.DEFAULT_BOLD
            scorePaint.isAntiAlias = true
            life[0] = BitmapFactory.decodeResource(resources, R.drawable.hearts)
            life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_grey)
            fishY = 550
            score = 0
            lifeCounterOfFish = 3
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvasWidth = canvas.width
            canvasHeight = canvas.height
            canvas.drawBitmap(backgroundImage, 0f, 0f, null)
            val minFishY = fish[0]!!.height
            val maxFishY = canvasHeight - fish[0]!!.height * 3
            fishY += fishSpeed
            if (fishY < minFishY) {
                fishY = minFishY
            }
            if (fishY > maxFishY) {
                fishY = maxFishY
            }
            fishSpeed += 2
            if (touch) {
                canvas.drawBitmap(fish[1]!!, fishX.toFloat(), fishY.toFloat(), null)
                touch = false
            } else {
                canvas.drawBitmap(fish[0]!!, fishX.toFloat(), fishY.toFloat(), null)
            }
            yellowX -= yellowSpeed
            if (hitBallChecker(yellowX, yellowY)) {
                score += 10
                yellowX = -100
            }
            if (yellowX < 0) {
                yellowX = canvasWidth + 21
                yellowY = (Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY).toInt()
            }
            canvas.drawCircle(yellowX.toFloat(), yellowY.toFloat(), 25f, yellowPaint)

            redX -= redSpeed
            if (hitBallChecker(redX, redY)) {
                redX = -100
                lifeCounterOfFish--

                if (lifeCounterOfFish == 0) {
                    Toast.makeText(context, "Game over", Toast.LENGTH_SHORT).show()
                    val gameOverIntent = Intent(context, GameOverActivity::class.java)
                    gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    gameOverIntent.putExtra("score", score)
                    context.startActivity(gameOverIntent)
                }
            }
            if (redX < 0) {
                redX = canvasWidth + 21
                redY = (Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY).toInt()
            }
            canvas.drawCircle(redX.toFloat(), redY.toFloat(), 30f, redPaint)

            greenX -= greenSpeed
            if (hitBallChecker(greenX, greenY)) {
                score += 20
                greenX = -100
            }
            if (greenX < 0) {
                greenX = canvasWidth + 21
                greenY = (Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY).toInt()
            }
            canvas.drawCircle(greenX.toFloat(), greenY.toFloat(), 25f, greenPaint)

            for (i in 0 until 3) {
                val x = (580 + life[0]!!.width * 1.5 * i).toInt()
                val y = 30

                canvas.drawText("Score: $score", 20f, 60f, scorePaint)

                if (i < lifeCounterOfFish) {
                    canvas.drawBitmap(life[0]!!, x.toFloat(), y.toFloat(), null)
                } else {
                    canvas.drawBitmap(life[1]!!, x.toFloat(), y.toFloat(), null)
                }
            }
        }

        fun hitBallChecker(x: Int, y: Int): Boolean {
            return fishX < x && x < fishX + fish[0]!!.width && fishY < y && y < fishY + fish[0]!!.height
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                touch = true
                fishSpeed = -22
            }
            return true
        }
    }
