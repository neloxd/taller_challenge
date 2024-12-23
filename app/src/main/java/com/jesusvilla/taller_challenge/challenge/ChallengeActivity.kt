package com.jesusvilla.taller_challenge.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesus.villa.taller.challenge.R
import com.jesus.villa.taller.challenge.databinding.ActivityChallengeBinding
import com.jesusvilla.taller_challenge.challenge.ui.main.ChallengeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeActivity : AppCompatActivity() {

    private lateinit var challengeActivityBinding: ActivityChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengeActivityBinding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(challengeActivityBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ChallengeFragment.newInstance())
                .commitNow()
        }
    }
}