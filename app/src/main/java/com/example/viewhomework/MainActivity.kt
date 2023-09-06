package com.example.viewhomework

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var editTextImageUrl: EditText
    private lateinit var imageView: ImageView
    private lateinit var buttonLoadImage: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        imageView = findViewById(R.id.imageView)
        buttonLoadImage = findViewById(R.id.buttonLoadImage)

        buttonLoadImage.setOnClickListener {
            val imageUrl = editTextImageUrl.text.toString()
            if (imageUrl.isNotEmpty()) {
                loadImage(imageUrl)
            } else {
                Toast.makeText(this@MainActivity, "Введите URL изображения", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun loadImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    imageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(this@MainActivity, "Ошибка при загрузке изображения", Toast.LENGTH_SHORT).show()
                }
            })
    }
}