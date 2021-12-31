package cn.lxyhome.jetpackcamerax.activity

import android.annotation.SuppressLint
import android.graphics.*
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import cn.lxyhome.jetpackcamerax.BuildConfig
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.util.loge
import cn.lxyhome.jetpackcamerax.util.startActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_preview.*
import okio.ByteString.Companion.toByteString
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.*

@SuppressLint("RestrictedApi")
class PreviewActivity : AppCompatActivity() {

    private lateinit var imageCapture: ImageCapture
    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var preview: Preview

    private lateinit var cameraProvider: ProcessCameraProvider

    // Initialize our background executor
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private var TargetRotation:Int = 0
    val value = object :
        ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            // ImageToBitmap(image)
            val toBitmap = image.toBitmap()
            runOnUiThread {
                img_preview.setImageBitmap(toBitmap)
            }
            image.close()
            super.onCaptureSuccess(image)
        }

        private fun ImageToBitmap(image: ImageProxy) {
            val planes = image.planes

            val yBuffer = planes[0].buffer // Y
            //  val uBuffer = planes[1].buffer // U
            val vBuffer = planes[2].buffer // V

            val ySize = yBuffer.remaining()
            // val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()

            val nv21 = ByteArray(ySize /*+ uSize*/ + vSize)

            //U and V are swapped
            yBuffer.get(nv21, 0, ySize)
            vBuffer.get(nv21, ySize, vSize)
            // uBuffer.get(nv21, ySize + vSize, uSize)


            val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
            val imageBytes = out.toByteArray()

            val decodeByteArray =
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            val matrix = Matrix()
            matrix.postRotate(TargetRotation.toFloat())
            val bitmap = Bitmap.createBitmap(
                decodeByteArray,
                0,
                0,
                decodeByteArray.width,
                decodeByteArray.height,
                matrix,
                true
            )

            img_preview.setImageBitmap(bitmap)


        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        initCameraX()

        /*val future = ProcessCameraProvider.getInstance(this)

        future.addListener(Runnable{
            val cameraProvider = future.get()
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector,preview,imageAnalysis,imageCapture)
        }, ContextCompat.getMainExecutor(this))

       // tv_preview.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        //tv_preview.preferredImplementationMode = PreviewView.ImplementationMode.TEXTURE_VIEW
        preview.setSurfaceProvider(tv_preview.surfaceProvider)
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(),ImageAnalysis.Analyzer {
            Log.i("PreviewActivity",it.cropRect.toString() + it.format + it.height + it.image.toString()+ it.planes)
            TargetRotation = it.imageInfo?.rotationDegrees
            it.close()
        })*/



        btn_kacha.setOnClickListener {
           // imageCapture.takePicture(Executors.newSingleThreadExecutor(), value)

            val tempFile = //File(this@PreviewActivity.filesDir.toString()+File.separator+"temp")
                this.applicationContext.externalMediaDirs.firstOrNull()?.let {
                    File(it,"JetpackCameraX").apply { mkdirs() }
                } ?: return@setOnClickListener
            if (!tempFile.exists()) {
                tempFile.mkdirs()
            }

            val imgaFile = File(tempFile,SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA).format(System.currentTimeMillis())+".png")
            val options = ImageCapture.OutputFileOptions.Builder(imgaFile).setMetadata(
                ImageCapture.Metadata()).build()
            imageCapture.takePicture(options,cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {//TODOLXY: lixinyang  Not yet implemented
                    val uri = outputFileResults.savedUri ?: Uri.fromFile(imgaFile)
                    runOnUiThread {
                        Glide.with(img_preview.context)
                            .load(uri)
                            .into(img_preview)
                    }
                    //通知 扫描 相册
                    MediaScannerConnection.scanFile(
                        this@PreviewActivity,
                        arrayOf(uri.toFile().absolutePath),
                        arrayOf(MimeTypeMap.getSingleton().getMimeTypeFromExtension(uri.toFile().extension))
                    ){ path,uri->
                        loge("path:$path||uri$uri")
                    }

                }

                override fun onError(exception: ImageCaptureException) {//TODOLXY: lixinyang  Not yet implemented
                    loge(exception.message)
                }

            })
        }

        img_preview.setOnClickListener {
            startActivity<GalleryActivity> {  }
        }

       /* CameraX.bindToLifecycle(this as LifecycleOwner, preview*//*, imageAnalysis, imageCapture*//*)
        preview.setOnPreviewOutputUpdateListener {
            tv_preview.surfaceTexture = it.surfaceTexture
            // Compute the center of preview (TextureView)
            val centerX = tv_preview.width.toFloat() / 2
            val centerY = tv_preview.height.toFloat() / 2

            // Correct preview output to account for display rotation
            val rotationDegrees = when (tv_preview.display?.rotation?:0) {
                Surface.ROTATION_0 -> 0
                Surface.ROTATION_90 -> 90
                Surface.ROTATION_180 -> 180
                Surface.ROTATION_270 -> 270
                else -> return@setOnPreviewOutputUpdateListener
            }

            val matrix = Matrix()
            matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

            // Finally, apply transformations to TextureView
            tv_preview.setTransform(matrix)
        }*/

    }

    private fun initCameraX() {
        val instance = ProcessCameraProvider.getInstance(this)
        instance.addListener({
            cameraProvider = instance.get()
            bindCamaraCases()

        },ContextCompat.getMainExecutor(this))
    }


    private fun bindCamaraCases() {
        val windowManager = androidx.window.WindowManager(this)
        val bounds = windowManager.getCurrentWindowMetrics().bounds
        val rotation = tv_preview.display.rotation
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        preview = Preview.Builder()
            .setTargetAspectRatio(aspectRatio(bounds.width(),bounds.height()))
            .setTargetRotation(rotation)
            .build()
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetRotation(rotation)
            .build()
       imageAnalysis =  ImageAnalysis.Builder()
           .setTargetAspectRatio(aspectRatio(bounds.width(),bounds.height()))
            .setTargetRotation(rotation)
            .build().apply {
                setAnalyzer(cameraExecutor, object : ImageAnalysis.Analyzer {

                    override fun analyze(image: ImageProxy) {
                        //image.planes[0].buffer
                        image.close()
                    }

                })
           }

        cameraProvider.unbindAll()

        try {
            val camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
            preview.setSurfaceProvider(tv_preview.surfaceProvider)
        } catch (e: Exception) {
        }

    }


    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }


    private fun imageToByteBuffer(image: ImageProxy, outputBuffer: ByteArray, pixelCount: Int) {
        if (BuildConfig.DEBUG && image.format != ImageFormat.YUV_420_888) {
            error("Assertion failed")
        }

        val imageCrop = image.cropRect
        val imagePlanes = image.planes

        imagePlanes.forEachIndexed { planeIndex, plane ->
            // How many values are read in input for each output value written
            // Only the Y plane has a value for every pixel, U and V have half the resolution i.e.
            //
            // Y Plane            U Plane    V Plane
            // ===============    =======    =======
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            val outputStride: Int

            // The index in the output buffer the next value will be written at
            // For Y it's zero, for U and V we start at the end of Y and interleave them i.e.
            //
            // First chunk        Second chunk
            // ===============    ===============
            // Y Y Y Y Y Y Y Y    V U V U V U V U
            // Y Y Y Y Y Y Y Y    V U V U V U V U
            // Y Y Y Y Y Y Y Y    V U V U V U V U
            // Y Y Y Y Y Y Y Y    V U V U V U V U
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            var outputOffset: Int

            when (planeIndex) {
                0 -> {
                    outputStride = 1
                    outputOffset = 0
                }
                1 -> {
                    outputStride = 2
                    // For NV21 format, U is in odd-numbered indices
                    outputOffset = pixelCount + 1
                }
                2 -> {
                    outputStride = 2
                    // For NV21 format, V is in even-numbered indices
                    outputOffset = pixelCount
                }
                else -> {
                    // Image contains more than 3 planes, something strange is going on
                    return@forEachIndexed
                }
            }

            val planeBuffer = plane.buffer
            val rowStride = plane.rowStride
            val pixelStride = plane.pixelStride

            // We have to divide the width and height by two if it's not the Y plane
            val planeCrop = if (planeIndex == 0) {
                imageCrop
            } else {
                Rect(
                    imageCrop.left / 2,
                    imageCrop.top / 2,
                    imageCrop.right / 2,
                    imageCrop.bottom / 2
                )
            }

            val planeWidth = planeCrop.width()
            val planeHeight = planeCrop.height()

            // Intermediate buffer used to store the bytes of each row
            val rowBuffer = ByteArray(plane.rowStride)

            // Size of each row in bytes
            val rowLength = if (pixelStride == 1 && outputStride == 1) {
                planeWidth
            } else {
                // Take into account that the stride may include data from pixels other than this
                // particular plane and row, and that could be between pixels and not after every
                // pixel:
                //
                // |---- Pixel stride ----|                    Row ends here --> |
                // | Pixel 1 | Other Data | Pixel 2 | Other Data | ... | Pixel N |
                //
                // We need to get (N-1) * (pixel stride bytes) per row + 1 byte for the last pixel
                (planeWidth - 1) * pixelStride + 1
            }

            for (row in 0 until planeHeight) {
                // Move buffer position to the beginning of this row
                planeBuffer.position(
                    (row + planeCrop.top) * rowStride + planeCrop.left * pixelStride
                )

                if (pixelStride == 1 && outputStride == 1) {
                    // When there is a single stride value for pixel and output, we can just copy
                    // the entire row in a single step
                    planeBuffer.get(outputBuffer, outputOffset, rowLength)
                    outputOffset += rowLength
                } else {
                    // When either pixel or output have a stride > 1 we must copy pixel by pixel
                    planeBuffer.get(rowBuffer, 0, rowLength)
                    for (col in 0 until planeWidth) {
                        outputBuffer[outputOffset] = rowBuffer[col * pixelStride]
                        outputOffset += outputStride
                    }
                }
            }
        }
    }

    private fun yuv420888ToNv21(image: ImageProxy): ByteArray {
        val pixelCount = image.cropRect.width() * image.cropRect.height()
        val pixelSizeBits = ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888)
        val outputBuffer = ByteArray(pixelCount * pixelSizeBits / 8)
        imageToByteBuffer(image, outputBuffer, pixelCount)
        return outputBuffer
    }

    fun ImageProxy.toBitmap(): Bitmap? {
        val nv21 = yuv420888ToNv21(this)
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        return yuvImage.toBitmap()
    }

    private fun YuvImage.toBitmap(): Bitmap? {
        val out = ByteArrayOutputStream()
        if (!compressToJpeg(Rect(0, 0, width, height), 100, out))
            return null
        val imageBytes: ByteArray = out.toByteArray()
        val decodeByteArray = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val matrix = Matrix()
        matrix.postRotate(TargetRotation.toFloat())
        return Bitmap.createBitmap(
            decodeByteArray,
            0,
            0,
            decodeByteArray.width,
            decodeByteArray.height,
            matrix,
            true
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //CameraX.unbindAll()
        ProcessCameraProvider.getInstance(this).get().unbindAll()
    }


    companion object{
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}
