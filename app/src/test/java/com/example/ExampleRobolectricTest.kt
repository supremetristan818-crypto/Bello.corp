package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Study Battle", appName)
  }

  @Test
  fun `question bank loads curriculum questions for all subjects`() {
    val subjects = com.example.data.repository.QuestionBank.SUBJECTS
    assertEquals(7, subjects.size)
    for (subject in subjects) {
      val questions = com.example.data.repository.QuestionBank.getQuestions(subject = subject, limit = 5)
      assertEquals(5, questions.size)
    }
  }
}
