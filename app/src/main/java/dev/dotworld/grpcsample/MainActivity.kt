package dev.dotworld.grpcsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.dotworld.grpcsample.databinding.ActivityMainBinding
import dev.dotworld.grpcsample.helloworld.GreeterGrpc
import dev.dotworld.grpcsample.helloworld.HelloRequest
import io.grpc.ManagedChannelBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        try {
            val channel = ManagedChannelBuilder.forAddress("127.0.0.1", 8080)
                .usePlaintext()
                .build()

            val stub = GreeterGrpc.newBlockingStub(channel)
            val request = HelloRequest.newBuilder().setName("Wow! It's working!").build()
            val reply = stub.sayHello(request)
            binding.textView.text = reply.message ?: "Error"
            channel.shutdownNow()
        } catch (e: Exception) {
            binding.textView.text = "Error : ${e.message}"
        }
    }
}