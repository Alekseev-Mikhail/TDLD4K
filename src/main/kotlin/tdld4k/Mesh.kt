package tdld4k

import org.lwjgl.opengl.GL46
import org.lwjgl.opengl.GL46.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL46.GL_FLOAT
import org.lwjgl.opengl.GL46.GL_STATIC_DRAW
import org.lwjgl.opengl.GL46.glBindBuffer
import org.lwjgl.opengl.GL46.glBindVertexArray
import org.lwjgl.opengl.GL46.glBufferData
import org.lwjgl.opengl.GL46.glDeleteVertexArrays
import org.lwjgl.opengl.GL46.glEnableVertexAttribArray
import org.lwjgl.opengl.GL46.glGenBuffers
import org.lwjgl.opengl.GL46.glGenVertexArrays
import org.lwjgl.opengl.GL46.glVertexAttribPointer
import org.lwjgl.system.MemoryStack

class Mesh(positions: FloatArray, numVertices: Int) {
    var numVertices = 0
    var vaoId = 0
    private var vboIdList = mutableListOf<Int>()

    init {
        MemoryStack.stackPush().use { stack ->
            this.numVertices = numVertices
            vboIdList = ArrayList()
            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)

            // Positions VBO
            val vboId: Int = glGenBuffers()
            vboIdList.add(vboId)
            val positionsBuffer = stack.callocFloat(positions.size)
            positionsBuffer.put(0, positions)
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW)
            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        }
    }

    fun cleanup() {
        vboIdList.forEach(GL46::glDeleteBuffers)
        glDeleteVertexArrays(vaoId)
    }
}
