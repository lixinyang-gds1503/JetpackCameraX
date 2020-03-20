package cn.lxyhome.jetpackcamerax.activity

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import cn.lxyhome.jetpackcamerax.R
import kotlinx.android.synthetic.main.activity_preview.*
import java.io.ByteArrayOutputStream

@SuppressLint("RestrictedApi")
class PreviewActivity : AppCompatActivity() {

    private val imageCapture: ImageCapture by lazy {
        ImageCapture.Builder().setBufferFormat(ImageFormat.YUV_420_888).build()
    }
    private val imageAnalysis: ImageAnalysis by lazy {
      ImageAnalysis.Builder().build()
  }
    private val preview: Preview by lazy {
      //拍摄预览的配置config
      //  val config = PreviewConfig.Builder().setLensFacing(CameraX.LensFacing.BACK)
    //    config.setTargetAspectRatio(Rational(1080 ,2048))
       // Preview(config.build())
       Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).build()
  }

    private var TargetRotation:Int = 0

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
       // val cameraXConfig = Camera2Config.defaultConfig()
        //cameraXConfig.
        val future = ProcessCameraProvider.getInstance(this)

        future.addListener(Runnable{
            val cameraProvider = future.get()
            cameraProvider.bindToLifecycle(this as LifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA,preview,imageAnalysis,imageCapture)
        }, ContextCompat.getMainExecutor(this))


        preview.setSurfaceProvider(tv_preview.previewSurfaceProvider)
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this),ImageAnalysis.Analyzer {
            Log.i("PreviewActivity",it.cropRect.toString() + it.format + it.height + it.image.toString()+ it.planes)
            TargetRotation = it.imageInfo?.rotationDegrees;
            it.close()
        })

        btn_kacha.setOnClickListener {
            val fileName = System.currentTimeMillis().toString()
            val fileFormat = ".jpg"
            val imageFile = createTempFile(fileName, fileFormat)

            // Store captured image in the temporary file
            imageCapture.takePicture(ContextCompat.getMainExecutor(this), object :
                ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    ImageToBitmap(image)
                    super.onCaptureSuccess(image)
                }

                private fun ImageToBitmap(image: ImageProxy) {
                    val planes = image.planes

                    val yBuffer = planes[0].buffer // Y
                    val uBuffer = planes[1].buffer // U
                    val vBuffer = planes[2].buffer // V

                    val ySize = yBuffer.remaining()
                    val uSize = uBuffer.remaining()
                    val vSize = vBuffer.remaining()

                    val nv21 = ByteArray(ySize + uSize + vSize)

                    //U and V are swapped
                    yBuffer.get(nv21, 0, ySize)
                    vBuffer.get(nv21, ySize, vSize)
                    uBuffer.get(nv21, ySize + vSize, uSize)


                    val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
                    val out = ByteArrayOutputStream()
                    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
                    val imageBytes = out.toByteArray()

                    val matrix = Matrix()
                    matrix.postRotate(TargetRotation.toFloat())
                    val decodeByteArray =
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    val bitmap = Bitmap.createBitmap(
                        decodeByteArray, 0, 0, decodeByteArray.width, decodeByteArray.height, matrix,true)

                    img_preview.setImageBitmap(bitmap)


                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                }
            })
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

    override fun onDestroy() {
        super.onDestroy()
        //CameraX.unbindAll()
        ProcessCameraProvider.getInstance(this).get().unbindAll()
    }

}
