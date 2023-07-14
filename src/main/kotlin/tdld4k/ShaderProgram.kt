package tdld4k

import org.lwjgl.opengl.GL46
import org.lwjgl.opengl.GL46.GL_COMPILE_STATUS
import org.lwjgl.opengl.GL46.GL_LINK_STATUS
import org.lwjgl.opengl.GL46.GL_VALIDATE_STATUS
import org.lwjgl.opengl.GL46.glAttachShader
import org.lwjgl.opengl.GL46.glCompileShader
import org.lwjgl.opengl.GL46.glCreateProgram
import org.lwjgl.opengl.GL46.glCreateShader
import org.lwjgl.opengl.GL46.glDeleteProgram
import org.lwjgl.opengl.GL46.glDetachShader
import org.lwjgl.opengl.GL46.glGetProgramInfoLog
import org.lwjgl.opengl.GL46.glGetProgrami
import org.lwjgl.opengl.GL46.glGetShaderInfoLog
import org.lwjgl.opengl.GL46.glGetShaderi
import org.lwjgl.opengl.GL46.glLinkProgram
import org.lwjgl.opengl.GL46.glShaderSource
import org.lwjgl.opengl.GL46.glUseProgram
import org.lwjgl.opengl.GL46.glValidateProgram

class ShaderProgram(shaderModuleDataList: List<ShaderModuleData>) {
    private val programId = glCreateProgram()

    init {
        if (programId == 0) {
            throw RuntimeException("Could not create Shader")
        }
        val shaderModules = mutableListOf<Int>()
        shaderModuleDataList.forEach { shaderModuleData ->
            shaderModules.add(
                createShader(Client.getShader(shaderModuleData.shaderFile), shaderModuleData.shaderType),
            )
        }
        link(shaderModules)
    }

    fun bind() {
        glUseProgram(programId)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            glDeleteProgram(programId)
        }
    }

    private fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw RuntimeException("Error creating shader. Type: $shaderType")
        }

        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
//            throw RuntimeException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024))
        }

        glAttachShader(programId, shaderId)

        return shaderId
    }

    private fun link(shaderModules: List<Int>) {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
//            throw RuntimeException("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024))
        }
        shaderModules.forEach { shaderModule -> glDetachShader(programId, shaderModule) }
        shaderModules.forEach(GL46::glDeleteShader)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun validate() {
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw RuntimeException("Error validating Shader code: " + glGetProgramInfoLog(programId, 1024))
        }
    }
}

data class ShaderModuleData(val shaderFile: String, val shaderType: Int)
