package com.example.mygreenapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.time.LocalDateTime

class NewsReaderWriter(ctx: Context): AppCompatActivity() {
    // context is required if FileOutputStream is used anywhere outside the MainActivity
    var context = ctx

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkFiles() {
        // Take file paths. The forward slash is necessary to make sure the file accesses the dir called "file"
        var fYear = context.filesDir.path + "/" + LAST_SYNC_YEAR
        var fDay = context.filesDir.path + "/" + LAST_SYNC_DAY
        // Treat them as files
        var fileYear = File(fYear)
        var fileDay = File(fDay)

        // Create year file if it does not exist
        if (!fileYear.exists()) {
            val time = "1999"
            var fos: FileOutputStream? = null
            try {
                fos = context.openFileOutput(LAST_SYNC_YEAR, MODE_PRIVATE)
                fos.write(time.toByteArray())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        // Create day file if it does not exist
        if (!fileDay.exists()) {
            val time = "1"
            var fos: FileOutputStream? = null
            try {
                fos = context.openFileOutput(LAST_SYNC_DAY, MODE_PRIVATE)
                fos.write(time.toByteArray())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    // Current time

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCurrentYear() {
        val time = LocalDateTime.now().year.toString()
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(LAST_SYNC_YEAR, MODE_PRIVATE)
            fos.write(time.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLastSyncYear(): Int {
        var fis: FileInputStream? = null
        val sb = StringBuilder()
        try {
            fis = context.openFileInput(LAST_SYNC_YEAR)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString().toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCurrentDay() {
        val time = LocalDateTime.now().dayOfYear.toString()
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(LAST_SYNC_DAY, MODE_PRIVATE)
            fos.write(time.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun loadLastSyncDay(): Int {
        var fis: FileInputStream? = null
        val sb = StringBuilder()
        try {
            fis = context.openFileInput(LAST_SYNC_DAY)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString().toInt()
    }

    // Image array

    fun writeToImageArray(imageList: ArrayList<String>) {
        // joinToString method converts the list into a string while customizing the string. This is really good
        val text = imageList.joinToString(
            separator = ",,"
        )
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(IMAGE_LIST, MODE_PRIVATE)
            fos.write(text.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun readFromImageArray() : Array<String> {
        var fis: FileInputStream? = null
        val sb = StringBuilder()
        try {
            fis = context.openFileInput(IMAGE_LIST)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        // Split the string and assign it to an array using the function toTypedArray()
        val str = sb.toString()
        val delim = ",,"

        val arr = str.split(delim).toTypedArray()

        return arr
    }

    // Title array

    fun writeToTitleArray(titleList: ArrayList<String>) {
        // joinToString method converts the list into a string while customizing the string. This is really good
        val text = titleList.joinToString(
            separator = ",,"
        )
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(TITLE_LIST, MODE_PRIVATE)
            fos.write(text.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun readFromTitleArray() : Array<String> {
        var fis: FileInputStream? = null
        val sb = StringBuilder()
        try {
            fis = context.openFileInput(TITLE_LIST)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        // Split the string and assign it to an array using the function toTypedArray()
        val str = sb.toString()
        val delim = ",,"

        val arr = str.split(delim).toTypedArray()

        return arr
    }

    // Description array

    fun writeToDescriptionArray(descriptionList: ArrayList<String>) {
        // joinToString method converts the list into a string while customizing the string. This is really good
        val text = descriptionList.joinToString(
            separator = ",,"
        )
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(DESCRIPTION_LIST, MODE_PRIVATE)
            fos.write(text.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun readFromDescriptionArray() : Array<String> {
        var fis: FileInputStream? = null
        val sb = StringBuilder()
        try {
            fis = context.openFileInput(DESCRIPTION_LIST)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        // Split the string and assign it to an array using the function toTypedArray()
        val str = sb.toString()
        val delim = ",,"

        val arr = str.split(delim).toTypedArray()

        return arr
    }

    companion object {
        private const val LAST_SYNC_YEAR = "last_sync_year.txt"
        private const val LAST_SYNC_DAY = "last_sync_day.txt"
        private const val IMAGE_LIST = "image_list.txt"
        private const val TITLE_LIST = "title_list.txt"
        private const val DESCRIPTION_LIST = "description_list.txt"
    }
}