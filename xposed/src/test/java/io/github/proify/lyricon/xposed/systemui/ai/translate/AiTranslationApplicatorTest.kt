package io.github.proify.lyricon.xposed.systemui.ai.translate

import io.github.proify.lyricon.lyric.model.RichLyricLine
import io.github.proify.lyricon.lyric.model.Song
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AiTranslationApplicatorTest {

    @Test
    fun `AI-applied translations carry a renderer marker`() {
        val song = Song(lyrics = listOf(
            RichLyricLine(begin = 0, end = 1000, text = "hello")
        ))

        val translated = AiTranslationApplicator.apply(
            song,
            listOf(TranslationItem(index = 0, translation = "你好"))
        )

        val line = translated.lyrics!!.single()
        assertEquals("你好", line.translation)
        assertTrue(line.metadata?.getBoolean("lyricon_ai_translation") == true)
    }

    @Test
    fun `AI translation replaces an existing provider translation`() {
        val song = Song(lyrics = listOf(
            RichLyricLine(
                begin = 0,
                end = 1000,
                text = "hello",
                translation = "QQ 音乐原有译文"
            )
        ))

        val translated = AiTranslationApplicator.apply(
            song,
            listOf(TranslationItem(index = 0, translation = "AI 新译文"))
        )

        val line = translated.lyrics!!.single()
        assertEquals("AI 新译文", line.translation)
        assertTrue(line.metadata?.getBoolean("lyricon_ai_translation") == true)
    }
}
